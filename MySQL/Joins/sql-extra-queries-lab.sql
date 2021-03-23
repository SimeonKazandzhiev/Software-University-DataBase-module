  
SELECT e.employee_id id, e.first_name, e.last_name, d.`name` department, a.address_text address, t.`name` town
FROM `employees` AS e LEFT JOIN `departments` d ON d.department_id = e.department_id
JOIN `addresses` a ON e.address_id = a.address_idaddresses
JOIN `towns` t ON a.town_id = t.town_id
ORDER BY e.employee_id;

SELECT e.employee_id id, e.first_name, e.last_name, d.`name` department, a.address_text address, t.`name` town
FROM `departments` d, `employees` e, `addresses` a, towns t
WHERE d.department_id = e.department_id AND e.address_id = a.address_id AND a.town_id = t.town_id
ORDER BY e.employee_id;

SELECT e.employee_id id, e.first_name, e.last_name, d.`name` 
	department, a.address_text address, t.`name` town
FROM `departments` d RIGHT JOIN `employees` AS e ON d.department_id = e.department_id, 
	`addresses` a, towns t
WHERE e.address_id = a.address_id AND a.town_id = t.town_id
ORDER BY e.employee_id;


CREATE INDEX ind_employees_job_title
ON `employees`(`job_title`);

DROP INDEX ind_employees_job_title
ON `employees`;