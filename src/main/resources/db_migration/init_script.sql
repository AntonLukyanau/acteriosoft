DROP TABLE IF EXISTS log_records;
DROP TABLE IF EXISTS banner_category;
DROP TABLE IF EXISTS banner;
DROP TABLE IF EXISTS category;

CREATE SEQUENCE IF NOT EXISTS category_id_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE TABLE IF NOT EXISTS category
(
    id            BIGINT PRIMARY KEY DEFAULT nextval('category_id_seq'),
    "name"        VARCHAR NOT NULL UNIQUE,
    request_param VARCHAR NOT NULL UNIQUE,
    deleted       BOOLEAN NOT NULL   DEFAULT false
);

CREATE SEQUENCE IF NOT EXISTS banner_id_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE TABLE IF NOT EXISTS banner
(
    id      BIGINT PRIMARY KEY      DEFAULT nextval('banner_id_seq'),
    "name"  VARCHAR        NOT NULL UNIQUE,
    body    VARCHAR        NOT NULL,
    price   NUMERIC(10, 2) NOT NULL,
    deleted BOOLEAN        NOT NULL DEFAULT false
);


CREATE TABLE IF NOT EXISTS banner_category
(
    banner_id   BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    CONSTRAINT banner_category_pk PRIMARY KEY (banner_id, category_id),
    CONSTRAINT banner_category_banner_fk FOREIGN KEY (banner_id) REFERENCES banner (id),
    CONSTRAINT banner_category_category_fk FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE SEQUENCE IF NOT EXISTS log_records_id_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;
CREATE TABLE IF NOT EXISTS log_records
(
    id                           BIGINT PRIMARY KEY DEFAULT nextval('log_records_id_seq'),
    request_ip_address           VARCHAR(255) NOT NULL,
    user_agent                   VARCHAR(255) NOT NULL,
    request_time                 TIMESTAMP    NOT NULL,
    selected_banner_id           BIGINT,
    selected_banner_category_ids VARCHAR(255),
    selected_banner_price        NUMERIC(10, 2),
    no_content_reason            VARCHAR(255)
);
