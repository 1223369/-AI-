package sparkx.sparkshop.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果，对齐前端 { data, total }
 */
@Data
public class PageResult<T> implements Serializable {

    /**
     * 当前页数据
     */
    @Schema(description = "当前页数据")
    private List<T> data;

    /**
     * 总记录数
     */
    @Schema(description = "总记录数")
    private Long total;

    /** 默认无参构造 */
    public PageResult() {
    }

    /**
     * 全参构造
     *
     * @param data  当前页数据
     * @param total 总记录数
     */
    public PageResult(List<T> data, Long total) {
        this.data = data;
        this.total = total;
    }
}
