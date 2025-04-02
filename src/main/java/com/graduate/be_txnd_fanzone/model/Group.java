package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

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

    @Column(name = "group_type")
    String groupType;

    @Column(name = "avatar_image")
    String avatarImage;

}
