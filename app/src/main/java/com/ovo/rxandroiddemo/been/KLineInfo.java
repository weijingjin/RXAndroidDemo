package com.ovo.rxandroiddemo.been;

/**
 * k线数据
 */
public class KLineInfo {
    private ReqMsgSubscribe reqMsgSubscribe;
    private ReqKLine reqKLine;

    public ReqMsgSubscribe getReqMsgSubscribe() {
        return reqMsgSubscribe;
    }

    public void setReqMsgSubscribe(ReqMsgSubscribe reqMsgSubscribe) {
        this.reqMsgSubscribe = reqMsgSubscribe;
    }

    public ReqKLine getReqKLine() {
        return reqKLine;
    }

    public void setReqKLine(ReqKLine reqKLine) {
        this.reqKLine = reqKLine;
    }

}
