package com.marinemammalapp.dataObjects;

public class SaveDataResponse {
    /*
    "statusCode": "300",
 	"statusMessage": "your request saved successfully",
     */

    public MammalDetails responseData;
    public String statusCode;
    public String statusMessage;

    public String objectid;
    public String createdAt;


    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }




    public MammalDetails getResponseData() {
        return responseData;
    }

    public void setResponseData(MammalDetails responseData) {
        this.responseData = responseData;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return "MammalDetailsRequest{" +
                "responseData=" + responseData +
                ", objectid='" + objectid + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}

