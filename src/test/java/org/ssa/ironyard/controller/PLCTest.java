package org.ssa.ironyard.controller;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ssa.ironyard.controller.mapper.EpisodeMapper;
import org.ssa.ironyard.controller.mapper.PlaylistMapper;
import org.ssa.ironyard.controller.mapper.ShowMapper;
import org.ssa.ironyard.crypto.BCryptSecurePassword;
import org.ssa.ironyard.model.Address;
import org.ssa.ironyard.model.User;
import org.ssa.ironyard.model.Address.State;
import org.ssa.ironyard.model.Address.ZipCode;
import org.ssa.ironyard.service.PlaylistService;

import com.mysql.cj.jdbc.MysqlDataSource;


public class PLCTest {

PlaylistController controller;

    PlaylistController playlistController;
    PlaylistMapper playlistMapper;
    EpisodeMapper episode;
    ShowMapper show;
    User testUser;
    
    PlaylistService service;

    @Before
    public void setUp() throws Exception {

        playlistMapper = new PlaylistMapper();
        episode = new EpisodeMapper();
        show = new ShowMapper();

        
        
        testUser = User.builder().email("test@test.com").firstName("Bob").lastName("Loblaw")
            .address(Address.builder().street("123 Mockingbird Ln").city("Mockingbird Heights").state(State.ALABAMA)
                .zip(new ZipCode("12345")).build())
            .password(new BCryptSecurePassword().secureHash("munsters")).build();
        
        show.setId(2362);
        show.setName("Science Magazine Podcast");
       
        episode.setDescription(
            "News stories on our earliest hunting companions, should we seed exoplanets with life, and finding space storm hot spots with David Grimm.  \n\nFrom the magazine\n\nTwo years ago, 43 students disappeared from a teacher’s college in Guerrero, Mexico. Months of protests and investigation have not yielded a believable account of what happened to them. The government of Mexico claims that the students were killed by cartel members and burned on an outdoor pyre in a dump outside Cucola. Lizzie Wade has been following this story with a focus on the science of fire investigation. She talks about an investigator in Australia that has burned pig carcasses in an effort to understand these events in Mexico.  \n\n[Image: Edgard Garrido/Reuters; Music: Jeffrey Cook]\n");
        episode.setDuration(1542);
        episode.setEpisodeId(199025);
        episode.setFileUrl("https://www.audiosear.ch/media/audio_file/304ba/SciencePodcast_160916.mp3");
        episode.setGenreId(6);
        episode.setName(
            "Podcast: A burning body experiment, prehistoric hunting dogs, and seeding life on other planets");
        episode.setShow(show);
        
        List<EpisodeMapper> episodes = new ArrayList<>();
        episodes.add(episode);
        
        playlistMapper.setId(null);
        playlistMapper.setCurrentDuration(0);
        playlistMapper.setTargetDuration(7200);
        playlistMapper.setName("Test Playlist - Andy");
        playlistMapper.setEpisodes(episodes);
       

    }
    
    @Before
    public void mock()
    {
        service = EasyMock.createNiceMock(PlaylistService.class);
        playlistController = new PlaylistController(service);
        
    }

    @Ignore
    @Test
    public void savePlaylistTest()
    {
        
    }
    
    @Ignore
    @Test
    public void deletePlaylistTest()
    {
        
    }
    
    @Ignore
    @Test
    public void getPlaylistsByUser()
    {
        
    }
    
    @Ignore
    @Test
    public void getPlaylistsById()
    {
        
    }

}
