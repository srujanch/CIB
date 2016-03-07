package za.co.standardbank.sbg.cma.migration;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: svenepal
 * Date: Mar 26, 2012
 * Time: 2:09:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class DealBean {

    private String dealID;
    private String title;
    private String description;
    private Date dealAnnouncedDate;
    private String sectorName;
    private String clientName;
    private String dealAmount;
    private String currency;
    private String contactName;
    private String contactTelNumber;
    private String status;
    private String regionName;
    private String summary;
    private String dollarAmount;
    private String exchangeRate;
    private String publishToHome;
    private String clientConsent;
    private String clientConfidentiality;
    private String clientConsulted;
    private String clientClearnace;
    private String comments;

    private Set<String> countryList;
    private Set<String> productList;

    private Set<String> segment;

    public Set<String> getCountryList() {
        return countryList;
    }

    public void setCountryList(Set<String> countryList) {
        this.countryList = countryList;
    }

    public Set<String> getProductList() {
        return productList;
    }

    public void setProductList(Set<String> productList) {
        this.productList = productList;
    }

    public Set<String> getSegment() {
        return segment;
    }

    public void setSegment(Set<String> segment) {
        this.segment = segment;
    }

    public String getDealID() {
        return dealID;
    }

    public void setDealID(String dealID) {
        this.dealID = dealID;
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

    public Date getDealAnnouncedDate() {
        return dealAnnouncedDate;
    }

    public void setDealAnnouncedDate(Date dealAnnouncedDate) {
        this.dealAnnouncedDate = dealAnnouncedDate;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(String dealAmount) {
        this.dealAmount = dealAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTelNumber() {
        return contactTelNumber;
    }

    public void setContactTelNumber(String contactTelNumber) {
        this.contactTelNumber = contactTelNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDollarAmount() {
        return dollarAmount;
    }

    public void setDollarAmount(String dollarAmount) {
        this.dollarAmount = dollarAmount;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getPublishToHome() {
        return publishToHome;
    }

    public void setPublishToHome(String publishToHome) {
        this.publishToHome = publishToHome;
    }

    public String getClientConsent() {
        return clientConsent;
    }

    public void setClientConsent(String clientConsent) {
        this.clientConsent = clientConsent;
    }

    public String getClientConfidentiality() {
        return clientConfidentiality;
    }

    public void setClientConfidentiality(String clientConfidentiality) {
        this.clientConfidentiality = clientConfidentiality;
    }

    public String getClientConsulted() {
        return clientConsulted;
    }

    public void setClientConsulted(String clientConsulted) {
        this.clientConsulted = clientConsulted;
    }

    public String getClientClearnace() {
        return clientClearnace;
    }

    public void setClientClearnace(String clientClearnace) {
        this.clientClearnace = clientClearnace;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
