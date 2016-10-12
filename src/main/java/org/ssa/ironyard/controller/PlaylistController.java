package org.ssa.ironyard.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Playlist;
import org.ssa.ironyard.model.User;
import org.ssa.ironyard.service.PlaylistService;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("{id}/playlists")
public class PlaylistController{ 
    
    ObjectMapper mapper = new ObjectMapper();

    final PlaylistService ps;
    
    @Autowired
    public PlaylistController(PlaylistService ps)
    {
        this.ps = ps;
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Playlist> createPlaylist(@RequestBody Map<String, Object> playlist, @PathVariable int id) throws JsonParseException, JsonMappingException, IOException
    {
        
        
        List<Episode> episodeList = new ArrayList<Episode>();
        
        for(Map<String,Object> episode: (List<Map<String, Object>>) playlist.get("episodes"))
        {
            Episode e = mapper.readValue(episode.get("Episode").toString(), Episode.class);
            episodeList.add(e);
        }
        
        Playlist newList = 
                Playlist.builder()
                    .name(playlist.get("name").toString())
                    .user((User)playlist.get("user"))
                    .targetDuration(Integer.parseInt(playlist.get("targetDuration").toString()))
                    .currentDuration(Integer.parseInt(playlist.get("currentDuration").toString()))
                    .episodes(episodeList)
                    .build();
           

        ps.savePlaylist(newList);
        
        return ResponseEntity.ok().header("Save Playlist", "Playlist").body(null);
        
    }

}