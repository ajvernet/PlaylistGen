package org.ssa.ironyard.dao;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ssa.ironyard.crypto.BCryptSecurePassword;
import org.ssa.ironyard.model.Address;
import org.ssa.ironyard.model.Address.State;
import org.ssa.ironyard.model.Address.ZipCode;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Playlist;
import org.ssa.ironyard.model.Show;
import org.ssa.ironyard.model.User;

import com.mysql.cj.jdbc.MysqlDataSource;

public class EpisodeDAOTest {

    static String URL = "jdbc:mysql://localhost/Playlistdb?" + "user=root&password=root&" + "useServerPrepStmts=true";
    UserDAOImpl users;
    PlaylistDAOImpl playlists;
    EpisodeDAOImpl episodes;
    User user;
    Playlist playlist;
    Show show;
    Episode episode, episode2, episode3;
    
    @Before
    public void setup(){
	MysqlDataSource datasource = new MysqlDataSource();
	datasource.setUrl(URL);
	users = new UserDAOImpl(datasource);
	episodes = new EpisodeDAOImpl(datasource);
	users.clear();
	episodes.clear();
	
	User testUser = User.builder().email("test@test.com").firstName("Bob").lastName("Loblaw")
		.address(Address.builder().street("123 Mockingbird Ln").city("Mockingbird Heights").state(State.ALABAMA)
			.zip(new ZipCode("12345")).build())
		.password(new BCryptSecurePassword().secureHash("munsters")).build();
	user = users.insert(testUser);
	
	//show = shows.insert(Show.builder().name("Test show").showId(1).imgUrl("http://nowhere").thumbUrl("http://nowhere").build());

	episode = Episode.builder().episodeId(1).duration(1000).fileUrl("http://test.com/test").name("TestCast Audio").show(new Show(1, "Test Show", "Test Show URL")).build();
	episode2 = Episode.builder(episode).episodeId(2).build();
	episode3 = Episode.builder(episode).episodeId(3).build();
    }
    
    
    @Test
    public void testInsert() {
	Episode loadedEpisode = episodes.insert(episode);
	assertTrue(loadedEpisode.isLoaded());
	assertTrue(loadedEpisode.getId() >= 0);
    }
    
    @Test
    public void testDelete(){
	Episode loadedEpisode = episodes.insert(episode);
	assertTrue(loadedEpisode.isLoaded());
	assertTrue(episodes.read(loadedEpisode.getId()).isLoaded());
	assertTrue(episodes.delete(loadedEpisode.getId()));
	assertNull(episodes.read(loadedEpisode.getId()));
    }

}
