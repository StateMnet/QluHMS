package com.ruoyi.hotel.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.hotel.pojo.HotelRoom;
import com.ruoyi.hotel.service.IHotelRoomService;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


/**
 * 房间信息Controller
 */
@RestController
@RequestMapping("/room")
public class HotelRoomController extends BaseController {
    @Autowired
    private IHotelRoomService hotelRoomService;

    /**
     * 查询房间信息列表
     */

    @GetMapping("/list")
    public TableDataInfo list(HotelRoom hotelRoom) {
        System.out.println("hotelRoom"+hotelRoom);
        startPage();
        List<HotelRoom> list = hotelRoomService.selectHotelRoomList(hotelRoom);
        System.err.println("list"+list);
        return getDataTable(list);
    }

    /**
     * 导出房间信息列表
     */

    @Log(title = "房间信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response,HotelRoom hotelRoom) {
        List<HotelRoom> list = hotelRoomService.selectHotelRoomList(hotelRoom);
        ExcelUtil<HotelRoom> util = new ExcelUtil<HotelRoom>(HotelRoom.class);
        util.exportExcel(response,list, "房间数据");
    }

    /**
     * 获取房间信息详细信息
     */

    @GetMapping(value = "/{roomId}")
    public AjaxResult getInfo(@PathVariable("roomId") Long roomId) {
        return success(hotelRoomService.selectHotelRoomById(roomId));
    }

    /**
     * 新增房间信息
     */

    @Log(title = "房间信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HotelRoom hotelRoom) {
        return toAjax(hotelRoomService.insertHotelRoom(hotelRoom));
    }

    @GetMapping("/getAllType")
    public AjaxResult getAllType() {
        HashMap<String,String> result =hotelRoomService.selectAllRoomType();
        System.err.println(result);
        return success(result);
    }

    /**
     * 修改房间信息
     */

    @Log(title = "房间信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HotelRoom hotelRoom) {
        return toAjax(hotelRoomService.updateHotelRoom(hotelRoom));
    }

    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody HotelRoom hotelRoom) {
//        HotelRoom hotelRoom = new HotelRoom();
//        hotelRoom.setRoomId(Long.parseLong(roomId));
//        hotelRoom.setStatus(Long.parseLong(status));
        return toAjax(hotelRoomService.updateHotelRoom(hotelRoom));
    }

    /**
     * 删除房间信息
     */
    @Log(title = "房间信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{roomIds}")
    public AjaxResult remove(@PathVariable Long[] roomIds) {
        return toAjax(hotelRoomService.deleteHotelRoomByIds(roomIds));
    }


}