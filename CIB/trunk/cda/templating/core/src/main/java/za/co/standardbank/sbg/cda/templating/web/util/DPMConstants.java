package za.co.standardbank.sbg.cda.templating.web.util;

/**
 * Created by IntelliJ IDEA.
 * User: svenepal
 * Date: Apr 20, 2012
 * Time: 9:18:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class DPMConstants {

    public static final String ACCOLADE_DATE_YEARS = "_ACCOLADE_DATE_YEARS";
    public static final String PARAM_ACCOLADE_DATE = "accoladeDate";
    public static final String PARAM_COUNTRY_CONST = "country";
    public static final String PARAM_PRODUCT_CONST = "product";
    public static final String PARAM_SECTOR_CONST = "sector";
    public static final String COUNTRY_OFFICE_INFO_CTD = "SBG-COUNTRY-OFFICE-INFO";
	public static final String VIDEO_CTD_XML_NAME = "DSX-MEDIA-VIDEO";
    public static final String PRODUCTS_CTD = "SBG-PRODUCT";
    public static final String SECTOR_CTD = "SBG-SECTOR";
    public static final String TITLE = "TITLE";
    public static final String COUNTRY_OFFICE_INFO_COUNTRY_NAME = "FRIENDLY-NAME";
    public static final String COUNTRIES_CONST = "Countries";
    public static final String SB_WEBSITES_CONST = "Standard Bank Websites";
    public static final String SECTORS_CONST = "Sectors";
    public static final String PRODUCTS_CONST = "Products";
    public static final String REFERENCE_RELATION_NAME = "SBG_REFERENCE_OPTIONS";
    public static final String REFERENCE_DATA_LABEL = "LABEL";
    public static final String REFERENCE_DATA_VALUE = "VALUE";
    public static final String REFERENCE_DATA_TYPE = "SBG-REFERENCE-DATA";
    public static final String DEALS_START_YEAR = "_DEALS_START_YEAR";
    public static final String MEDIA_START_YEAR = "_MEDIA_START_YEAR";
    public static final String REFERENCE_DATA_TITLE = "TITLE";
    public static final String PRESS_RELEASE_BUSINESS_SEGMENT_ID = "SBG_PRESS_RELEASE.SBG_RELATED_BUSINESS.BUSINESS-ID";
    public static final String PRESS_RELEASE_COUNTRY_ID = "SBG_PRESS_RELEASE.SBG_RELATED_COUNTRIES.COUNTRY-ID";

    public static final String DEALS_BUSINESS_SEGMENT_ID = "SBG_DEALS.SBG_RELATED_BUSINESS.BUSINESS-ID";
    public static final String DEALS_PRODUCT_SERVICE_ID = "SBG_DEALS.SBG_RELATED_PRODUCTS.PRODUCT-ID";
    public static final String DEALS_SECTOR_ID = "SBG_DEALS.SBG_RELATED_SECTORS.SECTOR-ID";
    public static final String DEALS_COUNTRY_ID = "SBG_DEALS.SBG_RELATED_COUNTRIES.COUNTRY-ID";
    public static final String CLIENT_NAME = "CLIENT-NAME";
    public static final String DEAL_DATE = "DEAL-DATE";
    public static final String PRESS_RELEASE_RELEASE_DATE = "LIVE-DATE";
    public static final String ACCOLADE_PUBLICATION_YEAR_RELATION = "SBG_RELATED_PUBLICATION_YEAR";
    public static final String ACCOLADE_PUBLICATION_YEAR_ID = "PUBLICATION-ID";
    public static final String COMMA_CONST = ",";
    public static final String CT_EXPR = "/query/@itemType";
    public static String REPLACE_EXPR = "#replace#";
    public static String EQUALS_EXPR = "/query/where/eq[" + REPLACE_EXPR + "]/term";
    public static String NOT_EQUALS_EXPR = "/query/where/neq[" + REPLACE_EXPR + "]/term";
    public static String HEAD_EXPR = "/query/where";
    public static final String DEALS_CTD_TYPE = "SBG-DEALS";
    public static final String BUSINESS_SEGMENT_ID = "BUSINESS-ID";
    public static final String PARAM_YEAR = "Year";
    public static final String PARAM_COUNTRY = "Country";
    public static final String PARAM_SECTOR = "Sector";
    public static final String PARAM_PRODUCT = "Product";
    public static final String PARAM_KEYWORD = "Keyword";
    public static final String PARAM_CLIENT_NAME = "Client";
    public static final String PARAM_VGNNEXT_START_INDEX = "vgnNextStartIndex";
    public static final int DEFAULT_RESULTS_PER_PAGE = 10;
    public static final int DEFAULT_PAGINATION_BLOCK_SIZE = 5;
    public static final int DEFAULT_MAX_RESULTS_SIZE = 50;
    public static final String	COMPONENT_DESCRIPTION_ATT = "vgnExtTemplatingComponentDescription";
	public static final String	ATTR_NAME_DATA = "vgnExtTemplatingComponentData";
    public static final String RESULTS_PER_PAGE_XML_ATT = "/data/resultsPerPage";
    public static final String PAGINATION_BLOCK_SIZE_XML_ATT = "/data/paginationBlockSize";
	    public static final String MAX_RESULTS_XML_ATT = "/data/maxResults";
    public static final String PAGINATION_PARAM_TOTAL_MATCHES = "totalMatches";
    public static final String PAGINATION_PARAM_NUMBER_OF_PAGES = "numberOfPages";
    public static final String PAGINATION_PARAM_CURRENT_PAGE_INDEX = "currentPageIndex";
    public static final String PAGINATION_PARAM_RESULTS = "results";
    public static final String SLASH = "/";
    public static final String VANITY_URL_CTD = "VgnExtFriendlyURL";
    public static final String VANITY_URL_TITLE = "SourceUrl";
    public static final String VANITY_URL_CHANNEL_LINK = "ChannelLink";
    public static final String VANITY_URL_ITEM_LINK = "ItemLink";
    public static final String VANITY_URL_EXTERNAL_LINK = "ExternalUrl";
    public static final String DEFAULT_FORMAT = "default";

    public static final String CONTENT_TYPE = "content";
    public static final String CHANNEL_TYPE = "channel";
    public static final String EXTERNAL_URL_TYPE = "externalURL";

    public static final String ACCOLADE_PUBLICATION = "PUBLICATION";
    
  //Channel Descriptor related.
    public static final String CHANNEL_DESCRIPTOR_CTD = "SBG-CHANNEL-DESCRIPTOR";
    public static final String CHANNEL_DESCRIPTOR_ATT_NAME = "SBG-CHANNEL-DESCRIPTOR";
    public static final String CHANNELDESCRIPTORSMAP_VAR_NAME = "channelDescriptorsMap";
    public static final String CHANNEL_DESC_NAV_COLUMNS = "NAVIGATION-COLUMNS";
    public static final String CHANNEL_DESC_NAV_ALIGNMENT = "NAVIGATION-ALIGNMENT";
    public static final String CHANNEL_DESC_NAV_COLUMN_NUMBER = "NAVIGATION-COLUMN-NUMBER";
    public static final String CHANNEL_DESC_SUB_NAV_ITEMS = "SUB-NAVIGATION-ITEMS";
    public static final String CHANNEL_DESC_NAV_PLACEHOLDER_CHANNEL = "NAVIGATION-PLACEHOLDER-CHANNEL";
}
