package sparkx.sparkshop.knowledge.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 更新聊天会话入参（仅标题 / 描述）。
 */
@Data
@Schema(description = "更新聊天会话入参")
public class ChatSessionUpdateValidate implements Serializable {

    @Schema(description = "标题")
    private String title;

    @Schema(description = "描述")
    private String description;
}
