package org.ssa.ironyard.dao;

<<<<<<< HEAD:src/main/java/org/ssa/ironyard/dao/PlayListDAO.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.ssa.ironyard.dao.orm.ORM;
import org.ssa.ironyard.dao.orm.PlayListORM;
import org.ssa.ironyard.model.PlayList;

public class PlayListDAO extends AbstractSpringDAO<PlayList>{

  final PlayListORM listorm = new PlayListORM();
    
    protected PlayListDAO(PlayListORM orm, DataSource dataSource) {
        super(orm, dataSource);
        // TODO Auto-generated constructor stub
=======
import org.ssa.ironyard.model.Playlist;

public class PlaylistDAO implements DAO<Playlist>{

    @Override
    public Playlist insert(Playlist domain) {
        // TODO Auto-generated method stub
        return null;
>>>>>>> 6a94ade0474692028e5e533afb7bfb10a92baab6:src/main/java/org/ssa/ironyard/dao/PlaylistDAO.java
    }

//    @Autowired
//    public PlayListDAO(DataSource dataSource){
//        this(dataSource, new PlayListORM<PlayList>() {
//        });        
//    }
    
    @Override
    protected void insertPreparer(PreparedStatement insertStatement, PlayList domainToInsert) throws SQLException {
        insertStatement.setString(1, domainToInsert.getName());
        insertStatement.setInt(2, domainToInsert.getUser().getId());
    }

    @Override
<<<<<<< HEAD:src/main/java/org/ssa/ironyard/dao/PlayListDAO.java
    protected PlayList afterInsert(PlayList copy, Integer id) {
=======
    public Playlist update(Playlist domain) {
>>>>>>> 6a94ade0474692028e5e533afb7bfb10a92baab6:src/main/java/org/ssa/ironyard/dao/PlaylistDAO.java
        // TODO Auto-generated method stub
        return copy.setId(id).setLoaded();
    }

    @Override
<<<<<<< HEAD:src/main/java/org/ssa/ironyard/dao/PlayListDAO.java
    protected PlayList afterUpdate(PlayList copy) {
        return copy.setLoaded();
=======
    public Playlist read(Integer id) {
        // TODO Auto-generated method stub
        return null;
>>>>>>> 6a94ade0474692028e5e533afb7bfb10a92baab6:src/main/java/org/ssa/ironyard/dao/PlaylistDAO.java
    }

    @Override
    protected PreparedStatementSetter updatePreparer(PlayList domainToUpdate) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public List<PlayList> readByUser(Integer user_id){
        List<PlayList> lists = new ArrayList<PlayList>();
        Connection connection = null;
        PreparedStatement readStatement;
        
        try
        {
            connection = dataSource.getConnection();
            readStatement = connection.prepareStatement(listorm.prepareReadByUser());
            readStatement.setInt(1, user_id);
            ResultSet results = readStatement.executeQuery();
            
            while (results.next())
                {
                PlayList p = listorm.map(results);
                lists.add(p);
                }
            
            this.cleanup(connection, readStatement, results);
            return lists;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            this.cleanup(connection, null, null);
        }
        return lists;
        
        
    }


}
