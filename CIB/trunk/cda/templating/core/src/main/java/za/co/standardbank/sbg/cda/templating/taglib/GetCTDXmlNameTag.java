package za.co.standardbank.sbg.cda.templating.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.tag.common.core.OutSupport;

import com.vignette.as.client.common.ref.ManagedObjectVCMRef;
import com.vignette.as.client.javabean.ContentInstance;
import com.vignette.as.client.javabean.ManagedObject;
import com.vignette.ext.templating.util.ContentUtil;

/**
 * Retrieves the CTD XML Name of given Content Item and places the value in
 * given scope.
 * 
 * @author Venkat
 */
public class GetCTDXmlNameTag extends OutSupport {

	private static final long serialVersionUID = 1L;

	private String varName;
	private String oid;
	private int varScope = PageContext.PAGE_SCOPE;

	private static final Logger logger = Logger.getLogger(GetCTDXmlNameTag.class);

	public final int doStartTag() throws JspException {

		if (logger.isDebugEnabled()) {
			logger.debug("Start GetCTDXmlNameTag");
		}

		try {
			ManagedObject mo = ContentUtil.getManagedObject(new ManagedObjectVCMRef(oid));
			if (varName != null && mo != null) {
				ContentInstance ci = (ContentInstance) mo;
				String ctdXmlName = ci.getObjectType().getData().getName();
				pageContext.setAttribute(varName, ctdXmlName, varScope);
			}

		} catch (Throwable e) {
			logger.error("Error occured while retrieving the CTD XML Name.::", e);
			throw new JspException("Error occured while retrieving the CTD XML Name.");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("End GetCTDXmlNameTag");
		}
		return SKIP_BODY;
	}

	/**
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 * @return int
	 */
	public final int doEndTag() {
		return EVAL_PAGE;
	}

	public void setVar(String name) throws JspException {
		if (name == null || "".equals(name)) {
			throw new JspException("The value specified for the var attribute is invalid");
		} else {
			varName = name;
		}
	}

	public void setOid(String oid) throws JspException {
		if (oid == null || "".equals(oid)) {
			throw new JspException("The value specified for the oid attribute is invalid");
		} else {
			this.oid = oid;
		}

	}

	public void setScope(String scope) throws JspException {
		if (scope.equalsIgnoreCase("page"))
			varScope = PageContext.PAGE_SCOPE;
		else if (scope.equalsIgnoreCase("request"))
			varScope = PageContext.REQUEST_SCOPE;
		else if (scope.equalsIgnoreCase("session"))
			varScope = PageContext.SESSION_SCOPE;
		else if (scope.equalsIgnoreCase("application"))
			varScope = PageContext.APPLICATION_SCOPE;
		else
			throw new JspException((new StringBuilder()).append("Invalid scope: ").append(scope).toString());
	}
}
