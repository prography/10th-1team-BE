✅ 초기 설정 절차

1. GCP 방화벽 설정
   포트 80, 443 허용

2. mkdir prography
3. cd prography
4. Nginx만 HTTP로 먼저 실행 (default.conf 443 주석처리)
   docker compose up -d nginx
5. 인증서 발급
   docker compose run --rm certbot
6. 인증서 파일 확인
   ls certbot/live/api.reviewmatch.co.kr/fullchain.pem
7. 전체 서비스 HTTPS로 재시작
   docker compose down
   docker compose up -d
8. ✅ HTTPS 적용 확인
   curl -I https://api.reviewmatch.co.kr
   → HTTP/2 200 또는 301, 인증서 적용 여부 확인
9. 자동 갱신(cron 예시)

crontab -e

0 3 * * * docker compose run --rm certbot renew && docker compose exec nginx nginx -s reload