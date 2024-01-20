CREATE TABLE IF NOT EXISTS user_en (
    id BINARY(16) PRIMARY KEY,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    birth_date DATE,
    phone_number VARCHAR(255),
    profile_photo_url VARCHAR(255),
    role VARCHAR(255)
);
