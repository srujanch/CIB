<%--
Copyright 1999-2003 Vignette Corporation.
All rights reserved.

THIS PROGRAM IS CONFIDENTIAL AND AN UNPUBLISHED WORK AND TRADE
SECRET OF THE COPYRIGHT HOLDER, AND DISTRIBUTED ONLY UNDER RESTRICTION.

EXCEPT AS EXPLICITLY STATED IN A WRITTEN AGREEMENT BETWEEN THE PARTIES,
THE SOFTWARE IS PROVIDED AS-IS, WITHOUT WARRANTIES OF ANY KIND, EXPRESS
OR IMPLIED, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, NONINFRINGEMENT,
PERFORMANCE, AND QUALITY.
--%>


<%
	if (pageBean.getLayoutType() == com.epicentric.page.PageLayout.LAYOUT_TYPE_NESTED_TABLE) {
		if (pageBean.isColumnOriented()) {
			%><%@ include file="displaypage_static_column_oriented.inc" %><%
		} else {
			%><%@ include file="displaypage_static_row_oriented.inc" %><%
		}
	} else if (pageBean.getLayoutType() == com.epicentric.page.PageLayout.LAYOUT_TYPE_SINGLE_TABLE) {
		%><%@ include file="displaypage_static_table_oriented.inc" %><%
	}
%>