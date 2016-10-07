package org.ssa.ironyard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Ignore;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.ssa.ironyard.dao.orm.PlaylistORM;
import org.ssa.ironyard.model.Playlist;


public class PlaylistDAO extends AbstractSpringDAO<Playlist>{

  final PlaylistORM listorm = new PlaylistORM();
    
    protected PlaylistDAO(PlaylistORM orm, DataSource dataSource) {
        super(orm, dataSource);
    }

    @Override
    public Playlist insert(Playlist domain) {
        // TODO Auto-generated method stub
        return null;
   }

//    @Autowired
//    public PlaylistDAO(DataSource dataSource){
//        this(dataSource, new PlaylistORM<Playlist>() {
//        });        
//    }
    
    @Override
    protected void insertPreparer(PreparedStatement insertStatement, Playlist domainToInsert) throws SQLException {
        insertStatement.setString(1, domainToInsert.getName());
        insertStatement.setInt(2, domainToInsert.getUser().getId());
    }

    @Override
    protected Playlist afterInsert(Playlist copy, Integer id) {
        return Playlist.builder(copy).loaded(true).id(id).build();
    }

    @Override
    protected Playlist afterUpdate(Playlist copy) {
        return Playlist.builder(copy).loaded(true).build();
    }
    
//    @Override
//    protected PreparedStatementSetter updatePreparer(Playlist domainToUpdate) {
//        return new PreparedStatementSetter() {
//
//            @Override
//            public void setValues(PreparedStatement ps) throws SQLException {
//            ps.setString(1, domainToUpdate.getEmail());
//            ps.setString(2, domainToUpdate.getPassword().getSalt());
//            ps.setString(3, domainToUpdate.getPassword().getHash());
//            ps.setString(4, domainToUpdate.getFirstName());
//            ps.setString(5, domainToUpdate.getLastName());
//            ps.setString(6, domainToUpdate.getAddress().getStreet());
//            ps.setString(7, domainToUpdate.getAddress().getCity());
//            ps.setString(8, domainToUpdate.getAddress().getState().getAbbreviation());
//            ps.setString(9, domainToUpdate.getAddress().getZip().datafy());
//            ps.setInt(10, domainToUpdate.getId());
//            }
//        };
//        // TODO Auto-generated method stub
//        return null;
//    }
//    
    public List<Playlist> readByUser(Integer user_id){
        List<Playlist> lists = new ArrayList<Playlist>();
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
                Playlist p = listorm.map(results);
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

    @Override
    public boolean delete(Integer id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int clear() throws UnsupportedOperationException {
        // TODO Auto-generated method stub
        return 0;
    }


}
