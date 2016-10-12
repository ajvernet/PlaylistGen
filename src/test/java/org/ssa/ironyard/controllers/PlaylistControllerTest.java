package org.ssa.ironyard.controllers;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.ssa.ironyard.controller.PlaylistController;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Playlist;
import org.ssa.ironyard.model.User;
import org.ssa.ironyard.service.PlaylistService;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class PlaylistControllerTest {

    PlaylistController controller;
    PlaylistService service;
    Map<String, String> map = new HashMap<>();
    Playlist list1;
    
    
    @Before
    public void mock()
    {
        service = EasyMock.createNiceMock(PlaylistService.class);
        controller = new PlaylistController(service);
        
        map.put("name", "Alex");
        map.put("user", User.builder().id(1).build().toString());
        map.put("targetDuration", "500");
        map.put("episodeDuration", "450");
        map.put("episodes", new ArrayList<>(Arrays.asList(Episode.builder().episodeId(2))).toString());
       
        List<Episode> episodes = new ArrayList<>();
        episodes.add(Episode.builder().id(5).build());
        
        
        list1 = Playlist.builder().name("Alex")
                .user(User.builder().id(1).build())
                .targetDuration(500)
                .currentDuration(450)
                .episodes(episodes)
                .build();
    }
    

    @Test
    public void savePlaylistTest() throws JsonParseException, JsonMappingException, IOException 
    {
       EasyMock.expect(this.service.savePlaylist(list1)).andReturn(list1);
       EasyMock.replay(this.service);
       
       //this.controller.createPlaylist(this.map, 1);
     //  System.out.println(this.controller.createPlaylist(this.map).getBody());
      // assertTrue(this.controller.createPlaylist(this.map).getBody().deeplyEquals(list1));
       
       EasyMock.verify(this.service);
       

    }
    
    @Test
    public void updatePlaylistTest()
    {
        
    }

}
