package org.ssa.ironyard.model;

import java.util.ArrayList;
import java.util.List;

public class PlayList extends DomainObject{

    
    final List<Podcast> podcasts = new ArrayList<>();
    
    
    public PlayList(Integer id, boolean loaded) {
        super(id, loaded);
        // TODO Auto-generated constructor stub
    }
    
    public PlayList()
    {
        this(null, false);
    }
    
    public List<Podcast> getPodcasts()
    {
        return new ArrayList<>(this.podcasts);
    }
    
    public List<Podcast> addPodcast(Podcast podcast)
    {
        this.podcasts.add(podcast);
        return new ArrayList<>(this.podcasts);
    }
    
    public List<Podcast> deletePodcast(int podcastId)
    {
        this.podcasts.remove(podcastId);
        return new ArrayList<>(this.podcasts);
    }
    
    public List<Podcast> addPodcasts(List<Podcast> podcasts)
    {
        this.podcasts.addAll(podcasts);
        return new ArrayList<>(this.podcasts);
    }
    
    public List<Podcast> replacePodcasts(List<Podcast> podcasts)
    {
        this.podcasts.clear();
        this.podcasts.addAll(podcasts);
        
        return new ArrayList<>(this.podcasts);
        
    }
    
    public PlayList setId(Integer id)
    {
        return new PlayList(id, this.loaded);
    }
    
    public PlayList setLoaded()
    {
        return new PlayList(this.id, true);
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
