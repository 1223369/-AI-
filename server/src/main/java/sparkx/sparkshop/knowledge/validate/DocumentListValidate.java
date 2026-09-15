package sparkx.sparkshop.knowledge.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sparkx.sparkshop.system.vo.PageQuery;

import java.io.Serializable;

/**
 * 文档列表入参（按知识库 + 关键词）。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "文档列表入参")
public class DocumentListValidate extends PageQuery implements Serializable {

    @Schema(description = "知识库 id")
    private String kbId;
}
