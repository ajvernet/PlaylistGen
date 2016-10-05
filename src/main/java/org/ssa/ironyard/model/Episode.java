package org.ssa.ironyard.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Episode extends DomainObject{

    final Series series;
    final String name;
    final Integer duration;
    final LocalDate publishDate;
    final List<String> tags = new ArrayList<>();
    final String fileUrl;

    public Episode(Integer id, boolean loaded, Series series, String name, Integer duration,
            LocalDate publishDate, String fileUrl) {
        super(id, loaded);
        this.series = series;
        this.name = name;
        this.duration = duration;
        this.publishDate = publishDate;
        this.fileUrl = fileUrl;
    }
    
    public Episode()
    {
        this(null, false, null, null, null, null, null);
    }
    

    public Episode(Series series, String name, Integer duration, LocalDate publishDate, String fileUrl)
    {
        this(null, false, series, name, duration, publishDate, fileUrl);
    }
    
    public Episode setId(Integer id)
    {
        return new Episode(id, this.loaded, this.series, this.name, this.duration, this.publishDate, this.fileUrl);
    }
    
    public Episode setLoaded()
    {
        return new Episode(this.id, true, this.series, this.name, this.duration, this.publishDate, this.fileUrl);
    }    
    
    public Episode setSeries(Series series)
    {
        return new Episode(this.id, this.loaded, series, this.name, this.duration, this.publishDate, this.fileUrl);
    }
    
    public Episode setDuration(Integer duration)
    {
        return new Episode(this.id, this.loaded, this.series, this.name, duration, this.publishDate, this.fileUrl);
    }
    
    public Episode setPublishDate(LocalDate date)
    {
        return new Episode(this.id, this.loaded, this.series, this.name, this.duration, date, this.fileUrl);
    }
    
    public Episode setURL(String url)
    {
        return new Episode(this.id, this.loaded, this.series, this.name, this.duration, this.publishDate, url);
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
    
    public Series getSeries() {
        return this.series;
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
        Episode other = (Episode) obj;
        if (duration == null) {
            if (other.duration != null)
                return false;
        } else if (!duration.equals(other.duration))
            return false;
        if (fileUrl == null) {
            if (other.fileUrl != null)
                return false;
        } else if (!fileUrl.equals(other.fileUrl))
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
        if (series == null) {
            if (other.series != null)
                return false;
        } else if (!series.equals(other.series))
            return false;
        if (tags == null) {
            if (other.tags != null)
                return false;
        } else if (!tags.equals(other.tags))
            return false;
        return true;
    }
}

