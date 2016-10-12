package org.ssa.ironyard.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssa.ironyard.dao.EpisodeDAO;
import org.ssa.ironyard.dao.EpisodeDAOImpl;
import org.ssa.ironyard.dao.PlaylistDAO;
import org.ssa.ironyard.dao.PlaylistDAOImpl;
import org.ssa.ironyard.model.Playlist;

public class PlaylistService {
    
    private final PlaylistDAO playlistDao;
    private final EpisodeDAO episodeDao;
    
    @Autowired
    public PlaylistService(DataSource dataSource){
	episodeDao = new EpisodeDAOImpl(dataSource);
	playlistDao = new PlaylistDAOImpl(dataSource, episodeDao);
    }
    
    public Playlist getPlaylistById(int id){
	return playlistDao.read(id);
    }
    
    public Playlist savePlaylist(Playlist playlist){
	if(playlist.getId() == null)
	    return playlistDao.insert(playlist);
	else return playlistDao.update(playlist);
    }
    
    
}
