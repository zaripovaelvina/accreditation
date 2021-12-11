CREATE TABLE citizenship -- таблица гражданство
(
    id   BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE country -- таблица страны
(
    id   BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE organization -- таблица организаций
(
    id   BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE events -- мероприятия
(
    id        BIGSERIAL PRIMARY KEY,
    name      TEXT NOT NULL,
    image     TEXT NOT NULL,
    completed BOOLEAN DEFAULT FALSE
);

CREATE TABLE disciplines -- дисциплины
(
    id           BIGSERIAL PRIMARY KEY,
    name         TEXT    NOT NULL,
    weaponStatus INT     NOT NULL,
    selected     BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE weapon -- таблица оружий
(
    id      BIGSERIAL PRIMARY KEY,
    name    TEXT    NOT NULL,
    image   TEXT    NOT NULL,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE person -- таблица участников
(
    id             BIGSERIAL PRIMARY KEY,
    name           TEXT        NOT NULL,
    surname        TEXT        NOT NULL,
    patronymic     TEXT        NOT NULL,
    birthday       timestamptz NOT NULL,
    phone          TEXT        NOT NULL,
    email          TEXT        NOT NULL,
    citizenship_id BIGSERIAL
        CONSTRAINT person_citizenship_id_fk REFERENCES citizenship,
    country_id     BIGSERIAL
        CONSTRAINT person_country_id_fk REFERENCES country,
    gender         TEXT        NOT NULL,
    image          TEXT        NOT NULL,
    removed        BOOLEAN     NOT NULL DEFAULT FALSE,
    created        timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE application -- заявка участника на мероприятие с разрешением
(
    id                  BIGSERIAL PRIMARY KEY,
    person_id           BIGSERIAL
        CONSTRAINT application_person_id_fk REFERENCES person,
    event_id            BIGSERIAL
        CONSTRAINT application_event_id_fk REFERENCES events,
    disciplines_id      BIGSERIAL
        CONSTRAINT application_disciplines_id_fk REFERENCES disciplines,
    organization_id     BIGSERIAL
        CONSTRAINT application_organization_id_fk REFERENCES organization,
    weapon_id           BIGSERIAL
        CONSTRAINT application_weapon_id_fk REFERENCES weapon,
    weapon_manufacturer TEXT        NOT NULL,
    permit_serial       TEXT        NOT NULL,
    permit_num          TEXT        NOT NULL,
    permit_date         timestamptz NOT NULL,
    permit_manufacturer TEXT        NOT NULL,
    image               TEXT        NOT NULL,
    removed             BOOLEAN     NOT NULL DEFAULT FALSE,
    created             timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status              INT         NOT NULL DEFAULT 0
);


