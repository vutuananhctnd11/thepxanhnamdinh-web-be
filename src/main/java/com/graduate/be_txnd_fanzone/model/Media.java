package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "media")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Media{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id", nullable = false)
    Long mediaId;

    @Column(name = "link_cloud", columnDefinition = "TEXT")
    String linkCloud;

    @Column(name = "type")
    Byte type;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;
}
