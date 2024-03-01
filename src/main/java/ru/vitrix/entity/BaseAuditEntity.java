package ru.vitrix.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@ToString(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditEntity extends BaseEntity implements Serializable {

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
}
