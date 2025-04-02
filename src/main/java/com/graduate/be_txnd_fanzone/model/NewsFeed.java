package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.CustomLog;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "news_feed")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsFeed extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_feed_id", nullable = false)
    Long newsFeedId;

    @Column(name = "content")
    String content;

    @Column(name = "type")
    String type;

    @Column(name = "status")
    String status;

    @Column(name = "images")
    String images;

    @Column(name = "videos")
    String videos;
}
