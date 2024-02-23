package ru.vitrix.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.vitrix.entity.base.BaseAuditEntity;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"image"})
@Table(name = "posts")
public class PostEntity extends BaseAuditEntity {

    @Column(name = "title")
    private String title;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private ImageEntity image;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity owner;
}