//package org.ssa.ironyard.dao;
//
//import static org.junit.Assert.assertTrue;
//
//import java.util.Objects;
//
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.ssa.ironyard.dao.PlayListDAO;
//import org.ssa.ironyard.dao.orm.PlayListORM;
//import org.ssa.ironyard.model.Password;
//import org.ssa.ironyard.model.PlayList;
//import org.ssa.ironyard.model.User;
//
//import com.mysql.cj.jdbc.MysqlDataSource;
//
//@Ignore
//public class PlayListDAOTest {
//
//    static String URL = "jdbc:mysql://localhost/playlistdb?" + "user=root&password=root&" + "useServerPrepStmts=true";
//    
//    PlayListDAO dao;
//    PlayListORM orm;
//    String name;
//    User user;
//    User user2;
//    MysqlDataSource dataSource;
//
//    @Before
//    public void setup()
//    {
//        dataSource = new MysqlDataSource();
//        dataSource.setURL(URL);
//        
//        orm = new PlayListORM();
//        dao = new PlayListDAO(orm, dataSource);
//        
//        
//       user = new User(1, true, "email", new Password("salt", "hash")); 
//       user = new User(1, true, "email", new Password("salt", "hash"));
//    }
//    
//    @Ignore
//    @Test
//    public void insertTest() 
//    {
//       PlayList list1 = new PlayList("list1", user);
//       dao.insert(list1);
//       
//       assertTrue(Objects.nonNull(dao.read(list1.getId())));
//       
//    }
//    
//    @Test
//    public void readtest()
//    {
//        
//    }
//    
//    @Test
//    public void readByUserTest()
//    {
//        
//    }
//
//}
