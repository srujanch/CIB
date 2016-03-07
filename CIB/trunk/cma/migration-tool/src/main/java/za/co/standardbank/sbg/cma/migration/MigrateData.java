package za.co.standardbank.sbg.cma.migration;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: svenepal
 * Date: Mar 21, 2012
 * Time: 10:12:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class MigrateData {

    private static final String MIGRATE_REFDATA_CLASS = "za.co.standardbank.sbg.cma.migration.RefDataMigration";
    private static final String MIGRATE_SECTORDATA_CLASS = "za.co.standardbank.sbg.cma.migration.SectorDataMigration";
    private static final String MIGRATE_PRODUCTDATA_CLASS = "za.co.standardbank.sbg.cma.migration.ProductDataMigration";
    private static final String MIGRATE_ACCOLADE_CLASS = "za.co.standardbank.sbg.cma.migration.AccoladeDataMigration";
    private static final String MIGRATE_CONTACTS_CLASS = "za.co.standardbank.sbg.cma.migration.ContactsDataMigration";
    private static final String MIGRATE_DEALS_CLASS = "za.co.standardbank.sbg.cma.migration.DealsDataMigration";
    private static final String MIGRATE_MEDIA_CLASS = "za.co.standardbank.sbg.cma.migration.MediaDataMigration";
    static Logger logger = Logger.getLogger(MigrateData.class);
    public static void main(String[] args){
        String action = null;
        if (args != null && args.length > 1){
            if ("-a".equalsIgnoreCase(args[0])){
                action = args[1];
                if (!action.equalsIgnoreCase("refdata") && !action.equalsIgnoreCase("sector") && !action.equalsIgnoreCase("product")
                        && !action.equalsIgnoreCase("accolades") && !action.equalsIgnoreCase("contacts") && !action.equalsIgnoreCase("deals")
                        && !action.equalsIgnoreCase("media")){
                    action = null;
                }
            }
        }else{
            System.out.println("Argments are null.."+args);
        }
        if (action == null){
            System.out.println("Usage: ./content_migrate.sh -a RefData");
            System.out.println("Usage: ./content_migrate.sh -a Sector");
            System.out.println("Usage: ./content_migrate.sh -a Product");
            System.out.println("Usage: ./content_migrate.sh -a Accolades");
            System.out.println("Usage: ./content_migrate.sh -a Contacts");
            System.out.println("Usage: ./content_migrate.sh -a Deals");
            System.out.println("Usage: ./content_migrate.sh -a Media");
            System.exit(MigrateAction.ERROR_CODE);
        }else{
            logger.info("Content Migration Started for "+action);
            MigrateAction migrateAction = null;
            if (action.equalsIgnoreCase("refdata")){
                migrateAction = (MigrateAction)MigrateActionFactory.newInstance(MIGRATE_REFDATA_CLASS);
            }else if (action.equalsIgnoreCase("sector")){
                migrateAction = (MigrateAction)MigrateActionFactory.newInstance(MIGRATE_SECTORDATA_CLASS);
            }else if (action.equalsIgnoreCase("product")){
                migrateAction = (MigrateAction)MigrateActionFactory.newInstance(MIGRATE_PRODUCTDATA_CLASS);
            }else if (action.equalsIgnoreCase("accolades")){
                migrateAction = (MigrateAction)MigrateActionFactory.newInstance(MIGRATE_ACCOLADE_CLASS);
            }else if (action.equalsIgnoreCase("contacts")){
                migrateAction = (MigrateAction)MigrateActionFactory.newInstance(MIGRATE_CONTACTS_CLASS);
            }else if (action.equalsIgnoreCase("deals")){
                migrateAction = (MigrateAction)MigrateActionFactory.newInstance(MIGRATE_DEALS_CLASS);
            }else if (action.equalsIgnoreCase("media")){
                migrateAction = (MigrateAction)MigrateActionFactory.newInstance(MIGRATE_MEDIA_CLASS);
            }
            long sTime = System.currentTimeMillis();
            migrateAction.init();
            int status = migrateAction.performContentMigration();
            logger.info("Content Migration Ended for "+action+" with status code "+status+" total time took::"+((System.currentTimeMillis()-sTime)/1000)+" seconds");

            System.exit(status);
        }
    }
}
