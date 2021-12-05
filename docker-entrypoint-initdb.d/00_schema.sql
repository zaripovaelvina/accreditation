CREATE TABLE citizenship -- таблица гражданство
(
    id   BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE country -- таблица страны
(
    id     BIGSERIAL PRIMARY KEY,
    name   TEXT NOT NULL,
    status INT  NOT NULL DEFAULT 0
);

CREATE TABLE organization -- таблица организаций
(
    id     BIGSERIAL PRIMARY KEY,
    name   TEXT NOT NULL,
    status INT  NOT NULL DEFAULT 0
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
    weapon_status INT NOT NULL DEFAULT 0
);

CREATE TABLE weapon -- таблица оружий
(
    id     BIGSERIAL PRIMARY KEY,
    name   TEXT NOT NULL,
    status INT  NOT NULL DEFAULT 0
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
    citizenship_id     INT
        CONSTRAINT person_citizenship_id_fk REFERENCES citizenship,
    country_id         INT
        CONSTRAINT person_country_id_fk REFERENCES country,
    gender             TEXT        NOT NULL,
    removed            BOOLEAN     NOT NULL DEFAULT FALSE,
    created            timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE person_disciplines -- таблица соответствий участника с дисциплиной
(
    id              BIGSERIAL PRIMARY KEY,
    person_id       INT,
    event_id        INT,
    disciplines_id  INT,
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
    person_disciplines_id  INT,
    file                   TEXT NOT NULL

);


