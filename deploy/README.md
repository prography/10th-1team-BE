✅ 초기 설정 절차

1. GCP 방화벽 설정
   포트 80, 443 허용

2. mkdir prography
3. cd prography
4. ./nginx 이하 폴더 전체 복사
5. Nginx만 HTTP로 먼저 실행 (default.conf 443 관련 모두 주석처리)
   docker compose up -d nginx
6. nano .env
   <br>
   EMAIL=your@email.com <br>
   DOMAIN=api.reviewmatch.co.kr

7. 인증서 발급
   docker compose run --rm certbot
8. 인증서 파일 확인
   ls certbot/live/api.reviewmatch.co.kr/fullchain.pem
9. 전체 서비스 HTTPS로 재시작
   docker compose down
   docker compose up -d
10. ✅ HTTPS 적용 확인
    curl -I https://api.reviewmatch.co.kr
    → HTTP/2 200 또는 301, 인증서 적용 여부 확인
11. 자동 갱신(cron 예시)

crontab -e

0 3 * * * docker compose run --rm certbot renew && docker compose exec nginx nginx -s reload