package org.ssa.ironyard.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;
import org.ssa.ironyard.model.Playlist;
import org.ssa.ironyard.model.User;

public class PlaylistORM implements ORM<Playlist> {

    public static UserORM userORM = new UserORM() {
    };

    @Override
    public String projection() {
        StringJoiner joiner = new StringJoiner(", " + table() + ".", table() + ".", "");
        joiner.add("id").add("name").add("user_id").add("target_duration");
        System.out.println(joiner.toString());
        return joiner.toString();
    }

    @Override
    public String eagerProjection() {
        return projection();
    }

    @Override
    public String table() {
        return "Playlists";
    }

    @Override
    public Playlist map(ResultSet results) throws SQLException
    {
        return Playlist.builder()
                .id(results.getInt(table() + ".id"))
                .name(results.getString(table()+ ".name"))
                .user(User.builder().id(results.getInt(table() + ".user_id")).build())
                .targetDuration(results.getInt(table()+ ".target_duration"))
                .build();
    }

    @Override
    public Playlist eagerMap(ResultSet results) throws SQLException {
        return Playlist.builder(map(results)).user(userORM.map(results)).build();

    }

    @Override
    public String prepareInsert() {
        return "INSERT INTO" + table() + "(name, user_id)" + " VALUES(?, ?)";
    }

    @Override
    public String prepareDelete() {
        return "DELETE FROM " + table() + " WHERE " + table() + ".id=?";
    }

    @Override
    public String prepareRead() {
        return "SELECT " + projection() + " FROM " + table() + " WHERE " + table() + ".id=?";
    }

    public String prepareReadByUser() {
        return "SELECT " + projection() + " FROM " + table() + " WHERE  " + table() + ".user_id=?";
    }

    @Override
    public String prepareUpdate() {
        return "UPDATE " + table() + " SET name=?, User_ID=?, target_duration=? WHERE " + table() + ".id=?)";
    }

    @Override
    public String prepareEagerRead() {
        return "SELECT " + projection() + ", " + userORM.projection() + " FROM " + table() + " INNER JOIN "
                + userORM.table() + " ON " + table() + ".user_id = " + userORM.table() + ".ID " + "WHERE " + table()
                + ".id=?";

    }
}
