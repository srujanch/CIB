package za.co.standardbank.sbg.cda.templating.web.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import za.co.standardbank.sbg.cda.templating.data.Deal;
import za.co.standardbank.sbg.cda.templating.data.PressRelease;

import com.vignette.as.client.api.bean.BeanFactoryFactory;
import com.vignette.as.client.api.bean.ManagedObjectBean;
import com.vignette.as.client.common.ContentInstanceDBQuery;
import com.vignette.as.client.common.ContentInstanceWhereClause;
import com.vignette.as.client.common.DateFilter;
import com.vignette.as.client.common.ObjectIdFilter;
import com.vignette.as.client.common.SearchFilterSet;
import com.vignette.as.client.common.SearchMgmtAttr;
import com.vignette.as.client.common.SearchResult;
import com.vignette.as.client.common.SortFilter;
import com.vignette.as.client.common.StringFilter;
import com.vignette.as.client.common.ref.ContentTypeRef;
import com.vignette.as.client.exception.ApplicationException;
import com.vignette.as.client.exception.ValidationException;
import com.vignette.as.client.javabean.ContentType;
import com.vignette.as.client.javabean.IPagingList;
import com.vignette.as.client.javabean.ManagedObject;
import com.vignette.as.client.javabean.QueryManager;
import com.vignette.as.client.javabean.SearchQuery;
import com.vignette.as.client.javabean.Site;
import com.vignette.ext.templating.util.ContentUtil;
import com.vignette.ext.templating.util.TemplatingUtil;
import com.vignette.util.GUID;
import com.vignette.util.StringQueryOp;


public class DisplayUtil {
	private static Logger logger = Logger.getLogger(DisplayUtil.class);

	/**
	 * 
	 * @param configVariableName
	 * @return List
	 */
	public static List<String> getYearsForSearch(String configVariableName) {
		logger.debug(" --Start getYearsForSearch");
		List<String> list = new ArrayList<String>();
		try {
			int startYear = Integer.parseInt(SBGConfigReader.getSBGPropertiesConfigValueByName(configVariableName));
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			for (int i = currentYear; i >= startYear; i--) {
				list.add(new Integer(i).toString());
			}
		} catch (Exception ex) {
			logger.error("exception occurred in getYearsForSearch");
		}
		logger.debug(" --End getYearsForSearch");
		return list;
	}

	/**
	 * This method returns all countries which are maintained in reference data
	 * 
	 * @return HashMap
	 */
	public static HashMap<String, String> getAllCountries() {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		logger.debug(" --Start getDealsCountries");
		try {
			return ReferenceDataLoder.getAllCountries();
		} catch (Exception ex) {
			logger.error("exception occurred in getYearsForSearch");
		}
		logger.debug(" --End getDealsCountries");
		return hashMap;
	}

	/**
	 * This method returns all Stanadard Bank countries which are 33
	 * 
	 * @return HashMap
	 */
	public static HashMap<String, String> getSBCountries() {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		logger.debug(" --Start getSBCountries");
		try {
			return ReferenceDataLoder.getSBCountries();
		} catch (Exception ex) {
			logger.error("exception occurred in getSBCountries");
		}
		logger.debug(" --End getSBCountries");
		return hashMap;
	}

	/**
	 * This method returns all Secors which are maintained in reference data
	 * 
	 * @return HashMap
	 */
	public static HashMap<String, String> getAllSectors() {
		HashMap hashMap = new HashMap<String, String>();
		logger.debug(" --Start getSectors");
		try {
			hashMap = ReferenceDataLoder.loadReferenceDataListByTitle(DPMConstants.SECTORS_CONST);
		} catch (Exception ex) {
			logger.error("exception occurred in getSectors");
		}
		logger.debug(" --End getSectors");
		return hashMap;
	}

	/**
	 * This method returns all Products which are maintained in reference data
	 * 
	 * @return HashMap
	 */
	public static HashMap<String, String> getAllProducts() {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		logger.debug(" --Start getProducts");
		try {
			hashMap = ReferenceDataLoder.loadReferenceDataListByTitle(DPMConstants.PRODUCTS_CONST);
		} catch (Exception ex) {
			logger.error("exception occurred in getProducts");
		}
		logger.debug(" --End getProducts");
		return hashMap;
	}

	/**
	 * Return Map.Entry Comparator
	 * 
	 * @return Comparator
	 */
	public static Comparator getHashMapEntrySetComparator() {
		return new Comparator() {
			public int compare(Object o1, Object o2) {
				Map.Entry e1 = (Map.Entry) o1;
				Map.Entry e2 = (Map.Entry) o2;
				String first = (String) e1.getValue();
				String second = (String) e2.getValue();
				return first.compareTo(second);
			}
		};
	}

	/**
	 * Returns Managed Object for a given ReferenceData title
	 * 
	 * @param title
	 * @return ManagedObject
	 * @throws ApplicationException
	 * @throws ValidationException
	 */
	public static ManagedObject searchReferenceDataByTitle(String title) throws ApplicationException, ValidationException {
		logger.debug("--Start searchReferenceDataByTitle");
		SearchFilterSet refDataFilter = SearchFilterSet.asCIFilterSet(DPMConstants.REFERENCE_DATA_TYPE);
		StringFilter filter1 = new StringFilter(DPMConstants.REFERENCE_DATA_TITLE).exactMatchAny(new String[] { title });
		refDataFilter.addSearchAttributeFilter(filter1);

		SearchQuery searchQuery = new SearchQuery();
		searchQuery.getData().addFilterSet(refDataFilter);

		IPagingList result = searchQuery.execute();
		if (logger.isDebugEnabled())
			logger.debug(" results size::" + result.size());
		logger.debug(" results size::" + result.size());
		List subList = result.asList();
		ManagedObject mo;
		Iterator iter = subList.iterator();
		if (iter.hasNext()) {
			SearchResult searchResult = (SearchResult) iter.next();
			mo = searchResult.getManagedObject();
			return mo;
		}

		logger.debug("--End searchReferenceDataByTitle");
		return null;

	}

	/**
	 * This method will be used to search press release for a given site.
	 * 
	 * @param siteName
	 * @param ctdNames
	 * @param mediaBean
	 * @return List
	 * @throws ApplicationException
	 * @throws ValidationException
	 */
	public static IPagingList searchPressRelease(String siteName, String[] ctdNames, PressRelease mediaBean) throws ApplicationException, ValidationException {
		logger.debug("--Start searchPressRelease::siteName::" + siteName + " ctdNames::" + ctdNames);
		SearchQuery query = new SearchQuery();
		SearchFilterSet mediaFilterSet = (ctdNames.length == 1) ? SearchFilterSet.asCIFilterSet(ctdNames[0]) : SearchFilterSet.asMultiTypeFilterSet(ctdNames);
		if (siteName != null) {
			Site site = Site.findByName(siteName);
			ObjectIdFilter idFilter = new ObjectIdFilter(SearchMgmtAttr.SITE);
			idFilter.equalsAny(new GUID[] { site.getId() });
			mediaFilterSet.addSearchAttributeFilter(idFilter);
		}
		int i;
		if (mediaBean.getSegment() != null && mediaBean.getSegment().size() > 0) {
			String[] segments = new String[mediaBean.getSegment().size()];
			i = 0;
			for (Object segment : mediaBean.getSegment()) {
				segments[i++] = segment.toString();
			}
			if (logger.isDebugEnabled())
				logger.debug("Segments::" + segments);
			StringFilter filter1 = new StringFilter(DPMConstants.PRESS_RELEASE_BUSINESS_SEGMENT_ID).exactMatchAny(segments);
			mediaFilterSet.addSearchAttributeFilter(filter1);
		}
		if (logger.isDebugEnabled())
			logger.debug("Country::" + mediaBean.getCountry());
		if (mediaBean.getCountry() != null) {
			StringFilter filter3 = new StringFilter(DPMConstants.PRESS_RELEASE_COUNTRY_ID).exactMatchAny(new String[] { mediaBean.getCountry() });
			mediaFilterSet.addSearchAttributeFilter(filter3);
		}
		if (logger.isDebugEnabled())
			logger.debug("Keyword::" + mediaBean.getKeyword());
		if (mediaBean.getKeyword() != null) {
			query.getData().setQueryText(mediaBean.getKeyword());
		}
		if (logger.isDebugEnabled())
			logger.debug("startDate::" + mediaBean.getStartDate() + " endDate::" + mediaBean.getEndDate());
		if (mediaBean.getStartDate() != null && mediaBean.getEndDate() != null) {
			DateFilter dateFilter = new DateFilter(DPMConstants.PRESS_RELEASE_RELEASE_DATE);
			dateFilter.valueInRange(mediaBean.getStartDate(), mediaBean.getEndDate());
			mediaFilterSet.addSearchAttributeFilter(dateFilter);
		}
		SortFilter sortFilter = new SortFilter(DPMConstants.PRESS_RELEASE_RELEASE_DATE);
		sortFilter.setDescending(true);
		mediaFilterSet.addSortFilter(sortFilter);

		query.getData().addFilterSet(mediaFilterSet);
		return query.execute();
	}

	/**
	 * This method will be used to search deals for a given site.
	 * 
	 * @param siteName
	 * @param ctdNames
	 * @param dealBean
	 * @return Deals Search Results Size
	 * @throws ApplicationException
	 * @throws ValidationException
	 */
	public static IPagingList searchDeals(String siteName, String[] ctdNames, Deal dealBean) throws ApplicationException, ValidationException {

		logger.debug("--Start searchDeals::siteName::" + siteName + " ctdNames::" + ctdNames);
		SearchFilterSet dealFilterSet = (ctdNames.length == 1) ? SearchFilterSet.asCIFilterSet(ctdNames[0]) : SearchFilterSet.asMultiTypeFilterSet(ctdNames);
		if (siteName != null) {
			Site site = Site.findByName(siteName);
			ObjectIdFilter idFilter = new ObjectIdFilter(SearchMgmtAttr.SITE);
			idFilter.equalsAny(new GUID[] { site.getId() });
			dealFilterSet.addSearchAttributeFilter(idFilter);
		}
		int i;
		if (dealBean.getSegment() != null && dealBean.getSegment().size() > 0) {
			String[] segments = new String[dealBean.getSegment().size()];
			i = 0;
			for (Object segment : dealBean.getSegment()) {
				segments[i++] = segment.toString();
			}
			if (logger.isDebugEnabled())
				logger.debug("Segments::" + segments);
			StringFilter filter1 = new StringFilter(DPMConstants.DEALS_BUSINESS_SEGMENT_ID).exactMatchAny(segments);
			dealFilterSet.addSearchAttributeFilter(filter1);
		}
		if (logger.isDebugEnabled())
			logger.debug("Product/Service::" + dealBean.getProductService());
		if (dealBean.getProductService() != null) {
			StringFilter filter2 = new StringFilter(DPMConstants.DEALS_PRODUCT_SERVICE_ID).exactMatchAny(new String[] { dealBean.getProductService() });
			dealFilterSet.addSearchAttributeFilter(filter2);
		}
		if (logger.isDebugEnabled())
			logger.debug("Country::" + dealBean.getCountry());
		if (dealBean.getCountry() != null) {
			StringFilter filter3 = new StringFilter(DPMConstants.DEALS_COUNTRY_ID).exactMatchAny(new String[] { dealBean.getCountry() });
			dealFilterSet.addSearchAttributeFilter(filter3);
		}
		if (logger.isDebugEnabled())
			logger.debug("Sector::" + dealBean.getSector());
		if (dealBean.getSector() != null && dealBean.getSector().trim().length() > 0) {
			StringFilter filter4 = new StringFilter(DPMConstants.DEALS_SECTOR_ID).exactMatchAny(new String[] { dealBean.getSector() });
			dealFilterSet.addSearchAttributeFilter(filter4);
		}
		if (logger.isDebugEnabled())
			logger.debug("startDate::" + dealBean.getStartDate() + " endDate::" + dealBean.getEndDate());
		if (logger.isDebugEnabled())
			logger.debug("Client::" + dealBean.getClient());
		if (dealBean.getClient() != null && dealBean.getClient().trim().length() > 0) {
			StringFilter filter5 = new StringFilter(DPMConstants.CLIENT_NAME).exactMatchAny(new String[] { dealBean.getClient() });
			dealFilterSet.addSearchAttributeFilter(filter5);
		}
		if (logger.isDebugEnabled())
			logger.debug("startDate::" + dealBean.getStartDate() + " endDate::" + dealBean.getEndDate());
		if (dealBean.getStartDate() != null && dealBean.getEndDate() != null) {
			DateFilter dateFilter = new DateFilter(DPMConstants.DEAL_DATE);
			dateFilter.valueInRange(dealBean.getStartDate(), dealBean.getEndDate());
			dealFilterSet.addSearchAttributeFilter(dateFilter);
		}
		SortFilter sortFilter = new SortFilter(DPMConstants.DEAL_DATE);
		sortFilter.setDescending(true);
		dealFilterSet.addSortFilter(sortFilter);

		SearchQuery query = new SearchQuery();
		query.getData().addFilterSet(dealFilterSet);
		return query.execute();
	}

	public static Object getXPathValue(String xml, String expression, QName returnType) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		logger.debug("--Start getXPathValue");
		ByteArrayInputStream buf = new ByteArrayInputStream(xml.getBytes());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document document;
		Object xPathValue = null;
		try {
			document = factory.newDocumentBuilder().parse(buf);
		} finally {
			if (buf != null) {
				try {
					buf.close();
				} catch (IOException ioe) {

				}
			}
		}
		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPathExpression xPathExpression = xPathFactory.newXPath().compile(expression);
		if (returnType == XPathConstants.NODE || returnType == null) {
			Node node = (Node) xPathExpression.evaluate(document, XPathConstants.NODE);
			if (node != null) {
				xPathValue = node.getTextContent();
			}
		} else if (returnType == XPathConstants.STRING) {
			xPathValue = xPathExpression.evaluate(document, returnType);
		} else if (returnType == XPathConstants.NODESET) {
			xPathValue = xPathExpression.evaluate(document, returnType);
		}
		if (logger.isDebugEnabled())
			logger.debug("--End getXPathValue " + xPathValue);
		return xPathValue;
	}

	public static Object getXPathValue(Document document, String expression, QName returnType) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		logger.debug("--Start getXPathValue");
		Object xPathValue = null;
		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPathExpression xPathExpression = xPathFactory.newXPath().compile(expression);
		if (returnType == XPathConstants.NODE || returnType == null) {
			Node node = (Node) xPathExpression.evaluate(document, XPathConstants.NODE);
			if (node != null) {
				xPathValue = node.getTextContent();
			}
		} else if (returnType == XPathConstants.STRING) {
			xPathValue = xPathExpression.evaluate(document, returnType);
		} else if (returnType == XPathConstants.NODESET) {
			xPathValue = xPathExpression.evaluate(document, returnType);
		}
		if (logger.isDebugEnabled())
			logger.debug("--End getXPathValue " + xPathValue);
		return xPathValue;
	}

	public static String getNodeValueByID(String id, NodeList nodeList) {
		String value = null;
		int length = (nodeList != null) ? nodeList.getLength() : 0;
		for (int i = 0; i < length; i++) {
			org.w3c.dom.Node item = nodeList.item(i);
			org.w3c.dom.Node textNode = null;
			if (item.getNodeType() != 1) {
				continue;
			}
			textNode = item.getChildNodes().item(0);
			if (id.equals(textNode.getNodeValue()) && (i + 1) < length) {
				item = nodeList.item(i + 1);
				textNode = item.getChildNodes().item(0);
				value = textNode.getNodeValue();
			}
		}
		return value;
	}

	/**
	 * This method will be used to get subList from Search Results based on
	 * pagination values.
	 * 
	 * @param results
	 * @param resultsPerPage
	 * @param maxNoOfResults
	 * @param pageContext
	 * @return List of ManagedObjectBeans
	 */
	public static List getSearchResultsSubList(IPagingList results, int vgnNextStartIndex, int resultsPerPage, int maxNoOfResults, PageContext pageContext) throws ApplicationException {
		List searchResults = new ArrayList();
		int totalNumberOfMatches = (results != null) ? results.size() : 0;
		if (totalNumberOfMatches > 0) {
			// total number of matches less than maximum number of results
			// configured
			if (totalNumberOfMatches < maxNoOfResults) {
				maxNoOfResults = totalNumberOfMatches;
			}
			int numberOfPages;
			if (totalNumberOfMatches <= resultsPerPage) {
				numberOfPages = 1;
			} else {
				numberOfPages = (maxNoOfResults / resultsPerPage);
				if ((maxNoOfResults % resultsPerPage) > 0 && ((maxNoOfResults % resultsPerPage) < resultsPerPage)) {
					numberOfPages += 1;
				}
			}
			if (vgnNextStartIndex > numberOfPages) {
				vgnNextStartIndex = 1;
			}
			try {
				List list = ((vgnNextStartIndex) * resultsPerPage <= totalNumberOfMatches) ? results.subList((vgnNextStartIndex - 1) * resultsPerPage, (((vgnNextStartIndex - 1) * resultsPerPage) + (resultsPerPage - 1))) : results.subList((vgnNextStartIndex - 1) * resultsPerPage, totalNumberOfMatches);
				if (list != null && list.size() > 0) {
					Iterator it = list.iterator();
					ManagedObject mo;
					ManagedObjectBean objBean;
					SearchResult searchResult;
					while (it.hasNext()) {
						searchResult = (SearchResult) it.next();
						mo = searchResult.getManagedObject();
						objBean = BeanFactoryFactory.getFactory().createBean(mo);
						searchResults.add(objBean);
					}
				}
				pageContext.setAttribute(DPMConstants.PAGINATION_PARAM_TOTAL_MATCHES, Integer.toString(totalNumberOfMatches), PageContext.REQUEST_SCOPE);
				pageContext.setAttribute(DPMConstants.PAGINATION_PARAM_NUMBER_OF_PAGES, Integer.toString(numberOfPages), PageContext.REQUEST_SCOPE);
				pageContext.setAttribute(DPMConstants.PAGINATION_PARAM_CURRENT_PAGE_INDEX, Integer.toString(vgnNextStartIndex), PageContext.REQUEST_SCOPE);
			} catch (Exception ex) {
				logger.error("Exception occurred while getting sublist from search results::" + ex.getMessage());
			}
		}
		return searchResults;
	}

	/**
	 * this method will be use to get sublist from smart list results based on
	 * pagination index.
	 * 
	 * @param results
	 * @param vgnNextStartIndex
	 * @param resultsPerPage
	 * @param maxNoOfResults
	 * @param pageContext
	 * @param resultsVarName
	 * @return List
	 */
	public static List getSmartListResultsSubList(List results, int vgnNextStartIndex, int resultsPerPage, int maxNoOfResults, PageContext pageContext, String resultsVarName) {
		List smartListResults = new ArrayList();
		int totalNumberOfMatches = (results != null) ? results.size() : 0;
		if (totalNumberOfMatches > 0) {
			// total number of matches less than maximum number of results
			// configured
			if (totalNumberOfMatches < maxNoOfResults) {
				maxNoOfResults = totalNumberOfMatches;
			}
			int numberOfPages;
			if (totalNumberOfMatches <= resultsPerPage) {
				numberOfPages = 1;
			} else {
				numberOfPages = (maxNoOfResults / resultsPerPage);
				if ((maxNoOfResults % resultsPerPage) > 0 && ((maxNoOfResults % resultsPerPage) < resultsPerPage)) {
					numberOfPages += 1;
				}
			}
			if (vgnNextStartIndex > numberOfPages) {
				vgnNextStartIndex = 1;
			}
			try {
				List list = ((vgnNextStartIndex) * resultsPerPage <= totalNumberOfMatches) ? results.subList((vgnNextStartIndex - 1) * resultsPerPage, (vgnNextStartIndex) * resultsPerPage) : results.subList((vgnNextStartIndex - 1) * resultsPerPage, totalNumberOfMatches);
				if (list != null && list.size() > 0) {
					Iterator it = list.iterator();
					ManagedObject mo;
					ManagedObjectBean objBean;
					while (it.hasNext()) {
						mo = (ManagedObject) it.next();
						objBean = BeanFactoryFactory.getFactory().createBean(mo);
						smartListResults.add(objBean);
					}
				}
				if (resultsVarName != null) {
					pageContext.setAttribute(resultsVarName, smartListResults, PageContext.REQUEST_SCOPE);
				} else {
					pageContext.setAttribute(DPMConstants.PAGINATION_PARAM_RESULTS, smartListResults, PageContext.REQUEST_SCOPE);
				}
				pageContext.setAttribute(DPMConstants.PAGINATION_PARAM_TOTAL_MATCHES, Integer.toString(totalNumberOfMatches), PageContext.REQUEST_SCOPE);
				pageContext.setAttribute(DPMConstants.PAGINATION_PARAM_NUMBER_OF_PAGES, Integer.toString(numberOfPages), PageContext.REQUEST_SCOPE);
				pageContext.setAttribute(DPMConstants.PAGINATION_PARAM_CURRENT_PAGE_INDEX, Integer.toString(vgnNextStartIndex), PageContext.REQUEST_SCOPE);
			} catch (Exception ex) {
				logger.error("Exception occurred while getting sublist from search results::" + ex.getMessage());
			}
		}
		return smartListResults;
	}

	/**
	 * This method will be used to get Document object based on given xml
	 * 
	 * @param xml
	 * @return Document Object
	 */
	public static Document getDocumentObject(String xml) throws ParserConfigurationException, SAXException, IOException {
		ByteArrayInputStream buf = new ByteArrayInputStream(xml.getBytes());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document document = null;
		try {
			document = factory.newDocumentBuilder().parse(buf);
		} finally {
			if (buf != null) {
				try {
					buf.close();
				} catch (IOException ioe) {

				}
			}
		}
		return document;
	}

	/**
	 * This method will be used to get configured value for a given vanity url
	 * title
	 * 
	 * @param vanityUrl
	 * @param type
	 *            (it could be content, channel or externalURL)
	 * @return item (It could be channel rcrd, or content instance rcrd or
	 *         external url)
	 */
	public static String getValueByVanityURL(String vanityUrl, String type) {
		String moRCRD = null;
		logger.debug("--Start getChannelRCRDByVanityURL::vanityURL::" + vanityUrl);
		try {
			if (vanityUrl != null) {
				vanityUrl = (vanityUrl.startsWith(DPMConstants.SLASH) ? vanityUrl : DPMConstants.SLASH + vanityUrl);

				ContentType ctType = (ContentType) ContentType.findByName(DPMConstants.VANITY_URL_CTD);
				ContentInstanceDBQuery dbQuery = new ContentInstanceDBQuery(new ContentTypeRef(ctType.getId()));

				ContentInstanceWhereClause nameClause = new ContentInstanceWhereClause();
				nameClause.setMatchAny(true);
				nameClause.checkAttribute(DPMConstants.VANITY_URL_TITLE, StringQueryOp.EQUAL, vanityUrl);
				dbQuery.setWhereClause(nameClause);

				IPagingList results = QueryManager.execute(dbQuery);
				int size = (results != null) ? results.size() : 0;
				if (logger.isDebugEnabled())
					logger.debug(" size::" + size);
				if (size > 0) {
					List list = results.asList();
					Iterator it = list.iterator();
					ManagedObject mo;
					if (it.hasNext()) {
						mo = (ManagedObject) it.next();
						if (DPMConstants.CONTENT_TYPE.equalsIgnoreCase(type)) {
							moRCRD = (String) mo.getAttributeValue(DPMConstants.VANITY_URL_ITEM_LINK);
						} else if (DPMConstants.CHANNEL_TYPE.equalsIgnoreCase(type)) {
							moRCRD = (String) mo.getAttributeValue(DPMConstants.VANITY_URL_CHANNEL_LINK);
						} else if (DPMConstants.EXTERNAL_URL_TYPE.equalsIgnoreCase(type)) {
							moRCRD = (String) mo.getAttributeValue(DPMConstants.VANITY_URL_EXTERNAL_LINK);
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error("Exception occurred in getChannelRCRDByVanityURL::" + ex.getMessage());
			ex.printStackTrace();
		}
		logger.debug("--End getChannelRCRDByVanityURL::vanityURL::" + vanityUrl + " channelRCRD::" + moRCRD);
		return moRCRD;
	}

	public static Comparator getManagedObjectTitleComparator() {
		return new Comparator() {
			public int compare(Object o1, Object o2) {
				ManagedObject mo1 = (ManagedObject) o1;
				ManagedObject mo2 = (ManagedObject) o2;
				int code = 0;
				try {
					code = mo1.getName().compareTo(mo2.getName());
				} catch (Exception ex) {
				}
				return code;
			}
		};
	}

	/**
	 * Return Map.Entry Comparator
	 * 
	 * @return Comparator
	 */
	public static Comparator getHashMapEntryKeyComparator() {
		return new Comparator() {
			public int compare(Object o1, Object o2) {
				Map.Entry e1 = (Map.Entry) o1;
				Map.Entry e2 = (Map.Entry) o2;
				String first = (String) e1.getKey();
				String second = (String) e2.getKey();
				return first.compareTo(second);
			}
		};
	}

	/**
	 * Check if the current file is a style
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isStyle(String filePath) {
		if (TemplatingUtil.isNullOrEmpty(filePath)) {
			return false;
		}

		return filePath.trim().endsWith(".css");
	}

	/**
	 * Check if the current file is a javascript file
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isJavascriptFile(String filePath) {
		if (TemplatingUtil.isNullOrEmpty(filePath)) {
			return false;
		}
		return filePath.trim().endsWith(".js");
	}
	
	public static ContentType getAttDescriptorContentType() throws  JspException{
		ContentType ct = null;;
		try {
			ct = (ContentType) ContentUtil.getObjectTypeByName(DPMConstants.CHANNEL_DESCRIPTOR_CTD);
		} catch (ApplicationException e) {
			logger.error("ApplicationException while retreiving Attribute Descriptor Content Type Object.::", e);
			throw new JspException("ApplicationException while retreiving Attribute Descriptor Content Type Object.");
		} catch (ValidationException e) {
			logger.error("ValidationException while retreiving Attribute Descriptor Content Type Object.::", e);
			throw new JspException("ValidationException while retreiving Attribute Descriptor Content Type Object.");
		}
		return ct;
	}
}
