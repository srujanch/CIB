package za.co.standardbank.sbg.cda.templating.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;

import za.co.standardbank.sbg.cda.domain.ResultSet;
import za.co.standardbank.sbg.cda.domain.SearchFilter;
import za.co.standardbank.sbg.cda.domain.impl.SearchFilterImpl;
import za.co.standardbank.sbg.cda.exception.CdaRuntimeException;
import za.co.standardbank.sbg.cda.service.SearchService;
import za.co.standardbank.sbg.cda.service.impl.SpringServiceLocator;
import za.co.standardbank.sbg.cda.templating.web.util.DPMConstants;

import com.vignette.as.client.exception.ApplicationException;
import com.vignette.as.client.javabean.Channel;
import com.vignette.as.client.javabean.Site;
import com.vignette.ext.templating.TemplatingConstants;
import com.vignette.ext.templating.util.RequestContext;

public class VideoGalleryComponentController extends AbstractContentComponentController implements ContentComponentController {

	private static final Logger logger = Logger.getLogger(VideoGalleryComponentController.class);

	private int resultsPerPage = DPMConstants.DEFAULT_RESULTS_PER_PAGE;
	private int paginationBlockSize = DPMConstants.DEFAULT_PAGINATION_BLOCK_SIZE;
	private int maxResults = DPMConstants.DEFAULT_MAX_RESULTS_SIZE;

	public VideoGalleryComponentController(PageContext pageContext) {
		super(pageContext);

		try {
			String strResultsPerPage = getXPathValue(DPMConstants.ATTR_NAME_DATA, DPMConstants.RESULTS_PER_PAGE_XML_ATT);
			logger.info("No of Video Results per Page ::" + strResultsPerPage);
			if (strResultsPerPage != null && !strResultsPerPage.trim().isEmpty()) {
				try {
					resultsPerPage = Integer.parseInt(strResultsPerPage.trim());
				} catch (NumberFormatException e) {
					logger.error("NumberFormatException:: Invalid value for Number of results per page. resultsPerPage should a number.", e);
					throw new CdaRuntimeException("NumberFormatException:: Invalid value for Number of results per page. resultsPerPage should a number.", e);
				}
			}
		} catch (XPathExpressionException e) {
			logger.error("Failed to retrieve required XML information from the current component. Please ensure that 'Data' attribute on the component has a well-formed XML including values for /data/resultsPerPage.::", e);
			throw new CdaRuntimeException("Failed to retrieve required XML information from the current component. Please ensure that 'Data' attribute on the component has a well-formed XML including values for /data/resultsPerPage.::", e);
		}

		try {
			String strPaginationBlockSize = getXPathValue(DPMConstants.ATTR_NAME_DATA, DPMConstants.PAGINATION_BLOCK_SIZE_XML_ATT);
			logger.info("Pagination Block Size for Videos::" + strPaginationBlockSize);
			if (strPaginationBlockSize != null && !strPaginationBlockSize.trim().isEmpty()) {
				try {
					paginationBlockSize = Integer.parseInt(strPaginationBlockSize.trim());
				} catch (NumberFormatException e) {
					logger.error("NumberFormatException:: Invalid value for Pagination Block Size. paginationBlockSize should a number.", e);
					throw new CdaRuntimeException("NumberFormatException:: Invalid value for Pagination Block Size. paginationBlockSize should be a number.", e);
				}
			}
		} catch (XPathExpressionException e) {
			logger.error("Failed to retrieve required XML information from the current component. Please ensure that 'Data' attribute on the component has a well-formed XML including values for /data/paginationBlockSize.::", e);
			throw new CdaRuntimeException("Failed to retrieve required XML information from the current component. Please ensure that 'Data' attribute on the component has a well-formed XML including values for /data/paginationBlockSize.::", e);
		}

		try {
			String strMaxResults = getXPathValue(DPMConstants.ATTR_NAME_DATA, DPMConstants.MAX_RESULTS_XML_ATT);
			logger.info("Maximum Results Size For Videos Listing::" + strMaxResults);
			if (strMaxResults != null && !strMaxResults.trim().isEmpty()) {
				try {
					maxResults = Integer.parseInt(strMaxResults.trim());
				} catch (NumberFormatException e) {
					logger.error("NumberFormatException:: Invalid value for Maximum Results Size. maxResults should a number.", e);
					throw new CdaRuntimeException("NumberFormatException:: Invalid value for Maximum Results Size. maxResults should a number.", e);
				}
			}
		} catch (XPathExpressionException e) {
			logger.error("Failed to retrieve required XML information from the current component. Please ensure that 'Data' attribute on the component has a well-formed XML including values for /data/maxResults.::", e);
			throw new CdaRuntimeException("Failed to retrieve required XML information from the current component. Please ensure that 'Data' attribute on the component has a well-formed XML including values for /data/maxResults.::", e);
		}
	}

	@Override
	protected void initialiseComponent() {
		try {
			if (logger.isDebugEnabled())
				logger.debug("----------------- VideoGalleryComponentController Begin -----------");
			RequestContext rc = getRequestContext();
			HttpServletRequest request = rc.getRequest();
			PageContext pageContext = getPageContext();
			if (maxResults <= resultsPerPage && maxResults != 0) {
				resultsPerPage = maxResults;
			}

			Site currentSite = rc.getCurrentSite();
			Channel currentChannel = rc.getRequestedChannel();
			SearchFilter filter = new SearchFilterImpl();
			filter.addContentType(DPMConstants.VIDEO_CTD_XML_NAME);
			filter.setSiteId(currentSite.getId().getId());
			filter.setChannelId(currentChannel.getId().getId());

			int startIndex = 1;
			String startIndexParam = request.getParameter(TemplatingConstants.QUERY_RESULTS_PAGINATION_NEXT_INDEX_KEY);
			if (startIndexParam != null) {
				try {
					startIndex = Integer.parseInt(startIndexParam);
				} catch (NumberFormatException e) {
					startIndex = 1;
					logger.warn("NumberFormatException:: Invalid value for vgnNextStartIndex parameter. vgnNextStartIndex should a number.", e);
				}
			}
			if (maxResults != 0 && startIndex >= maxResults) {
				startIndex = 1;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("startIndex::" + startIndex);
				logger.debug("resultsPerPage::" + resultsPerPage);
				logger.debug("paginationBlockSize::" + paginationBlockSize);
			}

			filter.setStartItem(startIndex - 1);
			filter.setPageSize(resultsPerPage - 1);
			SearchService searchService = (SearchService) SpringServiceLocator.getInstance().getService("searchService");
			ResultSet resultSet = searchService.getContentByFilter(filter);

			int totalResultsSize = resultSet.getTotalResultsSize();
			getPageContext().setAttribute("results", resultSet.getResults(), PageContext.REQUEST_SCOPE);
			getPageContext().setAttribute("totalResultsSize", totalResultsSize, PageContext.REQUEST_SCOPE);
			if (maxResults == 0 || maxResults > totalResultsSize) {
				maxResults = totalResultsSize;
			}
			if (logger.isDebugEnabled()) {
				logger.debug("totalResultsSize::" + totalResultsSize);
				logger.debug("maxResults::" + maxResults);
			}

			// Pagination

			// Get Total Number of Pages
			int totalPages = (maxResults / resultsPerPage);
			if (maxResults % resultsPerPage > 0) {
				totalPages++;
			}
			int currentPage = 1;
			String pageIndex = (String) rc.getRequest().getParameter("currentPage");
			if (pageIndex != null) {
				try {
					currentPage = Integer.parseInt(pageIndex);
					if (currentPage > totalPages) {
						currentPage = 1;
					}
				} catch (NumberFormatException e) {
					currentPage = 1;
					logger.warn("NumberFormatException:: Invalid value for currentPage. currentPage parameter value should a number.", e);
				}
				if (currentPage < 1)
					currentPage = 1;
			}

			int pageStartNumber = (currentPage / paginationBlockSize) * paginationBlockSize;
			if (pageStartNumber == 0) {
				pageStartNumber = 1;
			}
			int pageEndNumber = pageStartNumber + paginationBlockSize;
			if (currentPage / paginationBlockSize == 0)
				pageEndNumber = paginationBlockSize;
			if (pageEndNumber > totalPages)
				pageEndNumber = totalPages;

			boolean enablePreviousLink = true;
			boolean enableNextLink = true;
			if (currentPage == 1)
				enablePreviousLink = false;
			if (currentPage == totalPages)
				enableNextLink = false;

			String oidLink = "?";
			if (enablePreviousLink) {
				String previousLink = oidLink + TemplatingConstants.QUERY_RESULTS_PAGINATION_NEXT_INDEX_KEY + "=" + ((currentPage - 2) * resultsPerPage + 1) + "&currentPage=" + (currentPage - 1);
				pageContext.setAttribute("previousLink", previousLink, PageContext.REQUEST_SCOPE);
			}
			if (enableNextLink) {
				String nextLink = oidLink + TemplatingConstants.QUERY_RESULTS_PAGINATION_NEXT_INDEX_KEY + "=" + (currentPage * resultsPerPage + 1) + "&currentPage=" + (currentPage + 1);
				pageContext.setAttribute("nextLink", nextLink, PageContext.REQUEST_SCOPE);
			}

			List<String> resultLinks = new ArrayList<String>();
			for (int i = pageStartNumber; i <= pageEndNumber; i++) {
				String link = "" + i;
				if (i != currentPage)
					link = oidLink + TemplatingConstants.QUERY_RESULTS_PAGINATION_NEXT_INDEX_KEY + "=" + ((i - 1) * resultsPerPage + 1) + "&currentPage=" + i;
				resultLinks.add(link);
			}

			pageContext.setAttribute("resultLinks", resultLinks, PageContext.REQUEST_SCOPE);
			pageContext.setAttribute("pageStartNumber", pageStartNumber, PageContext.REQUEST_SCOPE);
			pageContext.setAttribute("currentPage", currentPage, PageContext.REQUEST_SCOPE);
			
			if (logger.isDebugEnabled())
				logger.debug("----------------- VideoGalleryComponentController End -----------");
		} catch (ApplicationException e) {
			logger.error("ApplicationException in  VideoGalleryComponentController in fetching search results in a site ", e);
			throw new CdaRuntimeException("ApplicationException in  VideoGalleryComponentController in fetching search results in a site ", e);
		} catch (Exception e) {
			logger.error("Unexpected Exception in  VideoGalleryComponentController in fetching search results in a site ", e);
			throw new CdaRuntimeException("Unexpected Exception in  VideoGalleryComponentController in fetching search results in a site ", e);
		}

	}

}
