package za.co.standardbank.sbg.cda.templating.taglib;

import com.vignette.as.client.api.bean.BeanFactory;
import com.vignette.as.client.api.bean.BeanFactoryFactory;
import com.vignette.as.client.api.bean.ManagedObjectBean;
import com.vignette.as.client.javabean.ManagedObject;
import com.vignette.ext.templating.client.javabean.QueryContentComponent;
import com.vignette.ext.templating.util.PageUtil;
import com.vignette.ext.templating.util.RequestContext;
import org.apache.log4j.Logger;
import za.co.standardbank.sbg.cda.templating.web.util.DPMConstants;
import za.co.standardbank.sbg.cda.templating.web.util.DisplayUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.*;

public class AccoladeDataTag extends TagSupport {
	Logger logger = Logger.getLogger(AccoladeDataTag.class);
	private String var;
	private List results;

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public List getResults() {
		return results;
	}

	public void setResults(List results) {
		this.results = results;
	}

	/* *********************************************
	 * IMPLEMENTATON
	 * *********************************************
	 */

	/**
	 * Implementation of the start of the tag.
	 * 
	 * @return int which specifies to evaluate the body of the tag.
	 * 
	 */
	public int doStartTag() throws JspException {
		try {

			RequestContext rc = PageUtil.getCurrentRequestContext(pageContext);
			ManagedObject mo = rc.getRenderedManagedObject();
			if (mo instanceof QueryContentComponent) {
				QueryContentComponent qcc = (QueryContentComponent) mo;
				setResults(qcc.getResults(rc));
			}
			if (getResults() != null && results.size() > 0) {
				if (logger.isDebugEnabled())
					logger.debug("results size::" + results.size());
				HashMap<String, List<ManagedObjectBean>> resultsMap = new HashMap<String, List<ManagedObjectBean>>();
				HashMap<String, List<ManagedObject>> map = new HashMap<String, List<ManagedObject>>();
				int size = results.size();
				ManagedObject moObj;
				String publication;
				List<ManagedObject> accoladesList;
				BeanFactory factory = BeanFactoryFactory.getFactory();
				for (int i = 0; i < size; i++) {
					moObj = (ManagedObject) results.get(i);
					publication = (String) moObj.getAttributeValue(DPMConstants.ACCOLADE_PUBLICATION);
					if (map.containsKey(publication)) {
						accoladesList = map.get(publication);
						accoladesList.add(moObj);
						map.put(publication, accoladesList);
					} else {
						accoladesList = new ArrayList<ManagedObject>();
						accoladesList.add(moObj);
						map.put(publication, accoladesList);
					}
				}
				List<ManagedObjectBean> accoladesListBean;
				for (String key : map.keySet()) {
					accoladesList = map.get(key);
					Collections.sort(accoladesList, DisplayUtil.getManagedObjectTitleComparator());
					accoladesListBean = new ArrayList<ManagedObjectBean>();
					for (ManagedObject moBean : accoladesList) {
						accoladesListBean.add(factory.createBean(moBean));
					}
					resultsMap.put(key, accoladesListBean);
				}
				List<Map.Entry> mapResults = new ArrayList<Map.Entry>(resultsMap.entrySet());
				Collections.sort(mapResults, DisplayUtil.getHashMapEntryKeyComparator());
				if (logger.isDebugEnabled())
					logger.debug(" map size::" + resultsMap.size());
				pageContext.setAttribute(getVar(), mapResults, PageContext.REQUEST_SCOPE);
			}
		} catch (Exception ex) {
			logger.error("Exception occurred while loading Accolade Data::" + ex.getMessage());
			throw new JspException("Exception occurred while loading Accolade Data::" + ex.getMessage());
		}
		return (SKIP_BODY);
	}

}
