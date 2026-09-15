package sparkx.sparkshop.knowledge.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 意图节点删除入参。
 */
@Data
@Schema(description = "意图节点删除入参")
public class IntentNodeDeleteValidate implements Serializable {

    @Schema(description = "节点 id")
    @NotBlank(message = "节点 id 不能为空")
    private String id;
}
