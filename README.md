# regisoc

API de gestión de clubes deportivos (Spring Boot 3 + Kotlin + MySQL + Flyway + JWT).

## Requisitos

- Java 21, Gradle wrapper incluido (`./gradlew`, no necesitas instalar Gradle).
- MySQL 8 en los 3 entornos (local con Docker Compose, dev/prod en servidor).
- Los tests son unitarios con mocks: **no** necesitan base de datos.

## Entornos

| Perfil | Fichero | Base de datos | Uso |
|---|---|---|---|
| `local` | `src/main/resources/application-local.yml` | MySQL `localhost:3306/regisoc` | Desarrollo en tu máquina |
| `dev` | `src/main/resources/application-dev.yml` | MySQL `regisoc_dev` (localhost o servidor compartido) | Ambiente de desarrollo/integración |
| `prod` | `src/main/resources/application-prod.yml` | La que diga `DB_URL` (Cloud SQL) | Google Cloud Run |

`src/main/resources/application.yml` tiene lo común (puerto `PORT`/`SERVER_PORT`, JWT, Flyway, `/actuator/health`).

## Variables de entorno

Copia la plantilla y ajústala (nunca subas `.env` a git):

```bash
cp .env.example .env
```

| Variable | local (defecto) | dev (defecto) | prod |
|---|---|---|---|
| `SPRING_PROFILES_ACTIVE` | `local` | `dev` | `prod` |
| `SERVER_PORT` / `PORT` | `8080` (Cloud Run inyecta `PORT`) | `8080` | lo define Cloud Run |
| `DB_URL` | `jdbc:mysql://localhost:3306/regisoc?...` | `jdbc:mysql://localhost:3306/regisoc_dev?...` | **obligatoria** (Cloud SQL) |
| `DB_USERNAME` (`DB_USER` alias) | `root` | `root` | **obligatoria** |
| `DB_PASSWORD` | vacía | vacía | **obligatoria** |
| `JWT_SECRET` (base64, ≥256 bits) | secreto de desarrollo | secreto de desarrollo | **obligatoria** (`openssl rand -base64 48`) |
| `JWT_EXPIRATION` | `1800000` | `1800000` | `1800000` |
| `SHOW_SQL` | `true` en local | `false` | `false` |

En `prod` la app **no arranca** si faltan `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` o `JWT_SECRET` (fail-fast).

## Arranque rápido (línea de comandos)

```bash
# 1) Base de datos local en Docker (crea `regisoc` y `regisoc_dev`)
docker compose up -d db

# 2) App según entorno
./gradlew bootRunLocal    # MySQL localhost, BD regisoc
./gradlew bootRunDev      # MySQL localhost, BD regisoc_dev (o servidor dev vía .env)
SPRING_PROFILES_ACTIVE=prod DB_URL=... DB_USERNAME=... DB_PASSWORD=... JWT_SECRET=... ./gradlew bootRunProd

# bootRun a secas = perfil local (respeta SPRING_PROFILES_ACTIVE si ya está definida)
./gradlew bootRun
SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun

# Tests (mocks, sin BD) y build
./gradlew test
./gradlew build          # o bootJar -> build/libs/*.jar
java -jar build/libs/*.jar --spring.profiles.active=dev
```

Salud del servicio: `GET http://localhost:8080/actuator/health`.

## Tests

Unitarios con JUnit 5 + MockK, sin Spring ni base de datos (`./gradlew test` funciona sin MySQL).
Si en el futuro se añade un test con contexto Spring, usará el perfil `dev` (ver `src/test/resources/application.yml`).

## IntelliJ IDEA (y otros IDEs)

Al importar el proyecto como **Gradle**, IntelliJ detecta el `build.gradle.kts` y las tareas `bootRun*` sin nada más que configurar.

Ya van incluidas configuraciones listas para ejecutar/depurar:

- `.run/bootRunLocal.run.xml`, `.run/bootRunDev.run.xml`, `.run/bootRunProd.run.xml`
- (duplicadas en `.idea/runConfigurations/` para versiones antiguas)

Aparecen en **Run ▶ Edit Configurations** como `bootRunLocal`, `bootRunDev` y `bootRunProd`.
Para variables de entorno en el IDE: edita la configuración y añade `SPRING_PROFILES_ACTIVE`, `DB_URL`, `JWT_SECRET`, etc., o usa el plugin **EnvFile** con tu `.env`.

VS Code / Eclipse: importa como proyecto Gradle y ejecuta las mismas tareas (`bootRunLocal`, …).

## Docker local

```bash
cp .env.example .env            # ajusta MYSQL_ROOT_PASSWORD, JWT_SECRET, ...
docker compose up --build       # app (perfil local) + MySQL
docker compose up -d db         # solo MySQL, app con ./gradlew bootRunLocal o bootRunDev
docker compose down -v          # parar y borrar datos
```

Dentro de Compose la app usa el host `db` (`DB_URL=jdbc:mysql://db:3306/...`); fuera de Compose usa `localhost`.

## Despliegue en Google Cloud

Imagen multi-stage lista para Cloud Run (`Dockerfile`): escucha en `$PORT`, corre como no-root y expone `/actuator/health`.

```bash
# Build + push + deploy con Cloud Build (edita _DB_URL o usa unix-socket de Cloud SQL)
gcloud builds submit --config=cloudbuild.yaml \
  --substitutions=_REGION=europe-west1,_REPO=regisoc-repo,_SERVICE=regisoc,_DB_URL="jdbc:mysql://<IP_CLOUD_SQL>:3306/regisoc?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
```

Crea antes en **Secret Manager**: `regisoc-db-username`, `regisoc-db-password`, `regisoc-jwt-secret`.
`cloudbuild.yaml` despliega en Cloud Run con `SPRING_PROFILES_ACTIVE=prod`, `--set-env-vars=DB_URL=...` y `--set-secrets=DB_USERNAME,DB_PASSWORD,JWT_SECRET`.
Para Cloud SQL con unix-socket usa la URL con `socketFactory=com.google.cloud.sql.mysql.SocketFactory` y añade el conector correspondiente.
Para desplegar el ambiente `dev`, repite el comando con `_SERVICE=regisoc-dev` y la `DB_URL` del servidor dev.

## Migraciones

Flyway (`src/main/resources/db/migration/V*.sql`) corre al arrancar en los tres entornos (`ddl-auto: validate`, el esquema lo gobierna Flyway).
