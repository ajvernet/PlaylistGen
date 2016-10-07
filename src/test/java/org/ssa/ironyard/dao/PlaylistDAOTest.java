package org.ssa.ironyard.dao;

import static org.junit.Assert.assertTrue;

import java.util.Objects;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ssa.ironyard.crypto.BCryptSecurePassword;
import org.ssa.ironyard.dao.orm.PlaylistORM;
import org.ssa.ironyard.model.Password;
import org.ssa.ironyard.model.Playlist;
import org.ssa.ironyard.model.User;

import com.mysql.cj.jdbc.MysqlDataSource;

@Ignore
public class PlaylistDAOTest {

    static String URL = "jdbc:mysql://localhost/Playlistdb?" + "user=root&password=root&" + "useServerPrepStmts=true";

    PlaylistDAO dao;
    PlaylistORM orm;
    String name;
    User user;
    User user2;
    MysqlDataSource dataSource;

    @Before
    public void setup() {
        dataSource = new MysqlDataSource();
        dataSource.setURL(URL);

        orm = new PlaylistORM() {
        };
        dao = new PlaylistDAO(orm, dataSource);

        UserDAO userDao = new UserDAO(dataSource);
        user = userDao.insert(User.builder().email("Test@test.com")
                .password(new BCryptSecurePassword().secureHash("password")).build());

        user2 = userDao.insert(User.builder(user).email("Test2@test.com").build());
    }

    @Ignore
    @Test
    public void insertTest() {
        Playlist list1 = Playlist.builder().name("testPlaylist").user(user).build();

        assertTrue(Objects.nonNull(dao.read(list1.getId())));

    }

    @Test
    public void readtest() {

    }

    @Test
    public void readByUserTest() {

    }

}
