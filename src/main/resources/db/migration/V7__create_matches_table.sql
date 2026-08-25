CREATE TABLE matches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    deleted_at DATETIME NULL,
    match_date_id BIGINT NOT NULL,
    home_club_id BIGINT NOT NULL,
    away_club_id BIGINT NOT NULL,
    scheduled_time DATETIME NOT NULL,
    status VARCHAR(255) NOT NULL DEFAULT 'UPCOMING',
    CONSTRAINT fk_matches_match_date FOREIGN KEY (match_date_id) REFERENCES match_dates(id),
    CONSTRAINT fk_matches_home_club FOREIGN KEY (home_club_id) REFERENCES clubs(id),
    CONSTRAINT fk_matches_away_club FOREIGN KEY (away_club_id) REFERENCES clubs(id)
);
