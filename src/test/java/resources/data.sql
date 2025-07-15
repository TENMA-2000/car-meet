/* rolesテーブル */
INSERT IGNORE INTO roles (role_id, name) VALUES (1, 'ROLE_GENERAL');
INSERT IGNORE INTO roles (role_id, name) VALUES (2, 'ROLE_ADMIN');

DELETE FROM users;
DELETE FROM roles;

/* usersテーブル */
-- ユーザー1: 一般ユーザー
INSERT IGNORE INTO users (name, email, password, phone_number, profile_image, car_life_year, gender, introduction, hobbies, enabled, role_id) VALUES ('山田太郎', 'taro.yamada@example.com', 'password123', '09012345678', 'https://example.com/profile/taro.jpg', 5, 0, 'ドライブと洗車が趣味です。', 'ドライブ,洗車,写真', TRUE, 1);

-- ユーザー2: 管理者ユーザー
INSERT IGNORE INTO users (name, email, password, phone_number, profile_image, car_life_year, gender, introduction, hobbies, enabled, role_id) VALUES ('佐藤花子', 'hanako.sato@example.com', 'adminpass456', '08098765432', 'https://example.com/profile/hanako.png', 10, 1, '車のイベント企画が好きです。', 'イベント企画,カフェ巡り', TRUE, 2);

-- ユーザー3: 
INSERT IGNORE INTO users (name, email, password, phone_number, profile_image, car_life_year, gender, enabled, role_id) VALUES ('田中一郎', 'ichiro.tanaka@example.com', 'tanaka789', NULL, NULL, NULL, NULL, TRUE, 1);

-- ユーザー4:
INSERT IGNORE INTO users (name, email, password, phone_number, enabled, role_id) VALUES ('鈴木次郎', 'jiro.suzuki@example.com', 'suzukiabc', NULL, FALSE, 1);