package com.ruoyi.hotel.pojo;


import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 房间类型对象 hotel_type
 */
public class HotelType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 房间类型ID */
    private Long typeId;

    /** 房间类型名称 */
    @Excel(name = "房间类型名称")
    private String name;

    /** 设施 */
    @Excel(name = "设施")
    private String amenities;

    /** 最大入住人数 */
    @Excel(name = "最大入住人数")
    private String capacity;

    /** 图片URL */
    @Excel(name = "图片URL")
    private String imageUrl;

    //=================== Getter & Setter ===================//

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    //=================== toString ===================//

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("typeId", getTypeId())
                .append("name", getName())
                .append("amenities", getAmenities())
                .append("capacity", getCapacity())
                .append("imageUrl", getImageUrl())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}