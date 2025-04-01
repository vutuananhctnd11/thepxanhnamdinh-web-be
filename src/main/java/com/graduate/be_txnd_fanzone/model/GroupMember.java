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
    String groupMemberId;

    @Column(name = "status")
    String status;

    @Column(name = "member_role")
    String memberRole;


}
