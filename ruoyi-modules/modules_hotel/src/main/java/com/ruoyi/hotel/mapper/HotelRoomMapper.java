package com.ruoyi.hotel.mapper;

import com.ruoyi.hotel.pojo.HotelRoom;
import java.util.List;

/**
 * 房间信息Mapper接口
 */
public interface HotelRoomMapper {
    /**
     * 查询房间信息
     * 
     * @param roomId 房间id
     * @return 房间信息
     */
    HotelRoom selectHotelRoomById(Long roomId);

    /**
     * 查询房间信息列表
     * 
     * @param hotelRoom 房间信息
     * @return 房间信息集合
     */
    List<HotelRoom> selectHotelRoomList(HotelRoom hotelRoom);

    /**
     * 新增房间信息
     * 
     * @param hotelRoom 房间信息
     * @return 结果
     */
    int insertHotelRoom(HotelRoom hotelRoom);

    /**
     * 修改房间信息
     * 
     * @param hotelRoom 房间信息
     * @return 结果
     */
    int updateHotelRoom(HotelRoom hotelRoom);

    /**
     * 删除房间信息
     * 
     * @param roomId 房间id
     * @return 结果
     */
    int deleteHotelRoomById(Long roomId);

    /**
     * 批量删除房间信息
     * 
     * @param roomIds 需要删除的数据id
     * @return 结果
     */
    int deleteHotelRoomByIds(Long[] roomIds);

    /**
     * 通过房间号查询房间信息
     * @param roomNumber 房间号
     * @return 房间信息
     */
    HotelRoom selectHotelRoomByRoomNumber(Long roomNumber);
}