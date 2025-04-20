package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "group_member")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupMember extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_member_id", nullable = false)
    Long groupMemberId;

    //0:pending, 1: approve
    @Column(name = "approved", columnDefinition = "boolean")
    Boolean approved;

    //0: member, 1: moderator, 2:admin_group
    @Column(name = "member_role")
    Byte memberRole;

    @ManyToOne
    @JoinColumn(name = "group_id")
    Group group;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;


}
