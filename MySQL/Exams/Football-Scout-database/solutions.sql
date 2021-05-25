# INSERT 
INSERT INTO `coaches`(first_name, last_name,salary,coach_level)
SELECT first_name , last_name , salary * 2 , 
char_length(first_name)
FROM players
WHERE age >= 45;

#UPDATE
UPDATE coaches
SET coach_level = coach_level + 1
WHERE LEFT(first_name,1) = 'A'
AND id IN (
SELECT coach_id 
FROM players_coaches);

#DELETE
DELETE FROM players
WHERE age >= 45;

#5
SELECT first_name, age , salary
FROM players
ORDER BY salary DESC;

#6
SELECT p.id, CONCAT(p.first_name,' ',p.last_name) AS full_name, p.age,p.position,p.hire_date
FROM players AS p
JOIN skills_data AS sdata
ON p.skills_data_id = sdata.id
WHERE p.age < 23 AND p.position = 'A' AND p.hire_date IS NULL
AND sdata.strength > 50
ORDER BY p.salary , p.age;

#7
SELECT t.name, t.established, t.fan_base ,COUNT(p.id) as pcount
FROM teams AS t
LEFT JOIN players AS p
ON p.team_id = t.id
GROUP BY t.id
ORDER BY pcount DESC, t.fan_base DESC;

#8
SELECT MAX(sd.speed) AS max_speed, t.name
FROM towns AS t
LEFT JOIN stadiums AS s
ON t.id = s.town_id
LEFT JOIN teams AS te
ON s.id = te.stadium_id
LEFT JOIN players AS p
ON te.id = p.team_id
LEFT JOIN skills_data AS sd
ON sd.id = p.skills_data_id
WHERE te.name != 'Devify'
GROUP BY t.id
ORder BY max_speed DESC, t.name;

#9
SELECT c.name, COUNT(p.id) AS total_c_players,
 SUM(p.salary) AS total_salary
FROM countries AS c
LEFT JOIN towns AS t
ON c.id = t.country_id
LEFT JOIN stadiums AS s
ON t.id = s.town_id
LEFT JOIN teams AS te
ON s.id = te.stadium_id
LEFT JOIN players AS p
ON te.id = p.team_id
GROUP BY c.id
ORDER BY total_c_players DESC, c.name;

