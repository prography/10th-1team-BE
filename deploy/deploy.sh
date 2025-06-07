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

# 4. 헬스체크
for i in {1..6}; do
  STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:$NEW_PORT/actuator/health)
  if [ "$STATUS" = "200" ]; then
    echo "✅ Health check passed on port $NEW_PORT"

    # 5. 포트 스위칭
    sed -i "s/$OLD_PORT/$NEW_PORT/" ./nginx/conf.d/default.conf
    docker compose exec nginx nginx -s reload

    # 6. 기존 앱 종료
    docker compose stop app-$RUNNING
    docker compose rm -f app-$RUNNING

    exit 0
  fi
  echo "Waiting for app-$TARGET to pass health check..."
  sleep 10
done

# 7. 실패 시 롤백
echo "❌ Health check failed. Deployment aborted."
docker compose stop app-$TARGET
docker compose rm -f app-$TARGET
exit 1
