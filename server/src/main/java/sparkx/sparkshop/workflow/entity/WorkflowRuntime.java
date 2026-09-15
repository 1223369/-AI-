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
 * 编排流程运行时：一次调试对话一行。
 */
@Data
@TableName("workflow_runtime")
public class WorkflowRuntime implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @TableField(value = "id")
    private Long id;

    /** 编排流程 id */
    @TableField(value = "workflow_id")
    private String workflowId;

    /** 用户 id */
    @TableField(value = "user_id")
    private String userId;

    /** 首问截断 */
    @TableField(value = "title")
    private String title;

    @TableField(value = "created_at", update = "now()")
    private LocalDateTime createdAt;

    @TableField(value = "updated_at")
    private LocalDateTime updatedAt;
}
