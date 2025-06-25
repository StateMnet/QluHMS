package com.ruoyi.hotel.service;

import com.ruoyi.hotel.pojo.HotelType;
import java.util.List;

/**
 * 房间类型 服务层
 */
public interface IHotelTypeService {
    /**
     * 查询房间类型
     */
    HotelType selectHotelTypeById(Long typeId);

    /**
     * 查询房间类型列表
     */
    List<HotelType> selectHotelTypeList(HotelType hotelType);

    /**
     * 新增房间类型
     */
    int insertHotelType(HotelType hotelType);

    /**
     * 修改房间类型
     */
    int updateHotelType(HotelType hotelType);

    /**
     * 删除房间类型信息
     */
    int deleteHotelTypeByIds(Long[] typeIds);
}