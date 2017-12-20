SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;


CREATE TABLE IF NOT EXISTS `mydb`.`users` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password_hash` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `mydb`.`tour_types` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `tour_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `tour_type_UNIQUE` (`tour_type` ASC))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `mydb`.`tours` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `hot` TINYINT ZEROFILL NOT NULL,
  `price` DOUBLE NULL,
  `duration` INT UNSIGNED NOT NULL,
  `agent_id` INT UNSIGNED NULL,
  `active` TINYINT NOT NULL,
  `tour_types_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `tour_types_id`),
  INDEX `agent_id_idx` (`agent_id` ASC),
  INDEX `fk_tours_tour_types1_idx` (`tour_types_id` ASC),
  CONSTRAINT `agent_id`
    FOREIGN KEY (`agent_id`)
    REFERENCES `mydb`.`users` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_tours_tour_types1`
    FOREIGN KEY (`tour_types_id`)
    REFERENCES `mydb`.`tour_types` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `mydb`.`orders` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `date` VARCHAR(45) NOT NULL,
  `active` TINYINT NOT NULL,
  `tour_id` INT UNSIGNED NOT NULL,
  `user_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `tour_id_idx` (`tour_id` ASC, `user_id` ASC),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `tour_id`
    FOREIGN KEY (`tour_id` , `user_id`)
    REFERENCES `mydb`.`tours` (`id` , `id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `customer_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`users` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = dec8
COLLATE = dec8_bin;


CREATE TABLE IF NOT EXISTS `mydb`.`permission_names` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `mydb`.`permissions` (
  `user_id` INT UNSIGNED NOT NULL,
  `permission_name_id` INT UNSIGNED NOT NULL,
  INDEX `user_id_idx` (`user_id` ASC),
  INDEX `permission_name_id_idx` (`permission_name_id` ASC),
  PRIMARY KEY (`user_id`, `permission_name_id`),
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`users` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `permission_name_id`
    FOREIGN KEY (`permission_name_id`)
    REFERENCES `mydb`.`permission_names` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

USE `mydb` ;


CREATE TABLE IF NOT EXISTS `mydb`.`view1` (`id` INT);

-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `mydb`.`view2` (`id` INT);


DROP TABLE IF EXISTS `mydb`.`view1`;
USE `mydb`;

DROP TABLE IF EXISTS `mydb`.`view2`;
USE `mydb`;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;