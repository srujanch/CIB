package za.co.standardbank.sbg.cma.component.cache;

import com.vignette.logging.Category;

/**
 * Factory which returns the correct Cache Key Generator based upon the given
 * cache strategy.
 * 
 * @author John Egan
 * 
 */
public class CacheKeyGeneratorFactory {

    /**
     * Factory singleton instance.
     */
    private static CacheKeyGeneratorFactory cacheKeyGeneratorFactory;

    /**
     * Log4j logger.
     */
    private static Category                 LOG = Category
                                                        .getInstance(CacheKeyGeneratorFactory.class);

    /**
     * Constructor.
     */
    protected CacheKeyGeneratorFactory() {
    }

    /**
     * Retrieves singleton instance.
     * 
     * @return Cache Key Generator factory
     */
    public static CacheKeyGeneratorFactory getInstance() {
        if (cacheKeyGeneratorFactory == null) {
            LOG.debug("Creating new CacheKeyGeneratorFactory instance");
            cacheKeyGeneratorFactory = new CacheKeyGeneratorFactory();
        }
        return cacheKeyGeneratorFactory;
    }

    /**
     * Returns the Cache Key Generator class name for a given Cache Strategy.
     * The cache strategy is typically selected by a content contributor when
     * configuring a component
     * 
     * @param cacheStrategy
     *            - Cache Strategy
     * @return Class name string
     * 
     */
    private static String getCacheKeyGeneratorClassName(
            final String cacheStrategy) {

        String strategyName = cacheStrategy;
        if (strategyName == null) {
            strategyName = "Default";
        }
        StringBuffer className;

        className = new StringBuffer(CacheKeyGeneratorFactory.class
                .getPackage().getName());
        className.append(".");
        className.append(strategyName);
        className.append("CacheKeyGenerator");

        return className.toString();
    }

    /**
     * Returns a Cache Key Generator class for the chosen strategy.
     * 
     * @param cacheStrategy
     *            - Cache Strategy of cache key generator required
     * @return Cache key generator string
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * 
     */
    public final ICacheKeyGenerator getCacheKeyGenerator(
            final String cacheStrategy) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException {

        AbstractCacheKeyGenerator cacheKeyGeneratorInstance = null;
        String cacheKeyGeneratorClassName = getCacheKeyGeneratorClassName(cacheStrategy);

        Class cacheKeyGeneratorClass = Class
                .forName(cacheKeyGeneratorClassName);

        cacheKeyGeneratorInstance = (AbstractCacheKeyGenerator) cacheKeyGeneratorClass
                .newInstance();
        LOG.debug("Successfully instantiated cache key generator class: "
                + cacheKeyGeneratorInstance);

        return cacheKeyGeneratorInstance;

    }

}
