CREATE DATABASE playlistdb;

USE playlistdb;

CREATE TABLE `shows` (
	`ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`Name` VARCHAR(500) NULL DEFAULT NULL,
	PRIMARY KEY (`ID`)
)

;
CREATE TABLE `episodes` (

            .add("id")
            .add("episodeId")
            .add("name")
            .add("duration")
            .add("fileUrl")
            .add("showId")
            .add("playlistId");
	`ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`episodeId` INT(10) UNSIGNED NOT NULL,
	`name` VARCHAR(500) NULL DEFAULT NULL,
	`duration` INT(11) NOT NULL,
	`fileUrl` VARCHAR(300) NULL DEFAULT NULL,
	`showId` INT(10) UNSIGNED NOT NULL,
	`playlistId` INT(10) UNSIGNED NOT NULL,
	PRIMARY KEY (`ID`),
	INDEX `Series_ID` (`Series_ID`),
	CONSTRAINT `episodes_ibfk_1` FOREIGN KEY (`Series_ID`) REFERENCES `series` (`ID`)
)
;

CREATE TABLE users
(id INT AUTO_INCREMENT PRIMARY KEY,
email VARCHAR(255) NOT NULL,
salt VARCHAR(50) NOT NULL,
hash VARCHAR(50) NOT NULL,
firstname VARCHAR(50) NOT NULL,
lastname VARCHAR(50) NOT NULL,
street VARCHAR(50) NOT NULL,
city VARCHAR(50) NOT NULL,
state VARCHAR(2) NOT NULL,
zip VARCHAR(9) NOT NULL,
UNIQUE (email))
ENGINE = InnoDB;


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
	PRIMARY KEY (Tag, Series_ID)

)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

