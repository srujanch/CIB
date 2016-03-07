package za.co.standardbank.sbg.cma.migration;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: svenepal
 * Date: Apr 9, 2012
 * Time: 3:45:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class MediaBean {
    private String mediaId;
    private String title;
    private String description;
    private Date fullDate;
    private String article;
    private Set<String> countryList;
    private Set<String> segmentsList;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getFullDate() {
        return fullDate;
    }

    public void setFullDate(Date fullDate) {
        this.fullDate = fullDate;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Set<String> getCountryList() {
        return countryList;
    }

    public void setCountryList(Set<String> countryList) {
        this.countryList = countryList;
    }

    public Set<String> getSegmentsList() {
        return segmentsList;
    }

    public void setSegmentsList(Set<String> segmentsList) {
        this.segmentsList = segmentsList;
    }
}
