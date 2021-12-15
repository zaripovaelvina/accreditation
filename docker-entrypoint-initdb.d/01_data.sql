INSERT INTO citizenship (name)
VALUES ('Гражданин РФ'),
       ('Иностранный гражданин');

INSERT INTO countries (name)
VALUES ('Российская федерация'),
       ('Соединенные Штаты Америки');

INSERT INTO organizations (name)
VALUES ('Организация РФ'),
       ('Иностранная организация');

INSERT INTO disciplines (name, status)
VALUES ('Шахматы', 0),
       ('Стрельба из ружья', 1),
       ('Большой теннис', 0),
       ('Биатлон', 1),
       ('Баскетбол', 0),
       ('Футбол', 0);

INSERT INTO weapons (name, image)
VALUES ('Пистолет', 'noimage.png'),
       ('Ружье', 'noimage.png'),
       ('Спортивная 5-разрядная винтовка Биатлон-7-2', 'noimage.png');

INSERT INTO events (name, image)
VALUES ('Чемпионат мира по стрельбе', 'noimage.png'),
       ('Чемпионат мира по биатлону', 'noimage.png'),
       ('Чемпионат по практической стрельбе', 'noimage.png'),
       ('Чемпионат мира по футболу', 'noimage.png');

INSERT INTO persons (name, surname, patronymic, birthday, phone, email,
                    citizenship_id, country_id, gender, image, removed, created)
VALUES ('Ivanov', 'Ivan', 'Ivanovich', to_date('10.10.2000', 'dd.mm.yyyy'), '12345678', 'test999java@mail.ru',
        '1', '1', 'M', 'noimage.png', FALSE, current_date),
       ('Abramova', 'Alina', 'Alexandrovna', to_date('08.08.2003', 'dd.mm.yyyy'), '12345678', 'test999java@mail.ru',
        '1', '1', 'W', 'noimage.png', FALSE, current_date),
       ('Rimanass', 'Alina', 'Albertovna', to_date('15.09.2003', 'dd.mm.yyyy'), '12345678', 'test999java@mail.ru',
        '2', '1', 'W', 'noimage.png', FALSE, current_date),
       ('Vasiliev', 'Vasiliy', 'Vasilievich', to_date('08.08.2000', 'dd.mm.yyyy'), '12345678', 'test999java@mail.ru',
        '1', '1', 'M', 'noimage.png', FALSE, current_date),
       ('Dmitriev', 'Ivan', 'Petrovich', to_date('09.08.2003', 'dd.mm.yyyy'), '12345678', 'test999java@mail.ru',
        '1', '1', 'M', 'noimage.png', FALSE, current_date),
       ('Andersen', 'Alex', 'Koy', to_date('01.12.2000', 'dd.mm.yyyy'), '12345678', 'test999java@mail.ru',
        '2', '1', 'M', 'noimage.png', FALSE, current_date);

INSERT INTO applications (person_id, event_id, disciplines_id, organization_id, weapon_id, weapon_manufacturer,
                         permit_serial, permit_num, permit_date, permit_manufacturer, image)
VALUES ('1', '3', '2', '1', '3', 'Калашников', 'AH', '678', to_date('10.03.2020', 'dd.mm.yyyy'), 'Производитель',
        'noimage.png');



