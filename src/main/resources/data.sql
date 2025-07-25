/* rolesテーブル */
INSERT IGNORE INTO roles (role_id, name) VALUES (1, 'ROLE_GENERAL');
INSERT IGNORE INTO roles (role_id, name) VALUES (2, 'ROLE_ADMIN');

/* usersテーブル */
-- ユーザー1: 一般ユーザー
INSERT IGNORE INTO users (name, email, password, phone_number, profile_image, car_life_year, gender, introduction, hobbies, enabled, role_id) VALUES ('山田太郎', 'taro.yamada@example.com', '$2a$12$S7iHdT4OCEloMkQ6Sshff.nW7iMn6aexz0FlkPfNoo/vZKFXFzpoe', '09012345678', '/images/taroyamada.png', 5, 0, 'ドライブと車いじりが趣味です！よろしくお願いいたします！', 'ドライブ,車いじり,写真', TRUE, 1);

-- ユーザー2: 管理者ユーザー
INSERT IGNORE INTO users (name, email, password, phone_number, profile_image, car_life_year, gender, introduction, hobbies, enabled, role_id) VALUES ('佐藤花子', 'hanako.sato@example.com', '$2a$12$2M8BpPeVsmEcMz1DiOOS9eLkhkU/yZdFhGQ1XNm3Rx0nZgbgcyqIW', '08098765432', 'https://example.com/profile/hanako.png', 10, 1, '車のイベント企画が好きです。', 'イベント企画,カフェ巡り', TRUE, 2);

-- ユーザー3: 
INSERT IGNORE INTO users (name, email, password, phone_number, profile_image, car_life_year, gender, introduction, hobbies, enabled, role_id) VALUES ('田中一郎', 'ichiro.tanaka@example.com', '$2a$10$gNEZcQwUT3sRFEgHL8JKVex2s0aFk0OfRbGswH1zxzEyO2DuXPBlS', '09012345678', '/images/itiroyamada.png', 12, 0, '週末は愛車でドライブに出かけるのが楽しみです。同じVIPカー乗り同士での楽しく交流をしたいと思ってます！', 'ドライブ,洗車,ラジコンカー」', TRUE, 1);

-- ユーザー4:
INSERT IGNORE INTO users (name, email, password, phone_number, enabled, role_id) VALUES ('鈴木次郎', 'jiro.suzuki@example.com', 'suzukiabc', NULL, FALSE, 1);

-- ユーザー5:
INSERT IGNORE INTO users (name, email, password, phone_number, profile_image, car_life_year, gender, introduction, hobbies, enabled, role_id) VALUES ('佐藤美智子', 'michiko.sato@example.com', '$2a$10$3EcsYpjcH3aOGfHFLP5nF.5YxRZTJ23E4d6Ztv6w3Awf3rK3UdwH2', '08023456789', '/images/users/michiko.jpg',  8,  1,  '車は日常の相棒。のんびりと景色を楽しむドライブが大好きです。',  'カフェ巡り、ドライブ、写真撮影、料理',  TRUE,  1);

/* postsテーブル */
INSERT INTO posts (
  user_id, caption, media_url, media_type, is_story, expires_at, created_at, updated_at, deleted_at, view_count, like_count, location_name, latitude, longitude
) VALUES
(1, '今日はお気に入りの峠を軽く流してきた。夕方の空気が気持ちよかった。', '/images/photo1.jpg', 'image', false, NULL, '2025-07-06 22:57:48', '2025-07-07 06:57:48', NULL, 183, 1, '峠道', 35.169, 136.906),
(1, '今日は洗車に行ってきた。手洗いの方がいいけど、洗車機は楽でいいね！', '/images/photo2.webp', 'image', false, NULL, '2025-06-25 22:57:48', '2025-06-26 05:57:48', NULL, 80, 44, '洗車', 35.165, 136.974),
(1, '海沿いの道を走ってきたよ。潮風が最高に気持ちよかった〜。', '/images/photo3.webp', 'image', false, NULL, '2025-07-12 22:57:48', '2025-07-13 00:57:48', NULL, 167, 17, '海周辺', 35.158, 136.905),
(1, '今日は友達とUFOラインまでドライブしてきた。一人もいいけどふたりでドライブも楽しいな！', '/images/photo4.webp', 'image', false, NULL, '2025-07-05 22:57:48', '2025-07-06 08:57:48', NULL, 50, 30, '峠道', 35.17, 137.047),
(1, '朝活ドライブして、誰もいない展望台でコーヒータイム。', '/images/photo5.jpg', 'image', false, NULL, '2025-06-28 22:57:48', '2025-06-29 04:57:48', NULL, 86, 31, '山道の展望台', 35.158, 136.905),
(1, '日帰りで山方面へドライブ。途中の景色がめちゃくちゃ良かった！', '/images/photo6.jpg', 'image', false, NULL, '2025-06-29 22:57:48', '2025-06-30 03:57:48', NULL, 155, 33, '峠道', 35.158, 136.905),
(1, '仕事終わりに近くの山へ夜景を見に軽くドライブへ！この静かな時間が好き。', '/images/photo7.webp', 'image', false, NULL, '2025-07-21 22:57:48', '2025-07-22 01:57:48', NULL, 138, 10, '峠道', 35.17, 137.047),
(1, 'ちょっとだけ夜の首都高をひとっ走り。風が気持ちよかった！', '/images/photo8.webp', 'image', false, NULL, '2025-07-22 22:57:48', '2025-07-23 05:57:48', NULL, 200, 20, '高速道路', 35.158, 136.905),
(1, '角島大橋へ走りに行ってきた！やっぱりいい景色だった～。', '/images/photo9.webp', 'image', false, NULL, '2025-07-05 22:57:48', '2025-07-06 03:57:48', NULL, 60, 40, '角島大橋', 35.169, 136.906),
(1, '今日は琵琶湖まで行ってきた！早朝の琵琶湖の景色は幻想的だな。', '/images/photo10.jpg', 'image', false, NULL, '2025-07-24 22:57:48', '2025-07-25 03:57:48', NULL, 135, 19, '琵琶湖', 35.17, 137.047),
(1, 'ガソリン満タンにして、どこまでも走れそうな気分だった。', '/images/photo11.jpg', 'image', false, NULL, '2025-07-19 22:57:48', '2025-07-20 04:57:48', NULL, 137, 12, 'ガソリンスタンド', 35.169, 136.906),
(1, '家から1時間の峠道へ。久しぶりに走ったけどやっぱり楽しい！', '/images/photo12.jpg', 'image', false, NULL, '2025-07-13 22:57:48', '2025-07-14 02:57:48', NULL, 72, 21, '峠道', 35.165, 136.974),
(1, 'カフェ巡りドライブ☕静かな森の中にある店で癒された〜。', '/images/photo13.webp', 'image', false, NULL, '2025-06-28 22:57:48', '2025-06-29 10:57:48', NULL, 152, 43, 'カフェ', 35.165, 136.974),
(1, 'ライトアップされた桜並木を見に行ってきた！きれいすぎて感動した～', '/images/photo14.jpg', 'image', false, NULL, '2025-06-26 22:57:48', '2025-06-27 04:57:48', NULL, 119, 37, '公園', 35.158, 136.905),
(1, '朝日を見に海辺までドライブ。寒かったけど最高の景色だった！', '/images/photo15.webp', 'image', false, NULL, '2025-07-23 22:57:48', '2025-07-24 09:57:48', NULL, 17, 35, '海周辺', 35.158, 136.905),
(1, '人生で初めてアクアに乗った。普段のスポーツカーと違ってやっぱり静かだな！', '/images/photo16.jpg', 'image', false, NULL, '2025-07-01 22:57:48', '2025-07-02 02:57:48', NULL, 187, 9, 'パーキングエリア', 35.158, 136.905),
(1, '今日はライトアップされた来栖海峡大橋を見に行ってきた！夕焼けをバックにしていてとてもきれいだった！', '/images/photo17.webp', 'image', false, NULL, '2025-07-22 22:57:48', '2025-07-23 00:57:48', NULL, 36, 29, '来栖海峡', 35.130, 136.907),
(1, '少し眠たいのでパーキングエリアで一休み。長距離移動はやっぱり疲れるな～(;´∀｀)', '/images/photo18.webp', 'image', false, NULL, '2025-07-05 22:57:48', '2025-07-06 00:57:48', NULL, 114, 18, 'パーキングエリア', 35.130, 136.907),
(1, '峠の頂上から湖と街並みを眺めてる。住み慣れてる町を上から見ると、普段は気づかないところに気が付けるんだよね。', '/images/photo19.webp', 'image', false, NULL, '2025-06-29 22:57:48', '2025-06-30 09:57:48', NULL, 164, 42, '山道の展望台', 35.17, 137.047),
(1, '紅葉を身に行きつけの山へ！真っ赤な紅葉がきれいだった。', '/images/photo20.jpg', 'image', false, NULL, '2025-07-09 22:57:48', '2025-07-09 23:57:48', NULL, 47, 38, '山道の展望台', 35.185, 136.899);