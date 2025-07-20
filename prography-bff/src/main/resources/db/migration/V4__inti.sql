DROP TABLE IF EXISTS bookmark_group;
DROP TABLE IF EXISTS BOOKMARK_GROUP;
DROP TABLE IF EXISTS bookmark;
DROP TABLE IF EXISTS BOOKMARK;

CREATE TABLE BOOKMARK_GROUP (
  id          UUID      PRIMARY KEY,
  icon        TEXT      NOT NULL,
  user_id     UUID      NOT NULL,
  group_name  TEXT      NOT NULL,
  total       BIGINT    NOT NULL DEFAULT 0,
  created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  modified_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),

  CONSTRAINT uq_bookmark_group_userid_groupname
    UNIQUE (user_id, group_name)
);

CREATE TABLE BOOKMARK (
    group_id UUID NOT NULL,
    place_id VARCHAR(255) NOT NULL,
    user_id UUID NOT NULL,
    saved_at TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (GROUP_ID, PLACE_ID), -- 기본 키 제약 조건 (자동으로 유니크)
    CONSTRAINT UK_BOOKMARK_GROUP_ID_PLACE_ID UNIQUE (group_id, place_id) -- 명시적으로 추가된 유니크 제약 조건 (이름은 다를 수 있음)
);