DROP DATABASE PlaylistDemo;
CREATE DATABASE PlaylistDemo;

USE PlaylistDemo;

CREATE TABLE Episodes (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	episodeId INT UNSIGNED NOT NULL,
	name VARCHAR(500)NULL DEFAULT NULL,
	genreId INT UNSIGNED NULL DEFAULT NULL,
	description VARCHAR(5000) NULL DEFAULT NULL,
	duration INT UNSIGNED NULL DEFAULT 0,
	fileUrl VARCHAR(300) NULL DEFAULT NULL,
	showId INT UNSIGNED NULL DEFAULT NULL,
	showName VARCHAR(500) NULL DEFAULT NULL,
	thumbUrl VARCHAR(300) NULL DEFAULT NULL,
	PRIMARY KEY (ID),
	UNIQUE (episodeId)
)
;

CREATE TABLE Users
(id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
email VARCHAR(255) NOT NULL,
salt VARCHAR(50) NOT NULL,
hash VARCHAR(50) NOT NULL,
firstname VARCHAR(50) NOT NULL,
lastname VARCHAR(50) NOT NULL,
street VARCHAR(50),
city VARCHAR(50),
state VARCHAR(2),
zip VARCHAR(9),
UNIQUE (email))
;


CREATE TABLE Playlists (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(500) NULL DEFAULT NULL,
	userId int unsigned not null,
	targetDuration int unsigned NULL DEFAULT NULL,
	currentDuration int unsigned NULL DEFAULT NULL,
	PRIMARY KEY (id),
	foreign key (userID) references users(id) on delete cascade
)
;

CREATE TABLE PlaylistEpisodes (
	playlistId INT UNSIGNED NOT NULL,
	episodeId INT UNSIGNED NOT NULL,
	sortOrder INT UNSIGNED NOT NULL,	
  	CONSTRAINT playlistepisodes_ibfk_1 FOREIGN KEY (playlistId) REFERENCES Playlists(id) on delete cascade,
  	CONSTRAINT playlistepisodes_ibfk_2 FOREIGN KEY (episodeId) REFERENCES Episodes(id) on delete cascade,
  	PRIMARY KEY (playlistId, episodeId)
);

