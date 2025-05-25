package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.CustomUserDetails;
import com.graduate.be_txnd_fanzone.dto.orderTicket.ListOrderTicketRequest;
import com.graduate.be_txnd_fanzone.dto.orderTicket.OrderTicketInfoRequest;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.model.OrderTicket;
import com.graduate.be_txnd_fanzone.model.OrderTicketDetail;
import com.graduate.be_txnd_fanzone.model.Ticket;
import com.graduate.be_txnd_fanzone.model.User;
import com.graduate.be_txnd_fanzone.repository.OrderTicketDetailRepository;
import com.graduate.be_txnd_fanzone.repository.OrderTicketRepository;
import com.graduate.be_txnd_fanzone.repository.TicketRepository;
import com.graduate.be_txnd_fanzone.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderTicketService {

    OrderTicketRepository orderTicketRepository;
    OrderTicketDetailRepository orderTicketDetailRepository;
    TicketRepository ticketRepository;
    UserRepository userRepository;
    VnpayService vnpayService;
    EmailService emailService;

    @Transactional
    public String orderTicket(ListOrderTicketRequest request, HttpServletRequest httpRequest) {
        long totalPrice = 0;
        //get user login
        CustomUserDetails userLogin = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUserIdAndDeleteFlagIsFalse(userLogin.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //create order ticket record
        OrderTicket orderTicket = new OrderTicket();
        orderTicket.setUser(user);
        orderTicket.setStatus("created");
        orderTicketRepository.save(orderTicket);

        List<OrderTicketDetail> orderTicketDetails = new ArrayList<>();
        List<OrderTicketInfoRequest> listTickets = request.getListOrderTickets();

        for (OrderTicketInfoRequest orderTicketInfoRequest : listTickets) {
            Ticket ticket = ticketRepository.findById(orderTicketInfoRequest.getTicketId())
                    .orElseThrow(() -> new CustomException(ErrorCode.TICKET_NOT_FOUND));

            int numberOfTicket = orderTicketInfoRequest.getQuantity();
            int quantity = ticket.getQuantity();
            if (numberOfTicket > quantity) {
                throw new CustomException(ErrorCode.NOT_ENOUGH_TICKET, ticket.getPosition(), quantity);
            }

            ticket.setQuantity(ticket.getQuantity() - numberOfTicket);
            ticketRepository.save(ticket);
            totalPrice += ticket.getPrice() * numberOfTicket;

            for (int i = 1; i <= numberOfTicket; i++) {
                //create order ticket detail
                OrderTicketDetail orderTicketDetail = new OrderTicketDetail();
                orderTicketDetail.setTicket(ticket);
                orderTicketDetail.setOrderTicket(orderTicket);
                orderTicketDetailRepository.save(orderTicketDetail);

                orderTicketDetails.add(orderTicketDetail);
            }
        }
        //set list order ticket detail or order ticket
        orderTicket.setOrderTicketDetails(orderTicketDetails);
        orderTicketRepository.save(orderTicket);

        //payment
        return vnpayService.createPaymentByVNPay(httpRequest, totalPrice,orderTicket.getOrderTicketId());
    }


    public void paymentSuccess (Long orderTicketId) throws Exception {
        OrderTicket orderTicket = orderTicketRepository.findByOrderTicketIdAndDeleteFlagIsFalse(orderTicketId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_TICKET_NOT_FOUND));
        orderTicket.setStatus("success");
        orderTicketRepository.save(orderTicket);

        //send email
        String fullName =orderTicket.getUser().getFirstName() + " "+ orderTicket.getUser().getLastName();
        LocalDateTime createDate = orderTicket.getCreateDate();
        List<String> orderTicketDetailIds = orderTicketDetailRepository.getOrderTicketDetailIdsByOrderTicketId(orderTicketId);
        String email = orderTicket.getUser().getEmailAddress();

        emailService.sendOrderTicketEmail(email, fullName, createDate, orderTicketDetailIds);
    }

    public void paymentError (Long orderTicketId) {
        OrderTicket orderTicket = orderTicketRepository.findByOrderTicketIdAndDeleteFlagIsFalse(orderTicketId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_TICKET_NOT_FOUND));
        orderTicket.setStatus("fail");
        orderTicketRepository.save(orderTicket);
    }
}
