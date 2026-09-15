package sparkx.sparkshop.knowledge.service;

import sparkx.sparkshop.knowledge.vo.IntentNodeSaveVo;
import sparkx.sparkshop.knowledge.vo.IntentNodeTreeVo;

import java.util.List;

/**
 * 意图树后台管理服务
 */
public interface IntentNodeService {

    /**
     * 查询整棵意图树（含禁用节点，组装好 children）
     */
    List<IntentNodeTreeVo> tree();

    /**
     * 新增节点
     */
    void add(IntentNodeSaveVo vo);

    /**
     * 编辑节点
     */
    void edit(IntentNodeSaveVo vo);

    /**
     * 删除节点（含子节点，逻辑删除）
     */
    void delete(String id);

    /**
     * 批量启用
     */
    void batchEnable(List<String> ids);

    /**
     * 批量禁用
     */
    void batchDisable(List<String> ids);
}
