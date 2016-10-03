package org.ssa.ironyard.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ssa.ironyard.model.DomainObject;

public interface ORM<T extends DomainObject> {
    String projection();
    
    String eagerProjection();

    String table();

    T map(ResultSet results) throws SQLException;
    
    T eagerMap(ResultSet results) throws SQLException;

    String prepareInsert();

    String prepareUpdate();

    String prepareRead();

    String prepareDelete();
    
    String prepareEagerRead();

}
