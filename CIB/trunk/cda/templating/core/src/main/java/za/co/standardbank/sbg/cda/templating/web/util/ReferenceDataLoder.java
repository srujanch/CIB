package za.co.standardbank.sbg.cda.templating.web.util;

import org.apache.log4j.Logger;

import java.util.*;

import com.vignette.as.client.common.ContentInstanceDBQuery;
import com.vignette.as.client.common.ContentInstanceWhereClause;
import com.vignette.as.client.common.RequestParameters;
import com.vignette.as.client.common.ref.ContentTypeRef;
import com.vignette.as.client.javabean.*;
import com.vignette.util.StringQueryOp;

/**
 * Created by IntelliJ IDEA. User: svenepal Date: Apr 13, 2012 Time: 3:02:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReferenceDataLoder {

	static HashMap<String, String> sbWebSitesList = new HashMap<String, String>();
	static HashMap<String, String> countriesList = new HashMap<String, String>();
	ReferenceDataLoder referenceData = new ReferenceDataLoder();
	static Logger logger = Logger.getLogger(ReferenceDataLoder.class);

	static {
		init();
	}

	private ReferenceDataLoder() {

	}

	private static void init() {
		// load Standard Bank Websites List (Country Offices)
		sbWebSitesList = loadReferenceDataListByTitle(DPMConstants.SB_WEBSITES_CONST);
		// load All Countries
		countriesList = loadReferenceDataListByTitle(DPMConstants.COUNTRIES_CONST);
	}

	/**
	 * Returns required reference data based on title
	 * 
	 * @param title
	 * @return HashMap
	 */
	public static HashMap<String, String> loadReferenceDataListByTitle(String title) {
		HashMap<String, String> mappedData = new HashMap<String, String>();
		try {
			logger.debug("-- Start loadReferenceDataListByTitle");

			ManagedObject mo = DisplayUtil.searchReferenceDataByTitle(title);
			if (mo != null) {
				ContentInstance ci = (ContentInstance) mo;
				AttributedObject[] attObjArray = ci.getRelations(DPMConstants.REFERENCE_RELATION_NAME);
				int size = (attObjArray != null) ? attObjArray.length : 0;
				logger.debug("AttributedObject Size::" + size);
				if (size > 0) {
					for (AttributedObject attObj : attObjArray) {
						// VALUE as KEY, LABEL as value
						mappedData.put(attObj.getAttributeValue(DPMConstants.REFERENCE_DATA_VALUE).toString(), attObj.getAttributeValue(DPMConstants.REFERENCE_DATA_LABEL).toString());
					}
				}
			}
			logger.debug("sbWebSitesList size::" + mappedData.size());
		} catch (Exception ex) {
			logger.error("exception occurred while loading loadReferenceDataListByTitlet::" + ex.getMessage());
			ex.printStackTrace();
		}
		logger.debug("-- End loadReferenceDataListByTitle");
		return mappedData;
	}

	public static String getSBWebsiteLabelByKey(String key) {
		String value = "";
		try {
			logger.debug(" -- Start getSBWebsiteLabelByKey");
			logger.debug(" -- key ::" + key);
			if (sbWebSitesList.containsKey(key)) {
				value = sbWebSitesList.get(key);
			}
			logger.debug(" -- Value ::" + value);
		} catch (Exception ex) {
			logger.error("exception occurred in getSBWebsiteLabelByKey::" + ex.getMessage());
			ex.printStackTrace();
		}
		logger.debug(" -- End getSBWebsiteLabelByKey");
		return value;
	}

	public static String getCountryLabelByKey(String key) {
		String value = "";
		try {
			logger.debug(" -- Start getCountryLabelByKey");
			logger.debug(" -- key ::" + key);
			if (countriesList.containsKey(key)) {
				value = countriesList.get(key);
			}
			logger.debug(" -- Value ::" + value);
		} catch (Exception ex) {
			logger.error("exception occurred in getCountryLabelByKey::" + ex.getMessage());
			ex.printStackTrace();
		}
		logger.debug(" -- End getCountryLabelByKey");
		return value;
	}

	public static HashMap getAllCountries() {
		return countriesList;
	}

	public static HashMap getSBCountries() {
		return sbWebSitesList;
	}
}
