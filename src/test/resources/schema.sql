CREATE TABLE IF NOT EXISTS roles (
	role_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
	user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	email VARCHAR(255) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL,
	phone_number VARCHAR(50) /*MVPでは未使用だが、将来的に使用するカラム*/,
	profile_image TEXT,
	car_life_year INT,
	gender TINYINT,
	introduction VARCHAR(255),
	hobbies VARCHAR(255),
	role_id INT NOT NULL,
	enabled BOOLEAN NOT NULL,
	created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	FOREIGN KEY (role_id) REFERENCES roles (role_id)
);

CREATE TABLE IF NOT EXISTS posts (
	post_id INT AUTO_INCREMENT PRIMARY KEY,
	user_id INT NOT NULL,
	caption TEXT,
	media_url VARCHAR(255),
	media_type VARCHAR(50),
	is_story BOOLEAN,
	expires_at DATETIME,
	created_at DATETIME,
	updated_at DATETIME,
	deleted_at DATETIME,
	view_count INT DEFAULT 0,
	like_count INT DEFAULT 0,
	location_name VARCHAR(255),
	latitude DOUBLE,
	longitude DOUBLE,
	FOREIGN KEY (user_id) REFERENCES user(user_id)
);