package com.ruoyi.hotel.service;

import com.ruoyi.hotel.pojo.HotelOrder;
import java.util.List;

/**
 * 订单信息Service接口
 */
public interface IHotelOrderService {
    /**
     * 查询订单信息
     * @param orderId 订单id
     * @return 订单信息
     */
    HotelOrder selectHotelOrderById(Long orderId);

    /**
     * 查询订单信息列表
     * @param hotelOrder 订单信息
     * @return 订单信息集合
     */
    List<HotelOrder> selectHotelOrderList(HotelOrder hotelOrder);

    /**
     * 新增订单信息（自动处理roomNumber转roomId）
     * @param hotelOrder 订单信息
     * @return 结果
     */
    int insertHotelOrder(HotelOrder hotelOrder);

    /**
     * 修改订单信息
     * @param hotelOrder 订单信息
     * @return 结果
     */
    int updateHotelOrder(HotelOrder hotelOrder);

    /**
     * 删除订单信息
     * @param orderId 订单id
     * @return 结果
     */
    int deleteHotelOrderById(Long orderId);

    /**
     * 批量删除订单信息
     * @param orderIds 需要删除的数据id
     * @return 结果
     */
    int deleteHotelOrderByIds(Long[] orderIds);
} 