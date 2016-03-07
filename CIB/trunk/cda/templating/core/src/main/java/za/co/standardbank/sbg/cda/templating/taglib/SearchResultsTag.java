package za.co.standardbank.sbg.cda.templating.taglib;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.apache.log4j.Logger;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.xpath.XPathConstants;

import com.vignette.ext.templating.util.RequestContext;
import com.vignette.ext.templating.util.PageUtil;
import com.vignette.ext.templating.util.TemplatingUtil;
import com.vignette.ext.templating.client.javabean.SearchResultsComponent;
import com.vignette.ext.templating.searchcomponent.SearchComponentConfig;
import com.vignette.as.client.javabean.ManagedObject;
import za.co.standardbank.sbg.cda.templating.web.util.DPMConstants;
import za.co.standardbank.sbg.cda.templating.web.util.DisplayUtil;

/**
 * Created by IntelliJ IDEA. User: svenepal Date: Apr 24, 2012 Time: 2:42:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchResultsTag extends TagSupport {

	private static Logger logger = Logger.getLogger(SearchResultsTag.class);
	private int vgnNextStartIndex = 1;
	private int resultsPerPage;
	private int maxNoOfResults;
	private String siteName;
	private String componentXML;
	private String[] searchableCTDs;
	private String businessSegmentValue;
	private Document document;

	public int getVgnNextStartIndex() {
		return vgnNextStartIndex;
	}

	public void setVgnNextStartIndex(int vgnNextStartIndex) {
		this.vgnNextStartIndex = vgnNextStartIndex;
	}

	public int getResultsPerPage() {
		return resultsPerPage;
	}

	public void setResultsPerPage(int resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}

	public int getMaxNoOfResults() {
		return maxNoOfResults;
	}

	public void setMaxNoOfResults(int maxNoOfResults) {
		this.maxNoOfResults = maxNoOfResults;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getComponentXML() {
		return componentXML;
	}

	public void setComponentXML(String componentXML) {
		this.componentXML = componentXML;
	}

	public String[] getSearchableCTDs() {
		return searchableCTDs;
	}

	public void setSearchableCTDs(String[] searchableCTDs) {
		this.searchableCTDs = searchableCTDs;
	}

	public String getBusinessSegmentValue() {
		return businessSegmentValue;
	}

	public void setBusinessSegmentValue(String businessSegmentValue) {
		this.businessSegmentValue = businessSegmentValue;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
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
			logger.debug("--Start SearchResultsTag");
		try {
			RequestContext rc = PageUtil.getCurrentRequestContext(pageContext);
			HttpServletRequest request = rc.getRequest();
			ManagedObject mo = rc.getRenderedManagedObject();
			if (mo instanceof SearchResultsComponent) {
				if (!TemplatingUtil.isNullOrEmpty(request.getParameter(DPMConstants.PARAM_VGNNEXT_START_INDEX))) {
					try {
						vgnNextStartIndex = Integer.parseInt(request.getParameter(DPMConstants.PARAM_VGNNEXT_START_INDEX));
					} catch (NumberFormatException nfe) {
						vgnNextStartIndex = 1;
					}
				} else if (TemplatingUtil.isNullOrEmpty(request.getParameter(DPMConstants.PARAM_VGNNEXT_START_INDEX))) {
					vgnNextStartIndex = 1;
				}
				if (logger.isDebugEnabled())
					logger.debug("vgnNextStartIndex value::" + vgnNextStartIndex);
				resultsPerPage = (mo.getAttribute(SearchComponentConfig.ATTRIBUTE_RESULTS_PER_PAGE) != null) ? ((Integer) mo.getAttribute(SearchComponentConfig.ATTRIBUTE_RESULTS_PER_PAGE).getValue()).intValue() : 0;
				maxNoOfResults = (mo.getAttribute(SearchComponentConfig.ATTRIBUTE_MAX_RESULTS) != null) ? ((Integer) mo.getAttribute(SearchComponentConfig.ATTRIBUTE_MAX_RESULTS).getValue()).intValue() : 0;
				if (logger.isDebugEnabled())
					logger.debug("resultsPerPage value::" + resultsPerPage + " maxNoOfResults value::" + maxNoOfResults);

				String searchBoundary = (String) mo.getAttribute(SearchComponentConfig.ATTRIBUTE_BOUNDARY).getValue();
				if (!TemplatingUtil.isNullOrEmpty(searchBoundary) && searchBoundary.equalsIgnoreCase(SearchComponentConfig.BOUNDARY_VALUE_CURRENT_SITE)) {
					siteName = (rc.getCurrentSite() != null) ? rc.getCurrentSite().getName() : null;
				}
				if (logger.isDebugEnabled())
					logger.debug("sitename::" + siteName);
				componentXML = (String) mo.getAttribute(SearchComponentConfig.ATTRIBUTE_CONTENT_QUERY).getValue();
				Document document = DisplayUtil.getDocumentObject(componentXML);
				String searchCTs = null;
				try {
					searchCTs = (String) DisplayUtil.getXPathValue(document, DPMConstants.CT_EXPR, XPathConstants.STRING);
				} catch (Exception ex) {
					logger.error("Exception occurred while getting CTDs configuration from SearchResults::" + ex.getMessage());
				}
				searchableCTDs = (searchCTs != null) ? searchCTs.split(",") : new String[] { DPMConstants.DEALS_CTD_TYPE };
				if (logger.isDebugEnabled())
					logger.debug("Configured Searchable CTDs::" + searchableCTDs);
				try {
					NodeList nodeList = (NodeList) DisplayUtil.getXPathValue(document, DPMConstants.EQUALS_EXPR.replaceAll(DPMConstants.REPLACE_EXPR, 1 + ""), XPathConstants.NODESET);
					businessSegmentValue = DisplayUtil.getNodeValueByID(DPMConstants.BUSINESS_SEGMENT_ID, nodeList);
				} catch (Exception ex) {
					logger.error("");
					ex.printStackTrace();
				}
				if (logger.isDebugEnabled())
					logger.debug("businessSegmentValue::" + businessSegmentValue);
			} else {
				if (logger.isDebugEnabled())
					logger.debug("--Inside SearchResultsTag..component is not SearchResultsComponent::");
			}
		} catch (Exception ex) {
			if (logger.isDebugEnabled())
				logger.debug("--Inside SearchResultsTag::" + ex.getMessage());
			ex.printStackTrace();
		}
		if (logger.isDebugEnabled())
			logger.debug("--End SearchResultsTag");
		return (SKIP_BODY);
	}
}
