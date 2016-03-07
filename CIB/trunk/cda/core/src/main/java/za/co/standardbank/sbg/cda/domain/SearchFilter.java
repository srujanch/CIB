package za.co.standardbank.sbg.cda.domain;

import java.util.List;

import com.vignette.as.client.common.AttributeFilter;

public interface SearchFilter {

	int getStartItem();

	void setStartItem(int startItem);

	int getPageSize();

	void setPageSize(int pageSize);

	void setOrderBy(String attributeName, boolean descending);

	String getOrderByAttribute();

	boolean getOrderByDescending();

	String getKeywords();

	void setKeywords(String keywords);

	void setSiteId(String siteId);

	String getSiteId();
	
	void setChannelId(String channelId);

	String getChannelId();

	void addAttributeFilter(AttributeFilter attrFilter);

	List<AttributeFilter> getAttributeFilters();

	void addContentType(String contentTypeXmlName);

	List<String> getContentTypes();

}
