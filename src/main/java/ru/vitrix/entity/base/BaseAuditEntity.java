package ru.vitrix.entity.base;

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
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
@ToString(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditEntity extends BaseEntity implements Serializable {

    @CreatedDate
    @DateTimeFormat(pattern = "dd.MMM.yyyy")
    @Column(name = "created_at")
    private LocalDate createdAt;

    @LastModifiedDate
    @DateTimeFormat(pattern = "dd.MMM.yyyy")
    @Column(name = "modified_at")
    private LocalDate modifiedAt;
}
