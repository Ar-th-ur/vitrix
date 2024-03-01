CREATE TABLE images
(
    id           BIGSERIAL NOT NULL UNIQUE,
    file_name    VARCHAR(256),
    file_size    BIGINT,
    content_type VARCHAR(32),
    data         BYTEA,
    created_at   TIMESTAMP,
    modified_at  TIMESTAMP
);

CREATE TABLE users
(
    id                BIGSERIAL     NOT NULL UNIQUE,
    image_id          BIGINT        NOT NULL,
    username          VARCHAR(64)   NOT NULL UNIQUE,
    password          VARCHAR(2048) NOT NULL,
    is_account_locked BOOLEAN       NOT NULL DEFAULT FALSE,
    role              VARCHAR(20)   NOT NULL,
    created_at        TIMESTAMP,
    modified_at       TIMESTAMP,

    CONSTRAINT fk_image_id FOREIGN KEY (image_id) REFERENCES images (id)
);

CREATE TABLE posts
(
    id          BIGSERIAL   NOT NULL UNIQUE,
    image_id    BIGINT      NOT NULL,
    user_id     BIGINT      NOT NULL,
    title       VARCHAR(30) NOT NULL,
    created_at  TIMESTAMP,
    modified_at TIMESTAMP,

    CONSTRAINT fk_image_id FOREIGN KEY (image_id) REFERENCES images (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

