package za.co.standardbank.sbg.cma.component.cache;

import com.vignette.as.client.exception.ApplicationException;
import com.vignette.ext.templating.cache.RenderedManagedObjectCacheKey;
import com.vignette.ext.templating.client.javabean.ContentComponent;
import com.vignette.logging.Category;

/**
 * Abstract cache key generator contains common functions necessary for all
 * cache key generator types.
 * 
 * @author John Egan
 * 
 */
public abstract class AbstractCacheKeyGenerator implements ICacheKeyGenerator {

    /**
     * Log4j logger.
     */
    private static Category LOG = Category
    .getInstance(AbstractCacheKeyGenerator.class);

	/**
	 * @see au.com.amp.vignette.domain.cache.ICacheKeyGenerator#createCacheKey(
	 * RenderedManagedObjectCacheKey, ContentComponent)
	 * 
	 * @param key -
	 *            cache key
	 * @param component -
	 *            content component
	 * @return cache key string
	 * @throws ApplicationException - ApplicationException
	 */
	public String createCacheKey(RenderedManagedObjectCacheKey key,
			ContentComponent component) throws ApplicationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Begin createCacheKey for BaseCacheKeyGenerator");
		}
		return "";

	}

}
