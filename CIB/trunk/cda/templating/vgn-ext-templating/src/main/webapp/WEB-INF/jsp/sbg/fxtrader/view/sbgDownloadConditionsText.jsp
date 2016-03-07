<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<templating:initComponent/>
<c:set var="dontShowTitle" value="true"/>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgShowTextBlockContent.jsp"%>

<script type="text/javascript">
	var runtimeVersion = "2.0.0",
	directLink = "http://download.efxnow.com/StandardBank/StandardfxTrader.application";
	function HasRuntimeVersion(v){
		var va = GetVersion(v),
			i,
			a = navigator.userAgent.match(/\.NET CLR [0-9.]+/g);
		if (a != null){
			for (i = 0; i < a.length; ++i){
				if (CompareVersions(va, GetVersion(a[i])) <= 0){
					return true;
				}
			}
		}
		return false;
	}
	function GetVersion(v){
		var a = v.match(/([0-9]+)\.([0-9]+)\.([0-9]+)/i);
		return a.slice(1);
	}
	function CompareVersions(v1, v2){
		for (i = 0; i < v1.length; ++i){
			var n1 = new Number(v1[i]),
			n2 = new Number(v2[i]);
			if (n1 < n2){
				return -1;
			}
			if (n1 > n2){
				return 1;
			}
		}
		return 0;
	}
	function loginToWinVer(){
		if (HasRuntimeVersion(runtimeVersion)) {
			window.location = "http://download.efxnow.com/StandardBank/StandardfxTrader.application"
		}else {
			window.location = "http://download.efxnow.com/StandardBank/"
		}
	}
( function($){// bodyguard function
	$(document).ready(function(){
		document.getElementById("chkIAgree").checked = false;
		if (HasRuntimeVersion(runtimeVersion)){
			$("#installButton").attr('onclick', 'window.location='+directLink);
		//	document.getElementById("BootstrapperSection").style.display = "none";
		}
		$('#chkIAgree').click(function(e){
			if($(this).prop('checked') === true){
				$('#installButton').show();
			}else{
				$('#installButton').hide();
			}
		});
		$('#installButton').click(function(e){
			window.location = 'http://download.efxnow.com/StandardBank/setup.exe';
			$('.window').remove();
			$('#mask').fadeOut();
		});
	});// bodyguard function
}) (jQuery);
	var _gaq = _gaq || [];
	_gaq.push(['_setAccount', 'UA-4811946-52']);
	_gaq.push(['_trackPageview']);

(function() {
	var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
})();

</script>