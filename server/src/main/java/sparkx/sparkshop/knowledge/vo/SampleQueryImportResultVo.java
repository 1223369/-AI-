package sparkx.sparkshop.knowledge.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 样例查询批量导入结果（同步返回）。
 */
@Data
public class SampleQueryImportResultVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Excel 解析后的总行数（清洗后） */
    private int total;

    /** 成功导入条数 */
    private int success;

    /** 失败条数（入库异常） */
    private int failed;

    /** 跳过条数（问题/答案为空等） */
    private int skipped;

    public static SampleQueryImportResultVo of(int total, int success, int failed, int skipped) {
        SampleQueryImportResultVo r = new SampleQueryImportResultVo();
        r.total = total;
        r.success = success;
        r.failed = failed;
        r.skipped = skipped;
        return r;
    }
}
