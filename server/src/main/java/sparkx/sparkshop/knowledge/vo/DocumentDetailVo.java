package sparkx.sparkshop.knowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 文档详情（含子块列表）VO
 */
@Data
@Schema(description = "文档详情")
public class DocumentDetailVo implements Serializable {

    @Schema(description = "文档信息")
    private DocumentVo document;

    @Schema(description = "子块列表")
    private List<ChunkVo> chunks;
}
