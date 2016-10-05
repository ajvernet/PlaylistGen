package org.ssa.ironyard.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.ssa.ironyard.model.DomainObject;

public interface DAO<T extends DomainObject> {

    T insert(T domain);
    
    boolean delete(Integer id);

    T update(T domain);

    T read(Integer id);

    int clear() throws UnsupportedOperationException;

    default public void cleanup(Connection connection, Statement statement, ResultSet results)
    {
       cleanup(statement);
       cleanup(connection);
       cleanup(results);
    }
    
    default public void cleanup(Statement statement)
    {
        try
        {
            if(statement != null)
                statement.close();
        }
        catch(SQLException e)
        {
            throw new RuntimeException();
        }
    }
    
    default public void cleanup(Connection connection)
    {
        try
        {
            if(connection != null)
                connection.close();
        }
        catch(SQLException e)
        {
            throw new RuntimeException();
        }
    }
    
    default public void cleanup(ResultSet results)
    {
        try
        {
            if(results != null)
                results.close();
        }
        catch(SQLException e)
        {
            throw new RuntimeException();
        }
    }
}