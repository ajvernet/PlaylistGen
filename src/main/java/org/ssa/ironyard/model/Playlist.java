package org.ssa.ironyard.model;

import org.ssa.ironyard.model.User.UserBuilder;

public class Playlist extends DomainObject {



    private final String name;
    private final User user;
    private final Integer targetDuration;

    public Playlist(Integer id, boolean loaded, String name, User user, Integer targetDuration) {
	super(id, loaded);
	this.name = name;
	this.user = user;
	this.targetDuration = targetDuration;
    }

    public Integer getTargetDuration() {
	return targetDuration;
    }

    public String getName() {
	return name;
    }

    public User getUser() {
	return user;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	DomainObject other = (DomainObject) obj;
	if (id == null) {
	    return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

    @Override
    public boolean deeplyEquals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Playlist other = (Playlist) obj;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (targetDuration == null) {
	    if (other.targetDuration != null)
		return false;
	} else if (!targetDuration.equals(other.targetDuration))
	    return false;
	if (user == null) {
	    if (other.user != null)
		return false;
	} else if (!user.equals(other.user))
	    return false;
	return true;
    }

    @Override
    public String toString() {
        return super.toString() + "Playlist [name=" + name + ", user=" + user + ", targetDuration=" + targetDuration + "]";
    }
    
    public static PlaylistBuilder builder()
    {
        return new PlaylistBuilder();
    }
    
    public static PlaylistBuilder builder(Playlist playlist)
    {
        return new PlaylistBuilder(playlist);
    }
    
    public static class PlaylistBuilder {
        private Integer id;
        private boolean loaded = false;
        private String name;
        private User user;
        private Integer targetDuration;
        
        public PlaylistBuilder() {
        }
    
        public PlaylistBuilder(Playlist playlist) {
            this.id = playlist.getId();
            this.loaded = playlist.isLoaded();
            this.name = playlist.getName();
            this.user = playlist.getUser();
            this.targetDuration = playlist.getTargetDuration();
    
        }
    
        public PlaylistBuilder id(Integer id) {
            this.id = id;
            return this;
        }
    
        public PlaylistBuilder loaded(boolean loaded) {
            this.loaded = loaded;
            return this;
        }
    
        public PlaylistBuilder name(String name) {
            this.name = name;
            return this;
        }
    
        public PlaylistBuilder user(User user) {
            this.user = user;
            return this;
        }
    
        public PlaylistBuilder targetDuration(Integer targetDuration) {
            this.targetDuration = targetDuration;
            return this;
        }
    
    
        public Playlist build() {
            return new Playlist(id, loaded, name, user, targetDuration);
        }
    }
}
