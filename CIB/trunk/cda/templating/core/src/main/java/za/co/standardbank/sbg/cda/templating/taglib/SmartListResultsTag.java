package za.co.standardbank.sbg.cda.templating.taglib;

import com.vignette.as.client.javabean.ManagedObject;
import com.vignette.ext.templating.client.javabean.QueryContentComponent;
import com.vignette.ext.templating.util.PageUtil;
import com.vignette.ext.templating.util.RequestContext;
import com.vignette.ext.templating.util.TemplatingUtil;
import org.apache.log4j.Logger;
import za.co.standardbank.sbg.cda.templating.web.util.DPMConstants;
import za.co.standardbank.sbg.cda.templating.web.util.DisplayUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: svenepal Date: Apr 24, 2012 Time: 5:30:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmartListResultsTag extends TagSupport {
	private static Logger logger = Logger.getLogger(SmartListResultsTag.class);
	private int vgnNextStartIndex = 1;
	private int maxNoOfResults;
	private List results;
	private Integer resultsPerPage;
	private String var;

	public List getResults() {
		return results;
	}

	public void setResults(List results) {
		this.results = results;
	}

	public Integer getResultsPerPage() {
		return resultsPerPage;
	}

	public void setResultsPerPage(Integer resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public int getVgnNextStartIndex() {
		return vgnNextStartIndex;
	}

	public void setVgnNextStartIndex(int vgnNextStartIndex) {
		this.vgnNextStartIndex = vgnNextStartIndex;
	}

	public int getMaxNoOfResults() {
		return maxNoOfResults;
	}

	public void setMaxNoOfResults(int maxNoOfResults) {
		this.maxNoOfResults = maxNoOfResults;
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
			logger.debug("--Start SmartListResultsTag");
		try {
			RequestContext rc = PageUtil.getCurrentRequestContext(pageContext);
			ManagedObject mo = rc.getRenderedManagedObject();
			HttpServletRequest request = rc.getRequest();
			if (mo instanceof QueryContentComponent) {
				QueryContentComponent qcc = (QueryContentComponent) mo;
				if (!TemplatingUtil.isNullOrEmpty(request.getParameter(DPMConstants.PARAM_VGNNEXT_START_INDEX))) {
					try {
						vgnNextStartIndex = Integer.parseInt(request.getParameter(DPMConstants.PARAM_VGNNEXT_START_INDEX));
					} catch (NumberFormatException nfe) {
						vgnNextStartIndex = 1;
					}
				} else {
					vgnNextStartIndex = 1; // if request parameter is null.
				}
				maxNoOfResults = (qcc.getAttribute(QueryContentComponent.ATTR_MAX_SIZE) != null) ? ((Integer) qcc.getAttribute(QueryContentComponent.ATTR_MAX_SIZE).getValue()).intValue() : 0;

				results = qcc.getResults(rc);
				int size = (results != null) ? results.size() : 0;
				if (logger.isDebugEnabled())
					logger.debug("Inside SmartListResultsTag::results size::" + size);
				if (logger.isDebugEnabled())
					logger.debug("Inside SmartListResultsTag::vgnNextStartIndex::" + getVgnNextStartIndex() + " resultsPerPage::" + resultsPerPage + " maxNumberofResults::" + maxNoOfResults);
				if (size > 0) {
					DisplayUtil.getSmartListResultsSubList(results, getVgnNextStartIndex(), getResultsPerPage().intValue(), getMaxNoOfResults(), pageContext, getVar());
				}

			}
		} catch (Exception ex) {
			logger.error("Exception occurred in SmartListResultsTag::" + ex.getMessage());
			ex.printStackTrace();
		}
		if (logger.isDebugEnabled())
			logger.debug("--End SmartListResultsTag");
		return (SKIP_BODY);
	}
}
