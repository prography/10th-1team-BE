#!/bin/bash
set -e

cd ~/prography
TAG=$1
echo "TAG=$TAG" > .env

# 1. 이미지 Pull
docker pull seogwoojin1/reviewmatch-bff:$TAG

# 2. 현재 실행중인 앱 확인
if docker ps --format '{{.Names}}' | grep -q app-blue; then
  TARGET=green
  RUNNING=blue
  NEW_PORT=8082
  OLD_PORT=8081
else
  TARGET=blue
  RUNNING=green
  NEW_PORT=8081
  OLD_PORT=8082
fi

# 3. 새로운 앱 띄우기
docker compose up -d app-$TARGET

echo "🩺 Starting health check on port $NEW_PORT..."

for i in {1..10}; do
  STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:$NEW_PORT/actuator/health || echo "curl-error")
  echo "  ▶ attempt $i: HTTP $STATUS"

  if [ "$STATUS" = "200" ]; then
    echo "✅ Health check passed on port $NEW_PORT"

    sed -i "s|proxy_pass http://.*:8080;|proxy_pass http://app-$TARGET:8080;|" ./nginx/conf.d/default.conf
    docker compose exec nginx nginx -s reload

    docker compose stop app-$RUNNING
    docker compose rm -f app-$RUNNING

    docker images seogwoojin1/reviewmatch-bff --format "{{.Tag}}" \
      | grep -v "^${TAG}$" \
      | xargs -r -I{} docker image rm seogwoojin1/reviewmatch-bff:{}

    exit 0
  fi

  sleep 10
done

echo "❌ Health check failed after 6 attempts. Deployment aborted."
docker compose stop app-$TARGET
docker compose rm -f app-$TARGET
exit 1