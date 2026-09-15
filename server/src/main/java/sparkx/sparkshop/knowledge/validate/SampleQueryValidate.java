package sparkx.sparkshop.knowledge.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 样例查询新增/编辑参数
 */
@Data
@Schema(description = "样例查询新增/编辑参数")
public class SampleQueryValidate implements Serializable {

    @Schema(description = "样例 id（编辑时必填）")
    private Long id;

    @Schema(description = "问题文本（参与向量化）")
    @NotBlank(message = "问题不能为空")
    private String question;

    @Schema(description = "答案文本（命中后直接返回）")
    @NotBlank(message = "答案不能为空")
    private String answer;

    @Schema(description = "状态：1启用 2禁用")
    private Integer status;
}
