package org.ssa.ironyard.dao.orm.main_table_ORMs;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ssa.ironyard.dao.orm.ORM;
import org.ssa.ironyard.model.User;

public class UserORM implements ORM<User> {

    @Override
    public String projection() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String eagerProjection() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String table() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User map(ResultSet results) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User eagerMap(ResultSet results) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String prepareInsert() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String prepareUpdate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String prepareRead() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String prepareDelete() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String prepareEagerRead() {
        // TODO Auto-generated method stub
        return null;
    }

}
