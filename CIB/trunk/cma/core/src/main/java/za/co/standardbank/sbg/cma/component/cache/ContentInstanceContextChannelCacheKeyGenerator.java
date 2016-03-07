package za.co.standardbank.sbg.cma.component.cache;

import com.vignette.ext.templating.cache.RenderedManagedObjectCacheKey;
import com.vignette.ext.templating.client.javabean.ContentComponent;
import com.vignette.ext.templating.util.RequestContext;
import com.vignette.as.client.javabean.Channel;
import com.vignette.as.client.exception.ApplicationException;

/**
 * The CurrentContentInstanceCurrentChannel cache key generator is used for JSPs
 * that typically exist on a Details page and whose HTML Fragment is dependent
 * upon the Current Context Channel in addition to the Content Instance being
 * displayed. A common example here is a set of links that are related to the
 * main Content Instance and context channel
 * 
 * @author John Egan
 * 
 */
public class ContentInstanceContextChannelCacheKeyGenerator extends
		AbstractCacheKeyGenerator {

	/**
	 * @see au.com.amp.vignette.domain.cache.ICacheKeyGenerator#createCacheKey(
	 * RenderedManagedObjectCacheKey, ContentComponent)
	 * 
	 * @param key - cache key
	 * @param component - content component
	 * @return cache key string
	 * @throws ApplicationException - ApplicationException
	 */
	public String createCacheKey(RenderedManagedObjectCacheKey key,
			ContentComponent component) throws ApplicationException {
		StringBuffer sb = new StringBuffer();

		// Start with the default behaviour...
		sb.append(super.createCacheKey(key, component));
		RequestContext rc = key.getRequestContext();

		if (rc != null) {
			sb.append("-");
			String vgnextoid = rc.getRequestOIDString();
			if (vgnextoid != null) {
				sb.append(vgnextoid);
			}
			sb.append("-");
			Channel c = rc.getRequestedChannel();
			if (c != null) {
				sb.append(c.getContentManagementId().getId());
			}
		}
		return sb.toString();
	}

}
