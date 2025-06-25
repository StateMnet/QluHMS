package com.ruoyi.hotel.pojo.vo;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

public class HotelRoomVO extends BaseEntity {
    /** 房间id */
    private Long roomId;

    /** 房间类型 */
    private Long roomTypeName;

    /** 房间号 */
    private Long roomNumber;

    /** 最大入住人数 */
    private Long peopleNum;

    /** 房间备注 */
    private String roomDesc;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomType(Long roomTypeName) {
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

    // ========== toString() ========== (可选)
    @Override
    public String toString() {
        return "HotelRoom{" +
                "roomId=" + roomId +
                ", roomType=" + roomTypeName +
                ", roomNumber=" + roomNumber +
                ", peopleNum=" + peopleNum +
                ", roomDesc='" + roomDesc + '\'' +
                '}';
    }

}
