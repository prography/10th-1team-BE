# 10th-1team-BE

> 📌 이 프로젝트는 **모듈 간 Spring 및 라이브러리 버전을 통일하고**, 기능별 변경 이력을 명확히 관리하기 위해  
> **브랜치 단위 분리 전략**을 사용합니다. 각 브랜치는 독립적인 개발과 배포가 가능하며, 공통 기반 위에서 안정적으로 관리됩니다.


# [📦 BFF (Backend For Frontend)](https://github.com/prography/10th-1team-BE/tree/module/bff)

프론트엔드 전용 API 서버입니다.  
다양한 외부/내부 데이터를 통합해 프론트엔드에 최적화된 응답을 제공합니다.

---

## 🛠️ 사용 기술
- Kotlin + Spring Boot
- ElasticSearch
- Postgres
- Feign Client

---

## 📌 주요 기능
- 사용자 인증 및 세션 관리
- 음식점/리뷰 정보 제공 API
- Crawler, DB 등과 통신


---


# 🕷️ [Crawler](https://github.com/prography/10th-1team-BE/tree/module/crawler)

외부 API 및 웹 데이터를 수집하여 DB에 저장하는 크롤링 전용 서비스입니다.

---

## 🛠️ 사용 기술
- Kotlin + Spring Boot
- Feign Client
- MongoDB

---

## 📌 주요 기능
- Kakao, Naver 등 외부 데이터 수집
- 음식점 및 리뷰 정보 저장
- 주기적 크롤링 스케줄러

--- 

