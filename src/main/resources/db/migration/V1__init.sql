CREATE TABLE IF NOT EXISTS oauth_access_token
(
    authentication_id VARCHAR(255) NOT NULL PRIMARY KEY,
    token_id          VARCHAR(255) NOT NULL,
    token             BLOB         NOT NULL,
    user_name         VARCHAR(255),
    client_id         VARCHAR(255) NOT NULL,
    authentication    BLOB         NOT NULL,
    refresh_token     VARCHAR(255) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

CREATE TABLE IF NOT EXISTS oauth_refresh_token
(
    token_id       VARCHAR(255) NOT NULL,
    token          BLOB         NOT NULL,
    authentication BLOB         NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

CREATE TABLE IF NOT EXISTS jwt_refresh_token
(
    id             VARCHAR(255) NOT NULL PRIMARY KEY,
    account_id     BIGINT       NOT NULL,
    token          BLOB         NOT NULL,
    authentication BLOB         NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

CREATE TABLE IF NOT EXISTS account
(
    id           BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username     VARCHAR(255) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    role         VARCHAR(5)   NOT NULL,
    name         VARCHAR(255),
    phone        VARCHAR(255) UNIQUE,
    email        VARCHAR(255) UNIQUE,
    enabled      BIT          NOT NULL default 1,
    locked       BIT          NOT NULL default 0,
    expired      BIT          NOT NULL default 0,
    created_by   VARCHAR(255) NOT NULL,
    updated_by   VARCHAR(255) NOT NULL,
    created_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    lock_version BIGINT       NOT NULL DEFAULT 0
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

INSERT INTO account (username, password, role, name, created_by, updated_by)
VALUES ('admin', '{bcrypt}$2a$10$pxbYjCopQfNeKL2NtrS13eG7eLSkTHXV5Wnc.f2VSXUrx48kOeFVS', 'ADMIN', 'Admin', 'SYSTEM',
        'SYSTEM')