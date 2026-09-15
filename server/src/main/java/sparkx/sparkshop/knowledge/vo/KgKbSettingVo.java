package sparkx.sparkshop.knowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 知识库级 KG 开关查询回执。
 *
 * <p>取代 Controller 手搓的 {@code {kbId, kgEnabled}} Map。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "知识库级 KG 开关")
public class KgKbSettingVo implements Serializable {

    @Schema(description = "知识库 id")
    private String kbId;

    @Schema(description = "KG 开关：1=启用 2=禁用")
    private Integer kgEnabled;
}
