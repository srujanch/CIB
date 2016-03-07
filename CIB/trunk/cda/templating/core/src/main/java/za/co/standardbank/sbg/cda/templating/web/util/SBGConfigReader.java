package za.co.standardbank.sbg.cda.templating.web.util;

import com.vignette.as.config.component.AppSvcsComponent;
import com.vignette.as.config.ConfigUtil;
import com.vignette.cms.config.component.GenericResourceComponent;
import com.vignette.config.client.common.ConfigException;

/**
 * Created by IntelliJ IDEA. User: svenepal Date: Apr 19, 2012 Time: 11:42:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class SBGConfigReader {

	private static final String SBG_CONFIG_PROPERTIES_NAME = "SBG_PROPERTIES";

	/**
	 * This method will read configconsole properties based on node name,
	 * property name and variable name
	 * 
	 * @param nodeName
	 *            like "Generic"
	 * @param propertyName
	 *            like "SBG_PROPERTIES"
	 * @param variableName
	 *            like "CIB_ACCOLADE_DATE_YEARS"
	 * @return
	 * @throws ConfigException
	 */
	public static String getConfigValue(String nodeName, String propertyName, String variableName) throws ConfigException {
		AppSvcsComponent asComp = ConfigUtil.getASComponent();
		GenericResourceComponent sbgConfigComponent = (GenericResourceComponent) asComp.getDAComponent().getResource(nodeName, propertyName);
		if (sbgConfigComponent != null) {
			return sbgConfigComponent.getVariableValue(variableName);
		}
		return null;
	}

	/**
	 * This method will read Generic configconsole properties for
	 * "SBG_PROPERTIES"
	 * 
	 * @param variableName
	 *            like "CIB_ACCOLADE_DATE_YEARS"
	 * @return
	 * @throws ConfigException
	 */
	public static String getSBGPropertiesConfigValueByName(String variableName) throws ConfigException {
		AppSvcsComponent asComp = ConfigUtil.getASComponent();
		GenericResourceComponent sbgConfigComponent = (GenericResourceComponent) asComp.getDAComponent().getResource(GenericResourceComponent.NODE_NAME, SBG_CONFIG_PROPERTIES_NAME);
		if (sbgConfigComponent != null) {
			return sbgConfigComponent.getVariableValue(variableName);
		}
		return null;
	}
}
