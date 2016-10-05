package org.ssa.ironyard.model;

import java.util.ArrayList;
import java.util.List;

public class PlayList extends DomainObject{

    final User user;
    final List<Episode> podcasts = new ArrayList<>();
    
    
    public PlayList(Integer id, boolean loaded, User user) {
        super(id, loaded);
        this.user = user;
        // TODO Auto-generated constructor stub
    }
    
    public PlayList()
    {
        this(null, false, null);
    }
    
    public List<Episode> getPodcasts()
    {
        return new ArrayList<>(this.podcasts);
    }
    
    public List<Episode> addPodcast(Episode podcast)
    {
        this.podcasts.add(podcast);
        return new ArrayList<>(this.podcasts);
    }
    
    public List<Episode> deletePodcast(int podcastId)
    {
        this.podcasts.remove(podcastId);
        return new ArrayList<>(this.podcasts);
    }
    
    public List<Episode> addPodcasts(List<Episode> podcasts)
    {
        this.podcasts.addAll(podcasts);
        return new ArrayList<>(this.podcasts);
    }
    
    public List<Episode> replacePodcasts(List<Episode> podcasts)
    {
        this.podcasts.clear();
        this.podcasts.addAll(podcasts);
        
        return new ArrayList<>(this.podcasts);
        
    }
    
    public PlayList setId(Integer id)
    {
        return new PlayList(id, this.loaded, this.user);
    }
    
    public PlayList setLoaded()
    {
        return new PlayList(this.id, true, this.user);
    }
    
    public PlayList setUser(User user)
    {
        return new PlayList(this.id, this.loaded, user);
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
