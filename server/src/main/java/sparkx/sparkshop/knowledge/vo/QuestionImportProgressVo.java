package sparkx.sparkshop.knowledge.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 问题批量导入进度（缓存于 Redis，前端轮询读取）。
 */
@Data
public class QuestionImportProgressVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** processing / done / failed */
    private String status;

    /** 总条数（清洗后实际要处理的行数） */
    private int total;

    /** 已处理条数（success + failed） */
    private int done;

    /** 成功条数 */
    private int success;

    /** 失败条数 */
    private int failed;

    /** 兜底错误信息（任务级失败时填写） */
    private String message;

    public static QuestionImportProgressVo processing(int total) {
        QuestionImportProgressVo p = new QuestionImportProgressVo();
        p.status = "processing";
        p.total = total;
        return p;
    }
}
