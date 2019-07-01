package com.ovo.rxandroiddemo.been;

public class ReqMsgSubscribe {
    private int version;
    private String msgType;
    private String requestIndex;
    private int retCode;
    private String retMsg;
    private String payload;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getRequestIndex() {
        return requestIndex;
    }

    public void setRequestIndex(String requestIndex) {
        this.requestIndex = requestIndex;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
