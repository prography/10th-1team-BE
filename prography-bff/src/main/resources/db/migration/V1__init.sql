CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       provider VARCHAR(50) NOT NULL,
                       provider_id VARCHAR(255) NOT NULL,
                       nickname VARCHAR(100) NOT NULL,
                       level INTEGER NOT NULL DEFAULT 0,
                       status BOOLEAN NOT NULL DEFAULT FALSE,
                       created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
                       updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE province (
                          province_code VARCHAR PRIMARY KEY,
                          province_name VARCHAR(50) NOT NULL
);

CREATE TABLE city (
                      city_code VARCHAR PRIMARY KEY,
                      city_name VARCHAR(50) NOT NULL,
                      is_scraped BOOLEAN NOT NULL DEFAULT FALSE,
                      province_code VARCHAR NOT NULL,
                      CONSTRAINT fk_city_province
                          FOREIGN KEY (province_code)
                              REFERENCES province(province_code)
                              ON DELETE CASCADE
);

CREATE TABLE dong (
                      code VARCHAR(10) PRIMARY KEY,
                      name VARCHAR NOT NULL,
                      city_code VARCHAR NOT NULL,
                      CONSTRAINT fk_dong_city
                          FOREIGN KEY (city_code)
                              REFERENCES city(city_code)
                              ON DELETE CASCADE
);

-- VOTE 테이블 생성
CREATE TABLE vote (
                      place_id VARCHAR NOT NULL,
                      platform_name TEXT NOT NULL,
                      total BIGINT NOT NULL,
                      many_reviews BIGINT NOT NULL,
                      detailed BIGINT NOT NULL,
                      honest BIGINT NOT NULL,
                      accurate BIGINT NOT NULL,
                      PRIMARY KEY (place_id, platform_name)
);

-- VOTE_HISTORY 테이블 생성
CREATE TABLE vote_history (
                              id BIGSERIAL PRIMARY KEY,
                              user_id UUID NOT NULL,
                              place_id VARCHAR NOT NULL,
                              category TEXT NOT NULL,
                              platform TEXT NOT NULL,
                              voted_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
--                              CONSTRAINT uk_vote_history_user_place UNIQUE (user_id, place_id)
);

-- 이미 생성된 vote_history 테이블에 유니크 제약 추가
ALTER TABLE vote_history
ADD CONSTRAINT uk_vote_history_user_place
UNIQUE (user_id, place_id);