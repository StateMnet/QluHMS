package com.ruoyi.hotel.mapper;


import com.ruoyi.hotel.pojo.HotelType;

import java.util.List;

/**
 * 房间类型Mapper接口
 */
public interface HotelTypeMapper {
    /**
     * 查询房间类型
     */
    HotelType selectHotelTypeById(Long typeId);

    /**
     * 查询房间类型列表
     */
    List<HotelType> selectHotelTypeList(HotelType roomType);

    /**
     * 新增房间类型
     */
    int insertHotelType(HotelType roomType);

    /**
     * 修改房间类型
     */
    int updateHotelType(HotelType roomType);

    /**
     * 删除房间类型
     */
    int deleteHotelTypeById(Long typeId);

    /**
     * 批量删除房间类型
     */
    int deleteHotelTypeByIds(Long[] typeIds);
}
