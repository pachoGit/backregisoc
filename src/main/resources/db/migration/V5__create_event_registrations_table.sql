CREATE TABLE event_registrations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    deleted_at DATETIME NULL,
    event_id BIGINT NOT NULL,
    club_id BIGINT NOT NULL,
    registered_at DATETIME NOT NULL,
    CONSTRAINT fk_event_registrations_event FOREIGN KEY (event_id) REFERENCES events(id),
    CONSTRAINT fk_event_registrations_club FOREIGN KEY (club_id) REFERENCES clubs(id)
);
