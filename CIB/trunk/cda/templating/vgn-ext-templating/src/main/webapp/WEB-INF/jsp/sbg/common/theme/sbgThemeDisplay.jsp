<%@ page import="com.vignette.ext.templating.util.RequestContext"%>
<%@ page import="java.util.List"%>
<%@ page import="com.vignette.ext.templating.util.PageUtil"%>
<%@ page import="za.co.standardbank.sbg.cda.templating.web.util.DisplayUtil"%>
<%@ page import="com.vignette.ext.templating.client.javabean.Theme"%>
<%
    RequestContext requestContext = PageUtil.getCurrentRequestContext(pageContext);
    Theme theme = requestContext.getTheme();

    List<String> themeFilePaths = theme.getThemeFilePaths(requestContext);

    StringBuffer paths = new StringBuffer();
    String render = request.getParameter("render");
    String templateType = request.getParameter("type");
    //out.println("render.."+render+" length.."+themeFilePaths.size());
    if (render != null && render.equalsIgnoreCase("css")){
        String media;
        boolean ieFlag;
        for (String themePath:themeFilePaths){
            if (DisplayUtil.isStyle(themePath)){
                media= "all, screen";
                ieFlag = false;
                if (themePath.indexOf("ie6Override") > -1){
                    paths.append("<!--[if lt IE 7]>");
                    ieFlag = true;
                }else if (themePath.indexOf("ieLt9Override") > -1){
                    paths.append("<!--[if lt IE 9]>");
                    ieFlag = true;
                }else if (themePath.indexOf("ie9Override") > -1){
                    paths.append("<!--[if IE 9]>");
                    ieFlag = true;
                }else if (themePath.indexOf("Print") > -1){
                    media = "print";
                }
                if (ieFlag){
                    media = "screen";
                }
                paths.append("<link ");
                paths.append(" href=\"");
                paths.append(themePath + "\" rel=\"stylesheet\" type=\"text/css\" media=\""+media+"\" />\n");
                if (ieFlag){
                    paths.append("<![endif]-->");
                }
            }
        }
    }else if (render != null && render.equalsIgnoreCase("js")){
        //out.println("template type::"+templateType);
        for (String themePath:themeFilePaths){
            if (DisplayUtil.isJavascriptFile(themePath)){
                if (themePath.indexOf("homePage") > -1){
                    if (!"homePage".equals(templateType)){
                         continue;
                    }
                }else if (themePath.indexOf("jquery.vid.min") > -1){
                    if (!"fxtVideoGallery".equals(templateType)){
                         continue;
                     }
                }else if (themePath.indexOf("jquery.validate1.8.1.min") > -1){
                    if (!"fxtContactUs".equals(templateType)){
                         continue;
                     }
                }else if (themePath.indexOf("jquery.formFunctions") > -1){
                    if (!"fxtContactUs".equals(templateType)){
                         continue;
                     }
                }
                paths.append("<script src=\"");
			    paths.append(themePath + "\" type=\"text/javascript\"></script>\n");
            }
        }
    }
    out.write(paths.toString());
%>