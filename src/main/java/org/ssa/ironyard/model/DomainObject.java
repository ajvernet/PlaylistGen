package org.ssa.ironyard.model;

public abstract class DomainObject {

    final Integer id;
    final String name;
    final Boolean isLoaded; 
    
    
    public DomainObject(int id, String name, Boolean isLoaded) {
        super();
        this.id = id;
        this.name = name;
        this.isLoaded = isLoaded;
    }
    
    public DomainObject(int id, String name){
        this(id, name, false);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
}
