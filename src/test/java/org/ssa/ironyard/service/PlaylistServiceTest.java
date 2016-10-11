package org.ssa.ironyard.service;

import static org.junit.Assert.*;

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
    PlaylistDAO playlistDAO;
    EpisodeDAO episodeDAO;
    MysqlDataSource dataSource;
    static String URL = "jdbc:mysql://localhost/Playlistdb?" + "user=root&password=root&" + "useServerPrepStmts=true";
  
    User user;
    User user2;
    
    Playlist list1;
    Playlist list2;
    
    Episode episode1;
    Episode episode2;
    
    @Before
    public void setUp() throws Exception 
    {
        dataSource = new MysqlDataSource();
        dataSource.setURL(URL);
        
        playlistDAO = new PlaylistDAO(dataSource);
        episodeDAO = new EpisodeDAO(dataSource);
        
        testService = new PlaylistService(playlistDAO, episodeDAO, dataSource);
  
        playlistDAO.clear();
        episodeDAO.clear();
        
        user = User.builder().email("test@test.com").firstName("Bob").lastName("Loblaw")
                .address(Address.builder().street("123 Mockingbird Ln").city("Mockingbird Heights").state(State.ALABAMA)
                    .zip(new ZipCode("12345")).build())
                .password(new BCryptSecurePassword().secureHash("munsters")).build();

        user2 = User.builder(user).email("Test2@test.com").build();
        
        Playlist list1 = Playlist.builder().name("testPlaylist").user(user).targetDuration(1000).build();
        Playlist list2 = Playlist.builder().name("testPlaylist2").user(user).targetDuration(1000).build();
        
        episode1 = Episode.builder().episodeId(1).duration(1000).fileUrl("http://test.com/test").name("TestCast Audio").build();
        episode2 = Episode.builder().episodeId(2).duration(1000).fileUrl("http://test.com/test2").name("TestCast Audio2").build();

        
    }

    @Test
    public void deletePlaylistTest() 
    {
       
    }
    
    @Test
    public void getPlaylistByIdTest()
    {
        
    }
    
    public void getAllPlaylistsTest()
    {
        
    }
    
    public void savePlaylistTest()
    {
        
    }

}
