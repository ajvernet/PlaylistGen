package org.ssa.ironyard.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssa.ironyard.dao.EpisodeDAO;
import org.ssa.ironyard.dao.PlaylistDAO;
import org.ssa.ironyard.dao.PlaylistDAOImpl;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Playlist;

public class PlaylistService {
    
    
    final PlaylistDAO playlistDao;
    final EpisodeDAO episodeDao;
    
    @Autowired
    public PlaylistService(DataSource dataSource){
	this.playlistDao = new PlaylistDAOImpl(dataSource);
	this.episodeDao = new EpisodeDAO(dataSource);
    }
    
    public PlaylistService(PlaylistDAO playlistDao, EpisodeDAO episodeDao,
    DataSource dataSource){
        this.playlistDao = new PlaylistDAOImpl(dataSource);
        this.episodeDao = new EpisodeDAO(dataSource);
       
    }

    
    
    public Playlist getPlaylistById(int id){
        return playlistDao.read(id);
    }
    
    public List<Playlist> getPlaylistsByUser(int userId)
    {
        return playlistDao.readByUser(userId);
    }
    
    public boolean deletePlaylist(int id)
    {
        return playlistDao.delete(id);
    }
    
    public Playlist savePlaylist(Playlist playlist){
	if(playlist.getId() == null){
	    for(Episode e: playlist.getEpisodes())
	        episodeDao.insert(e);
	    playlistDao.replaceEpisodes(playlist.getId(), playlist.getEpisodes());
	    return playlistDao.insert(playlist);
	}
	else {
	       for(Episode e: playlist.getEpisodes())
	            episodeDao.insert(e);
	    playlistDao.replaceEpisodes(playlist.getId(), playlist.getEpisodes());
	       
	    return playlistDao.update(playlist);
	    }
    }
    
    
}
