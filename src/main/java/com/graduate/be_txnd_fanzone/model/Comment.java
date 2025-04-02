package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "comment")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    Long commentId;

    @Column(name = "content")
    String content;

}
