package com.ruoyi.hotel.service.impl;

import java.util.List;

import com.ruoyi.hotel.mapper.HotelTypeMapper;
import com.ruoyi.hotel.pojo.HotelType;
import com.ruoyi.hotel.service.IHotelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 房间类型 服务层实现
 */
@Service
public class HotelTypeServiceImpl implements IHotelTypeService {
    @Autowired
    private HotelTypeMapper hotelTypeMapper;

    /**
     * 查询房间类型
     */
    @Override
    public HotelType selectHotelTypeById(Long typeId) {
        return hotelTypeMapper.selectHotelTypeById(typeId);
    }

    /**
     * 查询房间类型列表
     */
    @Override
    public List<HotelType> selectHotelTypeList(HotelType hotelType) {
        return hotelTypeMapper.selectHotelTypeList(hotelType);
    }

    /**
     * 新增房间类型
     */
    @Override
    public int insertHotelType(HotelType hotelType) {

        return hotelTypeMapper.insertHotelType(hotelType);
    }

    /**
     * 修改房间类型
     */
    @Override
    public int updateHotelType(HotelType hotelType) {
        return hotelTypeMapper.updateHotelType(hotelType);
    }

    /**
     * 删除房间类型对象
     */
    @Override
    public int deleteHotelTypeByIds(Long[] typeIds) {
        return hotelTypeMapper.deleteHotelTypeByIds(typeIds);
    }
}