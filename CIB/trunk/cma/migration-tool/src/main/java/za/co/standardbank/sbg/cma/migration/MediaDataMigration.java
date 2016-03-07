package za.co.standardbank.sbg.cma.migration;

import org.apache.log4j.Logger;
import com.vignette.as.client.common.RequestParameters;
import com.vignette.as.client.common.ContentRelationInstance;
import com.vignette.as.client.common.ManagedObjectStatus;
import com.vignette.as.client.common.ref.ObjectTypeRef;
import com.vignette.as.client.common.ref.ChannelRef;
import com.vignette.as.client.common.ref.ManagedObjectVCMRef;
import com.vignette.as.client.javabean.*;
import com.vignette.as.client.exception.ApplicationException;
import com.vignette.as.client.exception.ValidationException;
import com.vignette.as.client.exception.AuthorizationException;
import com.vignette.util.VgnIllegalArgumentException;

import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: svenepal
 * Date: Apr 9, 2012
 * Time: 3:38:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class MediaDataMigration extends MigrateAction{
    Logger logger = Logger.getLogger(this.getClass());

    private static final String MEDIA_MAIN_QUERY = "select MEDIA_ID, TITLE, DESCRIPTION, FULL_DATE, ARTICLE from Media " +
            " where status='ACTIVE' and publishstatus='Authorised' and MEDIA_ID >274 order by MEDIA_ID ";

    private static final String MEDIA_COUNTRY_BINDING_QUERY = "select con.VALUE from COUNTRIES con, COUNTRYBINDING conbind where " +
            " conbind.DAM_ID=? and conbind.COUNTRY_ID=con.ID and conbind.DAM_TYPE=3 order by DAM_ID";

    private static final String MEDIA_SEGMENT_BINDING_QUERY = "select seg.description from SEGMENTS seg, SEGMENTBINDING segbind where " +
            " segbind.DAM_ID=? and segbind.SEGMENT_ID=seg.ID and segbind.DAM_TYPE=3 order by DAM_ID";

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
            logger.info("Media Migration started with action:"+action);
            if (action.equals("delete")){
                statusCode = performContentDeletion();
            } else if (action.equals("migrate")){
                Calendar calendar = Calendar.getInstance();
                GregorianCalendar gc;
                List<MediaBean> beanList = new ArrayList<MediaBean>();
                String countryName;
                List<String> allCountryList = Arrays.asList(allCountryWebSites);
                List<String> allAfricaList = Arrays.asList(allAfricaWebSites);
                List<String> allGroupList = Arrays.asList(allCountryWebSites);
                List<String> allSegments = Arrays.asList(allSegmentsList);
                int year;
                int month;
                int day;
                PreparedStatement ps = connection.prepareStatement(MEDIA_MAIN_QUERY);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    MediaBean mediaBean = new MediaBean();
                    mediaBean.setMediaId(rs.getString(1));
                    mediaBean.setTitle(rs.getString(2));
                    mediaBean.setDescription(rs.getString(3));
                    calendar.clear();
                    calendar.setTime(rs.getDate(4));
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    gc = new GregorianCalendar(year,month,day,0,0,0);
                    logger.info("media live date::"+gc.getTime());
                    mediaBean.setFullDate(gc.getTime());
                    mediaBean.setArticle(rs.getString(5));

                    logger.info("After Media main query run..Media id::"+mediaBean.getMediaId()+" media title::"+mediaBean.getTitle());
                    if (mediaBean.getMediaId() != null){
                        PreparedStatement countryPS = connection.prepareStatement(MEDIA_COUNTRY_BINDING_QUERY);
                        countryPS.setInt(1,Integer.parseInt(mediaBean.getMediaId()));
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
                                 segmentList.addAll(allSegments);
                            }else {
                                logger.info("Inside MediaData Migration::country name::"+countryName);
                                countryList.add(countryName);
                            }
                        }
                        mediaBean.setCountryList(countryList);
                        logger.info("country list::"+listElements(countryList));
                        if (countryPS != null && countryRS != null){
                            countryRS.close();
                            countryPS.close();
                        }

                        PreparedStatement segmentPS = connection.prepareStatement(MEDIA_SEGMENT_BINDING_QUERY);
                        segmentPS.setInt(1,Integer.parseInt(mediaBean.getMediaId()));
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
                                 logger.info("Inside MediaData Migration::segment name::"+segmentName);
                                segmentList.add(segmentName);
                            }
                        }
                        mediaBean.setSegmentsList(segmentList);
                        logger.info("Segment list::"+listElements(segmentList));
                        if (segmentPS != null && segmentRS != null){
                            segmentRS.close();
                            segmentPS.close();
                        }
                    }
                    beanList.add(mediaBean);
                }
                closeConnections();
                logger.info("Media Bean Size::"+beanList.size());
                createMediaContent(beanList);
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

    private void createMediaContent(List<MediaBean> beanList) throws ValidationException, ApplicationException, AuthorizationException, VgnIllegalArgumentException {
        logger.info("Inside createMediaContent");
        LoginToVCM();
        ContentType ctType =  (ContentType) ContentType.findByName("SBG-PRESS-RELEASE");
        ObjectTypeRef otRef = new ObjectTypeRef(ctType);
        String contentLogicalPath = props.getProperty("media.content.path");
        int i = 0;
        int counter = 0;
        Channel channel = (Channel)Channel.findByContentManagementId(new ManagedObjectVCMRef(props.getProperty("media.channel.rcrd")));
        ChannelRef chRef = (channel != null)?new ChannelRef(channel.getId()):null;
        for (MediaBean mediaBean:beanList){
            ContentInstance ci = (ContentInstance)ctType.newInstance();
            logger.info("Media title::"+mediaBean.getTitle());
            ci.setAttributeValue("TITLE",mediaBean.getTitle());
            ci.setAttributeValue("INTRODUCTION",mediaBean.getDescription());
            ci.setAttributeValue("LIVE-DATE",mediaBean.getFullDate());
            ci.setAttributeValue("DESCRIPTION",mediaBean.getArticle());

            if (mediaBean.getCountryList() != null && mediaBean.getCountryList().size() > 0){
               ContentRelationInstance[] countries = new ContentRelationInstance[mediaBean.getCountryList().size()];
               i = 0;
               for (String country:mediaBean.getCountryList()){
                   logger.info(" country name..."+country);
                   ContentRelationInstance cri = new ContentRelationInstance(otRef);
                   cri.setAttributeValue("COUNTRY-ID",country);
                   countries[i] = cri;
                   i++;
               }
                ci.setRelations("SBG_RELATED_COUNTRIES", countries);
            }

            if (mediaBean.getSegmentsList() != null && mediaBean.getSegmentsList().size() > 0){
                ContentRelationInstance[] segments = new ContentRelationInstance[mediaBean.getSegmentsList().size()];
                i = 0;
                for (String segment:mediaBean.getSegmentsList()){
                    logger.info(" Segment::"+segment);
                    ContentRelationInstance segmentci = new ContentRelationInstance(otRef);
                    segmentci.setAttributeValue("BUSINESS-ID",segment);
                    segments[i] = segmentci;
                    i++;
                }
                ci.setRelations("SBG_RELATED_BUSINESS",segments);
            }
            ManagedObjectStatus objStatus = ManagedObjectStatus.getInstance(ManagedObjectStatus.APPROVED_STR);
            ci.setManagedObjectStatus(objStatus);
            ci.setLogicalPath(contentLogicalPath);
            if (chRef != null){
              ci.setChannelAssociations(new ChannelRef[]{chRef});
            }
            ci.commit();
            counter = counter+1;
            logger.info("Media Item Created..for media id::"+mediaBean.getMediaId()+" count:"+(counter));
            System.out.println("Media Item Created..for media id::"+mediaBean.getMediaId()+" count:"+(counter));
        }
    }

    public int performContentDeletion(){
        logger.info(" Inside performContentDeletion::");
        int statusCode = 0;
        try{
            LoginToVCM();
            String contentLogicalPath = props.getProperty("media.content.path");
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
     private String listElements(Set<String> set){
        String listElements = "";
        for (String element:set){
            listElements += element+",";
        }
        return listElements;
    }
}
