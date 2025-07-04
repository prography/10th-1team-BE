DROP TABLE IF EXISTS vote_history;

CREATE TABLE vote_history (
  id          BIGSERIAL PRIMARY KEY,
  user_id     UUID       NOT NULL,
  place_id    TEXT       NOT NULL,
  place_name  TEXT,
  reasons     TEXT       NOT NULL,
  platform    VARCHAR(50) NOT NULL,
  category    TEXT,
  voted_date  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),

  CONSTRAINT uq_vote_history_user_place
    UNIQUE (user_id, place_id)
);

CREATE TABLE bookmark_group (
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

CREATE TABLE bookmark (
  id         UUID    PRIMARY KEY,
  user_id    UUID    NOT NULL,
  group_id   UUID    NOT NULL,
  place_id   TEXT    NOT NULL,
  saved_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),


  CONSTRAINT uq_bookmark_user_group_place
    UNIQUE (user_id, group_id, place_id)
);