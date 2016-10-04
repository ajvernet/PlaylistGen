
CREATE TABLE `series` (
	`ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`Name` VARCHAR(500) NULL DEFAULT NULL,
	PRIMARY KEY (`ID`)
)

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
;

CREATE TABLE `users` (
	`ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`Email` VARCHAR(500) NULL DEFAULT NULL,
	PRIMARY KEY (`ID`)
)

;

CREATE TABLE `playlists` (
	`ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`Name` VARCHAR(500) NULL DEFAULT NULL,
	User_ID int unsigned not null,
	PRIMARY KEY (`ID`),
	foreign key (User_ID) references users(ID)
)

;


CREATE TABLE `episodes_tags` (
	Episode_ID INT(10) UNSIGNED NOT NULL,
	Tag VARCHAR(50) NOT NULL,

	FOREIGN KEY (Episode_ID) REFERENCES episodes (ID),
	PRIMARY KEY (Episode_ID, Tag)

)

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
;

CREATE TABLE `series_tags` (
	`Series_ID` INT(10) UNSIGNED NOT NULL,
	`Tag` VARCHAR(50) NOT NULL,

	CONSTRAINT `series_tags_ibfk_1` FOREIGN KEY (`Series_ID`) REFERENCES `series` (`ID`),
	PRIMARY KEY (Series_ID, Tag)

)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

