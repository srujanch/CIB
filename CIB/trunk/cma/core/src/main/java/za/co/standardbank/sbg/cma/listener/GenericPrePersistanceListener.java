package za.co.standardbank.sbg.cma.listener;

import com.vignette.as.server.event.IAsEventListener;
import com.vignette.as.server.event.AsEvent;
import com.vignette.as.server.event.AsPrePersistenceEvent;
import com.vignette.as.client.javabean.ManagedObject;
import com.vignette.as.client.javabean.ContentInstance;
import com.vignette.as.client.javabean.AttributedObject;
import com.vignette.as.client.exception.ValidationException;
import com.vignette.as.client.exception.ApplicationException;
import com.vignette.as.ui.spf.SPFErrorCode;
import com.vignette.util.MsgObject;
import com.vignette.util.CustomerMsg;
import org.apache.log4j.Category;

import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: svenepal
 * Date: Apr 9, 2012
 * Time: 4:56:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class GenericPrePersistanceListener implements IAsEventListener {

     private static Category logger = Category.getInstance(GenericPrePersistanceListener.class);

    public void consume(AsEvent asEvent)throws com.vignette.as.client.exception.ApplicationException, com.vignette.as.client.exception.AuthorizationException, com.vignette.as.client.exception.ValidationException{

        ManagedObject managedObject = null;
        ContentInstance contentInstance;
        logger.debug("LOG_BEGIN -- consume GenericPrePersistanceListener listener::event Type::"+asEvent.getType());
        // Make sure this is the right event
        if (asEvent.getClass() == (AsPrePersistenceEvent.class)){
            AsPrePersistenceEvent prePersistenceEvent = (AsPrePersistenceEvent) asEvent;
            managedObject = prePersistenceEvent.getManagedObject();
        }else{
            return;
        }
         // Only continue for content instances
        if (!(managedObject instanceof ContentInstance)) {
            return;
        }
        contentInstance = (ContentInstance) managedObject;
        // Branch based on event type
        if (asEvent.getType().equals(AsPrePersistenceEvent.PRE_CREATE)) {
            logger.debug("-- Pre-Create Event Type");
            preCreateProcessing(contentInstance);
        } else if (asEvent.getType().equals(AsPrePersistenceEvent.PRE_UPDATE)) {
            logger.debug("-- Pre-Update Event Type");
            preUpdateProcessing(contentInstance);
        }
    }
     /**
     * Performs pre-create processing on the content instance.
     *
     * @param contentInstance Content instance being processed.
     * @throws ApplicationException If an application exception occurs.
     * @throws com.vignette.as.client.exception.ValidationException  If a validation exception occurs.
     */
    protected void preCreateProcessing(ContentInstance contentInstance)
            throws ApplicationException,
            ValidationException {
         String contentType = contentInstance.getObjectType().getData().getName();
         if (contentType.equals(EventListenerConstants.ACCOLADES_CONTENT_TYPE)) {
              //set Accolade Publishing Year
             setLatestPublicationYear(contentInstance);
             //Business Segment is mandatory
             checkBusinessSegments(contentInstance);
             //Capture Date
             //setAccoladeCaptureDate(contentInstance);
         }else if (contentType.equals(EventListenerConstants.DEALS_CONTENT_TYPE)){
             //Business Segment is mandatory
             checkBusinessSegments(contentInstance);
             //Country is mandatory
             checkCountryList(contentInstance);
             //Set Deal Amount
             setDealAmount(contentInstance);
         }else if (contentType.equals(EventListenerConstants.PRESS_RELEASE_CONTENT_TYPE)){
            //Business Segment is mandatory
             checkBusinessSegments(contentInstance);
         }
     }
     /**
     * Performs pre-update processing on the content instance.
     *
     * @param contentInstance Content instance being processed.
     * @throws ApplicationException If an application exception occurs.
     * @throws com.vignette.as.client.exception.ValidationException  If a validation exception occurs.
     */
    protected void preUpdateProcessing(ContentInstance contentInstance)
            throws ApplicationException,
            ValidationException {
         String contentType = contentInstance.getObjectType().getData().getName();
         if (contentType.equals(EventListenerConstants.ACCOLADES_CONTENT_TYPE)) {
              //set Accolade Publishing Year
             setLatestPublicationYear(contentInstance);
             //Business Segment is mandatory
             checkBusinessSegments(contentInstance);
             //Capture Date
             //setAccoladeCaptureDate(contentInstance);
         }else if (contentType.equals(EventListenerConstants.DEALS_CONTENT_TYPE)){
             //Business Segment is mandatory
             checkBusinessSegments(contentInstance);
             //Country is mandatory
             checkCountryList(contentInstance);
             //Set Deal Amount
             setDealAmount(contentInstance);
         }else if (contentType.equals(EventListenerConstants.PRESS_RELEASE_CONTENT_TYPE)){
            //Business Segment is mandatory
             checkBusinessSegments(contentInstance);
         }
     }
    protected void setLatestPublicationYear(ContentInstance contentInstance)throws ApplicationException,
            ValidationException {
        logger.debug(" -- Start checkPublicationYear");
        MsgObject message;
        AttributedObject[] attObjArray =  contentInstance.getRelations(EventListenerConstants.PUBLICATION_YEAR_RELATION_NAME);
        if (attObjArray == null || attObjArray.length == 0){
            message = CustomerMsg.getMsgObject("Please select Publication Year.");
            throw ValidationException.getOne(SPFErrorCode.VALIDATION_ERROR, this, message);
        }else{
            int latestYear = 0;
            int year;
            for (AttributedObject attObj:attObjArray){
                    try{
                        year = Integer.parseInt(attObj.getAttributeValue(EventListenerConstants.PUBLICATION_YEAR_RELATION_ID).toString());
                    }catch(Exception ex){
                        logger.error("exception occurred in setLatestPublicationYear::"+ex.getMessage());
                        year = 0;
                    }
                    latestYear = (year > latestYear)?year:latestYear;
            }
            if (latestYear > 0){
                logger.debug("Setting Latest Publication Year::"+latestYear);
                contentInstance.setAttributeValue(EventListenerConstants.LATEST_PUBLICATION_YEAR, latestYear);
            }else{
                logger.debug("Latest Publication Year not set::");
            }
        }
        logger.debug(" -- End checkPublicationYear");

    }
    protected void checkBusinessSegments(ContentInstance contentInstance)throws ApplicationException,
            ValidationException {
        logger.debug(" -- Start checkBusinessSegments");
        MsgObject message;
        AttributedObject[] attObjArray =  contentInstance.getRelations(EventListenerConstants.BUSINESS_SEGMENTS_RELATION_NAME);
        if (attObjArray == null || attObjArray.length == 0){
            message = CustomerMsg.getMsgObject("Please select Business Segments.");
            throw ValidationException.getOne(SPFErrorCode.VALIDATION_ERROR, this, message);
        }
        logger.debug(" -- End checkBusinessSegments");
    }
    protected void checkCountryList(ContentInstance contentInstance)throws ApplicationException,
            ValidationException {
        logger.debug(" -- Start checkCountryList");
        MsgObject message;
        AttributedObject[] attObjArray =  contentInstance.getRelations(EventListenerConstants.COUNTRY_RELATION_NAME);
        if (attObjArray == null || attObjArray.length == 0){
            message = CustomerMsg.getMsgObject("Please select Country List.");
            throw ValidationException.getOne(SPFErrorCode.VALIDATION_ERROR, this, message);
        }
        logger.debug(" -- End checkCountryList");
    }
    protected void setDealAmount(ContentInstance contentInstance)throws ApplicationException,
            ValidationException {
        logger.debug(" -- Start setDealAmount");
        NumberFormat formatter = new DecimalFormat("#0");
        String dealAmount = contentInstance.getAttributeValue(EventListenerConstants.DEAL_AMOUNT).toString();
        double dealValue;
        double amount = 0;
        if (dealAmount != null && (dealAmount.indexOf("K") > -1 || dealAmount.indexOf("M") > -1 || dealAmount.indexOf("B") > -1)){
            if (dealAmount.indexOf("K") > -1){
                amount = 1000;
            }else if (dealAmount.indexOf("M") > -1){
                amount = 1000000;
            }else if (dealAmount.indexOf("B") > -1){
                amount = 1000000000;
            }
            logger.debug("deal value::"+dealAmount+" conversion amount::"+amount);
            dealAmount = dealAmount.replaceAll("K","").replaceAll("M", "").replaceAll("B","");
            dealValue = Double.parseDouble(dealAmount);
            dealValue *= amount;
            logger.debug("deal value::"+formatter.format(dealValue));
            contentInstance.setAttributeValue(EventListenerConstants.DEAL_AMOUNT,formatter.format(dealValue));
        }else{
            logger.debug("Deal Amount doesn't have K or M or B");
        }
        logger.debug(" -- End setDealAmount");
    }
    protected void setAccoladeCaptureDate(ContentInstance contentInstance)throws ApplicationException,
            ValidationException {
        logger.debug(" -- Start setAccoladeCaptureDate");
        if (contentInstance.getAttributeValue(EventListenerConstants.ACCOLADE_CAPTURE_DATE) != null){
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.setTime(new Date());
            contentInstance.setAttributeValue(EventListenerConstants.ACCOLADE_CAPTURE_DATE,cal.getTime());
        }
        logger.debug(" -- End setAccoladeCaptureDate");
    }
     public int getPriority(){
        return MEDIUM_PRIORITY;
     }
}
