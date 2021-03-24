CREATE TABLE IF NOT EXISTS `soft_uni`.`addresses` (
  `address_id` INT(10) NOT NULL AUTO_INCREMENT,
  `address_text` VARCHAR(100) NOT NULL,
  `town_id` INT(10) NOT NULL DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  UNIQUE INDEX `PK_Addresses` (`address_id` ASC),
  INDEX `fk_addresses_towns` (`town_id` ASC),
  CONSTRAINT `fk_addresses_towns`
    FOREIGN KEY (`town_id`)
    REFERENCES `soft_uni`.`towns` (`town_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 292
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `soft_uni`.`addresses` (
  `address_id` INT(10) NOT NULL AUTO_INCREMENT,
  `address_text` VARCHAR(100) NOT NULL,
  `town_id` INT(10) DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  UNIQUE INDEX `PK_Addresses` (`address_id` ASC),
  INDEX `fk_addresses_towns` (`town_id` ASC),
  CONSTRAINT `fk_addresses_towns`
    FOREIGN KEY (`town_id`)
    REFERENCES `soft_uni`.`towns` (`town_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 292
DEFAULT CHARACTER SET = utf8