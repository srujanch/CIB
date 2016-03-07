package za.co.standardbank.sbg.cda.templating.taglib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import za.co.standardbank.sbg.cda.templating.web.util.DPMConstants;

import com.vignette.as.client.common.ref.ManagedObjectVCMRef;
import com.vignette.as.client.javabean.AttributedObject;
import com.vignette.as.client.javabean.ContentInstance;
import com.vignette.ext.templating.util.ContentUtil;

public class AccoladePublicationYear extends TagSupport {
	Logger logger = Logger.getLogger(AccoladePublicationYear.class);
	private String var;
	private String oid;
	private String separator;
	private String order;

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	/* ********************************************
	 * IMPLEMENTATON *********************************************
	 */

	/**
	 * Implementation of the start of the tag.
	 * 
	 * @return int which specifies to evaluate the body of the tag.
	 * 
	 */
	public int doStartTag() throws JspException {
		if (logger.isDebugEnabled()) {
			logger.debug("Entered method doStartTag()");
		}
		String value = "";
		try {
			ContentInstance ci = (ContentInstance) ContentUtil.findManagedObjectByVCMRef(new ManagedObjectVCMRef(getOid()));
			if (ci != null) {
				AttributedObject[] attObjArray = ci.getRelations(DPMConstants.ACCOLADE_PUBLICATION_YEAR_RELATION);
				List<Integer> list = new ArrayList<Integer>();
				for (AttributedObject attObj : attObjArray) {
					list.add(new Integer(attObj.getAttributeValue(DPMConstants.ACCOLADE_PUBLICATION_YEAR_ID).toString()));
				}
				Collections.sort(list);

				if (getSeparator() != null && getSeparator().trim().length() == 0) {
					setSeparator(";");
				}

				value = getPublishYearFormat(list);
				// value =
				// (value.endsWith(", ")?value.substring(0,value.lastIndexOf(",")):value);
			}
		} catch (Exception ex) {
			logger.error("Exception occurred while loading Accolade");
			throw new JspException("Exception occurred while loading Accolade");
		}
		pageContext.setAttribute(getVar(), value);
		if (logger.isDebugEnabled()) {
			logger.debug("Exit method doStartTag()");
		}
		return (SKIP_BODY);
	}

	public String getPublishYearFormat(List<Integer> intArray) {
		HashMap<Integer, List> hashMap = new LinkedHashMap<Integer, List>();
		List<Integer> ab;
		int prevYear;
		for (Integer i : intArray) {
			prevYear = (i.intValue() - 1);
			if (hashMap.containsKey(new Integer(prevYear))) {
				ab = hashMap.get(new Integer(prevYear));
				ab.add(i);
				hashMap.remove(new Integer(prevYear));
				hashMap.put(i, ab);
			} else {
				ab = new ArrayList<Integer>();
				ab.add(i);
				hashMap.put(i, ab);
			}
		}
		String str1;
		StringBuffer sb = new StringBuffer("");
		Set<Map.Entry<Integer, List>> set = hashMap.entrySet();

		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, List> entry = (Map.Entry<Integer, List>) it.next();
			ab = entry.getValue();
			if (ab.size() == 1 || ab.size() == 2) {
				for (Integer in : ab) {
					sb.append(in.intValue());
					sb.append(getSeparator() + " ");
				}
			} else if (ab.size() >= 3) {
				sb.append(ab.get(0).intValue());
				sb.append("-");
				sb.append(ab.get(ab.size() - 1).intValue());
				sb.append(getSeparator() + " ");
			}
		}
		str1 = sb.toString();
		if (str1.endsWith(getSeparator() + " ")) {
			str1 = str1.substring(0, (str1.length() - (getSeparator().length() + " ".length())));
		}
		return str1;
	}
}
