package za.co.standardbank.sbg.cda.templating.taglib;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.tag.common.core.OutSupport;

import za.co.standardbank.sbg.cda.templating.web.util.DPMConstants;
import za.co.standardbank.sbg.cda.templating.web.util.DisplayUtil;

import com.vignette.as.client.common.AttributeData;
import com.vignette.as.client.common.DataType;
import com.vignette.as.client.common.ref.ManagedObjectVCMRef;
import com.vignette.as.client.javabean.ContentInstance;
import com.vignette.as.client.javabean.ContentType;
import com.vignette.as.client.javabean.ManagedObject;
import com.vignette.as.ui.cm.descriptor.DescriptorUtils;
import com.vignette.ext.templating.util.ContentUtil;

/**
 * Retrieves the Attribute value of the given descriptor content item and places
 * the value in given scope.
 * 
 * @author Venkat
 */
public class GetObjectDescriptorPropertyTag extends OutSupport {

	private static final long serialVersionUID = 1L;

	private String varName;
	private String channelId;
	private String attributeName;
	private int varScope = PageContext.PAGE_SCOPE;

	private static final Logger logger = Logger.getLogger(GetObjectDescriptorPropertyTag.class);

	public final int doStartTag() throws JspException {

		if (logger.isDebugEnabled()) {
			logger.debug("doStartTag() of GetObjectDescriptorPropertyTag Begin.");
		}

		try {
			Map<String, ContentInstance> channelDescriptorsMap = getChannelDescriptorsMap();
			ContentInstance descriptorCI = channelDescriptorsMap.get(channelId);
			if (descriptorCI == null) {
				ContentType ct = DisplayUtil.getAttDescriptorContentType();
				ManagedObject mo = ContentUtil.getManagedObject(new ManagedObjectVCMRef(channelId));
				if (logger.isDebugEnabled())
					logger.debug("Atttribute Descriptor for given channel('" + mo.getName() + "') is not found in the map.So loading the CI.");
				if (ct != null && mo != null) {
					descriptorCI = DescriptorUtils.loadContentInstance(mo, DPMConstants.CHANNEL_DESCRIPTOR_ATT_NAME, false, ct);
					if (descriptorCI == null) {
						if (logger.isDebugEnabled())
							logger.debug("Atttribute Descriptor for given channel('" + mo.getName() + "') is null with ContentUtil.getManagedObject().So using ContentUtil.findManagedObjectByVCMRef().");
						mo = ContentUtil.findManagedObjectByVCMRef(new ManagedObjectVCMRef(channelId));
						descriptorCI = DescriptorUtils.loadContentInstance(mo, DPMConstants.CHANNEL_DESCRIPTOR_ATT_NAME, false, ct);
					}
					if (descriptorCI != null) {
						channelDescriptorsMap.put(channelId, descriptorCI);
						if (logger.isDebugEnabled())
							logger.debug("Atttribute Descriptor for given channel('" + mo.getName() + "') has been loaded and placed in the map." + descriptorCI);
					}
				} else {
					logger.error("error in getting Managed object related to Content Type of attribute Descriptor CTD or Channel object with given Channel ID.::");
					throw new JspException("error in getting Managed object related to Content Type of attribute Descriptor CTD or Channel object with given Channel ID.::");
				}
			}
			if (descriptorCI != null) {
				AttributeData attData = descriptorCI.getAttribute(attributeName);
				if (attData != null) {
					Object attributeValue = attData.getValue();
					DataType dataType = attData.getDataType();
					if (dataType.isString()) {
						pageContext.setAttribute(varName, (String) attributeValue, varScope);
					} else if (dataType.isInt()) {
						pageContext.setAttribute(varName, (Integer) attributeValue, varScope);
					} else {
						pageContext.setAttribute(varName, attributeValue, varScope);
					}

					if (logger.isDebugEnabled())
						logger.debug("Value for Attribute '" + attributeName + "' ::" + attributeValue);
				} else {
					logger.debug(" AttributeData for Attribute ('" + attributeName + "' is null. Please enter a value if you want to  leverage this attribute.");
				}
			} else {
				logger.warn("Unable to load Attribute Descriptor Content item for given Channel ID ('" + channelId + "').Please enter the descriptor properties for channel if you want to leverage them.");
			}
		} catch (Throwable e) {
			logger.error("Error occured while retrieving  value for Attribute ('" + attributeName + "' from Channel Descriptor CI.", e);
			throw new JspException("Error occured while retrieving  value for Attribute ('" + attributeName + "' from Channel Descriptor CI.", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("End of doStartTag() of GetObjectDescriptorPropertyTag");
		}
		return SKIP_BODY;
	}

	private Map getChannelDescriptorsMap() {
		Map<String, ContentInstance> channelDescriptorsMap = (Map) pageContext.getAttribute(DPMConstants.CHANNELDESCRIPTORSMAP_VAR_NAME);
		if (channelDescriptorsMap == null) {
			channelDescriptorsMap = new HashMap<String, ContentInstance>();
			pageContext.setAttribute(DPMConstants.CHANNELDESCRIPTORSMAP_VAR_NAME, channelDescriptorsMap);
		}
		return channelDescriptorsMap;
	}

	/**
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 * @return int
	 */
	public final int doEndTag() {
		return EVAL_PAGE;
	}

	public void setVar(String name) throws JspException {
		if (name == null || "".equals(name)) {
			logger.error("The value specified for 'var' attribute is invalid");
			throw new JspException("The value specified for 'var' attribute is invalid");
		} else {
			varName = name;
		}
	}

	public void setChannelId(String channelId) throws JspException {
		if (channelId == null || channelId.trim().length() != 40) {
			logger.error("The value specified for 'channelid' (aka Channel GUID) attribute is invalid.It should be valid Channel GUID with 40 characters.");
			throw new JspException("The value specified for 'channelid' (aka Channel GUID) attribute is invalid.It should be valid Channel GUID with 40 characters.");
		} else {
			this.channelId = channelId;
		}

	}

	public void setAttributeName(String attributeName) throws JspException {
		if (attributeName == null || "".equals(attributeName)) {
			logger.error("The value specified for 'attributeName' attribute is invalid.Please provide Attribute XML Name.");
			throw new JspException("The value specified for 'attributeName' attribute is invalid.Please provide Attribute XML Name.");
		} else {
			this.attributeName = attributeName;
		}

	}

	public void setScope(String scope) throws JspException {
		if (scope.equalsIgnoreCase("page"))
			varScope = PageContext.PAGE_SCOPE;
		else if (scope.equalsIgnoreCase("request"))
			varScope = PageContext.REQUEST_SCOPE;
		else if (scope.equalsIgnoreCase("session"))
			varScope = PageContext.SESSION_SCOPE;
		else if (scope.equalsIgnoreCase("application"))
			varScope = PageContext.APPLICATION_SCOPE;
		else {
			logger.error("The value specified for 'scope'  attribute is invalid");
			throw new JspException("The value specified for 'scope'  attribute is invalid");
		}
	}
}
