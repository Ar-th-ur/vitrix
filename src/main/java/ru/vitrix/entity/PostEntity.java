package ru.vitrix.entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.*;

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

    @OneToOne(cascade = {REMOVE, PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private ImageEntity image;

    @ManyToOne(cascade = REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity owner;
}
