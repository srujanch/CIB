
function getSCodeObject(){
    return s_gi('accstandardbank');
}

function trackBannerClick(bannerTitle){
    var s=getSCodeObject();
    s.linkTrackVars='eVar33,events';
    s.linkTrackEvents="event16";
    s.events="event16";
    s.eVar33=bannerTitle;
    s.tl(this,'o',bannerTitle+' Clicks');
    return true;
}

function trackSocialMediaClick(socialMediaTitle){
    var s=getSCodeObject();
    s.linkTrackVars='eVar17,events';
    s.linkTrackEvents="event10";
    s.events="event10";
    s.eVar17=socialMediaTitle;
    s.tl(this,'o',socialMediaTitle+' Clicks');
    return true;
}

function trackFeaturedLinkClick(featuredLinkTitle){
   var s=getSCodeObject();
   s.linkTrackVars='eVar34,events';
   s.linkTrackEvents="event15";
   s.events="event15";
   s.eVar34=featuredLinkTitle;
   s.tl(this,'o',featuredLinkTitle+' Clicks');
   return true;
}

function trackActionClick(actionTitle){
    var s=getSCodeObject();
    s.linkTrackVars='eVar35,events';
    s.linkTrackEvents="event17";
    s.events="event17";
    s.eVar35=actionTitle;
    s.tl(this,'o',actionTitle+' Clicks');
    return true;
}

function trackFormClick(formName){
    var s=getSCodeObject();
    s.linkTrackVars='prop13,eVar26';
    s.prop13=formName;
    s.eVar26=formName;
    s.tl(this,'o',formName+' Clicks');
    return true;
}

function trackVideoClick(videoTitle){
    var s=getSCodeObject();
    s.linkTrackVars='eVar37,events,prop28';
    s.linkTrackEvents="event18";
    s.events="event18";
    s.eVar37=videoTitle;
    s.prop28=videoTitle;
    s.tl(this,'o',videoTitle+' Video Clicks');
    return true;
}

function trackTabClicks(breadCrumbPath, siteName){
    if (breadCrumbPath){
      var s=getSCodeObject();
      s.linkTrackVars='channel,pageName,hier1,prop1,prop2,prop3,prop4,prop5,prop6,prop7,prop8,prop26';
      s.pageName = breadCrumbPath;
      s.hier1 = breadCrumbPath;
      s.prop26 = siteName;
      var arr = breadCrumbPath.split(":");
      if (arr != null && arr.length > 0){
          s.channel = arr[0];
          for (var i=1;i<=arr.length;i++){
              switch(i){
                case 1: s.prop1 = arr[i-1]; break;
                case 2: s.prop2 = arr[i-2]+":"+arr[i-1];break;
                case 3: s.prop3 = arr[i-3]+":"+arr[i-2]+":"+arr[i-1];break;
                case 4: s.prop4 = arr[i-4]+":"+arr[i-3]+":"+arr[i-2]+":"+arr[i-1];break;
                case 5: s.prop5 = arr[i-5]+":"+arr[i-4]+":"+arr[i-3]+":"+arr[i-2]+":"+arr[i-1];break;
                case 6: s.prop6 = arr[i-6]+":"+arr[i-5]+":"+arr[i-4]+":"+arr[i-3]+":"+arr[i-2]+":"+arr[i-1];break;
                case 7: s.prop7 = arr[i-7]+":"+arr[i-6]+":"+arr[i-5]+":"+arr[i-4]+":"+arr[i-3]+":"+arr[i-2]+":"+arr[i-1];break;
                default: break;
              }
          }
          for (var i=arr.length+1;i<=8;i++){
              switch(i){
                 case 1: s.prop1 = ''; break;
                 case 2: s.prop2 = ''; break;
                 case 3: s.prop3 = ''; break;
                 case 4: s.prop4 = ''; break;
                 case 5: s.prop5 = ''; break;
                 case 6: s.prop6 = ''; break;
                 case 7: s.prop7 = ''; break;
                 default: break;
              }
          }
      }
      s.tl(this,'o',breadCrumbPath);
    }
	return true;
}

function trackContentClick(breadCrumbPath, siteName){
    if (breadCrumbPath){
      var s=getSCodeObject();
      s.linkTrackVars='channel,pageName,hier1,prop1,prop2,prop3,prop4,prop5,prop6,prop7,prop8,prop12,prop26';
      s.pageName = breadCrumbPath;
      s.prop26 = siteName;
      var arr = breadCrumbPath.split(":");
      if (arr != null && arr.length > 0){
          s.channel = arr[0];
          for (var i=1;i<arr.length;i++){
              switch(i){
                case 1: s.prop1 = arr[i-1]; s.hier1=s.prop1; break;
                case 2: s.prop2 = arr[i-2]+":"+arr[i-1];s.hier1=s.prop2;break;
                case 3: s.prop3 = arr[i-3]+":"+arr[i-2]+":"+arr[i-1];s.hier1=s.prop3;break;
                case 4: s.prop4 = arr[i-4]+":"+arr[i-3]+":"+arr[i-2]+":"+arr[i-1];s.hier1=s.prop4;break;
                case 5: s.prop5 = arr[i-5]+":"+arr[i-4]+":"+arr[i-3]+":"+arr[i-2]+":"+arr[i-1];s.hier1=s.prop5;break;
                case 6: s.prop6 = arr[i-6]+":"+arr[i-5]+":"+arr[i-4]+":"+arr[i-3]+":"+arr[i-2]+":"+arr[i-1];s.hier1=s.prop6;break;
                case 7: s.prop7 = arr[i-7]+":"+arr[i-6]+":"+arr[i-5]+":"+arr[i-4]+":"+arr[i-3]+":"+arr[i-2]+":"+arr[i-1];s.hier1=s.prop7;break;
                default: break;
              }
          }
          for (var i=arr.length;i<=8;i++){
              switch(i){
                 case 1: s.prop1 = ''; break;
                 case 2: s.prop2 = ''; break;
                 case 3: s.prop3 = ''; break;
                 case 4: s.prop4 = ''; break;
                 case 5: s.prop5 = ''; break;
                 case 6: s.prop6 = ''; break;
                 case 7: s.prop7 = ''; break;
                 default: break;
              }
          }
          s.prop12 = arr[arr.length-1];
      }
      s.tl(this,'o',breadCrumbPath);
    }
	return true;
}