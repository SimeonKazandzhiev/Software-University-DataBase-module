-- problem 1
SELECT `first_name`, `last_name` FROM `employees`
WHERE LEFT(`first_name`,2) = 'Sa';

-- problem 2
SELECT `first_name`, `last_name` FROM `employees`
WHERE `last_name` LIKE '%ei%';

-- problem 3
SELECT `first_name` FROM `employees`
WHERE `department_id` IN (3,10) 
AND YEAR(`hire_date`) BETWEEN 1995 AND 2005
ORDER BY `employee_id`;

-- problem 4
SELECT `first_name`, `last_name` FROM `employees`
WHERE `job_title` NOT LIKE '%engineer%';

-- problem 5
SELECT `name` FROM `towns`
WHERE CHAR_LENGTH(`name`) = 5 OR CHAR_LENGTH(`name`) = 6 # or CHAR_LENGTH(`name`) IN (5,6)
ORDER BY `name`;

-- problem 6
SELECT `town_id` , `name` FROM `towns`
WHERE `name` LIKE 'M%' OR `name` LIKE 'M%' OR `name` LIKE 'B%' OR `name` LIKE 'E%'
ORDER BY `name`;

-- problem 7
SELECT `town_id` , `name` FROM `towns`
WHERE LEFT(`name`,1) NOT IN ('R','B','D')
ORDER BY `name` ASC;

-- problem 8
CREATE VIEW `v_employees_hired_after_2000` AS
SELECT `first_name`, `last_name` FROM `employees`
WHERE YEAR(`hire_date`) > 2000;

SELECT * FROM `v_employees_hired_after_2000`;

-- problem 9
SELECT `first_name`, `last_name` FROM `employees`
WHERE char_length(`last_name`) = 5;