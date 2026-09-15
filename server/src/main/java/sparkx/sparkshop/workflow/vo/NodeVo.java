package sparkx.sparkshop.workflow.vo;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 流程节点。
 */
@Data
public class NodeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 节点 id（X6 cell id） */
    private String id;

    /** 类型（start-node / llm-node / answer-node ...） */
    private String shape;

    /** 节点数据 */
    private JSONObject data;
}
