package net.longfalcon.newsj.ws;

/**
 * User: Sten Martinez
 * Date: 10/21/15
 * Time: 11:58 AM
 */
public class GoogleSearchResponse {
    private GoogleSearchResponseData responseData;
    private String responseDetails;
    private Integer responseStatus;

    public GoogleSearchResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(GoogleSearchResponseData responseData) {
        this.responseData = responseData;
    }

    public String getResponseDetails() {
        return responseDetails;
    }

    public void setResponseDetails(String responseDetails) {
        this.responseDetails = responseDetails;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }
}
