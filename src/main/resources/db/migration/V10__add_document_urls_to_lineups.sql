-- match_lineups: columnas de coach
ALTER TABLE match_lineups ADD COLUMN coach_document_front_url TEXT NULL;
ALTER TABLE match_lineups ADD COLUMN coach_document_back_url TEXT NULL;

-- match_lineups: columnas de physical_trainer
ALTER TABLE match_lineups ADD COLUMN physical_trainer_document_front_url TEXT NULL;
ALTER TABLE match_lineups ADD COLUMN physical_trainer_document_back_url TEXT NULL;

-- match_lineup_players: columnas de player
ALTER TABLE match_lineup_players ADD COLUMN document_front_url TEXT NULL;
ALTER TABLE match_lineup_players ADD COLUMN document_back_url TEXT NULL;
