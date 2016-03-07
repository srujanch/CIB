package za.co.standardbank.sbg.cma.migration;

/**
 * Created by IntelliJ IDEA.
 * User: svenepal
 * Date: Mar 21, 2012
 * Time: 9:04:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class MigrateActionFactory {

    public static Object newInstance(String classzz){
        Class classObj;
        Object obj = null;
        try{
            classObj = Class.forName(classzz);
            obj = classObj.newInstance();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return obj;
    }
}
