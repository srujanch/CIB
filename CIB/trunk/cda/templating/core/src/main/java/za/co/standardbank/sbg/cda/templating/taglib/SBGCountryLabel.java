package za.co.standardbank.sbg.cda.templating.taglib;

import org.apache.log4j.Logger;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;

import za.co.standardbank.sbg.cda.templating.web.util.ReferenceDataLoder;

/**
 * Created by IntelliJ IDEA.
 * User: svenepal
 * Date: Apr 13, 2012
 * Time: 5:03:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class SBGCountryLabel extends TagSupport {

    Logger logger = Logger.getLogger(SBGCountryLabel.class);
    private String var;
    private String countryKey;

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getCountryKey() {
        return countryKey;
    }

    public void setCountryKey(String countryKey) {
        this.countryKey = countryKey;
    }
/* *************************************************************************************************
	 * IMPLEMENTATON **********************************************************************************
	 ***************************************************************************************************/

    /**
     * Implementation of the start of the tag.
     *
     * @return int which specifies to evaluate the body of the tag.
     *
     */
    public int doStartTag() throws JspException {
        if(logger.isDebugEnabled()){
            logger.debug("Entered method doStartTag()");
        }
        String label = ReferenceDataLoder.getCountryLabelByKey(getCountryKey());
        pageContext.setAttribute(getVar(),label);
        if(logger.isDebugEnabled()){
            logger.debug("Exit method doStartTag()");
        }
        return (SKIP_BODY);
    }

}
