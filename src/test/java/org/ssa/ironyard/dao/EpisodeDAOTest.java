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
    UserDAO users;
    PlaylistDAO playlists;
    EpisodeDAO episodes;
    User user;
    Playlist playlist;
    Show show;
    Episode episode;
    
    @Before
    public void setup(){
	MysqlDataSource datasource = new MysqlDataSource();
	datasource.setUrl(URL);
	users = new UserDAO(datasource);
	playlists = new PlaylistDAO(datasource);
	episodes = new EpisodeDAO(datasource);
	users.clear();
	playlists.clear();
	episodes.clear();
	
	User testUser = User.builder().email("test@test.com").firstName("Bob").lastName("Loblaw")
		.address(Address.builder().street("123 Mockingbird Ln").city("Mockingbird Heights").state(State.ALABAMA)
			.zip(new ZipCode("12345")).build())
		.password(new BCryptSecurePassword().secureHash("munsters")).build();
	user = users.insert(testUser);
	playlist = playlists.insert(Playlist.builder().name("testPlaylist").user(user).targetDuration(1000).build());
	//show = shows.insert(Show.builder().name("Test show").showId(1).imgUrl("http://nowhere").thumbUrl("http://nowhere").build());

	episode = Episode.builder().episodeId(1).duration(1000).fileUrl("http://test.com/test").name("TestCast Audio").build();

    }
    @Ignore
    @Test
    public void testInsert() {
	Episode loadedEpisode = episodes.insert(episode);
	assertTrue(loadedEpisode.isLoaded());
	assertTrue(loadedEpisode.getId() >= 0);
    }
    @Ignore
    @Test
    public void testDelete(){
	Episode loadedEpisode = episodes.insert(episode);
	assertTrue(loadedEpisode.isLoaded());
	assertTrue(episodes.read(loadedEpisode.getId()).isLoaded());
	assertTrue(episodes.delete(loadedEpisode.getId()));
	assertNull(episodes.read(loadedEpisode.getId()));
    }
    @Ignore
    @Test
    public void testReadByPlaylist(){
	assertTrue(episodes.getEpisodesByPlaylist(playlist.getId()).size()==0);
	episodes.insert(episode);
	assertTrue(episodes.getEpisodesByPlaylist(playlist.getId()).size()==1);
	episodes.insert(episode);
	assertTrue(episodes.getEpisodesByPlaylist(playlist.getId()).size()==2);
	episodes.clear();
	assertTrue(episodes.getEpisodesByPlaylist(playlist.getId()).size()==0);	
    }

}
