package org.ssa.ironyard.dao.orm.maintableORMs;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ssa.ironyard.dao.orm.ORM;
import org.ssa.ironyard.model.Password;
import org.ssa.ironyard.model.User;

public class UserORM implements ORM<User> {

    @Override
    public String projection() {
        // TODO Auto-generated method stub
        return "id, email, salt, hash";
    }

    @Override
    public String eagerProjection() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String table() {
        // TODO Auto-generated method stub
        return " users ";
    }

    @Override
    public User map(ResultSet results) throws SQLException {
        // TODO Auto-generated method stub
        Integer id = results.getInt(1);
        String email = results.getString(2);
        String salt = results.getString(3);
        String hash = results.getString(4);
        
        
        return new User(id, true, email, new Password(salt, hash));
 
    }

    @Override
    public User eagerMap(ResultSet results) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String prepareInsert() {
        // TODO Auto-generated method stub
        return "Insert Into" + table() + "(" + projection() +")" + " Values(?, ?, ?, ?)";
    }

    @Override
    public String prepareUpdate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String prepareRead() {
        return "Select " + projection() + " from " + table() + " Where id=?";
    }

    @Override
    public String prepareDelete() {
        // TODO Auto-generated method stub
        return "Delete From " + table() + " Where id=?";
    }

    @Override
    public String prepareEagerRead() {
        // TODO Auto-generated method stub
        return null;
    }

}
