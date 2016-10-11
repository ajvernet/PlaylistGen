package org.ssa.ironyard.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssa.ironyard.dao.EpisodeDAO;
import org.ssa.ironyard.dao.PlaylistDAO;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Playlist;

public class PlaylistService {
    
    private final PlaylistDAO playlistDao;
    private final EpisodeDAO episodeDao;
    
    @Autowired
    public PlaylistService(PlaylistDAO playlistDao, 
            EpisodeDAO episodeDao, DataSource dataSource){
        this.playlistDao = playlistDao;
        this.episodeDao = episodeDao;
        playlistDao = new PlaylistDAO(dataSource);
    }
    
    public boolean deletePlaylist(int id){
        
        return playlistDao.delete(id);             
    }
    
    public Playlist getPlaylistById(int id){
	
        return playlistDao.read(id);
    }
    
    public List<Playlist> getAllPlaylists(int userId){
        
        return playlistDao.readByUser(userId);
    }
    
    
    public Playlist savePlaylist(Playlist playlist){
    	if(playlist.getId() == null){
    	    for(Episode e: playlist.getEpisodes())
    	        episodeDao.insert(e);
    	        //playlistDao.replace(playlist);
    	    return playlistDao.insert(playlist);
    	}
    	else {
    	       for(Episode e: playlist.getEpisodes())
    	            episodeDao.insert(e);
               //playlistDao.replace(playlist);
    	    return playlistDao.update(playlist);
    	}
    }

    
    
}
