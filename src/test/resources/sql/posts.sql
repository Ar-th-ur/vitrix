INSERT INTO users(id, username, password, is_account_locked, role)
VALUES (2, 'John', '1234', false, 'USER'),
       (3, 'Alex', '1234', true, 'USER');

INSERT INTO images(id, file_name, file_size, content_type, data)
VALUES (1, 'file', 10, 'image/png', '');

INSERT INTO posts(id, title, image_id, user_id)
VALUES (1, 'title', 1, 2),
       (2, 'another', 1, 2),
       (3, 'TITLE', 1, 2),
       (4, 'my tiTle', 1, 2),
       (5, 'My tiTle', 1, 3);