package com.ruoyi.hotel.service.impl;

import com.ruoyi.hotel.mapper.HotelOrderMapper;
import com.ruoyi.hotel.mapper.HotelRoomMapper;
import com.ruoyi.hotel.pojo.HotelOrder;
import com.ruoyi.hotel.pojo.HotelRoom;
import com.ruoyi.hotel.service.IHotelOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelOrderServiceImpl implements IHotelOrderService {
    @Autowired
    private HotelOrderMapper hotelOrderMapper;
    @Autowired
    private HotelRoomMapper hotelRoomMapper;

    @Override
    public HotelOrder selectHotelOrderById(Long orderId) {
        HotelOrder order = hotelOrderMapper.selectHotelOrderById(orderId);
        if (order != null && order.getRoomId() != null) {
            HotelRoom room = hotelRoomMapper.selectHotelRoomById(order.getRoomId());
            if (room != null) {
                order.setRoomNumber(room.getRoomNumber());
            }
        }
        return order;
    }

    @Override
    public List<HotelOrder> selectHotelOrderList(HotelOrder hotelOrder) {
        List<HotelOrder> list = hotelOrderMapper.selectHotelOrderList(hotelOrder);
        for (HotelOrder order : list) {
            if (order.getRoomId() != null) {
                HotelRoom room = hotelRoomMapper.selectHotelRoomById(order.getRoomId());
                if (room != null) {
                    order.setRoomNumber(room.getRoomNumber());
                }
            }
        }
        return list;
    }

    @Override
    public int insertHotelOrder(HotelOrder hotelOrder) {
        // 处理roomNumber转roomId逻辑
        if (hotelOrder.getRoomId() == null && hotelOrder.getRoomNumber() != null) {
            HotelRoom room = hotelRoomMapper.selectHotelRoomByRoomNumber(hotelOrder.getRoomNumber());
            System.err.println(room);
            if (room != null) {
                hotelOrder.setRoomId(room.getRoomId());
            } else {
                throw new RuntimeException("房间号不存在");
            }
        }
        hotelOrder.setRoomNumber(null); // 防止误入库
        return hotelOrderMapper.insertHotelOrder(hotelOrder);
    }

    @Override
    public int updateHotelOrder(HotelOrder hotelOrder) {
        // 处理roomNumber转roomId逻辑
        if (hotelOrder.getRoomId() == null && hotelOrder.getRoomNumber() != null) {
            HotelRoom room = hotelRoomMapper.selectHotelRoomByRoomNumber(hotelOrder.getRoomNumber());
            if (room != null) {
                hotelOrder.setRoomId(room.getRoomId());
            } else {
                throw new RuntimeException("房间号不存在");
            }
        }
        hotelOrder.setRoomNumber(null); // 防止误入库
        return hotelOrderMapper.updateHotelOrder(hotelOrder);
    }

    @Override
    public int deleteHotelOrderById(Long orderId) {
        return hotelOrderMapper.deleteHotelOrderById(orderId);
    }

    @Override
    public int deleteHotelOrderByIds(Long[] orderIds) {
        return hotelOrderMapper.deleteHotelOrderByIds(orderIds);
    }
} 