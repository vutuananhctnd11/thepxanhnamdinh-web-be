package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "story")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Story extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_id", nullable = false)
    String storyId;

    @Column(name = "story_title")
    String storyTitle;

    @Column(name = "story_media")
    String storyMedia;
}
