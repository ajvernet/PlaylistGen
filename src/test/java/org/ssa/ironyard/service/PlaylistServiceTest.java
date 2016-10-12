package org.ssa.ironyard.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.crypto.BCryptSecurePassword;
import org.ssa.ironyard.dao.EpisodeDAO;
import org.ssa.ironyard.dao.PlaylistDAO;
import org.ssa.ironyard.dao.PlaylistDAOImpl;
import org.ssa.ironyard.model.Address;
import org.ssa.ironyard.model.User;
import org.ssa.ironyard.model.Address.State;
import org.ssa.ironyard.model.Address.ZipCode;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Playlist;

import com.mysql.cj.jdbc.MysqlDataSource;

public class PlaylistServiceTest {

    PlaylistService testService;
    PlaylistDAO playlistDao;
    EpisodeDAO episodeDao;

    DataSource dataSource;
    
    Playlist list1;
    Playlist list2;
    
    Episode episode1;
    Episode episode2;
    
    User user1;
    User user2;
    
    @Before
    public void mock()
    {
        this.playlistDao = EasyMock.createNiceMock(PlaylistDAO.class);
        this.episodeDao = EasyMock.createNiceMock(EpisodeDAO.class);
        this.dataSource = EasyMock.createNiceMock(MysqlDataSource.class);
        this.testService = new PlaylistService(playlistDao, episodeDao, dataSource);
        
        this.user1 = User.builder().email("test@test.com").firstName("Bob").lastName("Loblaw")
                .address(Address.builder().street("123 Mockingbird Ln").city("Mockingbird Heights").state(State.ALABAMA)
                    .zip(new ZipCode("12345")).build())
                .password(new BCryptSecurePassword().secureHash("munsters")).build();

        this.episode1 = Episode.builder().episodeId(1).duration(1000).fileUrl("http://test.com/test").name("TestCast Audio").build();

        this.episode2 = new Episode.EpisodeBuilder(episode1).id(2).build();
        
        list1 = Playlist.builder().id(2).name("list1").addEpisode(episode1).build();
        list2 = Playlist.builder(list1).build();
    }
    
    @Test
    public void savePlaylistTest()
    {
        
        EasyMock.expect(this.playlistDao.insert(list1)).andReturn(list1);
        EasyMock.replay(this.testService);
        
        
    }

    @Ignore
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
