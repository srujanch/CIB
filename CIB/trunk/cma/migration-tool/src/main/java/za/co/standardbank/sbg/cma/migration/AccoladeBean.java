package za.co.standardbank.sbg.cma.migration;

import java.util.List;
import java.util.Set;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: svenepal
 * Date: Mar 22, 2012
 * Time: 9:07:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccoladeBean {

    private String accoladeID;
    private String description;
    private List<Integer> publicationYear;
    private int latestPublicationYear;
    private String status;
    private String fullDescription;
    private String publishToHome;
    private String publication;
    private String region;
    private String sector;
    private Set<String> countryList;
    private Set<String> segment;
    private Set<String> productList;
    private List<String> accoladeIDList;
    private Date captureDate;

    public Date getCaptureDate() {
        return captureDate;
    }

    public void setCaptureDate(Date captureDate) {
        this.captureDate = captureDate;
    }

    public String getAccoladeID() {
        return accoladeID;
    }

    public void setAccoladeID(String accoladeID) {
        this.accoladeID = accoladeID;
    }

    public List<String> getAccoladeIDList() {
        return accoladeIDList;
    }

    public void setAccoladeIDList(List<String> accoladeIDList) {
        this.accoladeIDList = accoladeIDList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(List<Integer> publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getPublishToHome() {
        return publishToHome;
    }

    public void setPublishToHome(String publishToHome) {
        this.publishToHome = publishToHome;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Set<String> getCountryList() {
        return countryList;
    }

    public void setCountryList(Set countryList) {
        this.countryList = countryList;
    }

    public Set<String> getSegment() {
        return segment;
    }

    public void setSegment(Set<String> segment) {
        this.segment = segment;
    }

    public Set<String> getProductList() {
        return productList;
    }

    public void setProductList(Set productList) {
        this.productList = productList;
    }

    public int getLatestPublicationYear() {
        return latestPublicationYear;
    }

    public void setLatestPublicationYear(int latestPublicationYear) {
        this.latestPublicationYear = latestPublicationYear;
    }
}
