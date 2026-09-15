package sparkx.sparkshop.workflow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 编排流程主表。流程设计 JSON（X6 graph.toJSON()）持久化在 flow_data。
 */
@Data
@TableName("workflow")
public class Workflow implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键（UUID hex，业务生成） */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /** 编排名称 */
    @TableField(value = "name")
    private String name;

    /** 描述 */
    @TableField(value = "description")
    private String description;

    /** 流程设计 JSON（X6 graph.toJSON()） */
    @TableField(value = "flow_data")
    private String flowData;

    /** 1正常 2禁用 */
    @TableField(value = "status")
    private Integer status;

    @TableField(value = "created_at", update = "now()")
    private LocalDateTime createdAt;

    @TableField(value = "updated_at")
    private LocalDateTime updatedAt;
}
