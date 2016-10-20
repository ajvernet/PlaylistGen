package org.ssa.ironyard.dao;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ssa.ironyard.crypto.BCryptSecurePassword;
import org.ssa.ironyard.model.Address;
import org.ssa.ironyard.model.Address.State;
import org.ssa.ironyard.model.Address.ZipCode;
import org.ssa.ironyard.model.User;

import com.mysql.cj.jdbc.MysqlDataSource;

public class InitUsers {
    UserDAO users;
    static String URL = "jdbc:mysql://localhost/PlaylistDemo?" 
	    + "user=root&password=root" 
	    + "&useServerPrepStmt=true";
    User sally, hank, bobLoblaw, mark, will;

    @Before
    public void init() {
	MysqlDataSource mysqlDataSource = new MysqlDataSource();
	mysqlDataSource.setUrl(URL);
	users = new UserDAOImpl(mysqlDataSource);
	sally = User.builder()
		.firstName("Sally")
		.lastName("Rogers")
		.address(Address.builder()
			.street("565 N Clinton Dr.")
			.city("Milwaukee")
			.state(State.WISCONSIN)
			.zip(new ZipCode("12345"))
			.build()
			)
		.email("sally@gmail.com")
		.password(new BCryptSecurePassword().secureHash("sally"))
		.build();
	hank = User.builder()
		.firstName("Hank")
		.lastName("Williams")
		.address(Address.builder()
			.street("84 Rainey St.")
			.city("Arlen")
			.state(State.TEXAS)
			.zip(new ZipCode("12345"))
			.build()
			)
		.email("hank@gmail.com")
		.password(new BCryptSecurePassword().secureHash("hank"))
		.build();
	bobLoblaw = User.builder()
		.firstName("Bob")
		.lastName("Loblaw")
		.address(Address.builder()
			.street("1060 W Addison St.")
			.city("Chicago")
			.state(State.ILLINOIS)
			.zip(new ZipCode("12345"))
			.build()
			)
		.email("bobloblaw@gmail.com")
		.password(new BCryptSecurePassword().secureHash("bobloblaw"))
		.build();
	mark = User.builder()
		.firstName("Mark")
		.lastName("Christensen")
		.address(Address.builder()
			.street("112.5 Beacon St.")
			.city("Boston")
			.state(State.MASSACHUSETTS)
			.zip(new ZipCode("12345"))
			.build()
			)
		.email("mark@gmail.com")
		.password(new BCryptSecurePassword().secureHash("mark"))
		.build();
	will = User.builder()
		.firstName("Will")
		.lastName("Myers")
		.address(Address.builder()
			.street("1020 Palm Dr.")
			.city("Cocoa Beach")
			.state(State.FLORIDA)
			.zip(new ZipCode("12345"))
			.build()
			)
		.email("will@gmail.com")
		.password(new BCryptSecurePassword().secureHash("will"))
		.build();
    }
    
    @Ignore
    @Test 
    public void insertUsers(){
	users.clear();
	users.insert(sally);
	users.insert(hank);
	users.insert(bobLoblaw);
	users.insert(mark);
	users.insert(will);
    }
}
