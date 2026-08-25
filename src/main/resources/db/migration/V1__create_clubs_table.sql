CREATE TABLE clubs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    deleted_at DATETIME NULL,
    name VARCHAR(255) NOT NULL,
    founded_year INT NULL,
    crest_url TEXT NULL,
    description TEXT NULL,
    created_by VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);
