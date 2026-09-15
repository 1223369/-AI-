package sparkx.sparkshop.system.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询基类
 */
@Data
public class PageValidate implements Serializable {

    /**
     * 页码，默认 1
     */
    @Schema(description = "页码，默认 1")
    private Integer page = 1;

    /**
     * 每页条数，默认 10
     */
    @Schema(description = "每页条数，默认 10")
    private Integer limit = 10;
}
