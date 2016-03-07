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
 * Time: 3:31:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductDataMigration extends MigrateAction {

     Logger logger = Logger.getLogger(this.getClass());

    @Override
     public int performContentMigration(){
        logger.info("Inside performContentMigration");
        String tableName = props.getProperty("product.db.table");
        String columns = props.getProperty("product.db.columns");
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
            String column = props.getProperty("product.db.columns");

            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();
            ArrayList<String> list = new ArrayList<String>();
            while (rs.next()){
                list.add(rs.getString(column));
            }
            closeConnections();
            createProductDataContent(list);
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

     private void createProductDataContent(ArrayList<String> list) throws ValidationException, ApplicationException, AuthorizationException {
        logger.info("Inside createProductDataContent");
        LoginToVCM();
        ContentType ctType =  (ContentType)ContentType.findByName("SBG-PRODUCT");
        String contentLogicalPath = props.getProperty("product.content.path");
        for (String title:list){
            ContentInstance ci = (ContentInstance)ctType.newInstance();
            ci.setAttributeValue("TITLE",title);
            ci.setLogicalPath(contentLogicalPath);
            ManagedObjectStatus objStatus = ManagedObjectStatus.getInstance(ManagedObjectStatus.APPROVED_STR);
            ci.setManagedObjectStatus(objStatus);
            ci.commit();
        }
        logger.info("Exit createProductDataContent");
    }
}
