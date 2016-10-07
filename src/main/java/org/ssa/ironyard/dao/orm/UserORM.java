package org.ssa.ironyard.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

import org.ssa.ironyard.model.Address.AddressBuilder;
import org.ssa.ironyard.model.Address.State;
import org.ssa.ironyard.model.Address.ZipCode;
import org.ssa.ironyard.model.Password;
import org.ssa.ironyard.model.User;
import org.ssa.ironyard.model.User.UserBuilder;

public interface UserORM extends ORM<User> {

    @Override
    default String projection() {
	StringJoiner joiner = new StringJoiner(", " + table() + ".", table() + ".", "");
        joiner
            .add("id")
            .add("email")
            .add("salt")
            .add("hash")
            .add("firstname")
            .add("lastname")
            .add("street")
            .add("city")
            .add("state")
            .add("zip");
        return joiner.toString();
    }

    @Override
    default String eagerProjection() {
        return projection();
    }

    @Override
    default String table() {
        return "users";
    }

    @Override
    default User map(ResultSet results) throws SQLException {
        Integer id = results.getInt(1);
        String email = results.getString(2);
        String salt = results.getString(3);
        String hash = results.getString(4);
        String firstName = results.getString(5);
        String lastName  = results.getString(6);
        String street = results.getString(7);
        String city = results.getString(8);
        String state = results.getString(9);
        String zip = results.getString(10);
        
        return new UserBuilder()
        	.id(id)
        	.loaded(true)
        	.email(email)
        	.password(new Password(salt,hash))
        	.firstName(firstName)
        	.lastName(lastName)
        	.address(new AddressBuilder()
        		.street(street)
        		.city(city)
        		.state(State.getInstance(state))
        		.zip(new ZipCode(zip))
        		.build()
        	)	
        	.build();
    }

    @Override
    default User eagerMap(ResultSet results) throws SQLException {
        return map(results);
    }

    @Override
    default String prepareInsert() {
        return "INSERT INTO " + table() + "(email, salt, hash, firstname, lastname, street, city, state, zip)" + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    default String prepareUpdate() {
        return "UPDATE " + table() + " SET email=?, salt=?, hash=?, firstname=?, lastname=?, street=?, city=?, state=?, zip=? WHERE id=?)";
    }

    @Override
    default String prepareRead() {
        return "SELECT " + projection() + " FROM " + table() + " WHERE id=?";
    }

    @Override
    default String prepareDelete() {
        return "DELETE FROM " + table() + " WHERE id=?";
    }

    @Override
    default String prepareEagerRead() {
        return prepareRead();
    }

    default String prepareReadByEmail(){
	return "SELECT " + projection() + " FROM " + table() + " WHERE email=?";
    }

}
