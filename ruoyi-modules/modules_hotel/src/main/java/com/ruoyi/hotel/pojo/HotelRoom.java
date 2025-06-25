package com.ruoyi.hotel.pojo;


import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 房间信息对象 hotel_room
 */
public class HotelRoom extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 房间id */
    private Long roomId;

    /** 房间类型 */
//    @Excel(name = "房间类型")
    private Long roomType;

    /** 房间类型 */
    @Excel(name = "房间类型")
    private String roomTypeName;

    /** 房间号 */
    @Excel(name = "房间号")
    private Long roomNumber;

    /** 最大入住人数 */
    @Excel(name = "最大入住人数")
    private Long peopleNum;

    /** 房间状态 */
    private Long status;

    /** 房间备注 */
    @Excel(name = "房间备注")
    private String roomDesc;

    // ========== Getter & Setter ==========

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getRoomType() {
        return roomType;
    }

    public void setRoomType(Long roomType) {
        this.roomType = roomType;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setroomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public Long getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Long roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Long getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Long peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getRoomDesc() {
        return roomDesc;
    }

    public void setRoomDesc(String roomDesc) {
        this.roomDesc = roomDesc;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }


    // ========== toString() ========== (可选)
    @Override
    public String toString() {
        return "HotelRoom{" +
                "roomId=" + roomId +
                ", roomType=" + roomType +
                ", roomTypeName=" + roomTypeName +
                ", roomNumber=" + roomNumber +
                ", peopleNum=" + peopleNum +
                ", status=" + status +
                ", roomDesc='" + roomDesc + '\'' +
                '}';
    }
}