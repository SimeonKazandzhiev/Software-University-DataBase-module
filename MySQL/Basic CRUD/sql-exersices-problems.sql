SELECT * FROM `departments`
ORDER BY `department_id`;


SELECT `name` FROM `departments`;

SELECT `first_name` , `last_name`, `salary` FROM `employees`;

SELECT `first_name`, `middle_name` , `last_name` FROM `employees`;


SELECT concat(`first_name`,'.' , `last_name`, '@softuni.bg') AS 'full_email_address'
FROM `employees`;


SELECT DISTINCT `salary` FROM `employees`;


SELECT * FROM `employees`
WHERE `job_title` = 'Sales Representative'
ORDER BY `employee_id`;

SELECT `first_name`, `last_name`, `job_title` FROM `employees`
WHERE `salary` BETWEEN 20000 AND 30000;

SELECT `first_name` , `middle_name`, `last_name` AS 'Full Name' FROM `employees`
WHERE `salary` IN (25000, 14000, 12500, 23600);

SELECT `first_name`, `last_name` FROM `employees`
WHERE `manager_id` IS NULL;

SELECT `first_name`, `last_name`, `salary` FROM `employees`
WHERE `salary` > 50000
ORDER BY `salary` DESC;

SELECT `first_name` , `last_name` FROM `employees`
ORDER BY `salary` DESC
LIMIT 5;

SELECT `first_name` , `last_name` FROM `employees`
WHERE `department_id` != 4;


SELECT * FROM `employees`
ORDER BY `salary` DESC, `first_name` ASC, `last_name` DESC, `middle_name` ASC;


CREATE VIEW `v_employees_salaries` AS
SELECT `first_name` , `last_name` , `salary` FROM `employees`;


SELECT * FROM `v_employees_salaries`;

CREATE VIEW `v_employees_job_titles` AS
SELECT concat_ws(' ',`first_name`, `middle_name`, `last_name` )AS 'full_name', `job_title` 
FROM `employees`;

SELECT * FROM `v_employees_job_titles`;


SELECT DISTINCT `job_title` FROM `employees`
ORDER BY `job_title`;

SELECT `project_id`, `name`, `description`, `start_date`, `end_date` FROM `projects`
ORDER BY `start_date`, `name`;

SELECT `first_name`, `last_name`, `hire_date` FROM `employees`
ORDER BY `hire_date` DESC
LIMIT 7;

UPDATE `employees`
SET `salary` = `salary` * 1.12
WHERE `department_id` IN (1, 2, 4, 11);

SELECT `salary` FROM `employees`;
