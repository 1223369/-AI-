package sparkx.sparkshop.workflow.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 执行详情（按 step 排序的节点上下文）。
 */
@Data
public class RuntimeContextVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 节点类型 */
    private String nodeType;

    /** 步骤号 */
    private Integer step;

    /** 出参数据 JSON */
    private String outputData;

    /** 模型信息 JSON */
    private String modelData;

    /** X6 节点 id（调试详情关联节点用） */
    private String cell;
}
