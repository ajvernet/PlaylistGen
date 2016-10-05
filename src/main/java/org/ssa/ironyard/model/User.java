package org.ssa.ironyard.model;

import java.util.ArrayList;
import java.util.List;

public class User extends DomainObject{

    final String email;
    final Password password;
    
    public User(Integer id, boolean loaded, String email, Password password) {
        super(id, loaded);
        this.email = email;
        this.password = password;
    }
    
    public User(String email, Password password)
    {
        this(null, false, email, password);
    }

    public User setId(Integer id)
    {
        return new User(id, this.loaded, this.email, this.password);
    }
    
    public User setLoaded()
    {
        return new User(this.id, true,  this.email, this.password);
    }    

    @Override
    boolean deeplyEquals(Object obj) {
  
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        return true;
        
    }   

}
