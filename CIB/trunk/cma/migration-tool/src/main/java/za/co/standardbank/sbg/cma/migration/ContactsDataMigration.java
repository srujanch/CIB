package za.co.standardbank.sbg.cma.migration;

import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.vignette.as.client.exception.ValidationException;
import com.vignette.as.client.exception.ApplicationException;
import com.vignette.as.client.exception.AuthorizationException;
import com.vignette.as.client.javabean.ContentType;
import com.vignette.as.client.javabean.ContentInstance;
import com.vignette.as.client.common.ManagedObjectStatus;

/**
 * Created by IntelliJ IDEA.
 * User: svenepal
 * Date: Mar 26, 2012
 * Time: 11:41:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class ContactsDataMigration extends MigrateAction {

     Logger logger = Logger.getLogger(this.getClass());

    @Override
     public int performContentMigration(){
        logger.info("Inside performContentMigration");
        String tableName = props.getProperty("contacts.db.table");
        String columns = props.getProperty("contacts.db.columns");
        String sqlQuery = prepareSQL(tableName,columns);
        logger.info("SQL Query::"+sqlQuery);
        return performAction(sqlQuery);
    }

    private String prepareSQL(String tableName, String columns){
        return "select DISTINCT "+columns+" from "+tableName;
    }

    private int performAction(String sqlQuery){
        logger.info("Inside performAction");
        int statusCode = 0;
        try{
            String columns = props.getProperty("contacts.db.columns");
            String[] arr = columns.split(",");
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();
            ArrayList<String> list = new ArrayList<String>();
            while (rs.next()){
                 for(String column:arr){
                    list.add(rs.getString(column));
                }
            }
            closeConnections();
            createContacts(list);
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

     private void createContacts(ArrayList<String> list) throws ValidationException, ApplicationException, AuthorizationException {
        logger.info("Inside createContacts");
        LoginToVCM();
        ContentType ctType =  (ContentType)ContentType.findByName("SBG-INDIVIDUAL-CONTACT");
        String contentLogicalPath = props.getProperty("contacts.content.path");
        String emailID = props.getProperty("contacts.email.address");
        int size = list.size();
        String contactName;
        String[] conArray;
       for (int i=0;i<size;i+=2){
            contactName = list.get(i);
            conArray = contactName.split(" ");
            ContentInstance ci = (ContentInstance)ctType.newInstance();
            if (conArray != null && conArray.length == 2){
                ci.setAttributeValue("FIRST-NAME",conArray[0]);
                ci.setAttributeValue("SURNAME",conArray[1]);
            }else{
                ci.setAttributeValue("FIRST-NAME",contactName);
                ci.setAttributeValue("SURNAME",contactName);
            }
            ci.setAttributeValue("TELEPHONE-NUMBER1",list.get(i+1));
            ci.setAttributeValue("EMAILID",emailID);
            ManagedObjectStatus objStatus = ManagedObjectStatus.getInstance(ManagedObjectStatus.APPROVED_STR);
            ci.setManagedObjectStatus(objStatus);
            ci.setLogicalPath(contentLogicalPath);
            ci.commit();
        }
        logger.info("Exit createContacts");
    }
}
