package com.ruoyi.hotel.service.impl;

import java.util.HashMap;
import java.util.List;

import com.ruoyi.hotel.mapper.HotelTypeMapper;
import com.ruoyi.hotel.pojo.HotelType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hotel.mapper.HotelRoomMapper;
import com.ruoyi.hotel.pojo.HotelRoom;
import com.ruoyi.hotel.service.IHotelRoomService;

/**
 * 房间信息ServiceImpl
 */
@Service
public class HotelRoomServiceImpl implements IHotelRoomService {

    @Autowired
    private HotelRoomMapper hotelRoomMapper;

    @Autowired
    private HotelTypeMapper hotelTypeMapper;

    @Override
    public HotelRoom selectHotelRoomById(Long roomId) {
        return hotelRoomMapper.selectHotelRoomById(roomId);
    }

    @Override
    public List<HotelRoom> selectHotelRoomList(HotelRoom hotelRoom) {
        System.out.println("hotelRoommapper"+hotelRoom);
        List<HotelRoom> hotelRooms = hotelRoomMapper.selectHotelRoomList(hotelRoom);
        return hotelRooms;
    }

    @Override
    public int insertHotelRoom(HotelRoom hotelRoom) {
        HotelType hotelType = hotelTypeMapper.selectHotelTypeById(hotelRoom.getRoomType());
        hotelRoom.setStatus(1l);
        hotelRoom.setroomTypeName(hotelType.getName());
        return hotelRoomMapper.insertHotelRoom(hotelRoom);
    }

    @Override
    public int updateHotelRoom(HotelRoom hotelRoom) {
        return hotelRoomMapper.updateHotelRoom(hotelRoom);
    }

    @Override
    public int deleteHotelRoomByIds(Long[] roomIds) {
        return hotelRoomMapper.deleteHotelRoomByIds(roomIds);
    }

    @Override
    public int deleteHotelRoomById(Long roomId) {
        return hotelRoomMapper.deleteHotelRoomById(roomId);
    }

    @Override
    public HashMap<String, String> selectAllRoomType() {
        HotelType hotelType = new HotelType();
        HashMap<String, String> hashMap = new HashMap<>();
        List<HotelType> hotelTypes = hotelTypeMapper.selectHotelTypeList(hotelType);
        hotelTypes.forEach(type->{
            hashMap.put(type.getTypeId().toString(),type.getName());
        });
        return hashMap;
    }
}