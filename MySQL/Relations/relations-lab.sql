CREATE TABLE `mountains`(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	name VARCHAR(45) NOT NULL
);

DROP TABLE `peaks`;
CREATE TABLE `peaks`(
	`id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	`name` VARCHAR(45) NOT NULL,
    `mountain_id` INT,
    CONSTRAINT fk_peaks_mountains FOREIGN KEY(`mountain_id`)
		REFERENCES `mountains`(`id`) 
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

SELECT v.`driver_id`, v.`vehicle_type`, 
	CONCAT(c.`first_name`, ' ', c.`last_name`) AS `driver_name`
FROM `vehicles` AS v JOIN `campers` AS c
	ON v.`driver_id` = c.`id`;
    
SELECT `starting_point` AS `route_starting_point`,
	`end_point` AS `route_ending_point`,
	`leader_id`,
	CONCAT(c.`first_name`, ' ', c.`last_name`) AS `leader_name`
FROM `routes` AS r JOIN `campers` AS c
	ON r.`leader_id` = c.`id`;
    
ALTER TABLE `peaks`
DROP FOREIGN KEY `fk_peaks_mountains`;

ALTER TABLE `peaks`
ADD CONSTRAINT fk_peaks_mountains FOREIGN KEY(`mountain_id`)
		REFERENCES `mountains`(`id`) 
        ON UPDATE CASCADE
        ON DELETE CASCADE;