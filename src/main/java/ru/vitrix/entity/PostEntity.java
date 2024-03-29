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
@ToString(callSuper = true, exclude = {"image", "owner"})
@Table(name = "posts")
public class PostEntity extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToOne(cascade = {REMOVE, PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id", nullable = false)
    private ImageEntity image;

    @ManyToOne(cascade = REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner;
}
