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
	episodeDao = new EpisodeDAOImpl();
	playlistDao = new PlaylistDAOImpl(dataSource, episodeDao);

    }
    
    public PlaylistService(PlaylistDAO playlistDao, EpisodeDAO episodeDao,
    DataSource dataSource){

        this.episodeDao = new EpisodeDAOImpl(dataSource);        
        this.playlistDao = new PlaylistDAOImpl(dataSource, episodeDao);
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
