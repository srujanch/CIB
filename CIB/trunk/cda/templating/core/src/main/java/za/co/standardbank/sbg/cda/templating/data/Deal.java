package za.co.standardbank.sbg.cda.templating.data;

import java.util.ArrayList;
import java.util.Date;

public class Deal implements DAMObject
{
  public Boolean hasDescription = false;

  public long nDealId = 0L;

  public double XRate = 1.0D;

  public String sFile_name = "";

  public String sDAmount = "";

  public String sPublishToHome = "";

  public String sStatus = "";

  public String sTitle = "";

  public String sShort_description = "";

  public String sDescription = "";

  public String sAnnounced = "";

  public String sProduct_service = null;

  public String sSector;

  public String sClient;
  public long nAmount;
  public String sCurrency = "";

  // country is actually comma-separated list returned from sql
  public String sCountry = null;

  public String sRole_1 = "";

  public String sRole_2 = "";

  public String sContact_name = "";

  public String sContact_tel_number = "";

  public ArrayList sSegment = null;

  public String sRegion = "";

  public String sPublishStatus = "";

  public String sc_consent = "";

  public String sc_confidentiality = "";

  public String sc_clearance = "";

  public String sc_consulted = "";

  public String sShort_URL = "";

  public String sArticle_URL = "";
  public String sShort_URLText = "";
  public String sArticle_URLText = "";

  public Date startDate;

  public Date endDate;

  public Date dealDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    public String getShort_URL()
  {
    return this.sShort_URL;
  }

  public void setShortURL(String short_URL) {
    this.sShort_URL = short_URL;
  }

  public String getArticleURL() {
    return this.sArticle_URL;
  }

  public void setArticleURL(String article_URL) {
    this.sArticle_URL = article_URL;
  }
  public String getShortURLText() {
    return this.sShort_URLText;
  }

  public void setShortURLText(String short_URLText) {
    this.sShort_URLText = short_URLText;
  }

  public String getArticleURLText() {
    return this.sArticle_URLText;
  }

  public void setArticleURLText(String article_URLText) {
    this.sArticle_URLText = article_URLText;
  }

  public String getPublishStatus() {
    return this.sPublishStatus;
  }

  public double getXRate() {
    return this.XRate;
  }

  public void setXRate(double rate) {
    this.XRate = rate;
  }

  public void setPublishStatus(String publishToWeb) {
    this.sPublishStatus = publishToWeb;
  }

  public String getRegion() {
    return this.sRegion;
  }

  public void setRegion(String region) {
    this.sRegion = region;
  }

  public ArrayList getSegment() {
    return this.sSegment;
  }

  public void setSegment(ArrayList segment) {
    this.sSegment = segment;
  }

  public long getAmount() {
    return this.nAmount;
  }

  public void setAmount(long amount) {
    this.nAmount = amount;
  }

  public long getDealId() {
    return this.nDealId;
  }

  public void setDealId(long dealId) {
    this.nDealId = dealId;
  }

  public String getAnnounced() {
    return this.sAnnounced;
  }

  public void setAnnounced(String announced) {
    this.sAnnounced = announced;
  }

  public String getClient()
  {
    return this.sClient;
  }

  public void setClient(String client) {
    this.sClient = client;
  }

  public String getContactName() {
    return this.sContact_name;
  }

  public void setContactName(String contact_name) {
    this.sContact_name = contact_name;
  }

  public String getContactTelNumber() {
    return this.sContact_tel_number;
  }

  public void setContactTelNumber(String contact_tel_number) {
    this.sContact_tel_number = contact_tel_number;
  }

  public String getCountry() {
    return this.sCountry;
  }

  public void setCountry(String country) {
    this.sCountry = country;
  }

  public String getCurrency() {
    return this.sCurrency;
  }

  public void setCurrency(String currency) {
    this.sCurrency = currency;
  }

  public String getDescription() {
    return this.sDescription;
  }

  public void setDescription(String description) {
    this.sDescription = description;
  }

  public String getProductService()
  {
    return this.sProduct_service;
  }

  public void setProductService(String product_service) {
    this.sProduct_service = product_service;
  }

  public String getRole_1() {
    return this.sRole_1;
  }

  public void setRole_1(String role_1) {
    this.sRole_1 = role_1;
  }

  public String getRole_2() {
    return this.sRole_2;
  }

  public void setRole_2(String role_2) {
    this.sRole_2 = role_2;
  }

  public String getSector() {
    return this.sSector;
  }

  public void setSector(String sector) {
    this.sSector = sector;
  }

  public String getTitle() {
    return this.sTitle;
  }

  public void setTitle(String title) {
    this.sTitle = title;
  }

  public String getStatus() {
    return this.sStatus;
  }

  public void setStatus(String status) {
    this.sStatus = status;
  }

  public String getShortDescription() {
    return this.sShort_description;
  }

  public void setShortDescription(String short_description) {
    this.sShort_description = short_description;
  }
  public String getFileName() {
    return this.sFile_name;
  }

  public void setFileName(String file_name) {
    this.sFile_name = file_name;
  }
  public String getDAmount() {
    return this.sDAmount;
  }

  public void setDAmount(String amount) {
    this.sDAmount = amount;
  }

  public String getPublishToHome() {
    return this.sPublishToHome;
  }

  public void setPublishToHome(String publishToHome) {
    this.sPublishToHome = publishToHome;
  }

  public String getCConsent()
  {
    return this.sc_consent;
  }

  public void setCConsent(String sc_consent) {
    this.sc_consent = sc_consent;
  }

  public String getCConfidentiality() {
    return this.sc_confidentiality;
  }

  public void setCConfidentiality(String sc_confidentiality) {
    this.sc_confidentiality = sc_confidentiality;
  }

  public String getCClearance() {
    return this.sc_clearance;
  }

  public void setCClearance(String sc_clearance) {
    this.sc_clearance = sc_clearance;
  }

  public String getCConsulted() {
    return this.sc_consulted;
  }

  public void setCConsulted(String sc_consulted) {
    this.sc_consulted = sc_consulted;
  }

  public Boolean getHasDescription() {
        return hasDescription;
    }

  public void setHasDescription(Boolean hasDescription) {
        this.hasDescription = hasDescription;
    }

  public Deal()
  {
  }

  public Deal(String title, String short_description, String description, String announced, String product_service, String sector, String client, long amount, String currency, String country, String role_1, String role_2, String contact_name, String contact_tel_number, String region, ArrayList segment, String File_name, String PublishStatus, String PublishToHome, String DAmount, double xrate, String Compliance_file, String c_consent, String c_confidentiality, String c_consulted, String c_clearance, String shortURL, String articleURL, String shortURLText, String articleURLText, Boolean hasDescription)
    throws Exception
  {
    this.sTitle = title;
    this.sShort_description = short_description;
    this.sDescription = description;
    this.sAnnounced = announced;
    this.sProduct_service = product_service;
    this.sSector = sector;
    this.sClient = client;
    this.nAmount = amount;
    this.sCurrency = currency;
    this.sCountry = country;
    this.sRole_1 = role_1;
    this.sRole_2 = role_2;
    this.sContact_name = contact_name;
    this.sContact_tel_number = contact_tel_number;
    this.sSegment = segment;
    this.sRegion = region;
    this.sFile_name = File_name;
    this.sPublishStatus = PublishStatus;
    this.sPublishToHome = PublishToHome;
    this.sDAmount = DAmount;
    this.XRate = xrate;
    this.sc_consent = c_consent;
    this.sc_confidentiality = c_confidentiality;
    this.sc_consulted = c_consulted;
    this.sc_clearance = c_clearance;
    this.sShort_URL = shortURL;
    this.sArticle_URL = articleURL;
    this.sShort_URLText = shortURLText;
    this.sArticle_URLText = articleURLText;
    this.hasDescription = hasDescription;
  }

  public Deal(Deal thisdeal) throws Exception {
    this.nDealId = thisdeal.nDealId;
    this.sTitle = thisdeal.sTitle;
    this.sShort_description = thisdeal.sShort_description;
    this.sDescription = thisdeal.sDescription;
    this.sAnnounced = thisdeal.sAnnounced;
    this.sProduct_service = thisdeal.sProduct_service;
    this.sSector = thisdeal.sSector;
    this.sClient = thisdeal.sClient;
    this.nAmount = thisdeal.nAmount;
    this.sCurrency = thisdeal.sCurrency;
    this.sCountry = thisdeal.sCountry;
    this.sRole_1 = thisdeal.sRole_1;
    this.sRole_2 = thisdeal.sRole_2;
    this.sContact_name = thisdeal.sContact_name;
    this.sContact_tel_number = thisdeal.sContact_tel_number;
    this.sSegment = thisdeal.sSegment;
    this.sRegion = thisdeal.sRegion;
    this.sFile_name = thisdeal.sFile_name;
    this.sPublishStatus = thisdeal.sPublishStatus;
    this.sPublishToHome = thisdeal.sPublishToHome;
    this.sDAmount = thisdeal.sDAmount;
    this.XRate = thisdeal.XRate;
    this.sc_consent = thisdeal.sc_consent;
    this.sc_confidentiality = thisdeal.sc_confidentiality;
    this.sc_consulted = thisdeal.sc_consulted;
    this.sc_clearance = thisdeal.sc_clearance;
    this.sShort_URL = thisdeal.sShort_URL;
    this.sArticle_URL = thisdeal.sArticle_URL;
    this.sShort_URLText = thisdeal.sShort_URLText;
    this.sArticle_URLText = thisdeal.sArticle_URLText;
    this.hasDescription = thisdeal.hasDescription;
  }

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
//@Override
//public String toString() {
//    StringBuilder builder = new StringBuilder();
//    builder.append("Deal [hasDescription=");
//    builder.append(hasDescription);
//    builder.append(", nDealId=");
//    builder.append(nDealId);
//    builder.append(", XRate=");
//    builder.append(XRate);
//    builder.append(", sFile_name=");
//    builder.append(sFile_name);
//    builder.append(", sDAmount=");
//    builder.append(sDAmount);
//    builder.append(", sPublishToHome=");
//    builder.append(sPublishToHome);
//    builder.append(", sStatus=");
//    builder.append(sStatus);
//    builder.append(", sTitle=");
//    builder.append(sTitle);
//    builder.append(", sShort_description=");
//    builder.append(sShort_description);
//    builder.append(", sDescription=");
//    builder.append(sDescription);
//    builder.append(", sAnnounced=");
//    builder.append(sAnnounced);
//    builder.append(", sProduct_service=");
//    builder.append(sProduct_service);
//    builder.append(", sSector=");
//    builder.append(sSector);
//    builder.append(", sClient=");
//    builder.append(sClient);
//    builder.append(", nAmount=");
//    builder.append(nAmount);
//    builder.append(", sCurrency=");
//    builder.append(sCurrency);
//    builder.append(", sCountry=");
//    builder.append(sCountry);
//    builder.append(", sRole_1=");
//    builder.append(sRole_1);
//    builder.append(", sRole_2=");
//    builder.append(sRole_2);
//    builder.append(", sContact_name=");
//    builder.append(sContact_name);
//    builder.append(", sContact_tel_number=");
//    builder.append(sContact_tel_number);
//    builder.append(", sSegment=");
//    builder.append(sSegment);
//    builder.append(", sRegion=");
//    builder.append(sRegion);
//    builder.append(", sPublishStatus=");
//    builder.append(sPublishStatus);
//    builder.append(", sc_consent=");
//    builder.append(sc_consent);
//    builder.append(", sc_confidentiality=");
//    builder.append(sc_confidentiality);
//    builder.append(", sc_clearance=");
//    builder.append(sc_clearance);
//    builder.append(", sc_consulted=");
//    builder.append(sc_consulted);
//    builder.append(", sShort_URL=");
//    builder.append(sShort_URL);
//    builder.append(", sArticle_URL=");
//    builder.append(sArticle_URL);
//    builder.append(", sShort_URLText=");
//    builder.append(sShort_URLText);
//    builder.append(", sArticle_URLText=");
//    builder.append(sArticle_URLText);
//    builder.append("]");
//    return builder.toString();
//}

}