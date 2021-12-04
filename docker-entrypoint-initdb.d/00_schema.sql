CREATE TABLE citizenship -- таблица гражданство
(
    id   BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE country -- таблица страны
(
    id           BIGSERIAL PRIMARY KEY,
    name         TEXT NOT NULL,
    status       INT  NOT NULL,
    short_name   TEXT NOT NULL,
    cod_official TEXT NOT NULL
);

CREATE TABLE organization -- таблица организаций
(
    id     BIGSERIAL PRIMARY KEY,
    name   TEXT NOT NULL,
    status INT  NOT NULL
);

CREATE TABLE events -- мероприятия
(
    id     BIGSERIAL PRIMARY KEY,
    name   TEXT NOT NULL,
    status BOOLEAN DEFAULT FALSE
);

CREATE TABLE disciplines -- дисциплины
(
    id            BIGSERIAL PRIMARY KEY,
    name          TEXT NOT NULL,
    status        INT,
    weapon_status INT
);

CREATE TABLE weapon -- таблица оружий
(
    id     BIGSERIAL PRIMARY KEY,
    name   TEXT NOT NULL,
    status INT  NOT NULL
);

CREATE TABLE person -- таблица участников
(
    id                 BIGSERIAL PRIMARY KEY,
    name               TEXT        NOT NULL,
    surname            TEXT        NOT NULL,
    patronymic         TEXT        NOT NULL,
    birthday           timestamptz NOT NULL,
    phone              TEXT        NOT NULL,
    email              TEXT        NOT NULL,
    passport_serial    TEXT        NOT NULL,
    passport_number    TEXT        NOT NULL,
    passport_date      timestamptz NOT NULL,
    passport_author    TEXT        NOT NULL,
    passport_code      TEXT        NOT NULL,
    passport_address   TEXT        NOT NULL,
    birthplace_address TEXT        NOT NULL,
    citizenship_id     INT
        CONSTRAINT person_citizenship_id_fk REFERENCES citizenship,
    country_id         INT
        CONSTRAINT person_country_id_fk REFERENCES country,
    sex                TEXT        NOT NULL,
    removed            BOOLEAN     NOT NULL DEFAULT FALSE,
    created            timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE person_disciplines -- таблица соответствий участника с дисциплиной
(
    id              BIGSERIAL PRIMARY KEY,
    person_id       INT,
    event_id        INT,
    disciplines_id INT,
    organization_id INT
);

CREATE TABLE person_disciplines_weapon -- заявка
(
    id                     BIGSERIAL PRIMARY KEY,
    person_id              INT,
    weapon_id              INT,
    weapon_manufacturer    TEXT NOT NULL,
    permit_serial          TEXT NOT NULL,
    permit_num             TEXT NOT NULL,
    permit_date            DATE,
    permit_manufacturer    TEXT NOT NULL,
    person_competitions_id INT,
    file                   TEXT NOT NULL

);


