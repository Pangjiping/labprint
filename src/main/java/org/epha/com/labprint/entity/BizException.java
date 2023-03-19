package org.epha.com.labprint.entity;

import org.epha.com.labprint.enums.ResponseEnum;

/**
 * @author: YRH
 * @date: 2023/3/19
 */
public class BizException extends RuntimeException {
    protected int errorCode;

    protected String errorMsg;

    public BizException() {
        super();
    }

    public BizException(ResponseEnum err) {
        super(err.getMsg());
        this.errorCode = err.getStatus();
        this.errorMsg = err.getMsg();
    }

    public BizException(ResponseEnum err, Throwable cause) {
        super(err.getMsg(), cause);
        this.errorCode = err.getStatus();
        this.errorMsg = err.getMsg();
    }

    public BizException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public BizException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BizException(int errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "BizException{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
