package sparkx.sparkshop.knowledge.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sparkx.sparkshop.system.vo.PageQuery;

import java.io.Serializable;

/**
 * 知识图谱抽取记录列表入参（按知识库 / 文档 / 状态过滤）。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "知识图谱抽取记录列表入参")
public class KgRecordListValidate extends PageQuery implements Serializable {

    @Schema(description = "知识库 id")
    private String kbId;

    @Schema(description = "文档 id")
    private String documentId;

    @Schema(description = "抽取状态")
    private String status;
}
