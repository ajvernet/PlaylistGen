package org.ssa.ironyard.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.crypto.BCryptSecurePassword;
import org.ssa.ironyard.model.Address;
import org.ssa.ironyard.model.Address.State;
import org.ssa.ironyard.model.Address.ZipCode;
import org.ssa.ironyard.model.User;

import com.mysql.cj.jdbc.MysqlDataSource;

public class UserDAOTest {

    UserDAO users;
    static String URL = "jdbc:mysql://localhost/playlistdb?" + "user=root&password=root" + "&useServerPrepStmt=true";
    User testUser;
    
    @Before
    public void init() {
	MysqlDataSource mysqlDataSource = new MysqlDataSource();
	mysqlDataSource.setUrl(URL);
	users = new UserDAO(mysqlDataSource);
	users.clear();
	testUser = User.builder().email("test@test.com").firstName("Bob").lastName("Loblaw")
		.address(Address.builder().street("123 Mockingbird Ln").city("Mockingbird Heights").state(State.ALABAMA)
			.zip(new ZipCode("12345")).build())
		.password(new BCryptSecurePassword().secureHash("munsters")).build();
    }

    @Test
    public void testInsert() {
	User dbUser = users.insert(testUser);
	assertTrue(dbUser.isLoaded());
	assertFalse(testUser.isLoaded());
    }

    @Test
    public void testRead() {
	User dbUser = users.insert(testUser);
	assertTrue(dbUser.deeplyEquals(users.read(dbUser.getId())));
    }
    
    public void testReadByEmail(){
	User dbUser = users.insert(testUser);
	assertTrue(dbUser.deeplyEquals(users.readByEmail(testUser.getEmail())));
    }

    public void testUpdate(){
	User dbUser = users.insert(testUser);
	User changedUser = User.builder(dbUser).firstName("Jane").build();
	assertFalse(dbUser.deeplyEquals(changedUser));
	dbUser = users.update(changedUser);
	assertTrue(dbUser.deeplyEquals(changedUser));
    }
    
    public void testDelete(){
	User dbUser = users.insert(testUser);
	assertNotNull(users.read(dbUser.getId()));
	users.delete(dbUser.getId());
	assertNull(users.read(dbUser.getId()));
    }
}
