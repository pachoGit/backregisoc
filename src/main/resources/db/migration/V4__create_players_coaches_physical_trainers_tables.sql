CREATE TABLE players (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    deleted_at DATETIME NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    document_number VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    date_of_birth DATE NOT NULL,
    position VARCHAR(255) NULL,
    photo_url TEXT NULL,
    document_front_url TEXT NULL,
    document_back_url TEXT NULL,
    club_id BIGINT NOT NULL,
    CONSTRAINT fk_players_club FOREIGN KEY (club_id) REFERENCES clubs(id)
);

CREATE TABLE coaches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    deleted_at DATETIME NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    document_number VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    date_of_birth DATE NOT NULL,
    photo_url TEXT NULL,
    document_front_url TEXT NULL,
    document_back_url TEXT NULL,
    club_id BIGINT NOT NULL,
    CONSTRAINT fk_coaches_club FOREIGN KEY (club_id) REFERENCES clubs(id)
);

CREATE TABLE physical_trainers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    deleted_at DATETIME NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    document_number VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    date_of_birth DATE NOT NULL,
    photo_url TEXT NULL,
    document_front_url TEXT NULL,
    document_back_url TEXT NULL,
    club_id BIGINT NOT NULL,
    CONSTRAINT fk_physical_trainers_club FOREIGN KEY (club_id) REFERENCES clubs(id)
);
