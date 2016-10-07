package org.ssa.ironyard.model;

import java.util.Objects;

/**
 *
 * @author thurston
 */
public abstract class DomainObject implements Cloneable
{
    
    final Integer id;
    final boolean loaded;
    
    
    
    public DomainObject(Integer id, boolean loaded) {
        super();
        this.id = id;
        this.loaded = loaded;
    }
    
    
    public Integer getId()
    {
        return this.id;
    }
    
    public boolean isLoaded()
    {
        return this.loaded;
    }
    
    abstract boolean deeplyEquals(Object obj);

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "DomainObject [id=" + id + ", loaded=" + loaded + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DomainObject other = (DomainObject) obj;
        if (id == null) {
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public DomainObject clone()
    {
        return this;
    }
}


