package sparkx.sparkshop.workflow.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 下游是否为回复节点的检测结果。
 */
@Data
public class NextAnswerNodeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 是否是回复节点 */
    private boolean nodeIsAnswer;

    /** 回复类型 1:本节点输出内容 2:其他回复内容 */
    private int answerType;
}
