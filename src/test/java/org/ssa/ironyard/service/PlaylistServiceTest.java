package org.ssa.ironyard.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import javax.sql.DataSource;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ssa.ironyard.crypto.BCryptSecurePassword;
import org.ssa.ironyard.dao.EpisodeDAO;
import org.ssa.ironyard.dao.PlaylistDAO;
import org.ssa.ironyard.model.Address;
import org.ssa.ironyard.model.Address.State;
import org.ssa.ironyard.model.Address.ZipCode;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Playlist;
import org.ssa.ironyard.model.User;

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
        
        this.testService = new PlaylistService(playlistDao, episodeDao);
        
        this.user1 = User.builder().id(1).email("test@test.com").firstName("Bob").lastName("Loblaw")
                .address(Address.builder().street("123 Mockingbird Ln").city("Mockingbird Heights").state(State.ALABAMA)
                    .zip(new ZipCode("12345")).build())
                .password(new BCryptSecurePassword().secureHash("munsters")).build();

        this.episode1 = Episode.builder().episodeId(null).duration(1000).fileUrl("http://test.com/test").name("TestCast Audio").build();

        this.episode2 = new Episode.EpisodeBuilder(episode1).id(2).build();
        
        list1 = Playlist.builder().id(null).name("list1").addEpisode(episode1).build();
        list2 = Playlist.builder(list1).user(user1).id(1).addEpisode(episode1).addEpisode(episode2).build();
        
        
    }
    
    @Ignore
    @Test
    public void insertPlaylistTest()
    {

        EasyMock.expect(this.playlistDao.replaceEpisodes(list1.getId(), list1.getEpisodes()))
        .andReturn(true);
        EasyMock.expect(this.playlistDao.insert(list1)).andReturn(list1);
        EasyMock.replay(this.playlistDao);
   
        assertTrue(this.testService.savePlaylist(list1).deeplyEquals(list1));

        EasyMock.verify(this.playlistDao);

    }

    
    @Test
    public void updatePlaylistTest()
    {
        EasyMock.expect(this.playlistDao.replaceEpisodes(list2.getId(), list2.getEpisodes()))
        .andReturn(true);
        EasyMock.expect(this.playlistDao.update(list2)).andReturn(list2);
        EasyMock.replay(this.playlistDao);
   
        assertTrue(this.testService.savePlaylist(list2).deeplyEquals(list2));

        EasyMock.verify(this.playlistDao);
    }
    
    
    @Test
    public void deletePlaylistTest() 
    {
        EasyMock.expect(this.playlistDao.delete(list2.getId())).andReturn(true);
        EasyMock.replay(this.playlistDao);
        assertTrue(testService.deletePlaylist(list2.getId()));
        
        EasyMock.verify(this.playlistDao);
    }
    
    
    @Test
    public void getPlaylistByIdTest()
    {
        EasyMock.expect(this.playlistDao.read(list2.getId())).andReturn(list2);
        EasyMock.replay(this.playlistDao);
        assertTrue(testService.getPlaylistById(list2.getId()).deeplyEquals(list2));
        
        EasyMock.verify(this.playlistDao);
    }
    
    @Test
    public void getAllPlaylistsTest()
    {
        EasyMock.expect(this.playlistDao.readByUser(list2.getUser().getId()))
        .andReturn(new ArrayList<Playlist>(Arrays.asList(list2)));
        EasyMock.replay(this.playlistDao);

        assertTrue(testService.getPlaylistsByUser(user1.getId())
                .equals(new ArrayList<Playlist>(Arrays.asList(list2))));
        
        EasyMock.verify(this.playlistDao);
    }
    


}
