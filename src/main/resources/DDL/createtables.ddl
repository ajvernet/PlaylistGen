CREATE TABLE `series` (
	`ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`Name` VARCHAR(500) NULL DEFAULT NULL,
	PRIMARY KEY (`ID`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `episodes` (
	`ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`Series_ID` INT(10) UNSIGNED NOT NULL,
	`Name` VARCHAR(500) NULL DEFAULT NULL,
	`Duration` INT(11) NOT NULL,
	`Published` DATE NOT NULL,
	`URL` VARCHAR(300) NULL DEFAULT NULL,
	PRIMARY KEY (`ID`),
	INDEX `Series_ID` (`Series_ID`),
	CONSTRAINT `episodes_ibfk_1` FOREIGN KEY (`Series_ID`) REFERENCES `series` (`ID`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `playlists` (
	`ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`Name` VARCHAR(500) NULL DEFAULT NULL,
	PRIMARY KEY (`ID`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `states` (
	`ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`abbrev` VARCHAR(2) NULL DEFAULT NULL,
	PRIMARY KEY (`ID`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `tags` (
	`ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(20) NULL DEFAULT NULL,
	PRIMARY KEY (`ID`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `users` (
	`ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`Email` VARCHAR(500) NULL DEFAULT NULL,
	PRIMARY KEY (`ID`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `episodes_tags` (
	`Episode_ID` INT(10) UNSIGNED NOT NULL,
	`Tag_ID` INT(10) UNSIGNED NOT NULL,
	INDEX `Episode_ID` (`Episode_ID`),
	INDEX `Tag_ID` (`Tag_ID`),
	CONSTRAINT `episodes_tags_ibfk_1` FOREIGN KEY (`Episode_ID`) REFERENCES `episodes` (`ID`),
	CONSTRAINT `episodes_tags_ibfk_2` FOREIGN KEY (`Tag_ID`) REFERENCES `tags` (`ID`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `playlists_episodes` (
	`Series_ID` INT(10) UNSIGNED NOT NULL,
	`Episode_ID` INT(10) UNSIGNED NOT NULL,
	`Episode_Ordinal` INT(10) UNSIGNED NOT NULL,
	INDEX `Series_ID` (`Series_ID`),
	INDEX `Episode_ID` (`Episode_ID`),
	CONSTRAINT `playlists_episodes_ibfk_1` FOREIGN KEY (`Series_ID`) REFERENCES `series` (`ID`),
	CONSTRAINT `playlists_episodes_ibfk_2` FOREIGN KEY (`Episode_ID`) REFERENCES `episodes` (`ID`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `series_episodes` (
	`Series_ID` INT(10) UNSIGNED NOT NULL,
	`Episode_ID` INT(10) UNSIGNED NOT NULL,
	INDEX `Series_ID` (`Series_ID`),
	INDEX `Episode_ID` (`Episode_ID`),
	CONSTRAINT `series_episodes_ibfk_1` FOREIGN KEY (`Series_ID`) REFERENCES `series` (`ID`),
	CONSTRAINT `series_episodes_ibfk_2` FOREIGN KEY (`Episode_ID`) REFERENCES `episodes` (`ID`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `series_tags` (
	`Series_ID` INT(10) UNSIGNED NOT NULL,
	`Tag_ID` INT(10) UNSIGNED NOT NULL,
	INDEX `Series_ID` (`Series_ID`),
	INDEX `Tag_ID` (`Tag_ID`),
	CONSTRAINT `series_tags_ibfk_1` FOREIGN KEY (`Series_ID`) REFERENCES `series` (`ID`),
	CONSTRAINT `series_tags_ibfk_2` FOREIGN KEY (`Tag_ID`) REFERENCES `tags` (`ID`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `users_playlists` (
	`User_ID` INT(10) UNSIGNED NOT NULL,
	`PlayList_ID` INT(10) UNSIGNED NOT NULL,
	INDEX `User_ID` (`User_ID`),
	INDEX `PlayList_ID` (`PlayList_ID`),
	CONSTRAINT `users_playlists_ibfk_1` FOREIGN KEY (`User_ID`) REFERENCES `users` (`ID`),
	CONSTRAINT `users_playlists_ibfk_2` FOREIGN KEY (`PlayList_ID`) REFERENCES `playlists` (`ID`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

