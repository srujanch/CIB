package za.co.standardbank.sbg.cda.domain.impl;

import java.util.ArrayList;
import java.util.List;

import za.co.standardbank.sbg.cda.domain.SearchFilter;

import com.vignette.as.client.common.AttributeFilter;

public class SearchFilterImpl implements SearchFilter {

	public void addContentType(String contentTypeXmlName) {
		this.contentTypes.add(contentTypeXmlName);
	}

	public List<String> getContentTypes() {
		return this.contentTypes;
	}

	public void addAttributeFilter(AttributeFilter attrFilter) {
		this.attributeFilters.add(attrFilter);
	}

	public List<AttributeFilter> getAttributeFilters() {
		return this.attributeFilters;
	}

	public String getOrderByAttribute() {
		return this.orderByAttribute;
	}

	public boolean getOrderByDescending() {
		return this.orderByDescending;
	}

	public void setOrderBy(String attributeName, boolean descending) {
		this.orderByAttribute = attributeName;
		this.orderByDescending = descending;

	}

	private String siteId;
	
	private String channelId;

	private String keywords;

	private int pageSize = -1;

	private int startItem = -1;

	private String orderByAttribute;

	private boolean orderByDescending = false;

	private List<AttributeFilter> attributeFilters = new ArrayList<AttributeFilter>();

	private List<String> contentTypes = new ArrayList<String>();

	/**
	 * Returns the keywords attribute.
	 * 
	 * @return keywords attribute
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * Sets the keywords attribute.
	 * 
	 * @param keywords
	 *            - value of the keywords attribute
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * Returns the site attribute.
	 * 
	 * @return site attribute
	 */
	public String getSiteId() {
		return this.siteId;
	}

	/**
	 * Sets the site attribute.
	 * 
	 * @param site
	 *            - value of the site attribute
	 */
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	
	/**
	 * Returns the Channel GUID Value.
	 * 
	 * @return Channel GUID Value.
	 */
	public String getChannelId() {
		return this.channelId;
	}

	/**
	 * Sets the Channel GUID
	 * 
	 * @param channelId
	 *            - value of the Channel attribute
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	/**
	 * Returns the pageSize attribute.
	 * 
	 * @return pageSize attribute
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Sets the pageSize attribute.
	 * 
	 * @param pageSize
	 *            - value of the pageSize attribute
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * Returns the startItem attribute.
	 * 
	 * @return startItem attribute
	 */
	public int getStartItem() {
		return startItem;
	}

	/**
	 * Sets the startItem attribute.
	 * 
	 * @param startItem
	 *            - value of the startItem attribute
	 */
	public void setStartItem(int startItem) {
		this.startItem = startItem;
	}

}
