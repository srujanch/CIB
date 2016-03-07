package za.co.standardbank.sbg.cda.templating.data;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: svenepal
 * Date: Apr 24, 2012
 * Time: 11:57:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class PressRelease implements DAMObject {

    private Date startDate;

    private Date endDate;

    private String country;

    private String keyword;

    private List segment;

    public List getSegment() {
        return segment;
    }

    public void setSegment(List segment) {
        this.segment = segment;
    }

    public Date getStartDate() {
        return startDate;
    }



    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
