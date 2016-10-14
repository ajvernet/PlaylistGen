package org.ssa.ironyard.service.mapper;

public class AudioFiles {

    private String mp3;
    private String url;

    public AudioFiles(){}
    
    public AudioFiles(String mp3) {
	super();
	this.mp3 = mp3;
    }

    public String getMp3() {
        return mp3;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMp3(String mp3) {
        this.mp3 = mp3;
    }

    @Override
    public String toString() {
	return "AudioFile [mp3=" + mp3 + "]";
    }
    
    
}
