package za.co.standardbank.sbg.cma.component.cache;

import com.vignette.ext.templating.client.javabean.ContentComponent;
import com.vignette.ext.templating.cache.RenderedManagedObjectCacheKey;
import com.vignette.as.client.exception.ApplicationException;

/**
 * Interface defining the necessary methods for a Cache Key Generator class.
 * 
 * @author John Egan
 * 
 */
public interface ICacheKeyGenerator {

    /**
     * Creates cache key for the rendered managed object.
     * 
     * @param key
     *            - cache key
     * @param component
     *            - content component
     * @return cache key string
     * @throws ApplicationException
     *             - ApplicationException
     */
    String createCacheKey(RenderedManagedObjectCacheKey key,
            ContentComponent component) throws ApplicationException;

}
