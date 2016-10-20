package org.ssa.ironyard.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssa.ironyard.dao.EpisodeDAO;
import org.ssa.ironyard.dao.PlaylistDAO;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Playlist;

@Service
public class PlaylistService {

    private final PlaylistDAO playlistDao;
    private final EpisodeDAO episodeDao;

    Logger LOGGER = LogManager.getLogger(PlaylistService.class);

    @Autowired
    public PlaylistService(PlaylistDAO playlistDao, EpisodeDAO episodeDao) {
	this.playlistDao = playlistDao;
	this.episodeDao = episodeDao;
    }

    public Playlist getPlaylistById(int id) {
	return playlistDao.readByPlaylistId(id);
    }

    public List<Playlist> getPlaylistsByUser(int userId) {
	return playlistDao.readByUser(userId);
    }

    public boolean deletePlaylist(int id) {
	return playlistDao.delete(id);
    }

    public Playlist savePlaylist(Playlist playlist) {
	LOGGER.debug("Saving playlist");

	Integer playlistId = playlist.getId() == null ? playlistDao.insert(playlist).getId()
		: playlistDao.update(playlist).getId();
	LOGGER.debug("Playlist saved; saving episodes to playlist");
	playlistDao.replaceEpisodes(playlistId, playlist.getEpisodes());
	LOGGER.debug("Playlist successfully saved");
	return playlist.of().id(playlistId).build();
    }

    public List<Episode> getTopTenShowsByUserId(Integer userId) {
	return episodeDao.getTopTenShows(userId);
    }

}
