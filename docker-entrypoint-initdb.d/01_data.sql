INSERT INTO citizenship
VALUES (1,'Гражданин РФ'),
       (2,'Иностранный гражданин');

INSERT INTO country
VALUES (1,'Российская федерация', 0, 'Россия', '240'),
       (2,'Соединенные Штаты Америки', 0, 'США', '50');

INSERT INTO organization
VALUES (1,'Спортсмены РФ', 0),
       (2,'Иностранные спортсмены', 0);

INSERT INTO competitions
VALUES (1,'Шахматы', 0, 0),
       (2,'Стрельба из ружья', 0, 1);

INSERT INTO weapon
VALUES (1,'Пистолет', 0),
       (2,'Ружье', 0);

INSERT INTO person
VALUES (1,'Иванов','Иван','Петрович','01.02.2000','8927453847','primer@mail.ru','1233','7895435','07.02.2018',
        '9876','1234','Казань','Казань', 1, 1, 'm',false,'03.12.2021'),
       (2,'Вейшарт','Линда','Адамс','07.12.1999','785496214','primer@gmail.com','673d','365798',to_date('28.08.2019', 'dd.mm.yyyy'),
        '9876','1234','Нью-Йорк','Нью-Йорк', 1, 1, 'w',false,'03.12.2021');

INSERT INTO person_competitions
VALUES (1,1,2,1),
       (2,2,2,2);

INSERT INTO person_competitions_weapon
VALUES (1,1,2,'Калашников','S123','N345','02.02.2018','КАЗАНЬ', 2,'TEXT_FILE'),
       (2,2,2,'MRL-2','S234','N567','04.06.2017','НЬЮ_ЙОРК',2,'TEXT_FILE');