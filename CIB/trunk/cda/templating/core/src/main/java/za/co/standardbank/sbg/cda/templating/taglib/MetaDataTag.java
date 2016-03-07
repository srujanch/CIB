package za.co.standardbank.sbg.cda.templating.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.vignette.as.client.javabean.Channel;
import com.vignette.as.client.javabean.ManagedObject;
import com.vignette.as.client.javabean.Site;
import com.vignette.ext.templating.client.javabean.Page;
import com.vignette.ext.templating.util.PageUtil;
import com.vignette.ext.templating.util.RequestContext;

public class MetaDataTag extends TagSupport {
	Logger logger = Logger.getLogger(MetaDataTag.class);

	public int doStartTag() throws JspException {
		try {
			RequestContext rc = PageUtil.getCurrentRequestContext(pageContext);
			ManagedObject primaryRequestedObject = rc.getPrimaryRequestedObject();
			Site currentSite = rc.getCurrentSite();
			String siteBrandName = (String) currentSite.getAttributeValue("SEO-TITLE-PREFIX");
			Channel currentChannel = rc.getRequestedChannel();

			String metaTitle = rc.getMetaTitle();
			String metaKeywords = rc.getMetaKeywords();
			String metaDescription = rc.getMetaDescription();
			if (metaTitle == null || metaTitle.isEmpty()) {
				if (primaryRequestedObject instanceof Page) {
					metaTitle = currentChannel.getName();
				} else {
					metaTitle = primaryRequestedObject.getName();
				}
			}
			String pageTitle = siteBrandName + " | " + metaTitle;
			if (metaKeywords == null || metaKeywords.trim().equals("")) {
				metaKeywords = metaTitle;
			}
			if (metaDescription == null || metaDescription.trim().equals("")) {
				metaDescription = metaTitle;
			}

			pageContext.setAttribute("title", pageTitle, PageContext.REQUEST_SCOPE);
			pageContext.setAttribute("Keywords", metaKeywords, PageContext.REQUEST_SCOPE);
			pageContext.setAttribute("Description", metaDescription, PageContext.REQUEST_SCOPE);
		} catch (Exception ex) {
			logger.error("Exception occurred while Filling Metadata in MetaDataTag::" , ex);
			throw new JspException("Exception occurred while Filling Metadata in MetaDataTag::" + ex.getMessage());
		}
		return (SKIP_BODY);
	}
}
