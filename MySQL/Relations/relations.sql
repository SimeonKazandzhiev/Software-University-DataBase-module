CREATE TABLE `mountains` (
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(45) NOT NULL
);

CREATE TABLE `peaks` (
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(45) NOT NULL,
    mountain_id INT NOT NULL,
    CONSTRAINT fk_mointain_id FOREIGN KEY(mountain_id) REFERENCES `mountains`(id) 
		ON UPDATE CASCADE ON DELETE CASCADE
);

SELECT CONCAT(c.`first_name`, ' ', c.`last_name`) AS `driver_name`, c.id AS `driver_id`, v.`vehicle_type` FROM `vehicles` AS v, `campers` as c
WHERE v.driver_id = c.id; 

SELECT r.`starting_point` AS `route_starting_point`, r.`end_point` AS `route_ending_point`,	
	r.`leader_id`, CONCAT(c.`first_name`, ' ', c.`last_name`) AS `leader_name` 
    FROM `routes` r LEFT JOIN `campers` c ON r.`leader_id` = c.`id`;
    
ALTER TABLE `peaks`	
DROP FOREIGN KEY `fk_mointain_id`;
ALTER TABLE `peaks`	
ADD CONSTRAINT fk_mointain_id FOREIGN KEY(mountain_id) REFERENCES `mountains`(id) 
		ON UPDATE RESTRICT ON DELETE CASCADE;