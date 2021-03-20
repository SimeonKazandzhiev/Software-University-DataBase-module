SELECT `department_id`, COUNT(`id`) AS `Number of employees` 
FROM `employees`
GROUP BY `department_id`
ORDER BY `Number of employees` DESC;

SELECT `department_id`, 
CEILING(`salary` / 1000) * 1000 AS `Number of salaries`,
COUNT(*) AS `Count` 
FROM `employees`
GROUP BY `department_id`, `Number of salaries`
ORDER BY `department_id`;

SELECT `department_id`, COUNT(`salary`) AS `Number of salaries` 
FROM `employees`
GROUP BY `department_id`
ORDER BY `department_id`;

SELECT `department_id`, 
	ROUND(AVG(`salary`), 2) AS `Average Salary`
FROM `employees`
GROUP BY `department_id`
ORDER BY `department_id`;

SELECT `department_id`, 
	ROUND(MIN(`salary`), 2) AS `Min Salary`
FROM `employees`
GROUP BY `department_id`
HAVING `Min Salary` > 800
ORDER BY `department_id`;

SELECT COUNT(*) AS 'Count' FROM `products`
WHERE category_id = 2 AND price > 8;

SELECT `category_id`, 
	ROUND(AVG(`price`), 2) AS `Average Price`,
	ROUND(MIN(`price`), 2) AS `Cheapest Product`,
	ROUND(MAX(`price`), 2) AS `Most Expensive Product` 
FROM `products`
GROUP BY `category_id`;

# Additional Examples
SELECT `category_id`, `country`, 
	COUNT(`price`) AS `Number of Products`,
    ROUND(AVG(`price`), 2) AS `Average Price`
FROM `products`
GROUP BY `category_id`, `country`
HAVING `Number of Products` >= 2;

SELECT IF(GROUPING(`country`) = 1, 'Total:', `country`) AS `country`,  
	COUNT(`price`) AS `Number of Products`,
    ROUND(SUM(`price`), 2) AS `Total Price`
FROM `products`
GROUP BY `country` WITH ROLLUP
HAVING `Number of Products` >= 2;

SELECT   
	IF(GROUPING(`category_id`) = 1, 'All categories', `category_id`) AS `category_id`,
    IF(GROUPING(`country`) = 1, 'All countries', `country`) AS `country`,
	COUNT(`price`) AS `Number of Products`,
    ROUND(SUM(`price`), 2) AS `Total Price`
FROM `products`
GROUP BY `category_id`, `country` WITH ROLLUP;

SELECT 
	IF(GROUPING(`category_id`) = 1, 'All categories', `category_id`) AS `category_id`,
    IF(GROUPING(`country`) = 1, 'All countries', `country`) AS `country`,
	COUNT(`price`) AS `Number of Products`,
    ROUND(SUM(`price`), 2) AS `Total Price`
FROM `products`
GROUP BY `category_id`, `country` WITH ROLLUP;