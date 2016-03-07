package za.co.standardbank.sbg.cma.migration;

import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import com.vignette.as.client.exception.ValidationException;
import com.vignette.as.client.exception.ApplicationException;
import com.vignette.as.client.exception.AuthorizationException;
import com.vignette.as.client.javabean.*;
import com.vignette.as.client.common.ContentRelationInstance;
import com.vignette.as.client.common.ManagedObjectStatus;
import com.vignette.as.client.common.RequestParameters;
import com.vignette.as.client.common.ref.ObjectTypeRef;
import com.vignette.as.client.common.ref.ChannelRef;
import com.vignette.as.client.common.ref.ManagedObjectVCMRef;
import com.vignette.util.VgnIllegalArgumentException;

/**
 * Created by IntelliJ IDEA.
 * User: svenepal
 * Date: Mar 21, 2012
 * Time: 5:24:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccoladeDataMigration extends MigrateAction {

    Logger logger = Logger.getLogger(this.getClass());

    private static final String ACCOLADE_MAIN_QUERY = "select acc.ACCOLADE_ID ACCOLADE_ID, acc.DESCRIPTION DESCRIPTION, pub.value PUBLICATION, acc.YEAR YEAR, " +
            " acc.FULL_DESCRIPTION FULL_DESCRIPTION, sec.value SECTOR " +
            " from ACCOLADES acc, PUBLICATIONS pub, SECTORS sec " +
            " where acc.publication=pub.id and acc.sector=sec.id and acc.description=? and acc.publication=? and sec.id=? and status='ACTIVE' and publishstatus='Published' order by acc.Year ";

    private static String ACCOLADE_COUNTRY_BINDING_QUERY = "select con.VALUE from COUNTRIES con, COUNTRYBINDING conbind where " +
            " conbind.DAM_ID in (#) and conbind.COUNTRY_ID=con.ID and conbind.DAM_TYPE=2 order by DAM_ID";

    private static String ACCOLADE_SEGMENT_BINDING_QUERY = "select seg.description from SEGMENTS seg, SEGMENTBINDING segbind where " +
            " segbind.DAM_ID in (#) and segbind.SEGMENT_ID=seg.ID and segbind.DAM_TYPE=2 order by DAM_ID";

    private static String ACCOLADE_PRODUCT_BINDING_QUERY = "select prod.description from PRODUCTS prod, PRODUCTBINDING prodbind where " +
            " prodbind.DAM_ID in (#) and prodbind.PRODUCT_ID=prod.ID and prodbind.DAM_TYPE=2 order by DAM_ID";

    private static final String ACCOLADE_QUERY = "select description, publication, sector, count(publication) count from accolades where status='ACTIVE' and PublishStatus='Published' group by description,publication,sector order by description";
    //ROW_NUMBER() over (order by description) row_number,

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
            if (action.equals("delete")){
               statusCode = performContentDeletion();
            } else if (action.equals("migrate")){
            List<AccoladeBean> beanList = new ArrayList<AccoladeBean>();
            String description;
            int publication;
            //Calendar cal = Calendar.getInstance();
            GregorianCalendar gc;
            List<String> allCountryList = Arrays.asList(allCountryWebSites);
            List<String> allAfricaList = Arrays.asList(allAfricaWebSites);
            List<String> allGroupList = Arrays.asList(allCountryWebSites);
            List<String> allSegments = Arrays.asList(allSegmentsList);
            String countryName;
            int count;
            int sector;
            PreparedStatement ps = connection.prepareStatement(ACCOLADE_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                //rowNumber = rs.getInt(1);
                description = rs.getString(1);
                publication = rs.getInt(2);
                sector = rs.getInt(3);
                count = rs.getInt(4);
                logger.info("Accolade Description::"+description+" Accolade publication::"+publication+" Sector "+sector+" count::"+count);
//                if (rowNumber < 180){
//                    continue;
//                }

                PreparedStatement mainQueryPs = connection.prepareStatement(ACCOLADE_MAIN_QUERY);
                mainQueryPs.setString(1, description);
                mainQueryPs.setInt(2, publication);
                mainQueryPs.setInt(3, sector);
                ResultSet mainQueryRs = mainQueryPs.executeQuery();
                List<Integer> publicationYear  = new ArrayList<Integer>();
                List<String> accoladeIDsList = new ArrayList<String>();
                AccoladeBean accBean = new AccoladeBean();

                while (mainQueryRs.next()){
                    if (accBean.getAccoladeID() == null){
                        accBean.setAccoladeID(mainQueryRs.getString(1));
                    }
                    accoladeIDsList.add(mainQueryRs.getString(1));
                    if (accBean.getDescription() == null){
                        accBean.setDescription(mainQueryRs.getString(2));
                    }
                    if (accBean.getPublication() == null){
                        accBean.setPublication(mainQueryRs.getString(3));
                    }
                    publicationYear.add(mainQueryRs.getInt(4));
                    accBean.setLatestPublicationYear(mainQueryRs.getInt(4));
                    //cal.clear();
                    //cal.set(accBean.getLatestPublicationYear(),11,31);
                    gc = new GregorianCalendar(accBean.getLatestPublicationYear(),11,31,0,0,0);
                    accBean.setCaptureDate(gc.getTime());
                    if (accBean.getFullDescription() == null){
                        accBean.setFullDescription(mainQueryRs.getString(5));
                    }
                    if (accBean.getSector() == null){
                        accBean.setSector(mainQueryRs.getString(6));
                    }
                }
                if (mainQueryPs != null && mainQueryRs != null){
                    mainQueryRs.close();
                    mainQueryPs.close();
                }
                accBean.setPublicationYear(publicationYear);
                accBean.setAccoladeIDList(accoladeIDsList);
                logger.info("After Accolades main query run..accolade ids list size::"+accBean.getAccoladeIDList().size()+" latest publication year::"+accBean.getLatestPublicationYear());
            if (accBean.getAccoladeIDList().size() > 0){
                String accoladeIds = "";
                for (String accoladeID:accBean.getAccoladeIDList()){
                    accoladeIds += accoladeID+",";
                }
                if (accoladeIds.endsWith(",")){
                    accoladeIds = accoladeIds.substring(0,accoladeIds.length()-1);
                }
                logger.info("Accolade Ids::"+accoladeIds);
                logger.info("Sector::"+accBean.getSector());
                PreparedStatement countryPS = connection.prepareStatement(ACCOLADE_COUNTRY_BINDING_QUERY.replaceAll("#",accoladeIds));
                //countryPS.setInt(1,accoladeIds);
                ResultSet countryRS = countryPS.executeQuery();
                Set<String> countryList = new TreeSet<String>();
                Set<String> segmentList = new TreeSet<String>();
                while (countryRS.next()){
                    countryName = countryRS.getString(1);
                    if (countryName.equals(MigrateAction.ALL_COUNTRY_STRING)){
                         countryList.addAll(allCountryList);
                    }else if (countryName.equals(MigrateAction.ALL_AFRICA_STRING)){
                         countryList.addAll(allAfricaList);
                    }else if (countryName.equals(MigrateAction.ALL_GROUP_STRING)){
                         countryList.addAll(allGroupList);
                         //segmentList.addAll(allSegments);
                    }else {
                        logger.info("Inside Accolade..country name::"+countryName);
                        countryList.add(countryName);
                    }
                }
                accBean.setCountryList(countryList);

                if (countryPS != null && countryRS != null){
                    countryRS.close();
                    countryPS.close();
                }
                logger.info("country list::"+listElements(countryList));
                PreparedStatement segmentPS = connection.prepareStatement(ACCOLADE_SEGMENT_BINDING_QUERY.replaceAll("#",accoladeIds));
                //segmentPS.setString(1,accoladeIds);
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
                            logger.info("Inside Accolade..segment name::"+segmentName);
                            segmentList.add(segmentName);
                       }
                }
                accBean.setSegment(segmentList);
                if (segmentPS != null && segmentRS != null){
                    segmentRS.close();
                    segmentPS.close();
                }
                logger.info("Segment list::"+listElements(segmentList));
                PreparedStatement productPS = connection.prepareStatement(ACCOLADE_PRODUCT_BINDING_QUERY.replaceAll("#",accoladeIds));
                //productPS.setInt(1,Integer.parseInt(accBean.getAccoladeID()));
                ResultSet productRS = productPS.executeQuery();
                Set<String> productList = new TreeSet<String>();
                while(productRS.next()){
                    productList.add(productRS.getString(1));
                }
                if (productPS != null && productRS != null){
                    productRS.close();
                    productPS.close();
                }
                accBean.setProductList(productList);
                logger.info("productList list::"+listElements(productList));
            }
              beanList.add(accBean);
           }
            logger.info("Accolades Bean Size::"+beanList.size());
            closeConnections();
            createAccoladeContent(beanList);
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

    private void createAccoladeContent(List<AccoladeBean> list) throws ValidationException, ApplicationException, AuthorizationException, VgnIllegalArgumentException {
        logger.info("Inside createAccoladeContent");
        LoginToVCM();
        ContentType ctType =  (ContentType)ContentType.findByName("SBG-ACCOLADES");
        ObjectTypeRef otRef = new ObjectTypeRef(ctType);
        String contentLogicalPath = props.getProperty("accolade.content.path");
        int i = 0;
        int counter = 0;
        Channel channel = (Channel)Channel.findByContentManagementId(new ManagedObjectVCMRef(props.getProperty("accolade.channel.rcrd")));
        ChannelRef chRef = (channel != null)?new ChannelRef(channel.getId()):null;
        for (AccoladeBean bean:list){
            ContentInstance ci = (ContentInstance)ctType.newInstance();
            ci.setAttributeValue("TITLE",bean.getDescription());
            logger.info("TITLE:"+bean.getDescription());
            ci.setAttributeValue("PUBLICATION",bean.getPublication());
            logger.info("PUBLICATION:"+bean.getPublication());
            ci.setAttributeValue("LATEST-YEAR",bean.getLatestPublicationYear());
            logger.info("LATEST-PUBLICATION-YEAR:"+bean.getLatestPublicationYear());
            ci.setAttributeValue("CAPTURE-DATE",bean.getCaptureDate());
            logger.info("CAPTURE-DATE:"+bean.getCaptureDate());
            if (bean.getFullDescription() != null && bean.getFullDescription().trim().length() > 0){
                ci.setAttributeValue("FULL-DESCRIPTION",bean.getFullDescription());
                logger.info("FULL-DESCRIPTION:"+bean.getFullDescription());
            }else{
                logger.info("FULL-DESCRIPTION is null.");
            }
            if (bean.getPublicationYear() != null && bean.getPublicationYear().size() > 0){
                ContentRelationInstance[] pubYears = new ContentRelationInstance[bean.getPublicationYear().size()];
                for (int j =bean.getPublicationYear().size(), k = 0;j>0;j--,k++){
                    logger.info("bean.getPublicationYear().get(j-1).toString()::"+bean.getPublicationYear().get(j-1).toString());
                    ContentRelationInstance cri = new ContentRelationInstance(otRef);
                    cri.setAttributeValue("PUBLICATION-ID",bean.getPublicationYear().get(j-1).toString());
                    pubYears[k] = cri;
                }
                ci.setRelations("SBG_RELATED_PUBLICATION_YEAR",pubYears);
            }
            if (bean.getSegment() != null && bean.getSegment().size() > 0){
                ContentRelationInstance[] segments = new ContentRelationInstance[bean.getSegment().size()];
                i = 0;
                for (String segment:bean.getSegment()){
                    logger.info("segment name::"+segment);
                    ContentRelationInstance segmentCI = new ContentRelationInstance(otRef);
                    segmentCI.setAttributeValue("BUSINESS-ID",segment);
                    segments[i] = segmentCI;
                    i++;
                }
                 ci.setRelations("SBG_RELATED_BUSINESS",segments);
            }
           if (bean.getCountryList() != null && bean.getCountryList().size() > 0){
               ContentRelationInstance[] countries = new ContentRelationInstance[bean.getCountryList().size()];
               i = 0;
               for (String country:bean.getCountryList()){
                   logger.info("country name::"+country);
                   ContentRelationInstance cri = new ContentRelationInstance(otRef);
                   cri.setAttributeValue("COUNTRY-ID",country);
                   countries[i] = cri;
                   i++;
               }
              ci.setRelations("SBG_RELATED_COUNTRIES", countries);
           }
           if (bean.getSector() != null){
                   ContentRelationInstance sectorCI = new ContentRelationInstance(otRef);
                   sectorCI.setAttributeValue("SECTOR-ID",bean.getSector());
                   ContentRelationInstance[] sectors = new ContentRelationInstance[1];
                   sectors[0] = sectorCI;
                   ci.setRelations("SBG_RELATED_SECTORS",sectors);
           }
           if (bean.getProductList() != null && bean.getProductList().size() > 0){
               ContentRelationInstance[] products = new ContentRelationInstance[bean.getProductList().size()];
               i = 0;
               for (String product:bean.getProductList()){
                       logger.info("product name::"+product);
                       ContentRelationInstance productCI = new ContentRelationInstance(otRef);
                       productCI.setAttributeValue("PRODUCT-ID",product);
                       products[i] = productCI;
                      i++;
               }
               ci.setRelations("SBG_RELATED_PRODUCTS", products);
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
            logger.error("Accolade Created..count:"+(counter));
            System.out.println("Accolade Created..count:"+(counter));
        }
        logger.info("Exit createAccoladeContent");
    }

    public int performContentDeletion(){
        logger.info(" Inside performContentDeletion::");
        int statusCode = 0;
        try{
            LoginToVCM();
            String contentLogicalPath = props.getProperty("accolade.content.path");
            Project project = Project.findProjectByPath(contentLogicalPath);
            if (project != null){
                RequestParameters requestParams = new RequestParameters(false);
                IPagingList results = project.getManagedObjectsByType(requestParams,Project.ALL_CONTENT_INSTANCES);
                deleteContent(results,20);
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
    private String listElements(Set<String> set){
        String listElements = "";
        for (String element:set){
            listElements += element+",";
        }
        return listElements;
    }
}
