package org.ssa.ironyard.model;

public class PodcastSeries extends DomainObject{

    public PodcastSeries(Integer id, boolean loaded) {
        super(id, loaded);
        // TODO Auto-generated constructor stub
    }
    
    public PodcastSeries setId(Integer id)
    {
        return new PodcastSeries(id, this.loaded);
    }
    
    public PodcastSeries setLoaded()
    {
        return new PodcastSeries(this.id, true);
    }
    
    @Override
    public Integer getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    boolean deeplyEquals(Object other) {
        
        return super.equals(other);
    }
    
    
}
