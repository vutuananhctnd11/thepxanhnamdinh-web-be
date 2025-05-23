package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    Long userId;

    @Column(name = "username", unique = true, nullable = false)
    String username;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;

    @Column(name = "email_address", unique = true, nullable = false)
    String emailAddress;

    @Column(name = "address")
    String address;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "avatar")
    String avatar;

    @ManyToOne
    @JoinColumn(name = "role")
    Role role;

    @OneToMany(mappedBy = "user")
    List<OrderTicket> orderTickets;

    @OneToMany(mappedBy = "user")
    List<Post> posts;

    @OneToMany(mappedBy = "user")
    List<GroupMember> groupMembers;

    @OneToMany(mappedBy = "sender")
    List<Friend> sentFriendRequests;

    @OneToMany(mappedBy = "receiver")
    List<Friend> receivedFriendRequests;

    @OneToMany(mappedBy = "user")
    List<Comment> comments;

    @OneToMany(mappedBy = "user")
    List<Reaction> reactions;

    @OneToMany(mappedBy = "user")
    List<ReportPost> reportPosts;

}
