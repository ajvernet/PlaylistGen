package org.ssa.ironyard.controller;

import java.io.IOException;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ssa.ironyard.model.Playlist;
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
    public ResponseEntity<Playlist> createPlaylist(@RequestBody String json, @PathVariable int id) throws JsonParseException, JsonMappingException, IOException
    {
        
        User user = mapper.readValue("user", User.class);



        
        return ResponseEntity.ok().header("Save Playlist", "Playlist").body(null);
        
    }

}