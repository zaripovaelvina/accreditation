INSERT INTO persons
VALUES (1, 'Иван', 'Иванов', 'Петрович', '01.12.2010', '+789645824', 'test@mail.ru', 1, 1, 'm', false,
        '2021-12-08 08:33:40.750000 +00:00'),
       (1, 'Лин', 'Тейлор', 'Кирк', '29.07.2013', '+785675477', 'test2@mail.ru', 2, 2, 'm', false,
        '2021-12-08 08:33:40.750000 +00:00');

INSERT INTO events
VALUES (1, 'Чемпионат мира по стрельбе', 'FALSE');

INSERT INTO persons
VALUES (1, 'Ф', 'И', 'О', to_date('10.10.2000', 'dd.mm.yyyy'), '12345678', 'test@mail.ru', '1', '1', 'M', FALSE);


INSERT INTO applications (person_id, event_id, disciplines_id, organization_id, weapon_id, weapon_manufacturer,
                         permit_serial, permit_num, permit_date, permit_manufacturer, image)
VALUES ('3', '3', '2', '1', '3', 'Калашников', 'AH', '678', to_date('10.03.2020', 'dd.mm.yyyy'), 'Производитель',
        'noimage.png');

SELECT id, email FROM persons WHERE id = '1';

SELECT p.id, p.email,
       (SELECT a.person_id FROM applications a WHERE p.id = a.person_id) FROM persons p;

UPDATE applications SET status = '1'
WHERE id = 1 AND removed = FALSE;

SELECT p.id, p.email, a.status FROM persons p, applications a WHERE p.id = a.person_id AND a.status = '1';

INSERT INTO persons (name, surname, patronymic, birthday, phone, email,
                    citizenship_id, country_id, gender, image, removed, created)
VALUES ('Ivanov', 'Ivan', 'Ivanovich', to_date('10.10.2000', 'dd.mm.yyyy'), '12345678', 'elvina_zaripova@mail.ru',
        '1', '1', 'M', 'noimage.png', FALSE, current_date),
       ('Abramova', 'Alina', 'Alexandrovna', to_date('08.08.2003', 'dd.mm.yyyy'), '12345678', 'elvina_zaripova@mail.ru',
        '1', '1', 'W', 'noimage.png', FALSE, current_date),
       ('Rimanass', 'Alina', 'Albertovna', to_date('15.09.2003', 'dd.mm.yyyy'), '12345678', 'elvina_zaripova@mail.ru',
        '2', '1', 'W', 'noimage.png', FALSE, current_date),
       ('Vasiliev', 'Vasiliy', 'Vasilievich', to_date('08.08.2000', 'dd.mm.yyyy'), '12345678', 'elvina_zaripova@mail.ru',
        '1', '1', 'M', 'noimage.png', FALSE, current_date),
       ('Dmitriev', 'Ivan', 'Petrovich', to_date('09.08.2003', 'dd.mm.yyyy'), '12345678', 'elvina_zaripova@mail.ru',
        '1', '1', 'M', 'noimage.png', FALSE, current_date),
       ('Andersen', 'Alex', 'Koy', to_date('01.12.2000', 'dd.mm.yyyy'), '12345678', 'elvina_zaripova@mail.ru',
        '2', '1', 'M', 'noimage.png', FALSE, current_date);


SELECT p.id, p.email, a.status FROM persons p, applications a
WHERE p.id = a.person_id AND a.status = 1 and  p.id = 8;

SELECT p.email FROM persons p, applications a
WHERE p.id = a.person_id AND a.status = :status and  p.id = :id;


INSERT INTO applications (person_id, event_id, disciplines_id, organization_id, weapon_id, weapon_manufacturer,
                         permit_serial, permit_num, permit_date, permit_manufacturer, image)
VALUES ('1', '3', '2', '1', '3', 'Калашников', 'AH', '678', to_date('10.03.2020', 'dd.mm.yyyy'), 'Производитель',
        'noimage.png');

UPDATE persons SET email = 'test999java@gmail.com' WHERE id = '1';

UPDATE applications SET winner = TRUE WHERE id = 2 RETURNING id, winner;

SELECT a.id  application_id, a.event_id, p.id member, p.name, a.winner FROM persons p JOIN applications a on p.id = a.person_id
    JOIN events e on a.event_id = e.id
WHERE a.event_id = 6 AND e.completed AND a.winner
;

SELECT id, name, completed FROM events WHERE completed = TRUE;

UPDATE events SET completed = FALSE WHERE id = 8 RETURNING id, name, completed;

UPDATE applications SET winner = TRUE WHERE person_id = 1 AND event_id = 3;

SELECT count(*) from members GROUP BY event_id;

SELECT count(*) from members GROUP BY event_id;

CREATE VIEW membersss AS
SELECT a.id application_id,
       a.event_id,
       p.id member,
       p.surname,
       p.name,
       p.patronymic,
       p.birthday,
       p.phone,
       p.email,
       a.winner,
       a.status
FROM persons p
         JOIN applications a on p.id = a.person_id
         JOIN events e on a.event_id = e.id;


SELECT application_id, event_id, member, surname, name, patronymic,
       EXTRACT(EPOCH FROM birthday) AS birthday, phone, email, winner, status
FROM members
WHERE event_id = 5 AND status = 1
ORDER BY event_id;



