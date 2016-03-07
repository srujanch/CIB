<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>
<%@ page import="za.co.standardbank.sbg.cda.templating.web.util.DisplayUtil"%>
<%@ page import="java.util.*"%>
<%@ page import="com.vignette.ext.templating.util.*"%>
<%@ page import="za.co.standardbank.sbg.cda.templating.web.util.DPMConstants"%>

<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="fmt" value="${param.vgnextfmt}" />
<c:if test="${not empty component.channelPath}">
<templating:contentLink var="linkUrl" oid="${component.channelPath.system.id}" format="${fmt}"/>
</c:if>
<%
   long sTime = System.currentTimeMillis();
    try{
        RequestContext rc = PageUtil.getCurrentRequestContext(pageContext);
        String siteName = rc.getCurrentSite().getData().getName();

        List<String> years = DisplayUtil.getYearsForSearch(siteName.toUpperCase()+ DPMConstants.DEALS_START_YEAR);

        HashMap<String, String> countries = DisplayUtil.getAllCountries();
        List<Map.Entry> countrieAsc = (countries != null)?new ArrayList<Map.Entry>(countries.entrySet()):new ArrayList<Map.Entry>();
        Collections.sort(countrieAsc, DisplayUtil.getHashMapEntrySetComparator());

        HashMap<String, String> sectors = DisplayUtil.getAllSectors();
        List<Map.Entry> sectorsAsc = (sectors != null)?new ArrayList<Map.Entry>(sectors.entrySet()):new ArrayList<Map.Entry>();
        Collections.sort(sectorsAsc,DisplayUtil.getHashMapEntrySetComparator());

        HashMap<String, String> products = DisplayUtil.getAllProducts();
        List<Map.Entry> productsAsc = (products != null)?new ArrayList<Map.Entry>(products.entrySet()):new ArrayList<Map.Entry>();
        Collections.sort(productsAsc,DisplayUtil.getHashMapEntrySetComparator());

        pageContext.setAttribute("listYears",years);
        pageContext.setAttribute("listCountries",countrieAsc);
        pageContext.setAttribute("listSectors",sectorsAsc);
        pageContext.setAttribute("listProducts",productsAsc);
    }catch(Exception ex){
        ex.printStackTrace();
    }

%>



<div id="dealsSearchRight" class="search float portlet">

<form id="dealsForm" action="${linkUrl}" method="POST">

<h1>${not empty component.title?'Search deals':component.title}</h1>
    <p>
		<select class="year" name="Year" id="Year<%=sTime%>">
            <option value="">Year - all</option>
             <c:forEach items="${listYears}" var="year">
                 <c:choose>
                    <c:when test="${(not empty param.Year) and (param.Year eq year)}">
                        <option value="${year}" selected="selected">${year}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${year}">${year}</option>
                    </c:otherwise>
                </c:choose>
             </c:forEach>
        </select>
    </p>
    <p>
         <select class="country" name="Country" id="Country<%=sTime%>">
             <option value="">Country - all</option>
             <c:forEach items="${listCountries}" var="entry">
                <c:choose>
                    <c:when test="${(not empty param.Country) and (param.Country eq entry.key)}">
                        <option value="${entry.key}" selected="selected">${entry.value}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${entry.key}">${entry.value}</option>
                    </c:otherwise>
                </c:choose>
             </c:forEach>
         </select>
    </p>
    <p>
        <c:choose>
            <c:when test="${not empty param.sector}">
                <input type="hidden" name="Sector" id="Sector<%=sTime%>" value="${param.sector}" />
            </c:when>
            <c:when test="${not empty param.product}">
                  <input type="hidden" name="Sector" id="Sector<%=sTime%>" value="" />
            </c:when>
            <c:otherwise>
                <select class="sector" name="Sector" id="Sector<%=sTime%>">
                    <option value="">Sector - all</option>
                    <c:forEach items="${listSectors}" var="entry">
                        <c:choose>
                            <c:when test="${(not empty param.Sector) and (param.Sector eq entry.key)}">
                                <option value="${entry.key}" selected="selected">${entry.value}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${entry.key}">${entry.value}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </c:otherwise>
        </c:choose>
    </p>
    <p>
        <c:choose>
            <c:when test="${not empty param.product}">
                <input type="hidden" name="Product" id="Product<%=sTime%>" value="${param.product}" />
            </c:when>
            <c:when test="${not empty param.sector}">
                <input type="hidden" name="Product" id="Product<%=sTime%>" value="" />
            </c:when>
            <c:otherwise>
                <select class="product" name="Product" id="Product<%=sTime%>">
                    <option  value="">Product/service - all</option>
                    <c:forEach items="${listProducts}" var="entry">
                        <c:choose>
                            <c:when test="${(not empty param.Product) and (param.Product eq entry.key)}">
                                <option value="${entry.key}" selected="selected">${entry.value}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${entry.key}">${entry.value}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </c:otherwise>
        </c:choose>
    </p>
    <p>
        <input type="text" value="${empty param.Client ?'Client':param.Client}" class="client" id="Client<%=sTime%>" name="Client" onfocus="this.value=(this.value==this.defaultValue) ? '' : this.value;return true;" onblur="if(this.value==''){this.value='Client'; }" />
    </p>
    <p class="textRight">
        <c:choose>
            <c:when test="${fmt eq 'default'}">
                <input type="submit" value="Search deals" />
            </c:when>
             <c:otherwise>
                 <input type="button" class="searchDeals" value="Search deals" rel="dealsPage" href="${linkUrl}" />
             </c:otherwise>
        </c:choose>
    </p>

</form>

</div>
<c:if test="${!(fmt eq 'default')}">
<script type="text/javascript">
( function($) {
	$(document).ready(function() { // bodyguard function

        $('input.searchDeals').click(function (e) {

                var year = document.getElementById('Year<%=sTime%>').value;
				var country = document.getElementById('Country<%=sTime%>').value;
				var client = document.getElementById('Client<%=sTime%>').value;
				var product = document.getElementById('Product<%=sTime%>').value;
				var sector = document.getElementById('Sector<%=sTime%>').value;
				//alert('Year::'+year+' Country::'+country+' Client::'+client+' Product::'+product+' Sector::'+sector);
				var url = (product != '')?'?Product='+product+'&product='+product:'?Sector='+sector+'&sector='+sector;
				url = (year != '')? url+'&Year='+year:url;
				url = (country != '')?url+'&Country='+country:url;
				url = (client != '' && client != 'Client')?url+'&Client='+client:url;
                url = url+"&tabResults=<%=sTime%>";
                //alert('url::'+url);
				var $divId = $(this).attr('rel'), $hrefDest = $(this).attr('href');
                url = url+'&url='+$hrefDest;
                $hrefDest = $hrefDest+url;
                //alert('$hrefDest::'+$hrefDest);
				$('.tabBody').slideUp('medium');
				switch($hrefDest){
				case '#':
					$('#'+$divId).fadeIn('medium');
				  break;
				default:
					if($('#' + $divId).length==0){
						$.get($hrefDest, function(data){
							$('#content').append(data);
						});
					}else{$('#' + $divId).fadeIn('medium')}
				}

		});
    });// bodyguard function
}) (jQuery);
</script>
</c:if>