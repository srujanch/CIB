package za.co.standardbank.sbg.cda.templating.taglib;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;

import za.co.standardbank.sbg.cda.templating.data.PressRelease;
import za.co.standardbank.sbg.cda.templating.web.util.DPMConstants;
import za.co.standardbank.sbg.cda.templating.web.util.DisplayUtil;
import za.co.standardbank.sbg.cda.templating.web.util.SBGConfigReader;

import com.vignette.as.client.javabean.IPagingList;
import com.vignette.config.client.common.ConfigException;
import com.vignette.ext.templating.util.PageUtil;
import com.vignette.ext.templating.util.RequestContext;
import com.vignette.ext.templating.util.TemplatingUtil;

public class PressReleaseSearchTag extends SearchResultsTag {

	Logger logger = Logger.getLogger(DealsSearchTag.class);

	private String var;

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
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
	@Override
	public int doStartTag() throws JspException {
		if (logger.isDebugEnabled()) {
			logger.debug("Entered method doStartTag()");
		}
		List searchResults = new ArrayList();
		try {
			super.doStartTag();
			RequestContext rc = PageUtil.getCurrentRequestContext(pageContext);
			HttpServletRequest request = rc.getRequest();
			PressRelease mediaBean = new PressRelease();
			ArrayList list = new ArrayList();
			list.add(getBusinessSegmentValue() != null ? getBusinessSegmentValue() : "");
			mediaBean.setSegment(list);
			try {
				buildMediaBean(request, mediaBean, rc.getCurrentSite().getData().getName());
				IPagingList results = DisplayUtil.searchPressRelease(getSiteName(), getSearchableCTDs(), mediaBean);
				int size = (results != null) ? results.size() : 0;
				if (logger.isDebugEnabled())
					logger.debug("search results size::" + size);
				if (size > 0) {
					searchResults = DisplayUtil.getSearchResultsSubList(results, getVgnNextStartIndex(), getResultsPerPage(), getMaxNoOfResults(), pageContext);
				}
				pageContext.setAttribute(getVar(), searchResults);
			} catch (Exception ex) {
				logger.error("Exception occurred while searching for Deals::" + ex.getMessage());
				ex.printStackTrace();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return (SKIP_BODY);
	}

	private void buildMediaBean(HttpServletRequest request, PressRelease mediaBean, String siteName) {
		if (!TemplatingUtil.isNullOrEmpty(request.getParameter(DPMConstants.PARAM_YEAR))) { // year
																							// exists
			int year = 0;
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			int startYear;
			try {
				if (logger.isDebugEnabled()) {
					logger.debug(" SiteName::" + siteName);
					logger.debug(" start year::" + SBGConfigReader.getSBGPropertiesConfigValueByName(siteName.toUpperCase() + DPMConstants.MEDIA_START_YEAR));
				}

				startYear = Integer.parseInt(SBGConfigReader.getSBGPropertiesConfigValueByName(siteName.toUpperCase() + DPMConstants.MEDIA_START_YEAR));
			} catch (NumberFormatException nfe) {
				startYear = 2005; // default
			} catch (ConfigException cfe) {
				startYear = 2005; // default
			}
			try {
				year = Integer.parseInt(request.getParameter(DPMConstants.PARAM_YEAR));
				if (year > currentYear || year < startYear) { // default to
																// current year
					year = currentYear;
				}
			} catch (Exception ex) {
				year = 0;
			}
			if (logger.isDebugEnabled())
				logger.debug("param year ::" + year);
			if (year > 0) {
				GregorianCalendar calendar = new GregorianCalendar(year, 0, 1, 0, 0, 0); // 0
																							// means
																							// January
				if (logger.isDebugEnabled())
					logger.debug("start date::" + calendar.getTime());
				mediaBean.setStartDate(calendar.getTime());
				calendar = new GregorianCalendar(year, 11, 31, 0, 0, 0); // 11
																			// means
																			// December
				if (logger.isDebugEnabled())
					logger.debug("end date::" + calendar.getTime());
				mediaBean.setEndDate(calendar.getTime());
				if (logger.isDebugEnabled()) {
					logger.debug("start date ::" + mediaBean.getStartDate());
					logger.debug("end date ::" + mediaBean.getEndDate());
				}
			}
		}
		if (!TemplatingUtil.isNullOrEmpty(request.getParameter(DPMConstants.PARAM_COUNTRY))) {
			mediaBean.setCountry(request.getParameter(DPMConstants.PARAM_COUNTRY));
			if (logger.isDebugEnabled())
				logger.debug("Country ::" + mediaBean.getCountry());
		}
		if (!TemplatingUtil.isNullOrEmpty(request.getParameter(DPMConstants.PARAM_KEYWORD)) && !"keyword".equalsIgnoreCase(request.getParameter(DPMConstants.PARAM_KEYWORD))) {
			mediaBean.setKeyword(request.getParameter(DPMConstants.PARAM_KEYWORD));
			if (logger.isDebugEnabled())
				logger.debug("Keyword::" + mediaBean.getKeyword());
		}
	}
}
