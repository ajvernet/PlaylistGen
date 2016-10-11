package org.ssa.ironyard.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Objects;

import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.crypto.BCryptSecurePassword;
import org.ssa.ironyard.dao.orm.PlaylistORM;
import org.ssa.ironyard.model.Address;
import org.ssa.ironyard.model.Address.State;
import org.ssa.ironyard.model.Address.ZipCode;
import org.ssa.ironyard.model.Playlist;
import org.ssa.ironyard.model.User;

import com.mysql.cj.jdbc.MysqlDataSource;

public class PlaylistDAOImplTest {

    static String URL = "jdbc:mysql://localhost/Playlistdb?" + "user=root&password=root&" + "useServerPrepStmts=true";

    PlaylistDAOImpl dao;
    String name;
    User user;
    User user2;
    MysqlDataSource dataSource;
    Playlist list1;
    Playlist list2;
    Playlist list3;
    Playlist list4;
    Playlist list5;

    @Before
    public void setup() {
	dataSource = new MysqlDataSource();
	dataSource.setURL(URL);

	dao = new PlaylistDAOImpl(dataSource);

	UserDAO userDao = new UserDAO(dataSource);

	userDao.clear();

	user = User.builder().email("test@test.com").firstName("Bob").lastName("Loblaw")
		.address(Address.builder().street("123 Mockingbird Ln").city("Mockingbird Heights").state(State.ALABAMA)
			.zip(new ZipCode("12345")).build())
		.password(new BCryptSecurePassword().secureHash("munsters")).build();

	user = userDao.insert(user);

	user2 = userDao.insert(User.builder(user).email("Test2@test.com").build());
	list1 = Playlist.builder().name("testPlaylist").user(user).targetDuration(1000).currentDuration(1).build();

	list2 = list1;

	list3 = list1;

	list4 = Playlist.builder(list1).user(user2).build();

	list5 = Playlist.builder(list1).user(user2).build();

    }

    @Test
    public void insertAndReadTest() {
	list1 = dao.insert(list1);
	assertTrue(Objects.nonNull(dao.read(list1.getId())));

	assertTrue(list1.isLoaded());

    }

    @Test
    public void updatePlaylist() {
	list1 = dao.insert(list1);

	list1 = dao.update(Playlist.builder(list1).name("updatedPlaylist").build());

	assertEquals("updatedPlaylist", dao.read(list1.getId()).getName());
    }

    @Test
    public void deletePlaylist() {
	list1 = dao.insert(list1);

	dao.delete(list1.getId());

	assertTrue(Objects.isNull(dao.read(list1.getId())));
    }

    @Test
    public void readByUserTest() {

	list1 = dao.insert(list1);

	list1 = dao.insert(list2);

	list1 = dao.insert(list3);

	list1 = dao.insert(list4);

	list1 = dao.insert(list5);

	assertEquals(dao.readByUser(user2.getId()).size(), 2);

    }

}
