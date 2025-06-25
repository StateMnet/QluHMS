package com.ruoyi.hotel.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.hotel.pojo.HotelOrder;
import com.ruoyi.hotel.service.IHotelOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单信息Controller
 */
@RestController
@RequestMapping("/order")
public class HotelOrderController extends BaseController {
    @Autowired
    private IHotelOrderService hotelOrderService;

    /**
     * 查询订单信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(HotelOrder hotelOrder) {
        startPage();
        List<HotelOrder> list = hotelOrderService.selectHotelOrderList(hotelOrder);
        return getDataTable(list);
    }

    /**
     * 获取订单详细信息
     */
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId) {
        return success(hotelOrderService.selectHotelOrderById(orderId));
    }

    /**
     * 新增订单信息
     */
    @Log(title = "订单信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HotelOrder hotelOrder) {
        System.out.println(hotelOrder);
        return toAjax(hotelOrderService.insertHotelOrder(hotelOrder));
    }

    /**
     * 修改订单信息
     */
    @Log(title = "订单信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HotelOrder hotelOrder) {
        return toAjax(hotelOrderService.updateHotelOrder(hotelOrder));
    }

    /**
     * 修改订单状态
     */
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody HotelOrder hotelOrder) {
        return toAjax(hotelOrderService.updateHotelOrder(hotelOrder));
    }

    /**
     * 删除订单信息
     */
    @Log(title = "订单信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds) {
        return toAjax(hotelOrderService.deleteHotelOrderByIds(orderIds));
    }
} 