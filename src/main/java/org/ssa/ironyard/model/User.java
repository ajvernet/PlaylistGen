package org.ssa.ironyard.model;

import java.util.ArrayList;
import java.util.List;

public class User extends DomainObject{

    final String email;
    final List<PlayList> playLists = new ArrayList<>();
    
    public User(Integer id, boolean loaded, String email) {
        super(id, loaded);
        this.email = email;
    }
    
    public User(String email)
    {
        this(null, false, email);
    }

    public User setId(Integer id)
    {
        return new User(id, this.loaded, this.email);
    }
    
    public User setLoaded()
    {
        return new User(this.id, true,  this.email);
    }    
    
    public List<PlayList> getLists()
    {
        return new ArrayList<PlayList>(playLists);
    }
    
    public List<PlayList> addList(PlayList list)
    {
        this.playLists.add(list);
        return new ArrayList<>(this.playLists);
    }
    
    public List<PlayList> deleteList(int index)
    {
        this.playLists.remove(index);
        return new ArrayList<>(this.playLists);
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
            return true;
        
    }

}
