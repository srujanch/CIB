package za.co.standardbank.sbg.cma.migration;

import com.vignette.authn.AuthnBundle;
import com.vignette.authn.AuthnConsts;
import com.vignette.authn.LoginMgr;
import com.vignette.as.client.javabean.*;
import com.vignette.as.client.common.*;
import com.vignette.as.client.common.types.SearchTypeEnum;
import com.vignette.as.client.common.ref.ContentTypeRef;
import com.vignette.as.client.common.ref.ObjectTypeRef;
import com.vignette.as.client.exception.ApplicationException;
import com.vignette.util.StringQueryOp;
import com.vignette.util.GUID;
import com.vignette.ext.templating.util.ContentUtil;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.net.URLEncoder;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.soap.Node;
import javax.xml.namespace.QName;

//import za.co.standardbank.sbg.cda.templating.web.util.DPMConstants;

/**
 * Created by IntelliJ IDEA.
 * User: svenepal
 * Date: Mar 20, 2012
 * Time: 1:57:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginVCM {

    private static String HOST_IPADDRESS = "10.145.143.124";     //10.145.143.124
    private static String PORT = "27110";
    private static String PROTOCOL = "t3";
    private static String USER_NAME = "vgnadmin";
    private static String PASSWORD = "V1gn3tt3";           //V1gn3tt3
    private static String SERVER_FACTORY = "weblogic.jndi.WLInitialContextFactory";

    public static void LoginToVCM() {
        try {

            AuthnBundle bundle = new AuthnBundle();
            bundle.setFactory(SERVER_FACTORY);
            bundle.setAuthType(AuthnConsts.WEBLOGIC_CONTEXT);
            bundle.setProtocol(PROTOCOL);
            bundle.setUsername(USER_NAME);
            bundle.setPassword(PASSWORD);
            bundle.setPort(PORT);
            bundle.setHost(HOST_IPADDRESS);
            bundle.setEnableSSL(false);
            System.out.println("Connecting to VCM........");
            LoginMgr loginMgr = new LoginMgr();
            loginMgr.login(bundle);
            System.out.println("Connected to VCM........");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void createReferenceData(String title, ArrayList listValues){
        try{
            System.out.println("Inside createReferenceData");
            ContentType ctType =  (ContentType)ContentType.findByName("SBG-REFERENCE-DATA");
            ObjectTypeRef otRef = new ObjectTypeRef(ctType);

            ContentInstance ci = (ContentInstance)ctType.newInstance();
            ci.setAttributeValue("TITLE",title);
            ci.setLogicalPath("/SBG/CIB/Reference Data");

            ContentRelationInstance relationInstance = new ContentRelationInstance(otRef);
            relationInstance.setAttributeValue("LABEL",listValues.get(0));
            relationInstance.setAttributeValue("VALUE",listValues.get(1));
            //relationInstance.setAttributeValue("OPTION-ORDER",new Integer(1));

            AttributedObject[] attObjArray = {relationInstance};
            ci.setRelations("SBG_REFERENCE_OPTIONS",attObjArray);
            ci.commit();
            System.out.println("Exit createReferenceData");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void searchItemByType(String type) throws Exception{
        ContentType ctType = (ContentType)ContentType.findByName(type);
        ContentInstanceDBQuery query = new ContentInstanceDBQuery(new ContentTypeRef(ctType.getId()));

        ContentInstanceWhereClause nameClause = new ContentInstanceWhereClause();
        nameClause.setMatchAny(true);
        nameClause.checkAttribute("TITLE", StringQueryOp.EQUAL, "N/A");
        query.setWhereClause(nameClause);

        IPagingList results = QueryManager.execute(query);
        System.out.println(" size::"+results.size());
        List list = results.asList();
        Iterator it = list.iterator();
        ManagedObject mo;
        while (it.hasNext()){
            mo = (ManagedObject)it.next();
             System.out.println(" RCRD::"+mo.getContentManagementId());
        }


    }

    private static void createDealContent() throws Exception{
            System.out.println("inside createDealContent");
            ContentType ctType =  (ContentType)ContentType.findByName("SBG-DEALS");
            String contentLogicalPath = "/SBG/CIB/Deals";
            ContentInstance ci = (ContentInstance)ctType.newInstance();
            ci.setAttributeValue("TITLE","Test");


            ci.setAttributeValue("SUMMARY","Summary");
            ci.setAttributeValue("DEAL-DATE",new Date());
            ci.setAttributeValue("CLIENT-NAME","Test");
            ci.setAttributeValue("DEAL-AMOUNT","3M");
            ci.setAttributeValue("CURRENCY","55");

           NumberFormat nf = NumberFormat.getInstance();
           nf.setMaximumFractionDigits(3);

            ci.setAttributeValue("EXCHANGE-RATE", new BigDecimal("1"));
            ci.setAttributeValue("DOLLAR-AMOUNT", new BigDecimal("0"));

            ci.setAttributeValue("DESCRIPTION","desc");
            ci.setAttributeValue("STATUS","ACTIVE");
            ci.setAttributeValue("CLIENT-CONSENT","Yes");
            ci.setAttributeValue("CLIENT-CONFIDENTIALITY","Yes");
            ci.setAttributeValue("CONSULTED-COMP","Yes");
            ci.setAttributeValue("CONFLICTS-CLEARANCE","Yes");
            ci.setAttributeValue("COMMENTS","Comments");
            ci.setAttributeValue("PUBLISH-HOMEPAGE","Yes");
            ci.setAttributeValue("MARKETING-REGION", "All");
            ci.setAttributeValue("BUSINESS-SEGMENT","CIB");
            ci.setAttributeValue("CONTACT-ID", "8338adfadbd46310VgnVCM1000007c8f910aRCRD");
            ci.setLogicalPath(contentLogicalPath);
            ci.commit();
            System.out.println("Exit createDealContent");
    }
     private static Connection getOracleConnection() {
        Connection conn = null;
        System.out.println("Inside getConnection");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@10.145.143.126:1521:DB11G", "vcm_system", "vcm_system");
        } catch (ClassNotFoundException cnfe) {
             System.out.println("Make sure the oracle driver is in the classpath");
            //System.exit(MigrateAction.ERROR_CODE);
        } catch (SQLException sqle) {
             System.out.println(sqle);
           // System.exit(MigrateAction.ERROR_CODE);
        }
        System.out.println("Exit getConnection");
        return conn;
    }
    private static Connection getConnection() {
        Connection conn = null;
        System.out.println("Inside getConnection");
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS;database=SBSA", "sa", "V1gn3tt3");
        } catch (ClassNotFoundException cnfe) {
             System.out.println("Make sure the oracle driver is in the classpath");
            //System.exit(MigrateAction.ERROR_CODE);
        } catch (SQLException sqle) {
             System.out.println(sqle);
           // System.exit(MigrateAction.ERROR_CODE);
        }
        System.out.println("Exit getConnection");
        return conn;
    }
    private static void loadSBWebSitesList(){
        try{
           System.out.println("-- Start loadSBWebSitesList");
           ContentType contentType = (ContentType)ContentType.findByName("SBG-REFERENCE-DATA");
           ContentTypeRef ref = new ContentTypeRef(contentType.getId());
           ContentInstanceDBQuery query = new ContentInstanceDBQuery(ref);
           ContentInstanceWhereClause whereClause  = new ContentInstanceWhereClause();
           whereClause.setMatchAny(true);
           whereClause.checkAttribute("TITLE", StringQueryOp.EQUAL,"Standard Bank Websites");
           query.addWhereClause(whereClause);
           IPagingList results = QueryManager.execute(query, RequestParameters.getImmutableInstanceFullRelation());
           System.out.println("results size::"+results.size());
           List list = results.asList();
           if (list.size() > 0 ){
                ManagedObject mo = (ManagedObject)list.get(0);
                ContentInstance ci = (ContentInstance)mo;
                System.out.println("content instance title::"+ci.getName());
                AttributedObject[] attObjArray = ci.getRelations("SBG_REFERENCE_OPTIONS");
                int size = (attObjArray != null)?attObjArray.length:0;
                System.out.println("AttributedObject Size::"+size);
               if (size > 0){
                    for (AttributedObject attObj:attObjArray){
                         //VALUE as KEY, LABEL as value
                        System.out.println(" value::"+attObj.getAttributeValue("VALUE")+" LABEL::"+attObj.getAttributeValue("LABEL"));
                        //sbWebSitesList.put(,);
                    }
               }
           }
          // System.out.println("sbWebSitesList size::"+sbWebSitesList.size());
        }catch(Exception ex){
            System.out.println("exception occurred while loading Standard Bank WebSites List::"+ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println("-- End loadSBWebSitesList");
    }
    public static void main(String[] args) throws Exception {
        //LoginToVCM();
        //createDealContent();
        //searchItemByType("SBG-PRODUCT");
//        ArrayList listValues = new ArrayList();
//        listValues.add("Namibia Dollar");
//        listValues.add("NAD");

        //createReferenceData("CURRENCIES",listValues);
        //Double d= new Double("Infinity");
        //System.out.println("double value:;"+d.doubleValue());
//        Project p = Project.findProjectByPath("/SBG/CIB/Deals");
//        System.out.println(p.getChildrenCount(null));
//        RequestParameters requestParams = new RequestParameters(false);
//        IPagingList results = p.getManagedObjectsByType(requestParams,Project.ALL_CONTENT_INSTANCES);
//        List list = results.asList();
//        ManagedObject mo = (ManagedObject)list.get(0);
//        System.out.println(" mo title::"+mo.getName());
//        mo.remove();
//        String str = "?????? Standard Bank ?????????? ??????? ??????? ?? ?????????? ?????????? ????? ? ???????? ?????????????? ??????????????";
//        byte[] bytes = str.getBytes();
//        System.out.println(new String(bytes,"cp1251"));
//        Connection con = getConnection();
//        if (con != null){
//            PreparedStatement ps = con.prepareStatement("select title from media where media_id=239");
//
//            //ps.setInt(1, 463+","+385);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()){
//                byte[] bytes = rs.getBytes(1);
//
//                System.out.println(" "+ new String(bytes,"ISO-8859-1"));
//            }
//        }
//        if (con != null){
//            con.close();
//        }
//        //Connection oracleCon = getOracleConnection();
//        if (con != null){
//            System.out.println("connection String "+con.getMetaData().getUserName());
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery("select Announced from deals where deal_id=512");
//            if (rs.next()){
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd Z");
//                Date d = rs.getDate(1);
//                Calendar cal = Calendar.getInstance();
//                cal.clear();
//                cal.setTime(d);
//                System.out.println(sdf.format(d));
////                PreparedStatement ps = oracleCon.prepareStatement(" insert into test values(?, ?)");
////                ps.setString(1,"test");
////                ps.setDate(2,rs.getDate(1));
////                ps.executeUpdate();
//            }
            //con.close();
//            //oracleCon.close();
//        }
//        TimeZone tz = TimeZone.getTimeZone("Africa/Johannesburg");
//        System.out.println(tz.inDaylightTime());
//
//        String ids = TimeZone.getTimeZone("GMT+2").getID();
//        //if (ids.length == 0)
//         //    System.exit(0);
//        System.out.println("Current Time "+ids);
//
//         // create a Pacific Standard Time time zone
//         SimpleTimeZone pdt = new SimpleTimeZone(2 * 60 * 60 * 1000, ids);
//         Locale locale = new Locale("zu","ZA");
//         Calendar calendar = new GregorianCalendar(tz,locale);
//
//         System.out.println(locale.getDisplayCountry());
//         //Calendar calendar = new GregorianCalendar(pdt);
//          Date trialTime = new Date(107,02,29);
//          calendar.setTime(trialTime);
//          System.out.println("Current Time::"+calendar.getTime());
//         // System.out.println("ERA: " + calendar.get(Calendar.ERA));
//        Calendar cal = Calendar.getInstance();
//        cal.clear();
//        cal.setTime(new Date());
//        cal.set(2012,11,31);
//        System.out.println(cal.getTime());
//
//        String dealAmount = "2.75B";
//        double dealValue;
//        double amount = 0;
//        if (dealAmount != null && (dealAmount.indexOf("K") > -1 || dealAmount.indexOf("M") > -1 || dealAmount.indexOf("B") > -1)){
//            if (dealAmount.indexOf("K") > -1){
//                amount = 1000;
//            }else if (dealAmount.indexOf("M") > -1){
//                amount = 1000000;
//            }else if (dealAmount.indexOf("B") > -1){
//                amount = 1000000000;
//            }
//            System.out.println("deal value::"+dealAmount+" conversion amount::"+amount);
//            dealAmount = dealAmount.replaceAll("K","").replaceAll("M", "").replaceAll("B","");
//            dealValue = Double.parseDouble(dealAmount);
//            dealValue *= amount;
//            NumberFormat formatter = new DecimalFormat("#0.00");
//            System.out.println("deal value123::"+formatter.format(dealValue));
//            dealAmount = formatter.format(dealValue);
//            //dealAmount = dealAmount.substring(0,dealAmount.indexOf("."));
//            char[] charArray = dealAmount.toCharArray();
//            int counter = 0;
//            for (char ch:charArray){
//                if (ch == '0'){
//                    counter++;
//                }
//            }
//            System.out.println(" "+counter);
//            if (counter >= 9){
//                dealAmount = formatter.format(Double.parseDouble(dealAmount)/1000000000);
//                if (dealAmount.lastIndexOf(".") > -1){
//                   if (Double.parseDouble(dealAmount.substring(dealAmount.lastIndexOf("."),dealAmount.length())) == 0){
//                       dealAmount = dealAmount.substring(0,dealAmount.lastIndexOf("."));
//                   }
//                }
//                dealAmount = dealAmount+"B";
//            }else if (counter >=6 ){
//                dealAmount = formatter.format(Double.parseDouble(dealAmount)/1000000);
//                System.out.println(" "+dealAmount);
//                if (dealAmount.lastIndexOf(".") > -1){
//                   if (Double.parseDouble(dealAmount.substring(dealAmount.lastIndexOf("."),dealAmount.length())) == 0){
//                       dealAmount = dealAmount.substring(0,dealAmount.lastIndexOf("."));
//                   }
//                }
//                dealAmount = dealAmount+"M";
//            }else if (counter >=1 ){
//                dealAmount = formatter.format(Double.parseDouble(dealAmount)/1000);
//                System.out.println(" "+dealAmount);
//                if (dealAmount.lastIndexOf(".") > -1){
//                   if (Double.parseDouble(dealAmount.substring(dealAmount.lastIndexOf("."),dealAmount.length())) == 0){
//                       dealAmount = dealAmount.substring(0,dealAmount.lastIndexOf("."));
//                   }
//                }
//                dealAmount = dealAmount+"M";
//            }
//
//            System.out.println(" "+dealAmount);
//        }
//           LoginToVCM();
//          loadSBWebSitesList();

//            Calendar today = Calendar.getInstance();
//            GregorianCalendar calendar;
//            int day = today.get(Calendar.DAY_OF_MONTH);
//            int month = today.get(Calendar.MONTH);
//            int year = today.get(Calendar.YEAR);
//            int hour = 0;//today.get(Calendar.HOUR_OF_DAY);
//            int minute = 0;//today.get(Calendar.MINUTE);
//            int second = 0;//today.get(Calendar.SECOND);
//            calendar = new GregorianCalendar(year, month, day, hour, minute, second);
//            calendar.add(Calendar.YEAR,-2);
//            calendar.add(Calendar.MONTH,-6);
//            System.out.println(calendar.getTime());
            //int numberOfPages = 45/15;
//            System.out.println("numberof pages::"+(22/15));
//            System.out.println("numberof pages::"+(22%15));
//              String str = "2.6";
//              System.out.println(str.substring(0,str.indexOf(".")));
//              String[] arr = str.split(".");
//              System.out.println(arr[0]);
//              System.out.println(arr[1]);
//         HashMap first = new HashMap();
//         first.put("USA" , "USA");
//         first.put("AZ" , "Algeria");
//         first.put("ZA" , "South Africa");
//
//         ArrayList as = new ArrayList( first.entrySet() );
//
//         Collections.sort( as ,getHashMapEntrySetComparator());
//
//         Iterator i = as.iterator();
//         while ( i.hasNext() )
//         {
//             System.out.println( ((Map.Entry)i.next()).getValue() );
//         } \
//        long sTime = System.currentTimeMillis();
//        LoginToVCM();
//        //searchQueryByFilter();
//        IPagingList results = searchDeals();
//        executeQuery(results);
//        System.out.println("totla time took::"+(System.currentTimeMillis()-sTime)/1000+" seconds");
//          String xml = "<query version=\"1.0\" itemtype=\"SBG-DEALS\"><where><eq><term type=\"field\">BUSINESS-ID</term><term type=\"constant\">Corporate and Investment Banking</term></eq></where></query>";
//          String searchCTs =   getXPathValue(xml, DPMConstants.CT_EXPR, XPathConstants.STRING);
//         System.out.println("searchCTs:: "+searchCTs);
//        GregorianCalendar calendar = new GregorianCalendar(2011, 0, 1, 0, 0, 0);
//        System.out.println(calendar.getTime());
//                    Calendar fromDate = Calendar.getInstance();
//					fromDate.set(2012, 01, 01);
//                     System.out.println(fromDate.getTime());
            LoginToVCM();
            getValueByVanityURL("/deals");
    }
    public static String getValueByVanityURL(String vanityUrl){
        String moRCRD = null;
        System.out.println("--Start getChannelRCRDByVanityURL::vanityURL::"+vanityUrl);
        try{
           if (vanityUrl != null){
               vanityUrl = (vanityUrl.startsWith("/")?vanityUrl:"/"+vanityUrl);

               ContentType ctType = (ContentType)ContentType.findByName("VgnExtFriendlyURL");
               //ContentType ctType = (ContentType)ContentUtil.getObjectTypeByName("vgnExtFriendlyURL");
               System.out.println("ctType::"+ctType);
               ObjectTypeRef objRef = ctType.getObjectTypeRef();

               ContentInstanceDBQuery dbQuery = new ContentInstanceDBQuery(new ContentTypeRef(ctType.getId()));

               ContentInstanceWhereClause nameClause = new ContentInstanceWhereClause();
               nameClause.setMatchAny(true);
               nameClause.checkAttribute("SourceUrl", StringQueryOp.EQUAL, vanityUrl);

               dbQuery.setWhereClause(nameClause);

               IPagingList results = QueryManager.execute(dbQuery);
               System.out.println(" size::"+results);
               int size = (results != null)?results.size():0;
               System.out.println(" size::"+size);
               if (size > 0){
               List list = results.asList();
               Iterator it = list.iterator();
               ManagedObject mo;
               if (it.hasNext()){
                   mo = (ManagedObject)it.next();
                    moRCRD = (String)mo.getAttributeValue("ChannelLink");

               }
               }
           }
            System.out.println("morcrd::"+moRCRD);
        }catch(Exception ex){

            ex.printStackTrace();
        }
        return moRCRD;
    }
    public static Comparator getHashMapEntrySetComparator(){
        return new Comparator(){
        public int compare( Object o1 , Object o2 )
             {
                 Map.Entry e1 = (Map.Entry)o1 ;
                 Map.Entry e2 = (Map.Entry)o2 ;
                 String first = (String)e1.getValue();
                 String second = (String)e2.getValue();
                 return first.compareTo( second );
             }
         };
    }

    public static void searchQuery()throws Exception{
        SearchQuery query = new SearchQuery();
        SearchQueryData queryData = query.getData();
        queryData.setObjectType("SBG-REFERENCE-DATA");
        queryData.setQueryText("Countries");
        queryData.setSearchType(SearchTypeEnum.CONTENT);
        IPagingList result = query.execute();
        System.out.println(" results size::"+result.size());
        List subList = result.asList();
        ManagedObject mo = null;
        Iterator iter = subList.iterator();
         while (iter.hasNext()) {
           SearchResult searchResult = (SearchResult) iter.next();
           mo = searchResult.getManagedObject();
           // Act on managed object.
           if (mo != null) {
             if (mo instanceof ContentInstance) {
               ContentInstance ci = (ContentInstance) mo;
               System.out.println(ci.getName());
             } else { // Then it must be a static file.
               StaticFile sf = (StaticFile) mo;
               // Act on the static file...
             }
           }
         }
    }
    public static void searchQueryByFilter() throws Exception{

        SearchFilterSet refDataFilter = SearchFilterSet.asCIFilterSet("SBG-REFERENCE-DATA");
        StringFilter filter1= new StringFilter("TITLE").exactMatchAny(new String[]{"Countries"});
        refDataFilter.addSearchAttributeFilter(filter1);
        SearchQuery searchQuery = new SearchQuery();
        searchQuery.getData().addFilterSet(refDataFilter);

        IPagingList result = searchQuery.execute();
        System.out.println(" results size::"+result.size());
        List subList = result.asList();
        ManagedObject mo = null;
        Iterator iter = subList.iterator();
         while (iter.hasNext()) {
           SearchResult searchResult = (SearchResult) iter.next();
           mo = searchResult.getManagedObject();
           // Act on managed object.
           if (mo != null) {
             if (mo instanceof ContentInstance) {
               ContentInstance ci = (ContentInstance) mo;
               AttributedObject[] attObjArray = ci.getRelations("SBG_REFERENCE_OPTIONS");
                int size = (attObjArray != null)?attObjArray.length:0;

               if (size > 0){
                    for (AttributedObject attObj:attObjArray){
                         //VALUE as KEY, LABEL as value
                        System.out.println(attObj.getAttributeValue("LABEL").toString()+"::"+attObj.getAttributeValue("VALUE").toString());

                    }
               }
             }
           }
         }

    }
    public static IPagingList searchDeals() throws Exception{

        SearchFilterSet dealFilterSet = SearchFilterSet.asCIFilterSet("SBG-DEALS");

        Site site = Site.findByName("cib");

//        SearchFilter searchFilter = new SearchFilter();
//        searchFilter.setAttr(SearchMgmtAttr.SITE);
//        searchFilter.setValue1(site.getId());

//        ObjectIdFilter idFilter = new ObjectIdFilter(SearchMgmtAttr.SITE);
//        idFilter.equalsAny(new GUID[]{site.getId()});
//        dealFilterSet.addSearchAttributeFilter(idFilter);
        //dealFilterSet.ad

        StringFilter fileter1 = new StringFilter("SBG_DEALS.SBG_RELATED_BUSINESS.BUSINESS-ID").exactMatchAny(new String[]{"Corporate and Investment Banking","aaa"});
        StringFilter filter2 = new StringFilter("SBG_DEALS.SBG_RELATED_PRODUCTS.PRODUCT-ID").exactMatchAny(new String[]{"Acquisition finance"});
        StringFilter filter3 = new StringFilter("SBG_DEALS.SBG_RELATED_COUNTRIES.COUNTRY-ID").exactMatchAny(new String[]{"ZA"});
        StringFilter filter4 = new StringFilter("SBG_DEALS.SBG_RELATED_SECTORS.SECTOR-ID").exactMatchAny(new String[]{"Financial institutions"});
        StringFilter filter5 = new StringFilter("CLIENT-NAME").exactMatchAny(new String[]{"*Brait*"});

        Date firstDate = new SimpleDateFormat("yyyy-MM-dd").parse("2011-01-01");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2011-12-31");
        DateFilter dateFilter = new DateFilter("DEAL-DATE").valueInRange(firstDate,endDate);

        dealFilterSet.addSearchAttributeFilter(fileter1);
        dealFilterSet.addSearchAttributeFilter(filter2);
        dealFilterSet.addSearchAttributeFilter(filter3);
        dealFilterSet.addSearchAttributeFilter(filter4);
        dealFilterSet.addSearchAttributeFilter(filter5);
        dealFilterSet.addSearchAttributeFilter(dateFilter);
        SortFilter sortFilter = new SortFilter("DEAL-DATE");
        sortFilter.setDescending(true);
        dealFilterSet.addSortFilter(sortFilter);

        SearchQuery query = new SearchQuery();
        query.getData().addFilterSet(dealFilterSet);

        IPagingList result = query.execute();
        System.out.println(" results size::"+result.size());
        return result;
        //List subList = result.asList();
//        ManagedObject mo = null;
//        Iterator iter = subList.iterator();
//         while (iter.hasNext()) {
//           SearchResult searchResult = (SearchResult) iter.next();
//           mo = searchResult.getManagedObject();
//           // Act on managed object.
//           if (mo != null) {
//             if (mo instanceof ContentInstance) {
//               //ContentInstance ci = (ContentInstance) mo;
//               //System.out.println(ci.getName());
//             }
//           }
//         }
    }

    public static void executeQuery(IPagingList resultList) throws ApplicationException {
        List subList = resultList.subList(0,2);
        ManagedObject mo = null;
        Iterator iter = subList.iterator();
         while (iter.hasNext()) {
           SearchResult searchResult = (SearchResult) iter.next();
           mo = searchResult.getManagedObject();
           // Act on managed object.
           if (mo != null) {
             if (mo instanceof ContentInstance) {
               ContentInstance ci = (ContentInstance) mo;
               System.out.println(ci.getName());
             }
           }
         }
    }
     public static String getXPathValue(String xml, String expression, QName returnType)throws ParserConfigurationException, SAXException, IOException
    , XPathExpressionException {
        System.out.println ("--Start getXPathValue");
         ByteArrayInputStream buf = new ByteArrayInputStream(xml.getBytes());
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         Document document;
        String xPathValue = null;
        try{
         document = factory.newDocumentBuilder().parse(buf);
        }finally{
            if (buf != null){
                try{
                    buf.close();
                }catch(IOException ioe){

                }
            }
        }
         System.out.println ("--Start getXPathValue"+expression);
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPathExpression xPathExpression = xPathFactory.newXPath().compile(expression);
        if (returnType == XPathConstants.NODE || returnType == null){
            Node node = (Node) xPathExpression.evaluate(document,XPathConstants.NODE);
            if (node != null){
                xPathValue =  node.getTextContent();
            }
        }else if (returnType == XPathConstants.STRING){
            xPathValue = (String)xPathExpression.evaluate(document, returnType);
        }else if (returnType == XPathConstants.NODESET){
            NodeList nodeList = (NodeList)xPathExpression.evaluate(document,returnType);
            int length = (nodeList != null)?nodeList.getLength():0;
            for (int i = length; i < 0; i++){
                //Node node = nodeList.item(i);

            }
        }
        System.out.println ("--End getXPathValue "+xPathValue);
        return xPathValue;
    }

}
