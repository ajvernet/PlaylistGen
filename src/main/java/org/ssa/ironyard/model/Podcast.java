package org.ssa.ironyard.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Podcast extends DomainObject{

    final String name;
    final Integer duration;
    final LocalDate publishDate;
    final List<String> tags = new ArrayList<>();
    final String fileUrl;
    



    public Podcast(Integer id, boolean loaded, String name, Integer duration, LocalDate publishDate, String fileUrl) {
        super(id, loaded);
        this.name = name;
        this.duration = duration;
        this.publishDate = publishDate;
        this.fileUrl = fileUrl;
    }

    public Podcast(String name, Integer duration, LocalDate publishDate, String fileUrl)
    {
        this(null, false, name, duration, publishDate, fileUrl);
    }
    
    public Podcast setId(Integer id)
    {
        return new Podcast(id, this.loaded, this.name, this.duration, this.publishDate, this.fileUrl);
    }
    
    public Podcast setLoaded()
    {
        return new Podcast(this.id, true,  this.name, this.duration, this.publishDate, this.fileUrl);
    }    
    
    public List<String> addTag(String tag)
    {
        this.tags.add(tag);
        return new ArrayList<>(tags);
    }
    
    public List<String> addTags(List<String> tagList)
    {
        this.tags.addAll(tagList);
        return new ArrayList<>(tags);
    }
    
    public String getName() {
        return this.name;
    }

    public Integer getDuration() {
        return duration;
    }

    public List<String> getTags() {
        return new ArrayList<>(this.tags);
    }


    public LocalDate getPublishDate() {
        return publishDate;
    }

    public String getfileUrl() {
        return fileUrl;
    }

    @Override
    public String toString() {
        return super.toString() + "Podcast [name=" + name + ", duration=" + duration + ", publishDate=" + publishDate + ", tags=" + tags
                + ", fileUrl=" + fileUrl + "]";
    }

    @Override
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
        if (fileUrl == null) {
            if (other.fileUrl != null)
                return false;
        } else if (!fileUrl.equals(other.fileUrl))
            return false;
        return true;
    }

}
