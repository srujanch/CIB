package za.co.standardbank.sbg.cma.migration;

import com.vignette.as.client.common.ContentRelationInstance;
import com.vignette.as.client.common.ManagedObjectStatus;
import com.vignette.as.client.common.ref.ObjectTypeRef;
import com.vignette.as.client.exception.ApplicationException;
import com.vignette.as.client.exception.AuthorizationException;
import com.vignette.as.client.exception.ValidationException;
import com.vignette.as.client.javabean.ContentInstance;
import com.vignette.as.client.javabean.ContentType;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: svenepal
 * Date: Mar 21, 2012
 * Time: 9:23:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class RefDataMigration extends MigrateAction {

    Logger logger = Logger.getLogger(this.getClass());

    @Override
    public int performContentMigration(){
        logger.info("Inside performContentMigration");
        String tableName = props.getProperty("refdata.db.table");
        String columns = props.getProperty("refdata.db.columns");
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
            String columns = props.getProperty("refdata.db.columns");
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
            createRefDataContent(list);
        }catch(Exception ex){
            logger.error("Exception occurred in RefDataMigration ", ex);
            ex.printStackTrace();
            statusCode = MigrateAction.ERROR_CODE;
        }finally{
            closeConnections();
        }
        logger.info("Exit performAction");
        return statusCode;
    }

    private void createRefDataContent(ArrayList<String> list) throws ValidationException, ApplicationException, AuthorizationException {
        logger.info("Inside createRefDataContent");
        LoginToVCM();
        ContentType ctType =  (ContentType)ContentType.findByName("SBG-REFERENCE-DATA");
        ObjectTypeRef otRef = new ObjectTypeRef(ctType);

        ContentInstance ci = (ContentInstance)ctType.newInstance();
        ci.setAttributeValue("TITLE",props.getProperty("refdata.content.title"));
        ci.setLogicalPath(props.getProperty("refdata.content.path"));

        int size = list.size();
        ContentRelationInstance[] relationArray = new ContentRelationInstance[size/2];

        for (int i=0, j=0;i<size;i+=2,j++){

            ContentRelationInstance relationInstance = new ContentRelationInstance(otRef);
            relationInstance.setAttributeValue("LABEL",list.get(i));
            relationInstance.setAttributeValue("VALUE",list.get(i+1));
            relationArray[j] = relationInstance;
        }

        ci.setRelations("SBG_REFERENCE_OPTIONS",relationArray);
        ManagedObjectStatus objStatus = ManagedObjectStatus.getInstance(ManagedObjectStatus.APPROVED_STR);
        ci.setManagedObjectStatus(objStatus);
        ci.commit();
        logger.info("Exit createRefDataContent");
    }
}
