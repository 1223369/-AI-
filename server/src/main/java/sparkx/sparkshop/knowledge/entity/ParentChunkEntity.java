package sparkx.sparkshop.knowledge.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 父块实体 —— 只存全文，不存向量（不参与检索，仅被子块命中后展开）（移植自 sparkxV2）。
 */
@Data
@TableName("parent_chunks")
public class ParentChunkEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @TableField(value = "kb_id")
    private String kbId;

    @TableField(value = "content")
    private String content;

    @TableField(value = "metadata")
    private String metadata;

    @TableField(value = "created_at")
    private LocalDateTime createdAt;

    public ParentChunkEntity() {}

    public ParentChunkEntity(String id, String kbId, String content, String metadata) {
        this.id = id;
        this.kbId = kbId;
        this.content = content;
        this.metadata = metadata;
    }
}
