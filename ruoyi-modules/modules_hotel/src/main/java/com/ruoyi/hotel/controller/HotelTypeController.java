package com.ruoyi.hotel.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.hotel.pojo.HotelType;
import com.ruoyi.hotel.service.IHotelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 房型信息Controller
 */
@RestController
@RequestMapping("/type")
public class HotelTypeController extends BaseController {
    @Autowired
    private IHotelTypeService hotelTypeService;

    /**
     * 查询房型列表
     */
    @GetMapping("/list")
    public TableDataInfo list(HotelType hotelType) {
        startPage();
        List<HotelType> list = hotelTypeService.selectHotelTypeList(hotelType);
        return getDataTable(list);
    }

    /**
     * 导出房型列表
     */
    @Log(title = "房型信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response, HotelType hotelType) {
        List<HotelType> list = hotelTypeService.selectHotelTypeList(hotelType);
        ExcelUtil<HotelType> util = new ExcelUtil<>(HotelType.class);
        util.exportExcel(response, list, "房型数据");
    }

    /**
     * 获取房型详细信息
     */
    @GetMapping(value = "/{typeId}")
    public AjaxResult getInfo(@PathVariable("typeId") Long typeId) {
        return success(hotelTypeService.selectHotelTypeById(typeId));
    }

    /**
     * 新增房型
     */
    @Log(title = "房型信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HotelType hotelType) {
        return toAjax(hotelTypeService.insertHotelType(hotelType));
    }

    /**
     * 修改房型
     */
    @Log(title = "房型信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HotelType hotelType) {
        return toAjax(hotelTypeService.updateHotelType(hotelType));
    }

    /**
     * 删除房型
     */
    @Log(title = "房型信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{typeIds}")
    public AjaxResult remove(@PathVariable Long[] typeIds) {
        return toAjax(hotelTypeService.deleteHotelTypeByIds(typeIds));
    }

    /**
     * 获取所有房型（用于下拉框）
     */
    @GetMapping("/all")
    public AjaxResult getAllTypes() {
        List<HotelType> list = hotelTypeService.selectHotelTypeList(new HotelType());
        // 只返回typeId和name字段
        return AjaxResult.success(list.stream().map(type -> {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("typeId", type.getTypeId());
            map.put("name", type.getName());
            return map;
        }).collect(java.util.stream.Collectors.toList()));
    }
} 