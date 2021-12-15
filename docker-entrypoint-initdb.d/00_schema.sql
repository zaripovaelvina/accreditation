CREATE TABLE citizenship -- таблица гражданство
(
    id   BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE countries -- таблица страны
(
    id   BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE organizations -- таблица организаций
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
    id       BIGSERIAL PRIMARY KEY,
    name     TEXT    NOT NULL,
    status   INT     NOT NULL,
    selected BOOLEAN NOT NULL DEFAULT FALSE,
    deleted  BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE weapons -- таблица оружий
(
    id      BIGSERIAL PRIMARY KEY,
    name    TEXT    NOT NULL,
    image   TEXT    NOT NULL,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE persons -- таблица участников
(
    id             BIGSERIAL PRIMARY KEY,
    name           TEXT        NOT NULL,
    surname        TEXT        NOT NULL,
    patronymic     TEXT        NOT NULL,
    birthday       timestamptz NOT NULL,
    phone          TEXT        NOT NULL,
    email          TEXT        NOT NULL,
    citizenship_id BIGSERIAL
        CONSTRAINT persons_citizenship_id_fk REFERENCES citizenship,
    country_id     BIGSERIAL
        CONSTRAINT persons_countries_id_fk REFERENCES countries,
    gender         TEXT        NOT NULL,
    image          TEXT        NOT NULL,
    removed        BOOLEAN     NOT NULL DEFAULT FALSE,
    created        timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE applications -- заявка участника на мероприятие с разрешением
(
    id                  BIGSERIAL PRIMARY KEY,
    person_id           BIGSERIAL
        CONSTRAINT applications_persons_id_fk REFERENCES persons,
    event_id            BIGSERIAL
        CONSTRAINT applications_events_id_fk REFERENCES events,
    disciplines_id      BIGSERIAL
        CONSTRAINT applications_disciplines_id_fk REFERENCES disciplines,
    organization_id     BIGSERIAL
        CONSTRAINT applications_organizations_id_fk REFERENCES organizations,
    weapon_id           BIGSERIAL
        CONSTRAINT applications_weapons_id_fk REFERENCES weapons,
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


