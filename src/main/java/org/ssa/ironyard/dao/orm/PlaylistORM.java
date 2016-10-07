package org.ssa.ironyard.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;
import org.ssa.ironyard.model.Playlist;
import org.ssa.ironyard.model.User;

public interface PlaylistORM extends ORM<Playlist> {

    public static UserORM userORM = new UserORM() {
    };

    @Override
    default String projection() {
        StringJoiner joiner = new StringJoiner(", " + table() + ".", table() + ".", "");
        joiner.add("id").add("name").add("user_id").add("target_duration");
        System.out.println(joiner.toString());
        return joiner.toString();
    }

    @Override
    default String eagerProjection() {
        return projection();
    }

    @Override
    default String table() {
        return "Playlists";
    }

    @Override
    default Playlist map(ResultSet results) throws SQLException
    {
        return Playlist.builder()
                .id(results.getInt(table() + ".id"))
                .name(results.getString(table()+ ".name"))
                .user(User.builder().id(results.getInt(table() + ".user_id")).build())
                .targetDuration(results.getInt(table()+ ".target_duration"))
                .build();
    }

    @Override
    default Playlist eagerMap(ResultSet results) throws SQLException {
        return Playlist.builder(map(results)).user(userORM.map(results)).build();

    }

    @Override
    default String prepareInsert() {
        return "INSERT INTO " + table() + "(name, user_id)" + " VALUES(?, ?)";
    }

    @Override
    default String prepareDelete() {
        return "DELETE FROM " + table() + " WHERE " + table() + ".id=?";
    }

    @Override
    default String prepareRead() {
        return "SELECT " + projection() + " FROM " + table() + " WHERE " + table() + ".id=?";
    }

    default String prepareReadByUser() {
        return "SELECT " + projection() + " FROM " + table() + " WHERE  " + table() + ".user_id=?";
    }

    @Override
    default String prepareUpdate() {
        return "UPDATE " + table() + " SET name=?, User_ID=?, target_duration=? WHERE " + table() + ".id=?)";
    }

    @Override
    default String prepareEagerRead() {
        return "SELECT " + projection() + ", " + userORM.projection() + " FROM " + table() + " INNER JOIN "
                + userORM.table() + " ON " + table() + ".user_id = " + userORM.table() + ".ID " + "WHERE " + table()
                + ".id=?";

    }
}
