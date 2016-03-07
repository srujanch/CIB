<%
	String contextPath = request.getContextPath();
		if(contextPath !=null && (contextPath.equalsIgnoreCase("/cib") || contextPath.equalsIgnoreCase("/fxtrader"))){
		// '/error404_cib' - Vanity URL to Error 404 Channel Page
		response.sendRedirect(contextPath+"/404page");
	} else {
		response.sendRedirect("mapperconsole/404.jsp");
	}
%>