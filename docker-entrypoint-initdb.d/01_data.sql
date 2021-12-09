INSERT INTO citizenship (id, name)
VALUES (1, 'Гражданин РФ'),
       (2, 'Иностранный гражданин');

INSERT INTO country (id, name, status)
VALUES (1, 'Российская федерация', 0),
       (2, 'Соединенные Штаты Америки', 0);

INSERT INTO organization (id, name, status)
VALUES (1, 'Организация РФ', 0),
       (2, 'Иностранная организация', 0);

INSERT INTO disciplines (id, name, status, weapon_status)
VALUES (1, 'Шахматы', 0, 0),
       (2, 'Стрельба из ружья', 0, 1),
       (3, 'Биатлон', 0, 1);

INSERT INTO weapon (id, name, status)
VALUES (1, 'Пистолет', 0),
       (2, 'Ружье', 0);

INSERT INTO events (id, name, status)
VALUES (1, 'Чемпионат мира по стрельбе', FALSE),
       (2, 'Чемпионат мира по биатлону', FALSE);

INSERT INTO person (name, surname, patronymic, birthday, phone, email,
                    citizenship_id, country_id, gender, image, removed, created)
VALUES ('Ф', 'И', 'О', to_date('10.10.2000', 'dd.mm.yyyy'), '12345678', 'test@mail.ru',
        '1', '1', 'M', 'noimage.png', FALSE, current_date),
       ('A', 'A', 'A', to_date('08.08.2003', 'dd.mm.yyyy'), '12345678', 't@mail.ru',
        '1', '1', 'W', 'noimage.png', FALSE, current_date);

