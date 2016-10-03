package org.ssa.ironyard.dao;

import org.ssa.ironyard.model.DomainObject;

public interface DAO<T extends DomainObject> {

    T insert(T domain);
    
    boolean delete(Integer id);

    T update(T domain);

    T read(Integer id);

    int clear() throws UnsupportedOperationException;

}