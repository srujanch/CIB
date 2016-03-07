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
public class DefaultCacheKeyGenerator extends AbstractCacheKeyGenerator {

    /**
     * Log4j logger.
     */
    private static Category LOG = Category
                                        .getInstance(DefaultCacheKeyGenerator.class);

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
    public String createCacheKey(RenderedManagedObjectCacheKey key,
            ContentComponent component) throws ApplicationException {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Begin createCacheKey for DefaultCacheKeyGenerator");
        }

        // Start with the default behaviour...
        return super.createCacheKey(key, component);
    }

}
