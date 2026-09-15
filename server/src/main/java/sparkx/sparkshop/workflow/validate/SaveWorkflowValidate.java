package sparkx.sparkshop.workflow.validate;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 保存流程设计入参。
 */
@Data
public class SaveWorkflowValidate implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 编排 id */
    @NotEmpty(message = "编排 id 不能为空")
    private String id;

    /** 流程设计 JSON */
    @NotEmpty(message = "设计数据不能为空")
    private String flowData;
}
