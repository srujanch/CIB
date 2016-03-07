package za.co.standardbank.sbg.cma.migration;

import com.vignette.authn.AuthnBundle;
import com.vignette.authn.AuthnConsts;
import com.vignette.authn.LoginMgr;
import com.vignette.as.client.javabean.ContentType;
import com.vignette.as.client.javabean.IPagingList;
import com.vignette.as.client.javabean.QueryManager;
import com.vignette.as.client.javabean.ManagedObject;
import com.vignette.as.client.common.ContentInstanceDBQuery;
import com.vignette.as.client.common.ContentInstanceWhereClause;
import com.vignette.as.client.common.ref.ContentTypeRef;
import com.vignette.as.client.exception.ApplicationException;
import com.vignette.as.client.exception.ValidationException;
import com.vignette.util.StringQueryOp;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.List;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: svenepal
 * Date: Mar 21, 2012
 * Time: 9:06:39 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class MigrateAction {

    Logger logger = Logger.getLogger(this.getClass());
    Connection connection;
    Properties props;
    private static final String DB_URL = "dam.db.url";
    private static final String DB_USER = "dam.db.user";
    private static final String DB_PASSWORD = "dam.db.password";
    private static final String DB_DRIVER_CLASS_NAME = "db.driver.class.name";

    public static final int ERROR_CODE = 4;
    private static final String VCM_HOST_IP = "vcm.host.ip";
    private static final String VCM_HOST_PORT = "vcm.host.port";
    private static final String VCM_USER = "vcm.user";
    private static final String VCM_PASSWORD = "vcm.password";

    private String ipAddress = "10.145.143.124";     //10.145.143.124
    private String port = "27110";
    private String protocol = "t3";
    private String userName = "vgnadmin";
    private String password = "V1gn3tt3";           //V1gn3tt3
    private String SERVER_FACTORY = "weblogic.jndi.WLInitialContextFactory";

    String[] allCountryWebSites = new String[]{"AO","AR","BW","BR","CN","DRC","GH","HK","IM","JP","JE","KE","LS",
            "MW","MY","MU","MZ","NA","NG","RU","SG","ZA","SZ","TW","TZ","TR","UG","UA","AE","GB","US","ZM","ZW"};
    String[] allAfricaWebSites = new String[]{"AO","BW","DRC","GH","KE","LS","MW","MU","MZ","NA","NG","ZA","SZ","TZ","UG","ZM","ZW"};
    String[] allSegmentsList = new String[]{"CIB","PVT","OFF","SBG","PM", "BB"};
    String[] allCountryWebsiteNames = new String[]{"All - Africa CIB websites","All - country CIB websites","All - group websites"};
    static final String CIB_STRING = "Corporate and Investment Banking";
    static final String PBB_STRING = "Personal and Business Banking";
    static final String SBG_STRING = "Standard Bank Group";
    static final String ALL_COUNTRY_STRING = "All - country CIB websites";
    static final String ALL_AFRICA_STRING = "All - Africa CIB websites";
    static final String ALL_GROUP_STRING = "All - group websites";
    //String[] allGroupWebSites = new String[]{"ZA"};

    public abstract int performContentMigration();



    public void LoginToVCM() {
        logger.info("Inside LoginToVCM");
        try {

            AuthnBundle bundle = new AuthnBundle();
            bundle.setFactory(SERVER_FACTORY);
            bundle.setAuthType(AuthnConsts.WEBLOGIC_CONTEXT);
            bundle.setProtocol(protocol);
            bundle.setUsername(userName);
            bundle.setPassword(password);
            bundle.setPort(port);
            bundle.setHost(ipAddress);
            bundle.setEnableSSL(false);
            logger.info("Connecting to VCM........");
            LoginMgr loginMgr = new LoginMgr();
            loginMgr.login(bundle);
            logger.info("Connected to VCM........");
        } catch (Exception ex) {
            logger.error("Not able to login to VCM..", ex);
            ex.printStackTrace();
            System.exit(MigrateAction.ERROR_CODE);
        }
        logger.info("Exit LoginToVCM");
    }

    public String getContentRCRDByTypeAndTitle(String type, String title) throws ApplicationException, ValidationException {
        logger.info("Inside getContentRCRDByTypeAndTitle.type: "+type+" title.."+title);
        String rcrd = null;
        ContentType ctType = (ContentType)ContentType.findByName(type);
        ContentInstanceDBQuery query = new ContentInstanceDBQuery(new ContentTypeRef(ctType.getId()));

        ContentInstanceWhereClause nameClause = new ContentInstanceWhereClause();
        nameClause.setMatchAny(true);
        nameClause.checkAttribute("TITLE", StringQueryOp.EQUAL, title);
        query.setWhereClause(nameClause);

        IPagingList results = QueryManager.execute(query);
        if (results != null){
            logger.info(" search results size::"+results.size());
            List list = results.asList();
            Iterator it = list.iterator();
            ManagedObject mo;
           if (it.hasNext()){
                mo = (ManagedObject)it.next();
                logger.info(" mo title..."+mo.getName()+" RCRD::"+mo.getContentManagementId());
                rcrd = mo.getContentManagementId().getId();
            }
        }else{
           logger.info(" search results are empty::");
        }
       return rcrd;
    }

    public void init() {
        props = getProperties();

        ipAddress = props.getProperty(MigrateAction.VCM_HOST_IP);
        port = props.getProperty(MigrateAction.VCM_HOST_PORT);
        userName = props.getProperty(MigrateAction.VCM_USER);
        password = props.getProperty(MigrateAction.VCM_PASSWORD);

        String url = props.getProperty(MigrateAction.DB_URL);
        String user = props.getProperty(MigrateAction.DB_USER);
        String password = props.getProperty(MigrateAction.DB_PASSWORD);
        String driverClassName = props.getProperty(MigrateAction.DB_DRIVER_CLASS_NAME);
        logger.info("Driver class name:"+driverClassName);
        logger.info("DB URL:"+url);
        connection = getConnection(url,user,password,driverClassName);
    }
    private Properties getProperties() {
        Properties props = new Properties();
        InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("migration.properties");
        try {
            props.load(input);
        } catch (Exception e) {
            logger.error("migration.properties is not set up properly");
        }
        return props;
    }

    private Connection getConnection(String url, String user, String password, String driverClassName) {
        Connection conn = null;
        logger.info("Inside getConnection");
        try {
            Class.forName(driverClassName);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException cnfe) {
            logger.error("Make sure the oracle driver is in the classpath", cnfe);
            System.exit(MigrateAction.ERROR_CODE);
        } catch (SQLException sqle) {
            logger.error(sqle, sqle);
            System.exit(MigrateAction.ERROR_CODE);
        }
        logger.info("Exit getConnection");
        return conn;
    }

    void closeConnections() {
        logger.info("Inside closeConnection");
        closeConnection(connection);
        logger.info("Exit closeConnection");
    }

    void closeConnection(Connection conn){
        logger.info("Inside closeConnection");
        if (conn!= null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error(e, e);

            }
            conn = null;
        }
        logger.info("Exit closeConnection");
    }
    public void deleteContent(IPagingList results, int pageSize){
        try{
            int size = results.size();
            System.out.println("total results::"+size);
            List list;
            int counter = 0;
            int numberOfPages = 1;
            while(size > 0){
                System.out.println("total number of matches::"+numberOfPages+" total elements size::"+size);
                logger.info("total number of matches::"+numberOfPages+" total elements size::"+size);
                    int i = 1;
                    list = ((i)*pageSize <= size)?results.subList((i-1)*pageSize,(i)*(pageSize-1)):
                            results.subList((i-1)*pageSize,size);
                    System.out.println("After getting list::size "+list.size());
                    counter = deleteManagedObject(list, counter);
                    System.out.println("i value::"+i+" pageSize::"+pageSize+" does size changed::"+results.size());
                    size = list.size();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private int deleteManagedObject(List list, int counter) throws Exception{
        ManagedObject mo;
        int size = list.size();
        System.out.println("deleted elements size::"+size);
        logger.info("deleted elements size::"+size);
        for (int i=0;i<size;i++){
            mo = (ManagedObject)list.get(i);
            logger.info(" mo title::"+mo.getName()+" delete count::"+(counter+1));
            System.out.println("mo title::"+mo.getName()+" deleted count::"+(counter+1));
            mo.remove();
            counter++;
        }
        return counter;
    }

}
