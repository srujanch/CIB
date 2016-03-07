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
 * Date: Mar 21, 2012
 * Time: 2:51:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class SectorDataMigration extends MigrateAction {

    Logger logger = Logger.getLogger(this.getClass());

    @Override
     public int performContentMigration(){
        logger.info("Inside performContentMigration");
        String tableName = props.getProperty("sector.db.table");
        String columns = props.getProperty("sector.db.columns");
        String sqlQuery = prepareSQL(tableName,columns);
        return performAction(sqlQuery);
    }

     private String prepareSQL(String tableName, String columns){
        return "select "+columns+" from "+tableName;
    }

    private int performAction(String sqlQuery){
        logger.info("Inside performAction");
        int statusCode = 0;
        try{
            String column = props.getProperty("sector.db.columns");

            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();
            ArrayList<String> list = new ArrayList<String>();
            while (rs.next()){
                list.add(rs.getString(column));
            }
            closeConnections();
            createSectorDataContent(list);
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

    private void createSectorDataContent(ArrayList<String> list) throws ValidationException, ApplicationException, AuthorizationException {
        logger.info("Inside createSectorDataContent");
        LoginToVCM();
        ContentType ctType =  (ContentType)ContentType.findByName("SBG-SECTOR");
        String contentLogicalPath = props.getProperty("sector.content.path");
        for (String title:list){
            ContentInstance ci = (ContentInstance)ctType.newInstance();
            ci.setAttributeValue("TITLE",title);
            ManagedObjectStatus objStatus = ManagedObjectStatus.getInstance(ManagedObjectStatus.APPROVED_STR);
            ci.setManagedObjectStatus(objStatus);
            ci.setLogicalPath(contentLogicalPath);
            ci.commit();
        }
        logger.info("Exit createSectorDataContent");
    }
}
