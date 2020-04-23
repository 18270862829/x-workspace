package com.afiona.common.model;

import com.baomidou.mybatisplus.annotation.*;
import com.deepexi.util.config.JsonDateSerializer;
import com.deepexi.util.pojo.AbstractObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * domain父类
 *
 * @author yangxi
 */
@Setter
@Getter
public class SuperEntity extends AbstractObject {

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键ID")
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("租户ID")
    private String tenantId;

    /**
     * 版本号，乐观锁
     */
    @ApiModelProperty("版本号码")
    private Integer version;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 逻辑删除
     */
    @TableLogic
    @ApiModelProperty("是否启用")
    private Long dr;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建人")
    private String createdBy;

    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonDateSerializer.class)
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新人")
    private String updatedBy;

    /**
     * 更新时间
     */
    @JsonSerialize(using = JsonDateSerializer.class)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private Date updatedTime;

}