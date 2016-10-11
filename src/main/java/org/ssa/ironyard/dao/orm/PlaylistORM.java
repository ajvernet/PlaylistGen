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
	joiner.add("id").add("name").add("userid").add("targetDuration");
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
    default Playlist map(ResultSet results) throws SQLException {
	return Playlist.builder().id(results.getInt(table() + ".id")).name(results.getString(table() + ".name"))
		.user(User.builder().id(results.getInt(table() + ".userid")).build())
		.targetDuration(results.getInt(table() + ".targetDuration")).build();
    }

    @Override
    default Playlist eagerMap(ResultSet results) throws SQLException {
	return Playlist.builder(map(results)).user(userORM.map(results)).build();
    }

    @Override
    default String prepareInsert() {
	return "INSERT INTO " + table() + "(name, userid, targetDuration)" + " VALUES(?, ?, ?)";
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
	return "SELECT " + projection() + " FROM " + table() + " WHERE  " + table() + ".userid=?";
    }

    @Override
    default String prepareUpdate() {
	return "UPDATE " + table() + " SET name=?, userid=?, targetDuration=? WHERE " + table() + ".id=?";
    }

    @Override
    default String prepareEagerRead() {
	return "SELECT " + projection() + ", " + userORM.projection() + " FROM " + table() + " INNER JOIN "
		+ userORM.table() + " ON " + table() + ".userid = " + userORM.table() + ".ID " + "WHERE " + table()
		+ ".id=?";
    }
}
