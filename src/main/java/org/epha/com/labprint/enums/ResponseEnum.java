package org.epha.com.labprint.enums;

/**
 * @author: YRH
 * @date: 2023/3/3
 */
public enum ResponseEnum {
    SUCCESS(0, "SUCCESS"),
    ERROR(1001, "ERROR"),
    ILLEGAL_ARGUMENT(1002, "ILLEGAL_ARGUMENT"),
    USER_NOT_LOGIN(1003, "USER_NOT_LOGIN"),
    INVALID_CACHE_KEY(1004, "INVALID_CACHE_KEY"),
    CREATE_ORDER_FAIL(1005, "CREATE_ORDER_FAIL"),
    OVER_RATE_LIMIT(1006, "OVER_RATE_LIMIT"),
    CONSUMER_FAIL(1007, "CONSUMER_FAIL"),
    ADMIN_ROLE_HAS_EXIT(1008, "ADMIN_ROLE_HAS_EXIT"),
    DATABASE_ERROR(1009, "DATABASE_ERROR"),
    ERROR_EMAIL(1010, "ERROR_EMAIL"),
    UNEXPECTED(-1,"UNEXPECTED");

    private final int status;
    private final String msg;

    ResponseEnum(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

}
