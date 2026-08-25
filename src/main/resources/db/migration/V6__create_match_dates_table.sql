CREATE TABLE match_dates (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    deleted_at DATETIME NULL,
    event_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    status VARCHAR(255) NOT NULL DEFAULT 'UPCOMING',
    CONSTRAINT fk_match_dates_event FOREIGN KEY (event_id) REFERENCES events(id)
);
