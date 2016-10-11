
package org.ssa.ironyard.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

import org.ssa.ironyard.model.Show;



public interface ShowORM extends ORM<Show> {

    @Override
    default String projection() {
	StringJoiner joiner = new StringJoiner(", " + table() + ".", table() + ".", "");
        joiner
            .add("id")
            .add("name")
            .add("showId")
            .add("imgUrl")
            .add("thumbUrl");
        return joiner.toString();
    }

    @Override
    default String eagerProjection() {
	return projection();
    }

    @Override
    default String table() {return "shows";
    }

    @Override
    default Show map(ResultSet results) throws SQLException {
	return Show.builder()
		.id(results.getInt("id"))
		.loaded(true)
		.name(results.getString("name"))
		.showId(results.getInt("showId"))
		.imgUrl(results.getString("imgUrl"))
		.thumbUrl(results.getString("thumbUrl"))		
		.build();
    }

    @Override
    default Show eagerMap(ResultSet results) throws SQLException {return map(results);
    }

    @Override
    default String prepareInsert() {
	return "INSERT INTO " + table() + "(name, showId, imgUrl, thumbUrl) VALUES(?,?,?,?)";
    }

    @Override
    default String prepareUpdate() {
	return "UPDATE " + table() + "SET name=?, showId=?, imgUrl=?, thumbUrl=? WHERE id=?";
    }

    @Override
    default String prepareRead() {
	return "SELECT " + projection() + " FROM " + table() + " WHERE id=?";
    }

    @Override
    default String prepareDelete() {
	return "DELETE FROM " + table() + " WHERE id=?";
    }

    @Override
    default String prepareEagerRead() {
	return prepareRead();
    }

}
