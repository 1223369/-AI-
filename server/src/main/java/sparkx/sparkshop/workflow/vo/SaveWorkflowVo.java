package sparkx.sparkshop.workflow.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 流程详情返回（含 flowData）。
 */
@Data
public class SaveWorkflowVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 编排 id */
    private String id;

    /** 流程设计 JSON */
    private String flowData;
}
