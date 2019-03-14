package com.stocks.cb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cb {

@SerializedName("responseId")
@Expose
public String responseId;
@SerializedName("queryResult")
@Expose
public QueryResult queryResult;
@SerializedName("originalDetectIntentRequest")
@Expose
public OriginalDetectIntentRequest originalDetectIntentRequest;
@SerializedName("session")
@Expose
public String session;
public String getResponseId() {
	return responseId;
}
public void setResponseId(String responseId) {
	this.responseId = responseId;
}
public QueryResult getQueryResult() {
	return queryResult;
}
public void setQueryResult(QueryResult queryResult) {
	this.queryResult = queryResult;
}
public OriginalDetectIntentRequest getOriginalDetectIntentRequest() {
	return originalDetectIntentRequest;
}
public void setOriginalDetectIntentRequest(OriginalDetectIntentRequest originalDetectIntentRequest) {
	this.originalDetectIntentRequest = originalDetectIntentRequest;
}
public String getSession() {
	return session;
}
public void setSession(String session) {
	this.session = session;
}



}