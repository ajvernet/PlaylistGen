package org.ssa.ironyard.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

import org.ssa.ironyard.model.Playlist;
import org.ssa.ironyard.model.User;

public interface PlaylistORM extends ORM<Playlist> {

    public static EpisodeORM episodeOrm = new EpisodeORM() {
    };

    @Override
    default String projection() {
	StringJoiner joiner = new StringJoiner(", " + table() + ".", table() + ".", "");
	joiner.add("id").add("name").add("userId").add("targetDuration").add("currentDuration");
	return joiner.toString();
    }

    @Override
    default String eagerProjection() {
	return projection() + ", " + episodeOrm.projection();
    }

    @Override
    default String table() {
	return "Playlists";
    }

    @Override
    default Playlist map(ResultSet results) throws SQLException {
	return Playlist.builder().id(results.getInt(table() + ".id")).name(results.getString(table() + ".name"))
		.user(User.builder().id(results.getInt(table() + ".userid")).build())
		.targetDuration(results.getInt(table() + ".targetDuration"))
		.currentDuration(results.getInt(table() + ".currentDuration")).build();
    }

    @Override
    default Playlist eagerMap(ResultSet results) throws SQLException {
	return map(results);
    }

    @Override
    default String prepareInsert() {
	return "INSERT INTO " + table() + "(name, userid, targetDuration, currentDuration)" + " VALUES(?, ?, ?, ?)";
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
	return "UPDATE " + table() + " SET name=?, userid=?, targetDuration=?, currentDuration=? WHERE " + table() + ".id=?";
    }

    @Override
    default String prepareEagerRead() {
	return "SELECT " + projection() + ", " + episodeOrm.projection() + " FROM " + table() + " INNER JOIN PlaylistEpisodes ON " + table()
		+ ".id=PlaylistEpisodes.playlistId INNER JOIN " + episodeOrm.table() + " ON PlaylistEpisodes.episodeId="
		+ episodeOrm.table() + ".id WHERE " + table() + ".id=? ORDER BY PlaylistEpisodes.sortOrder ASC";
    }
}
