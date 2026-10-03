# Imagen lista para plataformas container (Google Cloud Run, Railway).
# Escucha en $PORT (inyectado por la plataforma) y expone /actuator/health.
# Build: docker build -t regisoc .
# Run:   docker run -p 8080:8080 --env-file .env regisoc
ARG JAVA_VERSION=21

# ---------- build ----------
FROM eclipse-temurin:${JAVA_VERSION}-jdk AS build
WORKDIR /workspace

# Copiamos primero los descriptores para aprovechar la caché de capas.
COPY gradlew settings.gradle.kts build.gradle.kts gradle.properties ./
COPY gradle ./gradle
RUN chmod +x gradlew
# Descarga dependencias (capa cacheable).
RUN ./gradlew dependencies --no-daemon || true

COPY src ./src
RUN ./gradlew bootJar -x test --no-daemon

# ---------- runtime ----------
FROM eclipse-temurin:${JAVA_VERSION}-jre AS runtime
WORKDIR /app

# Usuario no-root (buena práctica en Cloud Run / GKE).
RUN useradd -m -u 10001 appuser
USER appuser

COPY --from=build /workspace/build/libs/*.jar /app/app.jar

# La plataforma inyecta PORT; por defecto 8080 en local.
ENV PORT=8080 \
    SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

# Sin HEALTHCHECK de Docker: el health check lo hace la plataforma
# (Cloud Run / Railway) contra /actuator/health (ver railway.json).

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE:-prod}"]
