package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseModel {

    @Column(name = "create_date")
    @CreatedDate
    LocalDateTime createDate;

    @Column(name = "create_by")
    @CreatedBy
    Long createBy;

    @Column(name = "update_date")
    @LastModifiedDate
    LocalDateTime updateDate;

    @Column(name = "update_by")
    @LastModifiedBy
    Long updateBy;

    @Column(name = "delete_flag")
    Boolean deleteFlag = false;

}
