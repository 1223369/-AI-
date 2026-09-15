package sparkx.sparkshop.knowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 单个切片预览项（试切返回的单块）。
 *
 * <p>开启父子分块时，每个子块可携带其归属的父块全文（parentContext），
 * 供前端折叠查看；开启问题生成时携带由 LLM 生成的问题列表。
 */
@Data
@Schema(description = "切片预览项")
public class PreviewChunkVo implements Serializable {

    @Schema(description = "切片标题（可空）")
    private String title;

    @Schema(description = "切片正文")
    private String content;

    @Schema(description = "字符数")
    private Integer charCount;

    @Schema(description = "父块上下文（开启父子分块时携带，前端折叠查看；否则为空）")
    private String parentContext;

    @Schema(description = "父块序号（开启父子分块时携带，标识归属第几个父块）")
    private Integer parentIndex;

    @Schema(description = "该子块生成的问题列表（开启问题生成时携带）")
    private List<String> questions;

    public PreviewChunkVo() {}

    public PreviewChunkVo(String title, String content) {
        this.title = title;
        this.content = content;
        this.charCount = content == null ? 0 : content.length();
    }
}
