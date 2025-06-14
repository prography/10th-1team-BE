FROM gradle:8.12.0-jdk21-corretto AS builder
WORKDIR /build

COPY settings.gradle.kts build.gradle.kts /build/

RUN gradle :prography-bff:build -x test --parallel --dry-run > /dev/null 2>&1 || true

# 2) 전체 소스 복사
COPY . /build

# 3) 실제 BFF 패키징
RUN gradle :prography-bff:bootJar --no-daemon -x test

# ── 2단계: 런타임
FROM amazoncorretto:21-alpine AS production
WORKDIR /app

COPY --from=builder \
     /build/prography-bff/build/libs/prography-bff-*.jar \
     app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]