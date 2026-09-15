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
 * 编排流程节点运行时上下文：每个节点执行一行，按 step 排序。
 */
@Data
@TableName("workflow_runtime_context")
public class WorkflowRuntimeContext implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @TableField(value = "id")
    private Long id;

    /** 运行时 id */
    @TableField(value = "runtime_id")
    private Long runtimeId;

    /** 节点类型 */
    @TableField(value = "node_type")
    private String nodeType;

    /** 步骤号 */
    @TableField(value = "step")
    private Integer step;

    /** 出参数据 JSON */
    @TableField(value = "output_data")
    private String outputData;

    /** 模型数据 JSON（节点配置 + token 用量） */
    @TableField(value = "model_data")
    private String modelData;

    /** X6 节点 id */
    @TableField(value = "cell")
    private String cell;

    @TableField(value = "created_at", update = "now()")
    private LocalDateTime createdAt;

    @TableField(value = "updated_at")
    private LocalDateTime updatedAt;
}
