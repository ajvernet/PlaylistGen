package org.ssa.ironyard.model;

import java.util.ArrayList;
import java.util.List;

public class PlayList extends DomainObject{

    
    final List<Podcast> podcasts = new ArrayList<>();
    
    
    public PlayList(Integer id, boolean loaded) {
        super(id, loaded);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean deeplyEquals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        PlayList other = (PlayList) obj;
        if (podcasts == null) {
            if (other.podcasts != null)
                return false;
        } else if (!podcasts.equals(other.podcasts))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + "PlayList [podcasts=" + podcasts + "]";
    }


    
    
}
