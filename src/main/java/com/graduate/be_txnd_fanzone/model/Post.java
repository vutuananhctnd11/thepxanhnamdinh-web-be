package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Entity
@Table(name = "post")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    Long postId;

    @Column(name = "content")
    String content;

    //0: in personal page, 1: in group
    @Column(name = "type")
    Byte type;

    //0: friend, 1: public
    @Column(name = "status")
    Byte status;

    @Column(name = "report_flag", columnDefinition = "boolean")
    Boolean reportFlag;

    @Column(name = "censor_flag", columnDefinition = "boolean")
    Boolean censorFlag;

    @OneToMany(mappedBy = "post")
    List<Media> medias;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    Group group;
}
