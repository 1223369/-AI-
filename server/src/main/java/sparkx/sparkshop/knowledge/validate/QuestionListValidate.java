package sparkx.sparkshop.knowledge.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sparkx.sparkshop.system.vo.PageQuery;

import java.io.Serializable;

/**
 * 问题（Q&A）列表入参（按知识库）。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "问题列表入参")
public class QuestionListValidate extends PageQuery implements Serializable {

    @Schema(description = "知识库 id")
    private String kbId;
}
