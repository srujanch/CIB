package za.co.standardbank.sbg.cma.component.cache;

import com.vignette.as.client.exception.ApplicationException;
import com.vignette.as.client.javabean.Channel;
import com.vignette.ext.templating.cache.RenderedManagedObjectCacheKey;
import com.vignette.ext.templating.client.javabean.ContentComponent;
import com.vignette.ext.templating.util.RequestContext;
import com.vignette.logging.Category;

/**
 * The CurrentChannel cache key generator is to be used for JSPs whose output
 * HTML Fragment is dependent on the current channel. A common example here is
 * channel navigation or a breadcrumb component.
 * 
 * @author John Egan
 * 
 */
public class CurrentChannelCacheKeyGenerator extends AbstractCacheKeyGenerator {

    /**
     * Log4j logger.
     */
    private static Category LOG = Category
    .getInstance(CurrentChannelCacheKeyGenerator.class);

	/**
	 * @see au.com.amp.vignette.domain.cache.ICacheKeyGenerator#createCacheKey(
	 * RenderedManagedObjectCacheKey, ContentComponent)
	 * 
	 * @param key -
	 *            cache key
	 * @param component -
	 *            content component
	 * @return cache key string
	 * @throws ApplicationException -
	 *             Application Exception
	 */
	public String createCacheKey(RenderedManagedObjectCacheKey key,
			ContentComponent component) throws ApplicationException {

		if (LOG.isDebugEnabled()) {
			LOG
					.debug("Begin createCacheKey for CurrentChannelCacheKeyGenerator");
		}
		
		StringBuffer sb = new StringBuffer();

		// Start with the default behaviour...
		sb.append(super.createCacheKey(key, component));
		RequestContext rc = key.getRequestContext();

		if (rc != null) {
			// Check the strategy type and away we go.
			sb.append("-");
			Channel c = rc.getRequestedChannel();
			if (c != null) {
				sb.append(c.getContentManagementId().getId());
			}
		}
		return sb.toString();
	}

}
