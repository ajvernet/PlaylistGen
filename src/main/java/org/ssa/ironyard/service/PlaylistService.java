package org.ssa.ironyard.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssa.ironyard.dao.EpisodeDAO;

import org.ssa.ironyard.dao.EpisodeDAOImpl;

import org.ssa.ironyard.dao.PlaylistDAO;
import org.ssa.ironyard.dao.PlaylistDAOImpl;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Playlist;

public class PlaylistService {
    

    private final PlaylistDAO playlistDao;
    private final EpisodeDAO episodeDao;
    
    @Autowired
    public PlaylistService(){
	episodeDao = new EpisodeDAOImpl(null);
	playlistDao = new PlaylistDAOImpl(null, episodeDao);

    }
    
    public PlaylistService(PlaylistDAO playlistDao, EpisodeDAO episodeDao){

        this.episodeDao = episodeDao;       
        this.playlistDao = playlistDao;
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
	    playlistDao.replaceEpisodes(playlist.getId(), playlist.getEpisodes());
	    return playlistDao.insert(playlist);
	}
	else {
	    playlistDao.replaceEpisodes(playlist.getId(), playlist.getEpisodes());       
	    return playlistDao.update(playlist);
	    }
    }
    
    
}
