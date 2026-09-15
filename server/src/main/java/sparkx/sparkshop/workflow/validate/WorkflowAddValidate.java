package sparkx.sparkshop.workflow.validate;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 新建编排入参（名称/描述）。id 由后端生成，故不在此约束。
 */
@Data
public class WorkflowAddValidate implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    private String description;
}
