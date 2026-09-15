package sparkx.sparkshop.workflow.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 流程边（连线）。
 */
@Data
public class EdgeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 边 id */
    private String id;

    /** 起始节点 */
    private String source;

    /** 起始桩点 */
    private String sourcePort;

    /** 目标节点（同源并联时多个） */
    private List<String> target;
}
