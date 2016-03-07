<%@ page import="com.vignette.ext.templating.util.RequestContext"%>
<%@ page import="com.vignette.ext.templating.util.PageUtil"%>
<%@ page import="com.vignette.as.client.javabean.Channel"%>
<%@ page import="com.vignette.as.client.javabean.ManagedObject"%>
<%@ page import="com.vignette.ext.templating.util.ContentUtil"%>
<%@ page import="com.vignette.as.client.common.ref.ManagedObjectVCMRef"%>
<%@ page import="com.vignette.as.client.javabean.ContentInstance"%>
<%@ page import="com.vignette.as.config.ConfigUtil"%>
<%!
  private static final String SEARCH_INPUT_TEXT = "text";
  private static final String SEARCH_RESULTS_MATCHES = "totalMatches";
  private static final String SPACE = " ";
  private static final String JS_FILE_PATH = "/SBG/Common/SiteCatalyst";
%>
<%
    StringBuffer sb = new StringBuffer("");
    String siteName = "";
   // String filePath = "";
    try{
       // filePath = ConfigUtil.getURLPathPrefix("static_file")+JS_FILE_PATH;
        RequestContext rc = PageUtil.getCurrentRequestContext(pageContext);
        siteName = rc.getCurrentSite().getData().getName();
        Channel currentChannel = rc.getRequestedChannel();



        String oid = rc.getRequestOIDString();
        String title = "Landing";
        String currentChannelName = "";
        String searchInputText = request.getParameter(SEARCH_INPUT_TEXT) == null?(String)request.getAttribute(SEARCH_INPUT_TEXT):request.getParameter(SEARCH_INPUT_TEXT);
        String searchResultsSize = pageContext.getAttribute(SEARCH_RESULTS_MATCHES, PageContext.REQUEST_SCOPE) != null?
                        (String)pageContext.getAttribute(SEARCH_RESULTS_MATCHES, PageContext.REQUEST_SCOPE):null;

        String s_prop27;
        String s_prop28;

        if (oid != null){
            ManagedObject mo = ContentUtil.findManagedObjectByVCMRef(new ManagedObjectVCMRef(oid));
            if (mo instanceof ContentInstance){
                title = mo.getName();
                title = title.replaceAll("'","\'");
            }else if (mo instanceof Channel){
                currentChannel = (Channel)mo;
            }
        }
        String breadCrumbPath = "";
        if (currentChannel != null){
            currentChannelName = currentChannel.getData().getName();
            String[] breadCrumb = currentChannel.getBreadcrumbNamePath(true);
            int length = (breadCrumb != null)?breadCrumb.length:0;
            if (length > 1){
               sb.append("s.channel='");
               sb.append(breadCrumb[1]);
               sb.append("';");
               sb.append(SPACE);
               for (int i=1;i<length;i++){            //0 is for Home
                   breadCrumbPath += breadCrumb[i]+":";
               }

               sb.append("s.pageName='");
               sb.append(breadCrumbPath+title);
               sb.append("';");
               sb.append(SPACE);

               sb.append("s.hier1='");
               sb.append(breadCrumbPath.endsWith(":")?breadCrumbPath.substring(0,breadCrumbPath.length()-1):breadCrumbPath);
               sb.append("';");
               sb.append(SPACE);

               for (int i=2; i <= length; i++){
                    sb.append("s.prop"+(i-1));
                    sb.append("='");
                    breadCrumbPath = "";
                   for (int j=1;j < i;j++){          //0 is for Home
                       breadCrumbPath += breadCrumb[j]+":";
                   }
                   if (breadCrumbPath.endsWith(":")){
                       breadCrumbPath = breadCrumbPath.substring(0,breadCrumbPath.length()-1);
                   }
                   sb.append(breadCrumbPath);
                   sb.append("';");
                   sb.append(SPACE);
               }
            }else if (length == 0 || length == 1){
               sb.append("s.channel='");
               sb.append(currentChannelName);
               sb.append("';");
               sb.append(SPACE);

               sb.append("s.pageName='");
               sb.append(currentChannelName+":"+title);
               sb.append("';");
               sb.append(SPACE);

               sb.append("s.hier1='");
               sb.append(currentChannelName);
               sb.append("';");
               sb.append(SPACE);
            }
        }
        if (searchInputText != null && searchInputText.trim().length() > 0){
            sb.append("s.prop10='");
            sb.append(searchInputText);
            sb.append("';");
            sb.append(SPACE);
        }
        if (searchResultsSize != null){
            sb.append("s.prop11='");
            sb.append(searchResultsSize);
            sb.append("';");
            sb.append(SPACE);
        }else if (searchResultsSize == null || "0".equals(searchResultsSize)){
            sb.append("eVar36='");
            sb.append(searchInputText);
            sb.append("';");
            sb.append(SPACE);
        }
        if (title != null && !title.equals("Landing")){
            sb.append("s.prop12='");
            sb.append(title);
            sb.append("';");
            sb.append(SPACE);
        }
        if (siteName != null){
            sb.append("s.prop26='");
            sb.append(siteName.toUpperCase());
            sb.append("';");
            sb.append(SPACE);
        }
    }catch(Exception ex){
        ex.printStackTrace();
    }
%>
<!-- SiteCatalyst code version: H.21.
Copyright 1996-2010 Adobe, Inc. All Rights Reserved
More info available at http://www.omniture.com -->
<script language="JavaScript" type="text/javascript"><!--
/* You may give each page an identifying name, server, and channel on
the next lines. */
s.pageType= document.location.href.indexOf("404page") > -1?'errorPage':'';
<%=sb.toString()%>
/************* DO NOT ALTER ANYTHING BELOW THIS LINE ! **************/
var s_code=s.t();if(s_code)document.write(s_code)//--></script>
<script language="JavaScript" type="text/javascript"><!--
if(navigator.appVersion.indexOf('MSIE')>=0)document.write(unescape('%3C')+'\!-'+'-')
//--></script><noscript><a href="http://www.omniture.com" title="Web Analytics"><img
src="http://accstandardbank.112.2o7.net/b/ss/accstandardbankstandardfxtrader/1/H.21--NS/0?[AQB]&cdp=3&[AQE]"
height="1" width="1" border="0" alt="" /></a></noscript><!--/DO NOT REMOVE/-->
<!-- End SiteCatalyst code version: H.21. -->
