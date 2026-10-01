-- BDs de desarrollo local. El servicio `db` crea `regisoc` vía MYSQL_DATABASE;
-- este script añade `regisoc_dev` para el perfil `dev` (application-dev.yml).
CREATE DATABASE IF NOT EXISTS regisoc_dev CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
