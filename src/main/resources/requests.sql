INSERT INTO person
VALUES (1, 'Иван', 'Иванов', 'Петрович', '01.12.2010', '+789645824', 'test@mail.ru', 1, 1, 'm', false,
        '2021-12-08 08:33:40.750000 +00:00'),
       (1, 'Лин', 'Тейлор', 'Кирк', '29.07.2013', '+785675477', 'test2@mail.ru', 2, 2, 'm', false,
        '2021-12-08 08:33:40.750000 +00:00');

INSERT INTO events
VALUES (1, 'Чемпионат мира по стрельбе', 'FALSE');

INSERT INTO person
VALUES (1, 'Ф', 'И', 'О', to_date('10.10.2000', 'dd.mm.yyyy'), '12345678', 'test@mail.ru', '1', '1', 'M', FALSE);


INSERT INTO application (person_id, event_id, disciplines_id, organization_id, weapon_id, weapon_manufacturer,
                         permit_serial, permit_num, permit_date, permit_manufacturer, image, removed, created)
VALUES ('1', '3', '2', '1', '3', 'Калашников', 'AH', '678', to_date('10.03.2020', 'dd.mm.yyyy'), 'Производитель',
        'noimage.png', FALSE);