package sparkx.sparkshop.knowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 段落/子块展示 VO
 */
@Data
@Schema(description = "段落/子块")
public class ChunkVo implements Serializable {

    @Schema(description = "子块 id")
    private String id;

    @Schema(description = "知识库 id")
    private String kbId;

    @Schema(description = "文本内容")
    private String content;

    @Schema(description = "元数据 JSON")
    private String metadata;

    @Schema(description = "所属父块 id（文档维度列表回填，便于前端分组）")
    private String parentId;

    @Schema(description = "所属父块全文（文档维度列表回填，折叠展示用）")
    private String parentContent;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}
