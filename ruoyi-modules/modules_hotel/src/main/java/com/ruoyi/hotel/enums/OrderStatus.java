package com.ruoyi.hotel.enums;

/**
 * 订单状态枚举
 */
public enum OrderStatus {
    NOT_CHECKED_IN(0, "未入住"),
    CHECKED_IN(1, "正在入住"),
    CHECKED_OUT(2, "已退房");

    private final Integer code;
    private final String info;

    OrderStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static OrderStatus getByCode(Integer code) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}