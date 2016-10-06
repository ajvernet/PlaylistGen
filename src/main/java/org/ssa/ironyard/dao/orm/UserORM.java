package org.ssa.ironyard.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

import org.ssa.ironyard.model.Password;
import org.ssa.ironyard.model.User;

public interface UserORM extends ORM<User> {

    @Override
    default String projection() {
	StringJoiner joiner = new StringJoiner(", " + table() + ".", table() + ".", "");
        joiner.add("id").add("email").add("salt").add("hash");
        return joiner.toString();
    }

    @Override
    default String eagerProjection() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    default String table() {
        // TODO Auto-generated method stub
        return " users ";
    }

    @Override
    default User map(ResultSet results) throws SQLException {
        // TODO Auto-generated method stub
        Integer id = results.getInt(1);
        String email = results.getString(2);
        String salt = results.getString(3);
        String hash = results.getString(4);
        
        
        return new User(id, true, email, new Password(salt, hash));
 
    }

    @Override
    default User eagerMap(ResultSet results) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    default String prepareInsert() {
        // TODO Auto-generated method stub
        return "Insert Into" + table() + "(" + projection() +")" + " Values(?, ?, ?, ?)";
    }

    @Override
    default String prepareUpdate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    default String prepareRead() {
        return "Select " + projection() + " from " + table() + " Where id=?";
    }

    @Override
    default String prepareDelete() {
        // TODO Auto-generated method stub
        return "Delete From " + table() + " Where id=?";
    }

    @Override
    default String prepareEagerRead() {
        // TODO Auto-generated method stub
        return null;
    }

}
