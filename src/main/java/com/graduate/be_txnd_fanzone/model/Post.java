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

    @Column(name = "type")
    Byte type;

    @Column(name = "status")
    Byte status;

    @Column(name = "report_flag")
    Boolean reportFlag;

    @Column(name = "censor_flag")
    Boolean censorFlag;

    @OneToMany(mappedBy = "post")
    List<Media> medias;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
