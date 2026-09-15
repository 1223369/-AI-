package sparkx.sparkshop.knowledge.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sparkx.sparkshop.system.vo.PageQuery;

import java.io.Serializable;

/**
 * 段落/子块列表入参（按知识库或文档）。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "段落列表入参")
public class ParagraphListValidate extends PageQuery implements Serializable {

    @Schema(description = "知识库 id")
    private String kbId;

    @Schema(description = "文档 id（体现在 metadata.document_id，兜底 like）")
    private String documentId;
}
