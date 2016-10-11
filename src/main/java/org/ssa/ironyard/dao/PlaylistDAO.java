package org.ssa.ironyard.dao;

import java.util.List;

import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Playlist;

public interface PlaylistDAO extends DAO<Playlist>{

    List<Playlist> readByUser(Integer userId);

    boolean replaceEpisodes(Integer playlistId, List<Episode> episodes);
    
}