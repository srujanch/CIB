package za.co.standardbank.sbg.cda.templating.profile;

import com.vignette.ext.templating.link.RequestContextModifier;
import com.vignette.ext.templating.util.RequestContext;
import com.vignette.as.client.exception.ApplicationException;
import com.vignette.as.client.javabean.ManagedObject;
import com.vignette.as.client.javabean.ContentInstance;
import com.vignette.config.client.common.ConfigException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

import za.co.standardbank.sbg.cda.exception.CdaRuntimeException;
import za.co.standardbank.sbg.cda.templating.web.util.SBGConfigReader;
import za.co.standardbank.sbg.cda.templating.web.util.DPMConstants;

public class SBGRequestContextModifier implements RequestContextModifier {
	private static Logger logger = Logger.getLogger(SBGRequestContextModifier.class);

	public void populate(RequestContext requestContext) throws ApplicationException {
		/**
		 * Following code is responsible to set the values required for Youth
		 * Portal Project
		 */
		if (logger.isDebugEnabled())
			logger.debug("Inside the SBGRequestContextModifier populate");
		Date quarterOld = null;
		Date yearOld = null;
		try {
			HttpServletRequest request = requestContext.getRequest();
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -3);
			quarterOld = calendar.getTime();
			Calendar calendar1 = Calendar.getInstance();
			calendar1.add(Calendar.MONTH, -12);
			yearOld = calendar1.getTime();

			request.setAttribute("quarterOld", Long.toString(quarterOld.getTime()));
			request.setAttribute("yearOld", Long.toString(yearOld.getTime()));

			if (logger.isDebugEnabled())
				logger.debug(" user name in rcmodifier is :: " + requestContext.getProfile().getCurrentUserName() + " -- " + requestContext.getCurrentUserName());
		} catch (Exception e) {
			logger.error("Finally an exception at requestContextModifierExt=" + e);
		}
		// End of Youth Portal related code.

		/**
		 * Following code is responsible to set the values required for CIB
		 * Portal Project
		 */
		String siteName = "";
		HttpServletRequest request = requestContext.getRequest();
		if (requestContext.getCurrentSite() != null) {
			siteName = requestContext.getCurrentSite().getName().toUpperCase();
		}
		if (logger.isDebugEnabled())
			logger.debug("site name::" + siteName);
		Date date = new Date();
		try {
			String accoladateYear = SBGConfigReader.getSBGPropertiesConfigValueByName(siteName + DPMConstants.ACCOLADE_DATE_YEARS);
			if (accoladateYear != null) {
				if (logger.isDebugEnabled())
					logger.debug("configured accoladateYear::" + accoladateYear);
				Calendar today = Calendar.getInstance();
				GregorianCalendar calendar;
				int day = today.get(Calendar.DAY_OF_MONTH);
				int month = today.get(Calendar.MONTH);
				int year = today.get(Calendar.YEAR);
				int hour = 0;// today.get(Calendar.HOUR_OF_DAY);
				int minute = 0;// today.get(Calendar.MINUTE);
				int second = 0;// today.get(Calendar.SECOND);
				calendar = new GregorianCalendar(year, month, day, hour, minute, second);
				if (accoladateYear.indexOf(".") == -1) {
					calendar.add(Calendar.YEAR, -(new Integer(accoladateYear).intValue()));
				} else if (accoladateYear.indexOf(".") > -1) {
					String beforeDecimal = accoladateYear.substring(0, accoladateYear.indexOf("."));
					String afterDecimal = accoladateYear.substring(accoladateYear.indexOf(".") + 1, accoladateYear.length());
					if (logger.isDebugEnabled()) {
						logger.debug("beforeDecimal::" + beforeDecimal);
						logger.debug("afterDecimal::" + afterDecimal);
					}
					calendar.add(Calendar.YEAR, -(new Integer(beforeDecimal).intValue()));
					calendar.add(Calendar.MONTH, -(new Integer(afterDecimal).intValue()));
				}
				date = calendar.getTime();
			} else {
				logger.warn("config component is null or " + siteName + DPMConstants.ACCOLADE_DATE_YEARS + " not configured under SBG_PROPERTIES. ");
			}
		} catch (ConfigException cfe) {
			logger.error("Error occurred while getting Configuration");
			throw new CdaRuntimeException("Error occurred while getting Configuration");
		}
		if (logger.isDebugEnabled())
			logger.debug(" after applying Accolade Year, the date is:: " + date);
		request.setAttribute(DPMConstants.PARAM_ACCOLADE_DATE, date);

		ManagedObject mo = requestContext.getPrimaryRequestedObject();
		if (logger.isDebugEnabled())
			logger.debug(" " + (mo != null ? mo.getName() : "mo is null"));
		if (mo instanceof ContentInstance) {
			ContentInstance ci = (ContentInstance) mo;
			String ctdName = ci.getObjectType().getData().getName();
			if (logger.isDebugEnabled())
				logger.debug("CTD Name::" + ctdName);
			if (DPMConstants.COUNTRY_OFFICE_INFO_CTD.equals(ctdName)) {
				if (logger.isDebugEnabled())
					logger.debug("Setting up country code in request object");
				request.setAttribute(DPMConstants.PARAM_COUNTRY_CONST, (String) ci.getAttributeValue(DPMConstants.COUNTRY_OFFICE_INFO_COUNTRY_NAME));
			} else if (DPMConstants.PRODUCTS_CTD.equals(ctdName)) {
				if (logger.isDebugEnabled())
					logger.debug("Setting up product name in request object");
				request.setAttribute(DPMConstants.PARAM_PRODUCT_CONST, (String) ci.getAttributeValue(DPMConstants.TITLE));
			} else if (DPMConstants.SECTOR_CTD.equals(ctdName)) {
				if (logger.isDebugEnabled())
					logger.debug("Setting up sector name in request object");
				request.setAttribute(DPMConstants.PARAM_SECTOR_CONST, (String) ci.getAttributeValue(DPMConstants.TITLE));
			}
		}
	}
}
