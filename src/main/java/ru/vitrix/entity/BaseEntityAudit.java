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
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
@ToString(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntityAudit extends BaseEntity implements Serializable {

    @CreatedDate
    @DateTimeFormat(pattern = "dd.MMM.yyyy")
    @Column(name = "date_of_created")
    private LocalDate dateOfCreation;

    @LastModifiedDate
    @DateTimeFormat(pattern = "dd.MMM.yyyy")
    @Column(name = "date_of_last_modification")
    private LocalDate dateOfLastModification;
}
