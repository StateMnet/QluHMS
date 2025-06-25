package com.ruoyi.hotel.pojo;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 订单信息对象 hotel_order
 *
 * @author ruoyi
 * @date 2023-07-10
 */
public class HotelOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 订单号 */
    private Long orderId;

    /** 房间类型 */
    @Excel(name = "房间类型")
    private String roomType;

    /** 房间号 */
    @Excel(name = "房间号")
    private Long roomId;

    /** 入住人id */
    @Excel(name = "入住人id")
    private Long userId;

    /** 入住人姓名 */
    @Excel(name = "入住人姓名")
    private String userName;

    /** 同住人姓名 */
    @Excel(name = "同住人姓名")
    private String othersName;

    /** 入住时间 */
    @Excel(name = "入住时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date checkInTime;

    /** 退房时间 */
    @Excel(name = "退房时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date checkOutTime;

    /** 订单状态（0：未入住,1：正在入住，2：已退房） */
    @Excel(name = "订单状态", readConverterExp = "0=未入住,1=正在入住,2=已退房")
    private Integer status;

    private Long roomNumber; // 新增，仅用于接收前端参数

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setOthersName(String othersName) {
        this.othersName = othersName;
    }

    public String getOthersName() {
        return othersName;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public Long getRoomNumber() { return roomNumber; }
    public void setRoomNumber(Long roomNumber) { this.roomNumber = roomNumber; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("orderId", getOrderId())
                .append("roomType", getRoomType())
                .append("roomId", getRoomId())
                .append("roomNumber", getRoomNumber())
                .append("userId", getUserId())
                .append("userName", getUserName())
                .append("othersName", getOthersName())
                .append("checkInTime", getCheckInTime())
                .append("checkOutTime", getCheckOutTime())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
