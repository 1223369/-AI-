package sparkx.sparkshop.workflow.validate;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 编排元信息（名称/描述）入参。
 */
@Data
public class WorkflowMetaValidate implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "编排 id 不能为空")
    private String id;

    private String name;

    private String description;
}
