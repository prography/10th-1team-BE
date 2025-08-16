ALTER TABLE BOOKMARK_GROUP
DROP CONSTRAINT uq_bookmark_group_userid_groupname;

ALTER TABLE BOOKMARK_GROUP
    ADD CONSTRAINT uq_bookmark_group_user_group_roulette
        UNIQUE (user_id, group_name, roulette);