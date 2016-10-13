package org.ssa.ironyard.controllers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

public class PlaylistControllerTest {

    PlaylistController controller;
    PlaylistService service;
    Map<String, Object> map = new HashMap<>();
    Playlist list1;
    
    
    @Before
    public void mock()
    {
        service = EasyMock.createNiceMock(PlaylistService.class);
        controller = new PlaylistController(service);
        map.add("name", "Alex");
        map.add("user", User.builder().id(1).build());
        map.add("targetDuration", 500);
        map.add("episodeDuration", 450);
        map.add("episodes", new ArrayList<>(Arrays.asList(Episode.builder().episodeId(2))));
       
        list1 = Playlist.builder().name("Alex")
                .user(User.builder().id(1).build())
                .targetDuration(500)
                .currentDuration(450)
                .episodes(new ArrayList<>(Arrays.asList((Episode.builder().episodeId(2).build()))))
                .build();
    }
    

    @Test
    public void savePlaylistTest() 
    {
       EasyMock.expect(this.service.savePlaylist(list1)).andReturn(list1);
       EasyMock.replay(this.service);
       System.out.println(this.controller.createPlaylist(this.map, 1));
       assertTrue(this.controller.createPlaylist(this.map).getBody().deeplyEquals(list1));
       
       EasyMock.verify(this.service);
       

    }
    
    @Test
    public void updatePlaylistTest()
    {
        
    }

}
