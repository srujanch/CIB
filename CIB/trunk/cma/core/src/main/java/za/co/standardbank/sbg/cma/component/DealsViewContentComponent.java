package za.co.standardbank.sbg.cma.component;

import za.co.standardbank.sbg.cma.component.cache.CacheKeyGeneratorFactory;
import za.co.standardbank.sbg.cma.component.cache.ICacheKeyGenerator;
import za.co.standardbank.sbg.cma.exception.CmaRuntimeException;

import com.vignette.as.client.exception.ApplicationException;
import com.vignette.ext.templating.cache.RenderedManagedObjectCacheKey;
import com.vignette.ext.templating.util.SysUtil;
import com.vignette.logging.Category;

public class DealsViewContentComponent extends AbstractSBSAContentComponent {

    private static Category     LOG                      = Category
                                                                 .getInstance(DealsViewContentComponent.class);

    private static final long   serialVersionUID         = 1L;
    public static final String  ATTR_NAME_QUERY_TYPE	= "QUERY-TYPE";
	public static final String  ATTR_NAME_CUSTOM_QUERY	= "CUSTOM-QUERY";
	public static final String  ATTR_NAME_MAX_RESULT	= "MAX-RESULT";
	public static final String  ATTR_NAME_CACHE_TTL		= "CUSTOM-TTL";
    private static final String ATTR_NAME_CACHE_STRATEGY = "CACHE-STRATEGY";

    public String getQueryType() throws ApplicationException {
        String data = (String) getAttributeValue(ATTR_NAME_QUERY_TYPE);
        return data;
    }
	
	public String getCustomQuery() throws ApplicationException {
        String data = (String) getAttributeValue(ATTR_NAME_CUSTOM_QUERY);
        return data;
    }
	
	public int getMaxResult() throws ApplicationException {
        int data = (Integer) getAttributeValue(ATTR_NAME_MAX_RESULT);
        return data;
    }

	public int getCacheTTL() throws ApplicationException {
        int data = (Integer) getAttributeValue(ATTR_NAME_CACHE_TTL);
        return data;
    }
    public long getComponentDefaultTTL() {
        return SysUtil.getConfigValLong(
                "default.view.component.cache.cacheLifeTime", 300000L);
    }

    @Override
    public String createCacheKey(RenderedManagedObjectCacheKey key)
            throws ApplicationException {
        StringBuffer sb = new StringBuffer();
        sb.append(super.createCacheKey(key));
        String cacheStrategy = (String) getAttributeValue(ATTR_NAME_CACHE_STRATEGY);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Successfully obtained cache strategy: " + cacheStrategy);
        }

        ICacheKeyGenerator keyGen;
        try {
            keyGen = CacheKeyGeneratorFactory.getInstance()
                    .getCacheKeyGenerator(cacheStrategy);
        } catch (Exception e) {
            throw new CmaRuntimeException(
                    "Failed to create cache key for component", e);
        }
        sb.append(keyGen.createCacheKey(key, this));

        // Now add pagination if existing
        String vgnNextStartIndex = key.getRequestContext().getParameter(
                "vgnNextStartIndex");
        if (vgnNextStartIndex != null) {
            sb.append("#");
            sb.append("vgnNextStartIndex=" + vgnNextStartIndex);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Successfully created View Component cache key: "
                    + sb.toString());
        }

        return sb.toString();
    }

}
