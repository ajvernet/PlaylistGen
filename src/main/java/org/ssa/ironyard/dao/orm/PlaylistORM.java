package org.ssa.ironyard.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;
import org.ssa.ironyard.model.Playlist;
import org.ssa.ironyard.model.User;


public class PlaylistORM implements ORM<Playlist> {
  
    @Override
    public String projection(){
        StringJoiner joiner = new StringJoiner(", " + table() + ".", table() + ".", "");
        joiner.add("id").add("name").add("user_id");
        System.out.println(joiner.toString());
        return joiner.toString();
    }
    
    @Override
    public String eagerProjection()
    {
        return projection();
    }
    
    @Override
    public String table()
    {
        return "Playlists";
    }
    
    @Override
    public Playlist map(ResultSet results) throws SQLException
    {
        Integer id = results.getInt(1);
        String name = results.getString(2);
        Integer user_id = results.getInt(3);
        
        return new Playlist(id, true, name, new User().setId(user_id));
    }

    
    @Override
    public Playlist eagerMap(ResultSet results) throws SQLException
    {
        return map(results);

    }
    @Override
    public String prepareInsert(){
        return "INSERT INTO" + table() + "(name, user_id)" + " VALUES(?, ?)";
    }
    
    @Override
    public String prepareDelete() {
        return "DELETE FROM " + table() + " WHERE id=?";
    }
    
    @Override
    public String prepareRead() {
        return "SELECT " + projection() + " FROM " + table() + " WHERE id=?";
    }
    
    public String prepareReadByUser(){
        return "SELECT " + projection() + " FROM " + table() + 
                " WHERE user_id=?";
    }

    @Override
    public String prepareUpdate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String prepareEagerRead() {
        // TODO Auto-generated method stub
        return null;
    }
}
