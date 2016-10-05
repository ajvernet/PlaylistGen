package org.ssa.ironyard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ssa.ironyard.dao.orm.maintableORMs.UserORM;
import org.ssa.ironyard.model.Password;
import org.ssa.ironyard.model.User;

import com.mysql.cj.api.jdbc.Statement;




@Component
public class UserDAO implements DAO<User>{

    final DataSource datasource;
    final UserORM orm;
    
    @Autowired
    public UserDAO(DataSource datasource, UserORM orm)
    {
        this.datasource = datasource;
        this.orm = orm;
    }
   
    
    @Override
    public User insert(User domain) {
        User user = null;
        Connection connection = null;
        PreparedStatement insertStatement = null;
        ResultSet results = null;
        
        try{
            connection = datasource.getConnection();
            insertStatement = connection.prepareStatement(orm.prepareInsert(), Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, domain.getId());
            insertStatement.setString(2, domain.getEmail());
            insertStatement.setString(3,  domain.getPassword().getSalt());
            insertStatement.setString(4,  domain.getPassword().getHash());
            
            if (insertStatement.executeUpdate() > 0)
            {
                results = insertStatement.getGeneratedKeys();
                if(results.next())
                {
                    return new User(results.getInt(1), true, results.getString(2), new Password(results.getString(3), results.getString(4)));
                }
            }
            
            return user;
        } catch(SQLException e){
            throw new RuntimeException();
        } finally {
            cleanup(connection, insertStatement, results);
        }

    }

    @Override
    public boolean delete(Integer id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public User update(User domain) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User read(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int clear() throws UnsupportedOperationException {
        // TODO Auto-generated method stub
        return 0;
    }


}
