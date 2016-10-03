package org.ssa.ironyard.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Podcast extends DomainObject{

    final String name;
    final  Float duration;
    final LocalDate publishDate;
    final List<String> tags = new ArrayList<>();
    

    public Podcast(Integer id, boolean loaded, String name, Float duration, LocalDate publishDate) {
        super(id, loaded);
        this.name = name;
        this.duration = duration;
        this.publishDate = publishDate;
    }

    public Podcast(String name, Float duration, LocalDate publishDate)
    {
        this(null, false, name, duration, publishDate);
    }
    
    public Podcast setId(Integer id)
    {
        return new Podcast(id, this.loaded, this.name, this.duration, this.publishDate);
    }
    
    public Podcast setLoaded()
    {
        return new Podcast(this.id, true,  this.name, this.duration, this.publishDate);
    }
    
    @Override
    public Integer getId() {
        // TODO Auto-generated method stub
        return null;
    }
    
    
    public String getName() {
        return this.name;
    }

    public Float getDuration() {
        return duration;
    }

    public List<String> getTags() {
        return new ArrayList<>(this.tags);
    }

    @Override
    public String toString() {
        return super.toString() + "Podcast [name=" + name + ", duration=" + duration + ", publishDate=" + publishDate + ", tags=" + tags
                + "]";
    }


    public boolean deeplyEquals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Podcast other = (Podcast) obj;
        if (duration == null) {
            if (other.duration != null)
                return false;
        } else if (!duration.equals(other.duration))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (publishDate == null) {
            if (other.publishDate != null)
                return false;
        } else if (!publishDate.equals(other.publishDate))
            return false;
        if (tags == null) {
            if (other.tags != null)
                return false;
        } else if (!tags.equals(other.tags))
            return false;
        return true;
    }  
    
}
