package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "report_post")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportPost extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    Long reportId;

    @Column(name = "reason")
    String reason;

    //0: pending, 1: reject, 2: resolved
    @Column(name = "status")
    Byte status;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;
}
