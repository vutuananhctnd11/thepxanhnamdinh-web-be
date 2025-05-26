package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.PageableListResponse;
import com.graduate.be_txnd_fanzone.dto.search.SearchRequest;
import com.graduate.be_txnd_fanzone.dto.search.SearchUserResponse;
import com.graduate.be_txnd_fanzone.dto.user.*;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.enums.RoleEnums;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.UserMapper;
import com.graduate.be_txnd_fanzone.model.Role;
import com.graduate.be_txnd_fanzone.model.User;
import com.graduate.be_txnd_fanzone.repository.FriendRepository;
import com.graduate.be_txnd_fanzone.repository.PostRepository;
import com.graduate.be_txnd_fanzone.repository.RoleRepository;
import com.graduate.be_txnd_fanzone.repository.UserRepository;
import com.graduate.be_txnd_fanzone.util.JwtUtil;
import com.graduate.be_txnd_fanzone.util.OtpRandomUtil;
import com.graduate.be_txnd_fanzone.util.RandomPasswordUtil;
import com.graduate.be_txnd_fanzone.util.SecurityUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    JwtUtil jwtUtil;
    EmailService emailService;
    SecurityUtil securityUtil;
    FriendRepository friendRepository;
    PostRepository postRepository;

    public CreateUserResponse createUser(@Valid @RequestBody CreateUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) throw new CustomException(ErrorCode.USER_EXISTED);
        if (userRepository.existsByEmailAddress(request.getEmailAddress()))
            throw new CustomException(ErrorCode.EMAIL_EXISTED);
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByRoleName(RoleEnums.USER.name()).orElseThrow(() ->
                new CustomException(ErrorCode.ROLE_NOT_FOUND));
        user.setRole(role);
        userRepository.save(user);
        CreateUserResponse response = userMapper.toCreateUserResponse(user);
        response.setRole(user.getRole().getRoleName());
        return response;
    }

    @Transactional
    public UpdateUserResponse updateUser(Long userId, @Valid @RequestBody UpdateUserRequest request) {
        String usernameLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserIdAndDeleteFlagIsFalse(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!usernameLogin.equals(user.getUsername())) throw new CustomException(ErrorCode.UNAUTHENTICATED);
        user = userMapper.updateUser(user, request);

        if (user.getAvatar() != null && request.getAvatar().isEmpty()) {
            user.setAvatar(null);
        }

        if (request.getPassword() != null) user.setPassword(passwordEncoder.encode(request.getPassword()));
        UpdateUserResponse response = userMapper.toUpdateUserResponse(user);
        response.setRole(user.getRole().getRoleName());
        return response;
    }

    @Transactional
    public void softDeleteUser(Long userId) {
        User user = userRepository.findByUserIdAndDeleteFlagIsFalse(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));
        user.setDeleteFlag(true);
        userRepository.save(user);

        Long userLoginId = securityUtil.getCurrentUserId();
        User userLogin = userRepository.findByUserIdAndDeleteFlagIsFalse(userLoginId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        String userDelete = userLogin.getUsername();
        String userFullName = user.getUsername();
        String emailSupport = userLogin.getEmailAddress();
        String sendTo = user.getEmailAddress();
        emailService.sendDeleteUserEmail(sendTo, userFullName, userDelete, emailSupport);
    }

    public UserInfoResponse getUserLoginInfo() {
        String usernameLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User userLogin = userRepository.findByUsernameAndDeleteFlagIsFalse(usernameLogin).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserInfoResponse(userLogin);
    }

    public AdminInfoResponse getAdminLoginInfo() {
        String usernameLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User userLogin = userRepository.findByUsernameAndDeleteFlagIsFalse(usernameLogin).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toAdminInfoResponse(userLogin);
    }

    public void forgotPassword(String identifier, HttpServletResponse response) {
        User user = userRepository.findByUsernameAndDeleteFlagIsFalse(identifier)
                .orElseGet(() -> userRepository.findByEmailAddressAndDeleteFlagIsFalse(identifier)
                        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));

        String otp = OtpRandomUtil.generateOtp(8);
        String token = jwtUtil.createForgotPasswordToken(user.getEmailAddress(), otp);
        Cookie cookie = new Cookie("otpToken", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 10);
        response.addCookie(cookie);

        emailService.sendForgotPasswordEmail(user.getEmailAddress(), user.getUsername(), otp);
    }

    public PersonalPageResponse getPersonalPage(Long userId) {
        User user = userRepository.findByUserIdAndDeleteFlagIsFalse(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Long userLoginId = securityUtil.getCurrentUserId();
        PersonalPageResponse response;

        if (!Objects.equals(userLoginId, user.getUserId())) {
            response = userMapper.toOtherUserPersonalPageResponse(user);
        } else {
            response = userMapper.toPersonalPageResponse(user);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        response.setDateOfBirth(user.getCreateDate().toLocalDate().format(formatter));
        response.setTotalFriends(friendRepository.countByReceiver_UserIdAndStatusAndDeleteFlagIsFalse(user.getUserId(), (byte) 1)
                + friendRepository.countBySender_UserIdAndStatusAndDeleteFlagIsFalse(user.getUserId(), (byte) 1));
        response.setTotalPosts(postRepository.countByUser_UserIdAndGroupIsNullAndDeleteFlagIsFalse(user.getUserId()));
        return response;
    }

    public PageableListResponse<SearchUserResponse> searchUsers(SearchRequest request) {
        PageableListResponse<SearchUserResponse> response = new PageableListResponse<>();
        Long userLoginId = securityUtil.getCurrentUserId();
        Pageable pageable = PageRequest.of(request.getPage()-1, request.getLimit());
        Page<User> userList = userRepository.searchUsers(request.getSearch(), userLoginId, pageable);

        //get list friend ids of user login
        List<Long> friendIds = friendRepository.getListFriendIds(userLoginId,(byte) 1);

        //get list sent friend requests of user login
        List<Long> listSentAddFriends = friendRepository.findAllBySender_UserIdAndStatusAndDeleteFlagIsFalse(userLoginId, (byte) 0)
                .stream().map(friend -> friend.getReceiver().getUserId()).toList();
        //get list of received friend requests  of user login
        List<Long> listReceiverAddFriends = friendRepository.findAllByReceiver_UserIdAndStatusAndDeleteFlagIsFalse(userLoginId, (byte) 0)
                .stream().map(friend -> friend.getSender().getUserId()).toList();

        //get list total friend by userId
        List<Long> searchFriendIds = userList.getContent().stream().map(User::getUserId).toList();
        Map<Long, Long> friendCountMap = mapListObjectToMap(friendRepository.countFriendsByListUserIds(searchFriendIds));

        List<SearchUserResponse> users = userList.stream().map(user -> {
            SearchUserResponse searchUserResponse = userMapper.toSearchUserResponse(user);
            searchUserResponse.setTotalFriends(friendCountMap.getOrDefault(user.getUserId(), 0L));
            searchUserResponse.setIsFriend(friendIds.contains(user.getUserId()));
            Long userId = user.getUserId();

            if (listSentAddFriends.contains(userId)) {
                searchUserResponse.setIsSentRequest(true);
                searchUserResponse.setIsSender(true);
            } else if (listReceiverAddFriends.contains(userId)) {
                searchUserResponse.setIsSentRequest(true);
                searchUserResponse.setIsSender(false);
            } else {
                searchUserResponse.setIsSentRequest(false);
            }
            return searchUserResponse;
        }).toList();
        response.setListResults(users);
        response.setPage(request.getPage());
        response.setLimit(request.getLimit());
        response.setTotalPage((long) userList.getTotalPages());
        return response;
    }

    // convert list object from repository to Map
    private Map<Long, Long> mapListObjectToMap(List<Object[]> listObjects) {
        return listObjects.stream().collect(Collectors.toMap(
                row -> (Long) row[0], row -> (Long) row[1]
        ));
    }

    public PageableListResponse<UserShortInfoResponse> getListUserByRole(int page, int limit, int role){
        Pageable pageable = PageRequest.of(page-1, limit);
        Page<User> userList = userRepository.findByRole_IdAndDeleteFlagIsFalseOrderByCreateDateDesc(role, pageable);
        return convertToUserShortInfo(userList, page, limit);
    }

    public PageableListResponse<UserShortInfoResponse> getListUser(int page, int limit){
        Pageable pageable = PageRequest.of(page-1, limit);
        Page<User> userList = userRepository.findByDeleteFlagIsFalseOrderByCreateDateDesc(pageable);
        return convertToUserShortInfo(userList, page, limit);
    }

    private PageableListResponse<UserShortInfoResponse> convertToUserShortInfo (Page<User> userList, int page, int limit){
        PageableListResponse<UserShortInfoResponse> response = new PageableListResponse<>();
        List<UserShortInfoResponse> userShortInfoResponseList =userList.getContent()
                .stream().map(userMapper::toUserShortInfoResponse).toList();
        response.setListResults(userShortInfoResponseList);
        response.setPage(page);
        response.setLimit(limit);
        response.setTotalPage((long) userList.getTotalPages());
        return response;
    }

    public UserManagementResponse getUserManagementInfo(Long userId){
        User user = userRepository.findByUserIdAndDeleteFlagIsFalse(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserManagementResponse(user);
    }

    public UserShortInfoResponse createUserByAdmin(AdminCreateUserRequest request){
        boolean isExistsEmail = userRepository.existsByEmailAddress(request.getEmailAddress());
        if(isExistsEmail){
            throw new CustomException(ErrorCode.EMAIL_EXISTED);
        }
        boolean isExistsName = userRepository.existsByUsername(request.getUsername());
        if(isExistsName){
            throw new CustomException(ErrorCode.USER_EXISTED);
        }

        String randomPassword = RandomPasswordUtil.generateRandomPassword(8);
        User user = userMapper.toUser(request);
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new CustomException(ErrorCode.ROLE_NOT_FOUND));
        user.setRole(role);

        String username = user.getUsername();
        String email = user.getEmailAddress();

        if (request.getPassword() == null) {
            user.setPassword(passwordEncoder.encode(randomPassword));
            emailService.sendCreateUserEmail(email, username, randomPassword);
        } else {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            emailService.sendCreateUserEmail(email, username, request.getPassword());
        }
        userRepository.save(user);
        return userMapper.toUserShortInfoResponse(user);
    }



}
