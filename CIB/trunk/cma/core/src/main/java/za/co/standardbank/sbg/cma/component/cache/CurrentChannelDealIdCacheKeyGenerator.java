package za.co.standardbank.sbg.cma.component.cache;

import com.vignette.as.client.exception.ApplicationException;
import com.vignette.as.client.javabean.Channel;
import com.vignette.ext.templating.cache.RenderedManagedObjectCacheKey;
import com.vignette.ext.templating.client.javabean.ContentComponent;
import com.vignette.ext.templating.util.RequestContext;
import com.vignette.logging.Category;

/**
 * The CurrentChannelDealIdCacheKeyGenerator is used for JSPs whose output
 * HTML Fragment is dependent on the current channel and the current DealId. 
 * This is for Deals Component on Deals Details Page.
 * 
 * @author vbyreddy
 * 
 */
public class CurrentChannelDealIdCacheKeyGenerator extends AbstractCacheKeyGenerator {

	/**
	 * Log4j logger.
	 */
	private static Category LOG = Category.getInstance(CurrentChannelDealIdCacheKeyGenerator.class);

	/**
	 * @see au.com.amp.vignette.domain.cache.ICacheKeyGenerator#createCacheKey(RenderedManagedObjectCacheKey,
	 *      ContentComponent)
	 * 
	 * @param key
	 *            - cache key
	 * @param component
	 *            - content component
	 * @return cache key string
	 * @throws ApplicationException
	 *             - Application Exception
	 */
	public String createCacheKey(RenderedManagedObjectCacheKey key, ContentComponent component) throws ApplicationException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Begin createCacheKey for DealIdCacheKeyGenerator");
		}

		StringBuffer sb = new StringBuffer();

		// Start with the default behaviour...
		sb.append(super.createCacheKey(key, component));
		RequestContext rc = key.getRequestContext();
		if (rc != null) {
			// Check the strategy type and away we go.
			Channel c = rc.getRequestedChannel();
			if (c != null) {
				sb.append("-"+c.getContentManagementId().getId());
			}
			String dealId = (String) rc.getParameter("dealId");
			if (LOG.isDebugEnabled()) {
				LOG.debug("Currenr Deal ID::"+dealId);
			}
			System.out.println("DealId::" + dealId);
			if (dealId != null) {
				sb.append("-"+dealId);
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("END of createCacheKey for DealIdCacheKeyGenerator. Cache Key::"+sb);
		}
		return sb.toString();
	}

}
