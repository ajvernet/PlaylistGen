package org.ssa.ironyard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.ssa.ironyard.dao.orm.PlaylistORM;
import org.ssa.ironyard.dao.orm.UserORM;
import org.ssa.ironyard.model.Playlist;


public class PlaylistDAO extends AbstractSpringDAO<Playlist>{
  
    protected PlaylistDAO(PlaylistORM orm, DataSource dataSource) {
        super(orm, dataSource);
    }


    @Autowired
    public PlaylistDAO(DataSource dataSource){
        this(new PlaylistORM(){}, dataSource);        
    }
    
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
    
    @Override
    protected PreparedStatementSetter updatePreparer(Playlist domainToUpdate) {
        return new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
            ps.setString(1, domainToUpdate.getName());
            ps.setInt(2, domainToUpdate.getUser().getId());
            ps.setInt(3, domainToUpdate.getTargetDuration());
            ps.setInt(4, domainToUpdate.getId());
            }
        };
    }
    
    public List<Playlist> readByUser(Integer user_id){
        List<Playlist> lists = new ArrayList<Playlist>();
       
        return this.springTemplate.query(((PlaylistORM)this.orm).prepareReadByUser(), (PreparedStatement ps) -> ps.setInt(1, user_id),
                    (ResultSet cursor) -> 
                     {  while (cursor.next())
                          lists.add(this.orm.map(cursor));
                      
                     return lists;
        }); 
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
