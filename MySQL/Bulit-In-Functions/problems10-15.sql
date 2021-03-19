-- problem 10
SELECT `country_name`, `iso_code` FROM `countries`
WHERE `country_name` LIKE '%A%A%A%'
ORDER BY `iso_code`;

-- problem 11 
SELECT `peak_name`, `river_name`,
LOWER(CONCAT(`peak_name`,SUBSTRING(`river_name`,2))) AS 'mix'
FROM `peaks`  , `rivers` 
WHERE RIGHT(`peak_name`,1) = LEFT(`river_name`,1)
ORDER BY `mix`;

-- problem 12
SELECT `name`,`start` FROM `games`
WHERE YEAR(`start`) BETWEEN 2011 AND 2012
ORDER BY `start`
LIMIT 50;

-- problem 13
SELECT `user_name` , SUBSTRING(`email`,LOCATE('@',`email`) + 1) AS 'Email Provider'
FROM `users`
ORDER BY `Email Provider`, `user_name` ;

-- problem 14
SELECT `user_name`,`ip_address` FROM `users`
WHERE `ip_address` LIKE '___.1%.%.___'
ORDER BY `user_name`;

-- problem 15
SELECT `name` AS 'game',
(
CASE
	WHEN HOUR(`start`) BETWEEN 0 AND 11 THEN 'Morning'
    WHEN HOUR(`start`) BETWEEN 12 AND 17 THEN 'Afternoon'
    WHEN HOUR(`start`) BETWEEN 18 AND 23 THEN 'Evening'
	END
)AS 'Part of the Day', 
(
CASE 
	WHEN `duration` <= 3 THEN 'Extra Short'
	WHEN `duration` BETWEEN 4 AND 6 THEN 'Short'
	WHEN `duration` BETWEEN 7 AND 10 THEN 'Long'
	ELSE 'Extra Long'
	END
) AS 'Duration'

 FROM `games`;




