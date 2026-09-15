package sparkx.sparkshop.knowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 命中测试结果 VO
 */
@Data
@Schema(description = "命中测试结果")
public class HitTestVo implements Serializable {

    @Schema(description = "子块 id")
    private String chunkId;

    @Schema(description = "文本内容")
    private String content;

    @Schema(description = "相似度分数")
    private Double score;

    @Schema(description = "来源文档名")
    private String documentName;

    @Schema(description = "元数据 JSON")
    private String metadata;
}
