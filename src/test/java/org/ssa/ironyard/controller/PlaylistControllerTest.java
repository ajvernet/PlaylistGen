package org.ssa.ironyard.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.controller.mapper.EpisodeMapper;
import org.ssa.ironyard.controller.mapper.PlaylistMapper;
import org.ssa.ironyard.controller.mapper.ShowMapper;
import org.ssa.ironyard.crypto.BCryptSecurePassword;
import org.ssa.ironyard.dao.EpisodeDAO;
import org.ssa.ironyard.dao.EpisodeDAOImpl;
import org.ssa.ironyard.dao.PlaylistDAO;
import org.ssa.ironyard.dao.PlaylistDAOImpl;
import org.ssa.ironyard.dao.UserDAO;
import org.ssa.ironyard.dao.UserDAOImpl;
import org.ssa.ironyard.model.Address;
import org.ssa.ironyard.model.Address.State;
import org.ssa.ironyard.model.Address.ZipCode;
import org.ssa.ironyard.model.User;
import org.ssa.ironyard.service.PlaylistService;

import com.mysql.cj.jdbc.MysqlDataSource;

public class PlaylistControllerTest {

    PlaylistController playlistController;
    PlaylistMapper playlistMapper;
    EpisodeMapper episode;
    ShowMapper show;
    UserDAO userDao;
    User testUser;
    static String URL = "jdbc:mysql://localhost/Playlistdb?" + "user=root&password=root&" + "useServerPrepStmts=true";

    @Before
    public void setup() {
	playlistMapper = new PlaylistMapper();
	episode = new EpisodeMapper();
	show = new ShowMapper();
	MysqlDataSource mysqlDataSource = new MysqlDataSource();
	mysqlDataSource.setUrl(URL);
	EpisodeDAO episodeDao = new EpisodeDAOImpl(mysqlDataSource);
	PlaylistDAO playlistDao = new PlaylistDAOImpl(mysqlDataSource, episodeDao);
	userDao = new UserDAOImpl(mysqlDataSource);
	userDao.clear();
	testUser = User.builder().email("test@test.com").firstName("Bob").lastName("Loblaw")
		.address(Address.builder().street("123 Mockingbird Ln").city("Mockingbird Heights").state(State.ALABAMA)
			.zip(new ZipCode("12345")).build())
		.password(new BCryptSecurePassword().secureHash("munsters")).build();
	testUser = userDao.insert(testUser);
	show.setId(2362);
	show.setName("Science Magazine Podcast");
	playlistMapper.setId(null);
	playlistMapper.setCurrentDuration(0);
	playlistMapper.setTargetDuration(7200);
	playlistMapper.setName("Test Playlist - Andy");
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
	playlistMapper.setEpisodes(episodes);
	playlistController = new PlaylistController(new PlaylistService(playlistDao, episodeDao));
    }    
    
    @Test
    public void testSave(){
	playlistController.savePlaylist(playlistMapper, testUser.getId());
    }
}
