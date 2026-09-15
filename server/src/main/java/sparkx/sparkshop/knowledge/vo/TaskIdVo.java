package sparkx.sparkshop.knowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 异步任务回执（统一承载 taskId，供前端轮询配套的 /progress 接口）。
 *
 * <p>取代各 Controller 原本手搓的 {@code Map<String,Object> + data.put("taskId", ...)}。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "异步任务回执")
public class TaskIdVo implements Serializable {

    @Schema(description = "任务 id（前端用它轮询配套进度接口）")
    private String taskId;
}
