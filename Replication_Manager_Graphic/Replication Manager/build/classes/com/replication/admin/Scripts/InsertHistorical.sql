CREATE TABLE IF NOT EXISTS `Historial` (
  `table_name` VARCHAR(64) NOT NULL,
  `action` VARCHAR(10) NULL,
  `row_pk` INT(11) NOT NULL,
  `field_name` VARCHAR(64) NULL,
  `old_value` VARCHAR(64) NULL,
  `new_value` VARCHAR(64) NULL,
  `timestamp` VARCHAR(45) NULL,
  `consultado` TINYINT(1) NULL DEFAULT 0,
  `idHistorial` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idHistorial`))
ENGINE = InnoDB;