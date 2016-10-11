package org.ssa.ironyard.service;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.crypto.BCryptSecurePassword;
import org.ssa.ironyard.dao.EpisodeDAO;
import org.ssa.ironyard.dao.PlaylistDAO;
import org.ssa.ironyard.model.Address;
import org.ssa.ironyard.model.User;
import org.ssa.ironyard.model.Address.State;
import org.ssa.ironyard.model.Address.ZipCode;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Playlist;

import com.mysql.cj.jdbc.MysqlDataSource;

public class PlaylistServiceTest {

    PlaylistService testService;
    

 
    
    @Before
    public void mock()
    {
        this.testService = EasyMock.createNiceMock(PlaylistService.class);
        
    }
    
    @Test
    public void savePlaylistTest()
    {
        testService.savePlaylist(list1);
        assertTrue(list1.deeplyEquals(playlistDAO.read(list1.getId())));
    }

    @Test
    public void deletePlaylistTest() 
    {
       testService.savePlaylist(list1);
       testService.deletePlaylist(list1.getId());
       
       assertNull(playlistDAO.read(list1.getId()));
    }
    
    @Test
    public void getPlaylistByIdTest()
    {
        testService.savePlaylist(list1);
        assertTrue(list1.deeplyEquals(testService.getPlaylistById(list1.getId())));
    }
    
    @Test
    public void getAllPlaylistsTest()
    {
        testService.savePlaylist(list1);
        testService.savePlaylist(list2);
        
        assertTrue(testService.getAllPlaylists(user.getId()).size() == 2);
    }
    


}
