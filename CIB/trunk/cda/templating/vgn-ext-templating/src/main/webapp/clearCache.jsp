	<%--
/*######################################################################################
Copyright 2001-2006 Vignette Corporation.  All rights reserved.  This software
is an unpublished work and trade secret of Vignette, and distributed only
under restriction.  This software (or any part of it) may not be used,
modified, reproduced, stored on a retrieval system, distributed, or
transmitted without the express written consent of Vignette.  Violation of
the provisions contained herein may result in severe civil and criminal
penalties, and any violators will be prosecuted to the maximum extent
possible under the law.  Further, by using this software you acknowledge and
agree that if this software is modified by anyone such as you, a third party
or Vignette, then Vignette will have no obligation to provide support or
maintenance for this software.
#####################################################################################*/
--%>

<%@ page import="java.util.*,
				 com.vignette.ext.templating.util.ContentUtil" %>


<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<html>
	<head>
	<title> Clear Cache </title>	
	</head>
<body>
<% ContentUtil.clearCache(); %>
</body>
</html>
