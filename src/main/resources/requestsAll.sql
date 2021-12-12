SELECT NOW(); -- вычисляемые таблицы или поля

SELECT id, plan, salary, plan - salary AS diff FROM managers;

SELECT id, qty * price AS total FROM sale_positions;
-- AS - не обязателен
SELECT id, qty * price total FROM sale_positions;

-- postgreSQL operators

-- BETWEEN
SELECT id, qty * price AS total FROM sale_positions
WHERE qty * price BETWEEN  100 AND 1000;

SELECT id, name FROM managers WHERE department IN ('bar', 'rest');
-- аналог
SELECT id, name FROM managers WHERE department = 'bar' OR department = 'rest';

-- like (ilike - ignore case)
SELECT id, name FROM managers WHERE name LIKE 'P%';
-- % любое количество символов
-- _ - ровно один символ ___a - находит все включения
SELECT id, name FROM managers WHERE name ILIKE 'p%';

SELECT id, name FROM managers WHERE name ILIKE '_asha';

SELECT id, name FROM managers WHERE name ILIKE '%a';
-- %x - опасная вещь

-- запомнить: это неправильно
SELECT id, name FROM managers WHERE boss_id = NULL;
-- правильно:
SELECT id, name FROM managers WHERE boss_id IS NULL;
SELECT id, name FROM managers WHERE boss_id IS NOT NULL;

-- statistics
SELECT count(*) FROM managers;
SELECT sum(salary) FROM managers;
SELECT ceil(avg(salary)) FROM managers;
SELECT floor(avg(salary)) FROM managers;

-- RowMapper - возвращает одну строчку, один объект
-- агрегирующая функция возвращает одну строчку
SELECT count(*), sum(salary), ceil(avg(salary)), min(salary), max(salary) FROM managers;

SELECT sum(salary) FROM managers WHERE department = 'bar';
-- SELECT count(*) FROM registrations WHERE complete = TRUE;

SELECT sum(plan-salary) FROM managers;
SELECT sum(plan-salary) / count(*) FROM managers;

-- queryForObject('...', Long.class)
-- в SELECT можно писать только агрегирующие функции, либо теми столбцами, что есть в GROUP BY
-- неправильно:
-- SELECT id, min(salary) FROM managers; не работает

-- статистика в разрезе подразделений, несколько подразделений
SELECT avg(salary) FROM managers GROUP BY department;
-- department можно включать в селект, поскольку по нему группировали
SELECT department, avg(salary) FROM managers GROUP BY department;

-- события и регистрации event_id, status - complete/ incomplete
--SELECT event_id, count(*) FROM events WHERE status = 'complete' GROUP BY event_id;

SELECT product_id, sum(qty) FROM sale_positions
GROUP BY product_id;

-- WHERE - из имеющихся выпускает только те, которые удовлетворяют условию
-- HAVING - фильтрует после GROUP BY
SELECT product_id, sum(qty) FROM sale_positions
GROUP BY product_id HAVING sum(qty) > 10;

-- 1. Вложенные подзапросы - сначала пишем простой запрос, потом усложняем
-- 2. JOIN's
-- связанный подзапрос, для каждого внешнего запроса выполняет внутренний запрос
-- добавляем псевдонимы таблиц (первая буква таблицы)
SELECT id, manager_id,
       (SELECT name FROM managers WHERE id = manager_id) FROM sales;

SELECT s.id, s.manager_id FROM sales AS s;

SELECT s.id, s.manager_id,
       (SELECT m.name FROM managers AS m WHERE m.id = s.manager_id) FROM sales AS s;
-- AS писать не обязательно
SELECT s.id, s.manager_id,
       -- вложенный запрос должен возвращать 1 столбец (обязатель) и 1 строку (иначе будет NULL)
       (SELECT m.name FROM managers m WHERE m.id = s.manager_id) FROM sales s;
-- без manager_id
SELECT s.id,
       (SELECT m.name FROM managers m WHERE m.id = s.manager_id) FROM sales s;
-- связанный запрос
SELECT s.id, s.manager_id,
       (SELECT m.name FROM managers m WHERE m.id = s.manager_id) AS manager_name FROM sales s;

-- не связанный запрос
SELECT m.id, m.name, m.salary - (SELECT avg(mm.salary) FROM managers AS mm) AS diff FROM managers AS m;
-- без AS
SELECT m.id, m.name, m.salary - (SELECT avg(mm.salary) FROM managers mm) diff FROM managers m;

-- JOIN (часто считается быстрее)
-- устаревшая форма (не использовать)
SELECT s.id, s.manager_id, m.name FROM sales s, managers m WHERE s.manager_id = m.id;

-- правильная форма!!! шаг 1:
SELECT s.id, s.manager_id FROM sales s;
-- шаг 2: плюсуем таблицу JOIN + условие
SELECT s.id, s.manager_id FROM sales s JOIN managers m on s.manager_id = m.id;
-- шаг 3: добавляем SELECT нужные данные из заJOIN'енной таблицы
SELECT s.id, s.manager_id, m.name manager_name FROM sales s INNER JOIN managers m ON s.manager_id = m.id;
-- INNER часто не пишется
SELECT s.id, s.manager_id, m.name manager_name FROM sales s JOIN managers m ON s.manager_id = m.id;

-- статистику по продажам каждого менеджера
-- 1. статистика по продажам конкретного товара - !!! идем по товарам и к нему смотрим продажи
-- товар (id, name), sum(qty), sum(qty*price) - итого
SELECT p.id, p.name FROM products p;
SELECT p.id, p.name, (SELECT sum(sp.qty) FROM sale_positions sp WHERE sp.product_id = p.id) sold_qty FROM products p;
SELECT sum(sp.qty) FROM sale_positions sp WHERE sp.product_id = 1;
-- правильно:
SELECT p.id, p.name FROM products p;
SELECT sp.product_id, sum(sp.qty) FROM sale_positions sp GROUP BY sp.product_id; -- физически не существует
-- SELECT sp.product_id, sum(sp.qty) FROM sale_positions sp GROUP BY sp.product_id ORDER BY sp.product_id;

SELECT p.id, p.name, ps.total_qty FROM products p
                                           JOIN (
    SELECT sp.product_id, sum(sp.qty) AS total_qty FROM sale_positions sp GROUP BY sp.product_id)
    AS ps ON p.id = ps.product_id ORDER BY p.id;

-- или используем VIEW - прописываем с схеме и пользуемся
-- coalesce(a, b, c) - выбирает первый не NULL
SELECT p.id, p.name, coalesce(pss.total_qty, 0) total_qty FROM products p
LEFT JOIN product_sale_stats pss on p.id = pss.product_id ORDER BY total_qty;

DELETE FROM sale_positions WHERE id = 5;

-- 1. Есть подзапросы (но лучше JOIN)
-- 2. JOIN:
-- - INNER JOIN
-- - LEFT OUTER JOIN - берет и значения, где NULL

-- 2. Кто и сколько продаж сделал
-- 3. Кто и на сколько продал

-- Соединяем таблицы по вертикали (например, когда таблицу Book объединяем с таблицей Smartphone):
SELECT p.id, p.name FROM products p
UNION ALL
SELECT m.id, m.name FROM managers m;


