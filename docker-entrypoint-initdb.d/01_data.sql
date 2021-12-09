INSERT INTO citizenship (id, name)
VALUES (1, 'Гражданин РФ'),
       (2, 'Иностранный гражданин');

INSERT INTO country (id, name)
VALUES (1, 'Российская федерация'),
       (2, 'Соединенные Штаты Америки');

INSERT INTO organization (id, name)
VALUES (1, 'Организация РФ'),
       (2, 'Иностранная организация');

INSERT INTO disciplines (id, name, weaponStatus)
VALUES (1, 'Шахматы', 0),
       (2, 'Стрельба из ружья', 1),
       (3, 'Биатлон', 0);

INSERT INTO weapon (id, name, image)
VALUES (1, 'Пистолет', 'noimage.png'),
       (2, 'Ружье', 'noimage.png');

INSERT INTO events (id, name, image)
VALUES (1, 'Чемпионат мира по стрельбе', 'noimage.png'),
       (2, 'Чемпионат мира по биатлону', 'noimage.png');

INSERT INTO person (name, surname, patronymic, birthday, phone, email,
                    citizenship_id, country_id, gender, image, removed, created)
VALUES ('Ф', 'И', 'О', to_date('10.10.2000', 'dd.mm.yyyy'), '12345678', 'test@mail.ru',
        '1', '1', 'M', 'noimage.png', FALSE, current_date),
       ('A', 'A', 'A', to_date('08.08.2003', 'dd.mm.yyyy'), '12345678', 't@mail.ru',
        '1', '1', 'W', 'noimage.png', FALSE, current_date);


