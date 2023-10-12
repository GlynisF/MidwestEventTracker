DROP TABLE IF EXISTS `test_et`.`user`;

CREATE TABLE IF NOT EXISTS `test_et`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(255) NULL,
  `last_name` VARCHAR(255) NULL,
  `user_name` VARCHAR(255) NULL,
  `email_address` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NULL,
  `date_of_birth` DATE NOT NULL,
  `gender` CHAR(3) NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC) VISIBLE,
  UNIQUE INDEX `email_address_UNIQUE` (`email_address` ASC) VISIBLE);


use test_et;
insert into user values (1, 'Joe', 'Coyne', 'jcoyne', 'jcoyne@email.com', 'supersecret1', '1964-04-01', 'F');
insert into user values (2, 'Fred', 'Hensen', 'fhensen', 'fhansen@email.com', 'supersecret2', '1988-05-08', 'M');
insert into user values (3, 'Barney', 'Curry', 'bcurry', 'bcurry@email.com', 'supersecret3',  '1947-11-11', 'NB');
insert into user values (4, 'Karen', 'Mack', 'kmack', 'kmack@email.com', 'supersecret4', '1986-07-08', 'NS');
insert into user values (5, 'Dianne', 'Klein', 'dklein', 'dklein@email.com', 'supersecret5', '1991-01-22', 'F');
insert into user values (6, 'Dawn', 'Tillman', 'dtillman', 'dtillman@email.com', 'supersecret6', '1979-08-30', 'F');