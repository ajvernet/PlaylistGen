package org.ssa.ironyard.dao;

import static org.junit.Assert.*;

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
        
        userDao.clear();
        
        user = User.builder().email("test@test.com").firstName("Bob").lastName("Loblaw")
                .address(Address.builder().street("123 Mockingbird Ln").city("Mockingbird Heights").state(State.ALABAMA)
                    .zip(new ZipCode("12345")).build())
                .password(new BCryptSecurePassword().secureHash("munsters")).build();
       
        user = userDao.insert(user);
        
        user2 = userDao.insert(User.builder(user).email("Test2@test.com").build());
    }


    
    @Test
    public void insertAndReadTest() {
        Playlist list1 = Playlist.builder().name("testPlaylist").user(user).build();
        list1 = dao.insert(list1);
        assertTrue(Objects.nonNull(dao.read(list1.getId())));

    }



    @Test
    public void readByUserTest() {
        Playlist list1 = Playlist.builder().name("testPlaylist").user(user).build();
        list1 = dao.insert(list1);
        
        Playlist list2 = Playlist.builder().name("testPlaylist").user(user).build();
        list1 = dao.insert(list2);
        
        Playlist list3 = Playlist.builder().name("testPlaylist").user(user).build();
        list1 = dao.insert(list3);
       
        Playlist list4 = Playlist.builder().name("testPlaylist").user(user2).build();
        list1 = dao.insert(list4);
        
        Playlist list5 = Playlist.builder().name("testPlaylist").user(user2).build();
        list1 = dao.insert(list5);
        
        assertEquals(dao.readByUser(user2.getId()).size(), 2);
        
        }

}
