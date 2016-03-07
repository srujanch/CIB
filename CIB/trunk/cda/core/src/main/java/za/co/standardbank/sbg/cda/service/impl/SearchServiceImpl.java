package za.co.standardbank.sbg.cda.service.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import za.co.standardbank.sbg.cda.domain.ResultSet;
import za.co.standardbank.sbg.cda.domain.SearchFilter;
import za.co.standardbank.sbg.cda.domain.impl.ResultSetImpl;
import za.co.standardbank.sbg.cda.exception.CdaRuntimeException;
import za.co.standardbank.sbg.cda.service.SearchService;

import com.vignette.as.client.api.bean.BeanFactory;
import com.vignette.as.client.api.bean.BeanFactoryFactory;
import com.vignette.as.client.api.bean.ManagedObjectBean;
import com.vignette.as.client.common.AttributeFilter;
import com.vignette.as.client.common.ObjectIdFilter;
import com.vignette.as.client.common.SearchFilterSet;
import com.vignette.as.client.common.SearchMgmtAttr;
import com.vignette.as.client.common.SearchQueryData;
import com.vignette.as.client.common.SearchResult;
import com.vignette.as.client.common.SortFilter;
import com.vignette.as.client.common.ref.ManagedObjectVCMRef;
import com.vignette.as.client.common.types.SearchTypeEnum;
import com.vignette.as.client.javabean.IPagingList;
import com.vignette.as.client.javabean.ManagedObject;
import com.vignette.as.client.javabean.SearchQuery;
import com.vignette.ext.templating.util.ContentUtil;

public class SearchServiceImpl implements SearchService {

	private static final Logger logger = Logger.getLogger(SearchServiceImpl.class);

	public ResultSet getContentByFilter(SearchFilter filter) {

		if (logger.isDebugEnabled())
			logger.debug("Constructing new search query...");

		try {
			SearchQuery searchQuery = new SearchQuery();
			SearchQueryData queryData = searchQuery.getData();
			queryData.setSearchType(SearchTypeEnum.CONTENT);
			/*
			 * Only load IDs from search engine! The complete object will be
			 * retrieved from DPM object cache
			 */
			queryData.setLoadFromSearchEngine(true);

			// Search content of specified CTDs
			if (filter.getContentTypes().isEmpty()) {
				logger.error("Please specify at least one content type in the SearchFilter.");
				throw new CdaRuntimeException("Please specify at least one content type in the SearchFilter.");
			}
			SearchFilterSet ciFilterSet = SearchFilterSet.asMultiTypeFilterSet((String[]) filter.getContentTypes().toArray(new String[0]));

			// Filter by site if provided
			if (filter.getSiteId() != null) {
				ObjectIdFilter oidFilter = new ObjectIdFilter(SearchMgmtAttr.SITE);
				oidFilter.equalsAny(new String[] { filter.getSiteId() });
				ciFilterSet.addSearchAttributeFilter(oidFilter);
			}

			// Filter by Channel if provided
			if (filter.getChannelId() != null) {
				ObjectIdFilter oidFilter = new ObjectIdFilter(SearchMgmtAttr.CHANNEL);
				oidFilter.equalsAny(new String[] { filter.getChannelId() });
				ciFilterSet.addSearchAttributeFilter(oidFilter);
			}

			for (AttributeFilter attrFilter : filter.getAttributeFilters()) {
				ciFilterSet.addSearchAttributeFilter(attrFilter);
			}

			queryData.addFilterSet(ciFilterSet);

			// Add keywords if provided
			if (filter.getKeywords() != null) {
				queryData.setQueryText(filter.getKeywords());
			}

			// Need to change the implementation to accept requestparams to
			// determine the start and end range instead of using aslist and
			// sublist
			IPagingList pagingList = searchQuery.execute();

			if (filter.getOrderByAttribute() != null) {
				SortFilter orderBy = new SortFilter(filter.getOrderByAttribute());
				orderBy.setDescending(filter.getOrderByDescending());
				ciFilterSet.addSortFilter(orderBy);
			}

			ResultSet resultSet = new ResultSetImpl();
			resultSet.setTotalResultsSize(pagingList.size());

			List results;
			int startItem = filter.getStartItem();
			int pageSize = filter.getPageSize();
			if (resultSet.getTotalResultsSize() > 0) {
				if ((startItem != -1) && (pageSize != -1)) {
					int endItem = startItem + pageSize;
					if (endItem > (resultSet.getTotalResultsSize())) {
						endItem = resultSet.getTotalResultsSize();
						if (startItem > endItem) {
							startItem = endItem;
						}
					}
					results = pagingList.subList(startItem, endItem);
				} else {
					results = pagingList.asList();
				}
				if (logger.isDebugEnabled())
					logger.debug("Successfully retrieved page of results containing " + results.size() + " items");
				if (results != null && results.size() > 0) {

					Iterator<SearchResult> iterator = results.iterator();

					BeanFactory beanFactory = BeanFactoryFactory.getFactory();
					while (iterator.hasNext()) {
						// Retrieving content instance from search result
						SearchResult searchResult = iterator.next();
						ManagedObject mo = ContentUtil.getManagedObject((ManagedObjectVCMRef) searchResult.getManagedObjectRef());
						ManagedObjectBean bean = beanFactory.createBean(mo);
						resultSet.addResult(bean);

					}
				}
			}
			return resultSet;
		} catch (Exception e) {
			logger.error("Exception occurred attempting to execute Search query", e);
			throw new CdaRuntimeException("Exception occurred attempting to execute Search query", e);
		}

	}
}
