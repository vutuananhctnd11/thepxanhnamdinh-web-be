package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Entity
@Table(name = "social_group")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Group extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false)
    Long groupId;

    @Column(name = "group_name")
    String groupName;

    @Column(name = "description")
    String description;

    // 0: private, 1: public, 2:fandom
    @Column(name = "group_type")
    Byte type;

    @Column(name = "approved", columnDefinition = "boolean")
    Boolean approved;

    @Column(name = "censor_post", columnDefinition = "boolean")
    Boolean censorPost;

    @Column(name = "censor_member", columnDefinition = "boolean")
    Boolean censorMember;

    @Column(name = "avatar_image")
    String avatarImage;

    @OneToMany(mappedBy = "group")
    List<Post> posts;

    @OneToMany(mappedBy = "group")
    List<GroupMember> groupMembers;


}
