package tv.ustream.domain;

import java.util.Date;

public class Repository {

    private String name;
    private Date creationTime;
    private String creator;
    private long accessCounter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public long getAccessCounter() {
        return accessCounter;
    }

    public void setAccessCounter(long accessCounter) {
        this.accessCounter = accessCounter;
    }
}
