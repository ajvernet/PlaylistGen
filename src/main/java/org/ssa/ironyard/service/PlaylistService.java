package org.ssa.ironyard.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssa.ironyard.dao.PlaylistDAO;
import org.ssa.ironyard.model.Playlist;

public class PlaylistService {
    
    private final PlaylistDAO playlistDao;
    
    @Autowired
    public PlaylistService(DataSource dataSource){
	playlistDao = new PlaylistDAO(dataSource);
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
