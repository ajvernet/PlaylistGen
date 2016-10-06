package org.ssa.ironyard.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

import org.ssa.ironyard.model.Password;
import org.ssa.ironyard.model.User;
import org.ssa.ironyard.model.User.UserBuilder;

public interface UserORM extends ORM<User> {

    @Override
    default String projection() {
	StringJoiner joiner = new StringJoiner(", " + table() + ".", table() + ".", "");
        joiner.add("id").add("email").add("salt").add("hash");
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
        return new UserBuilder()
        	.id(id)
        	.loaded(true)
        	.email(email)
        	.password(new Password(salt,hash))
        	.build();
    }

    @Override
    default User eagerMap(ResultSet results) throws SQLException {
        return map(results);
    }

    @Override
    default String prepareInsert() {
        return "INSERT INTO" + table() + "(email, salt, hash)" + " VALUES(?, ?, ?)";
    }

    @Override
    default String prepareUpdate() {
        return "UPDATE " + table() + " SET email=?, salt=?, hash=? WHERE id=?)";
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

}
