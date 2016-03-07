<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>

<div class="errorMsg col_4">
	<p><strong>Search for what you were looking for:</strong></p>
	<div class="searchBar errorSearchField">
	
		<form action="/portal/site/cib/cibsearch/?javax.portlet.tpst=8218de6111cd803a93077103c7f819a0_ws_MX&amp;javax.portlet.prp_8218de6111cd803a93077103c7f819a0=prv%3D%26cn%3Dy%26viewID%3Drealtime_basic_search_process&amp;javax.portlet.begCacheTok=com.vignette.cachetoken&amp;javax.portlet.endCacheTok=com.vignette.cachetoken" method="post" name="searchform1" id="searchform1">
	
			<input type="hidden" name="age0" value="3600000" />
			<input type="hidden" name="collection_scope" value="use_custom_collections" />
			<input type="hidden" name="cri_field0" value="BODY" />
			<input type="hidden" value="search" id="search" name="search">
			<input type="hidden" name="day_constraint0" value="use_from_to_date" />
			<input type="hidden" name="search_advanced" value="y" />
			<input type="hidden" name="from_date0" value="" />
			<input type="hidden" name="hidden_key" value="" />
			<input type="hidden" name="origin_page" value="" />
			<input type="hidden" name="to_date0" value="" />
			<input type="hidden" name="cri_restriction0" value="Could contain" />
			
			
			<input type="hidden" name="OpenText:CIB-Dev-Delivery-Spider-Index" value="selected" />			
			<input type="hidden" name="OpenText:SBSA-Site-Spider-Index" value="selected" />
			
			
			<input type="text" class="" size="20" 
			value="I am looking for..." name="cri_restriction_text0" onblur="if(this.value == '') { this.value = 'I am looking for...'; }" onfocus="this.value = ( this.value == this.defaultValue ) ? '' : this.value;return true;" />
			<input type="submit" onclick="this.checked=1" name="submit" value="Search" />
		
		</form>
		
		<br class="clearBoth" />
	</div><br />
	<p><strong>Alternatively, are you looking for one of the following pages?</strong></p>
</div>
<br class="clearboth" />