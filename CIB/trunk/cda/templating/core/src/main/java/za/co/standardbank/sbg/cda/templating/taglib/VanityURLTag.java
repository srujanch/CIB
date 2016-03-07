package za.co.standardbank.sbg.cda.templating.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import za.co.standardbank.sbg.cda.templating.web.util.DPMConstants;
import za.co.standardbank.sbg.cda.templating.web.util.DisplayUtil;

import com.vignette.ext.templating.util.PageUtil;
import com.vignette.ext.templating.util.RequestContext;
import com.vignette.ext.templating.util.XSLPageUtil;

/**
 * Created by IntelliJ IDEA. User: svenepal Date: Apr 25, 2012 Time: 5:22:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class VanityURLTag extends TagSupport {
	private static final Logger logger = Logger.getLogger(VanityURLTag.class);
	private String var;
	private String vanityURL;
	private String format;
	private String itemType;

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getVanityURL() {
		return vanityURL;
	}

	public void setVanityURL(String vanityURL) {
		this.vanityURL = vanityURL;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	/* *************************************************************************************************
	 * IMPLEMENTATON
	 * ************************************************************
	 * **********************
	 * ****************************************************
	 * *********************************************
	 */

	/**
	 * Implementation of the start of the tag.
	 * 
	 * @return int which specifies to evaluate the body of the tag.
	 */
	public int doStartTag() throws JspException {
		if (logger.isDebugEnabled())
			logger.debug("--Start VanityURLTag::vanityURL::" + getVanityURL() + " format::" + getFormat());
		try {
			RequestContext rc = PageUtil.getCurrentRequestContext(pageContext);
			if (getItemType() == null) {
				setItemType(DPMConstants.CHANNEL_TYPE);
			}
			String str = DisplayUtil.getValueByVanityURL(getVanityURL(), getItemType());
			if (getFormat() == null) {
				setFormat(DPMConstants.DEFAULT_FORMAT);
			}
			String url = (str != null) ? ((DPMConstants.EXTERNAL_URL_TYPE.equals(getItemType())) ? str : XSLPageUtil.buildLinkURI(rc, str, "", getFormat())) : "";
			if (logger.isDebugEnabled())
				logger.debug("friendly URL::" + url);
			pageContext.setAttribute(getVar(), url, PageContext.REQUEST_SCOPE);
		} catch (Exception ex) {
			logger.error("Exception occurred in VanityURLTag::" + ex.getMessage());
			throw new JspException("Exception occurred in VanityURLTag::" + ex.getMessage());
		}
		if (logger.isDebugEnabled())
			logger.debug("--End VanityURLTag");
		return (SKIP_BODY);
	}
}
