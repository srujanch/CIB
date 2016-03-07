package za.co.standardbank.sbg.cma.migration;

import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;
import java.text.SimpleDateFormat;


import com.vignette.as.client.exception.ValidationException;
import com.vignette.as.client.exception.ApplicationException;
import com.vignette.as.client.exception.AuthorizationException;
import com.vignette.as.client.javabean.*;
import com.vignette.as.client.common.ref.ObjectTypeRef;
import com.vignette.as.client.common.ref.ContentTypeRef;
import com.vignette.as.client.common.ref.ManagedObjectVCMRef;
import com.vignette.as.client.common.ref.ChannelRef;
import com.vignette.as.client.common.*;
import com.vignette.util.VgnIllegalArgumentException;
import com.vignette.util.StringQueryOp;

/**
 * Created by IntelliJ IDEA.
 * User: svenepal
 * Date: Mar 26, 2012
 * Time: 2:16:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class DealsDataMigration extends MigrateAction {

    Logger logger = Logger.getLogger(this.getClass());

    private static final String DEAL_MAIN_QUERY = "select DEAL_ID, TITLE, SHORT_DESCRIPTION, ANNOUNCED, CLIENT, AMOUNT, CURRENCY, deals.DESCRIPTION, COMMENTS, sec.value sector" +
            " from DEALS deals, SECTORS sec " +
            " where deals.sector=sec.id and status='ACTIVE' and publishstatus='Published' order by DEAL_ID ";

    private static final String DEALS_COUNTRY_BINDING_QUERY = "select con.VALUE from COUNTRIES con, COUNTRYBINDING conbind where " +
            " conbind.DAM_ID=? and conbind.COUNTRY_ID=con.ID and conbind.DAM_TYPE=1 order by DAM_ID";

    private static final String DEALS_SEGMENT_BINDING_QUERY = "select seg.description from SEGMENTS seg, SEGMENTBINDING segbind where " +
            " segbind.DAM_ID=? and segbind.SEGMENT_ID=seg.ID and segbind.DAM_TYPE=1 order by DAM_ID";

    private static final String DEALS_PRODUCT_BINDING_QUERY = "select prod.description from PRODUCTS prod, PRODUCTBINDING prodbind where " +
            " prodbind.DAM_ID=? and prodbind.PRODUCT_ID=prod.ID and prodbind.DAM_TYPE=1 order by DAM_ID";

    private static final String CURRENCY_QUERY = "select VALUE from CURRENCIES where ID=?";

    @Override
     public int performContentMigration(){
        logger.info("Inside performContentMigration");
        return performAction();
    }

    private int performAction(){
        logger.info("Inside performAction");
        int statusCode = 0;
        try{
            String action = props.getProperty("perform.action");
            logger.info("Deals Migration started with action:"+action);
            if (action.equals("delete")){
               statusCode = performContentDeletion();
            } else if (action.equals("migrate")){
            PreparedStatement ps = connection.prepareStatement(DEAL_MAIN_QUERY);
            ResultSet rs = ps.executeQuery();
            List<String> allCountryNames = Arrays.asList(allCountryWebsiteNames);
            List<DealBean> beanList = new ArrayList<DealBean>();
            GregorianCalendar gc;
            Calendar calendar = Calendar.getInstance();
            int year;
            int month;
            int day;
            while (rs.next()){
                DealBean dealBean = new DealBean();
                dealBean.setDealID(rs.getString(1));
                dealBean.setTitle(rs.getString(2));
                dealBean.setSummary(rs.getString(3));

                //java.sql.Date d = rs.getDate(4);
                //Timestamp t = new Timestamp(d.getTime());
                calendar.clear();
                calendar.setTime(rs.getDate(4));
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                gc = new GregorianCalendar(year,month,day,0,0,0);
                logger.info(" deal announced date::"+gc.getTime());

                dealBean.setDealAnnouncedDate(gc.getTime());
                dealBean.setClientName(rs.getString(5));
                dealBean.setDealAmount(rs.getString(6));


                if (rs.getString(7) != null){
                    PreparedStatement currencyPS = connection.prepareStatement(CURRENCY_QUERY);
                    currencyPS.setString(1, rs.getString(7));
                    ResultSet currencyRS = currencyPS.executeQuery();
                    if (currencyRS.next()){
                        dealBean.setCurrency(currencyRS.getString(1));
                    }
                    if (currencyPS != null && currencyRS != null){
                        currencyRS.close();
                        currencyPS.close();
                    }
                }

                dealBean.setDescription(rs.getString(8));
                dealBean.setComments(rs.getString(9));
                dealBean.setSectorName(rs.getString(10));

                logger.info("After Deals main query run..deal id::"+dealBean.getDealID());
            if (dealBean.getDealID() != null){
                PreparedStatement countryPS = connection.prepareStatement(DEALS_COUNTRY_BINDING_QUERY);
                countryPS.setInt(1,Integer.parseInt(dealBean.getDealID()));
                ResultSet countryRS = countryPS.executeQuery();
                Set<String> countryList = new TreeSet<String>();
                Set<String> segmentList = new TreeSet<String>();
                String countryName;
                while (countryRS.next()){
                    countryName = countryRS.getString(1);
                     if (allCountryNames.contains(countryName)){ //if country list consists any of the all country names, then assign ZA
                         logger.info("Deal has "+countryName+" for deal title::"+dealBean.getTitle());
                         countryList.add("ZA");
                         continue;
                     }
                    countryList.add(countryName);
                }
                //if countryList is empty, then assign SouthAfrica country
                if (countryList.size() == 0){
                    logger.info("Assigning South Africa country to a Deal");
                    countryList.add("ZA");
                }
                dealBean.setCountryList(countryList);

                if (countryPS != null && countryRS != null){
                    countryRS.close();
                    countryPS.close();
                }

                PreparedStatement segmentPS = connection.prepareStatement(DEALS_SEGMENT_BINDING_QUERY);
                segmentPS.setInt(1,Integer.parseInt(dealBean.getDealID()));
                ResultSet segmentRS = segmentPS.executeQuery();
                String segmentName;
                while (segmentRS.next()){
                    segmentName = segmentRS.getString(1);
                    if (segmentName.equals(PBB_STRING)){
                        segmentList.add("PM");
                        segmentList.add("BB");
                    }else if (segmentName.equals(CIB_STRING)){
                        segmentList.add("CIB");
                    }else if (segmentName.equals(SBG_STRING)){
                        segmentList.add("SBG");
                    }else{
                        logger.info(" not matching any segment condition::segmentname::"+segmentName);
                        segmentList.add(segmentName);
                    }
                }
                dealBean.setSegment(segmentList);
                if (segmentPS != null && segmentRS != null){
                    segmentRS.close();
                    segmentPS.close();
                }

                PreparedStatement productPS = connection.prepareStatement(DEALS_PRODUCT_BINDING_QUERY);
                productPS.setInt(1,Integer.parseInt(dealBean.getDealID()));
                ResultSet productRS = productPS.executeQuery();
                Set<String> productList = new TreeSet<String>();
                while(productRS.next()){
                    productList.add(productRS.getString(1));
                }
                if (productPS != null && productRS != null){
                    productRS.close();
                    productPS.close();
                }
                dealBean.setProductList(productList);
            }
              beanList.add(dealBean);
           }
            logger.info("Deals Bean Size::"+beanList.size());
            closeConnections();
            createDealContent(beanList);
         }
        }catch(Exception ex){
            logger.error("Exception occurred in performAction ", ex);
            ex.printStackTrace();
            statusCode = MigrateAction.ERROR_CODE;
        }finally{
            closeConnections();
        }
        logger.info("Exit performAction");
        return statusCode;
    }
     private void createDealContent(List<DealBean> list) throws ValidationException, ApplicationException, AuthorizationException, VgnIllegalArgumentException {
        logger.info("Inside createDealContent");
        LoginToVCM();
        ContentType ctType =  (ContentType)ContentType.findByName("SBG-DEALS");
        ObjectTypeRef otRef = new ObjectTypeRef(ctType);
        String contentLogicalPath = props.getProperty("deals.content.path");
         int i = 0;
        int counter = 0;
        Channel channel = (Channel)Channel.findByContentManagementId(new ManagedObjectVCMRef(props.getProperty("deal.channel.rcrd")));
        ChannelRef chRef = (channel != null)?new ChannelRef(channel.getId()):null;
        for (DealBean bean:list){
            ContentInstance ci = (ContentInstance)ctType.newInstance();
            ci.setAttributeValue("TITLE",bean.getTitle());
            logger.info("TITLE:"+bean.getTitle());
            ci.setAttributeValue("SUMMARY",bean.getSummary());
            ci.setAttributeValue("DEAL-DATE",bean.getDealAnnouncedDate());
            ci.setAttributeValue("CLIENT-NAME",bean.getClientName());
            ci.setAttributeValue("DEAL-AMOUNT",bean.getDealAmount());
            ci.setAttributeValue("CURRENCY",bean.getCurrency());
            ci.setAttributeValue("DESCRIPTION",bean.getDescription());
            ci.setAttributeValue("COMMENTS",bean.getComments());
            if (bean.getSegment() != null && bean.getSegment().size() > 0){
                ContentRelationInstance[] segments = new ContentRelationInstance[bean.getSegment().size()];
                i = 0;
                for (String segment:bean.getSegment()){
                    logger.info(" Segment::"+segment);
                    ContentRelationInstance segmentci = new ContentRelationInstance(otRef);
                    segmentci.setAttributeValue("BUSINESS-ID",segment);
                    segments[i] = segmentci;
                    i++;
                }
                ci.setRelations("SBG_RELATED_BUSINESS",segments);
            }
           if (bean.getCountryList() != null && bean.getCountryList().size() > 0){
               ContentRelationInstance[] countries = new ContentRelationInstance[bean.getCountryList().size()];
               i = 0;
               for (String country:bean.getCountryList()){
                   logger.info(" country name..."+country);
                   ContentRelationInstance cri = new ContentRelationInstance(otRef);
                   cri.setAttributeValue("COUNTRY-ID",country);
                   countries[i] = cri;
                   i++;
               }
              ci.setRelations("SBG_RELATED_COUNTRIES", countries);
           }
           if (bean.getSectorName() != null){
                logger.info(" Sector name::"+bean.getSectorName());
                   ContentRelationInstance sectorCI = new ContentRelationInstance(otRef);
                   sectorCI.setAttributeValue("SECTOR-ID",bean.getSectorName());
                   ContentRelationInstance[] sectors = new ContentRelationInstance[1];
                   sectors[0] = sectorCI;
                   ci.setRelations("SBG_RELATED_SECTORS",sectors);

           }
           if (bean.getProductList() != null && bean.getProductList().size() > 0){
               List<ContentRelationInstance> prodList = new ArrayList<ContentRelationInstance>();
               for (String product:bean.getProductList()){
                   logger.info(" Product name::"+product);
                       ContentRelationInstance productCI = new ContentRelationInstance(otRef);
                       productCI.setAttributeValue("PRODUCT-ID",product);
                       prodList.add(productCI);

               }
               if (prodList != null && prodList.size() > 0){
                   ContentRelationInstance[] products = new ContentRelationInstance[prodList.size()];
                   products = prodList.toArray(products);
                   ci.setRelations("SBG_RELATED_PRODUCTS", products);
               }
           }
            ManagedObjectStatus objStatus = ManagedObjectStatus.getInstance(ManagedObjectStatus.APPROVED_STR);
            ci.setManagedObjectStatus(objStatus);
            ci.setLogicalPath(contentLogicalPath);
            if (chRef != null){
              ci.setChannelAssociations(new ChannelRef[]{chRef});
            }
            try {
            	logger.error(ci.getAttributeValue("TITLE"));
				ci.commit();
			} catch (ApplicationException e) {
				logger.error("ApplicationException =" + e.getMessage());
				e.printStackTrace();
			} catch (AuthorizationException e) {
				logger.error("ApplicationException =" + e.getMessage());
				e.printStackTrace();
			} catch (ValidationException e) {
				logger.error("ApplicationException =" + e.getMessage());
				e.printStackTrace();
			}
            counter = counter+1;
            logger.error("Deal Created..for deal id::"+bean.getDealID()+" count:"+(counter));
            System.out.println("Deal Created..for deal id::"+bean.getDealID()+" count:"+(counter));
        }
        logger.info("Exit createDealContent");
    }

     private String getContactCIRCRD(DealBean dealBean) throws ApplicationException, ValidationException {
        logger.info("Inside getContactCIRCRD. contact name: "+dealBean.getContactName()+" contact tel number.."+dealBean.getContactTelNumber());
        String rcrd = null;
        ContentType ctType = (ContentType)ContentType.findByName("SBG-INDIVIDUAL-CONTACT");
        ContentInstanceDBQuery query = new ContentInstanceDBQuery(new ContentTypeRef(ctType.getId()));

         String[] conArr = dealBean.getContactName().split(" ");

        ContentInstanceWhereClause nameClause = new ContentInstanceWhereClause();
        nameClause.setMatchAny(true);
        if (conArr != null && conArr.length == 2){
            nameClause.checkAttribute("FIRST-NAME", StringQueryOp.EQUAL, conArr[0]);
            nameClause.checkAttribute("SURNAME", StringQueryOp.EQUAL, conArr[1]);
        }else{
            nameClause.checkAttribute("FIRST-NAME", StringQueryOp.EQUAL, dealBean.getContactName());
        }
        nameClause.checkAttribute("TELEPHONE-NUMBER1", StringQueryOp.EQUAL, dealBean.getContactTelNumber());
        query.setWhereClause(nameClause);

        IPagingList results = QueryManager.execute(query);
        if (results != null){
            logger.info(" Contacts search results size::"+results.size());
            List list = results.asList();
            Iterator it = list.iterator();
            ManagedObject mo;
           if (it.hasNext()){
                mo = (ManagedObject)it.next();
                logger.info(" mo title..."+mo.getName()+" RCRD::"+mo.getContentManagementId());
                rcrd = mo.getContentManagementId().getId();
            }
        }else{
           logger.info(" Contacts search results are empty::");
        }
       return rcrd;
    }

    public int performContentDeletion(){
        logger.info(" Inside performContentDeletion::");
        int statusCode = 0;
        try{
            LoginToVCM();
            String contentLogicalPath = props.getProperty("deals.content.path");
            Project project = Project.findProjectByPath(contentLogicalPath);
            if (project != null){
                RequestParameters requestParams = new RequestParameters(false);
                IPagingList results = project.getManagedObjectsByType(requestParams,Project.ALL_CONTENT_INSTANCES);
                deleteContent(results, 20);

            } else {
                logger.info(" Project doesn't exists with given path::"+contentLogicalPath);
            }
        }catch(Exception ex){
            logger.error("exception occurred in performContentDeletion "+ex.getMessage());
            ex.printStackTrace();
            statusCode = 3;
        }
        logger.info(" End of performContentDeletion::");
        return statusCode;
    }

}
