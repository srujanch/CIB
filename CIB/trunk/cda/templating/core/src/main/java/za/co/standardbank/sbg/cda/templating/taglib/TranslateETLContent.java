package za.co.standardbank.sbg.cda.templating.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.tag.common.core.OutSupport;

import za.co.standardbank.sbg.cda.exception.CdaRuntimeException;

import com.vignette.as.client.exception.ApplicationException;
import com.vignette.as.client.exception.AuthorizationException;
import com.vignette.as.client.exception.ValidationException;
import com.vignette.ext.link.delivery.ETLDeliveryTranslator;
import com.vignette.ext.templating.util.PageUtil;
import com.vignette.ext.templating.util.RequestContext;

/**
 * Transforms ETL Links in the ETL content(content from Edit Live for Java CCE
 * widget) and places the transformed content in request scope.
 * 
 * @author Venkat
 */
public class TranslateETLContent extends OutSupport {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	private String etlContent;

	private String varName;

	/**
	 * Log4j logger.
	 */
	private static final Logger logger = Logger.getLogger(TranslateETLContent.class);

	public final int doStartTag() throws JspException {

		if (logger.isDebugEnabled()) {
			logger.debug("Start TranslateETLContent Tag");
		}

		RequestContext rc = PageUtil.getCurrentRequestContext(pageContext);
		String trasformedContent = "";

		if (this.etlContent != null && (this.etlContent.trim().length() > 0)) {
			try {
				ETLDeliveryTranslator etlTranslator = new ETLDeliveryTranslator();
				trasformedContent = etlTranslator.translate(etlContent, rc);
			} catch (ValidationException e) {
				logger.error("ValidationException in TranslateETLContent Tag - Exception in transforming the ETL Content::", e);
				throw new CdaRuntimeException("ValidationException in TranslateETLContent Tag - Exception in transforming the ETL Content::", e);
			} catch (AuthorizationException e) {
				logger.error("AuthorizationException in TranslateETLContent Tag - Exception in transforming the ETL Content::", e);
				throw new CdaRuntimeException("AuthorizationException in TranslateETLContent Tag - Exception in transforming the ETL Content::", e);
			} catch (ApplicationException e) {
				logger.error("ApplicationException in TranslateETLContent Tag - Exception in transforming the ETL Content::", e);
				throw new CdaRuntimeException("ApplicationException in TranslateETLContent Tag - Exception in transforming the ETL Content::", e);
			}
		}
		// Placing Transformed Content back in the scoped variable if var is
		// provided else print the value.
		if (varName != null && !varName.trim().isEmpty()) {
			pageContext.setAttribute(varName, trasformedContent, PageContext.REQUEST_SCOPE);
		} else {
			try {
				out(pageContext, false, trasformedContent);
			} catch (IOException e) {
				logger.error("IOException in TranslateETLContent Tag - Exception in printing the ETL Content::" + e);
				throw new CdaRuntimeException("IOException in TranslateETLContent Tag - Exception in transforming the ETL Content::", e);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("End TranslateETLContent Tag");
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

	public String getEtlContent() {
		return etlContent;
	}

	public void setEtlContent(String etlContent) throws JspException {
		this.etlContent = etlContent;
	}

	public String getVar() {
		return varName;
	}

	public void setVar(String name) throws JspException {
		this.varName = name;
	}
}
