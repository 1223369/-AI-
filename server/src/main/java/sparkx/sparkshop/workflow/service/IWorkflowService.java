package sparkx.sparkshop.workflow.service;

import sparkx.sparkshop.system.vo.PageQuery;
import sparkx.sparkshop.system.vo.PageResult;
import sparkx.sparkshop.workflow.validate.SaveWorkflowValidate;
import sparkx.sparkshop.workflow.validate.WorkflowAddValidate;
import sparkx.sparkshop.workflow.validate.WorkflowMetaValidate;
import sparkx.sparkshop.workflow.vo.RuntimeContextVo;
import sparkx.sparkshop.workflow.vo.SaveWorkflowVo;
import sparkx.sparkshop.workflow.vo.WorkflowVo;

import java.util.List;

/**
 * 编排流程服务。
 */
public interface IWorkflowService {

    /** 分页列表 */
    PageResult<WorkflowVo> page(PageQuery query);

    /** 流程详情（含 flowData） */
    SaveWorkflowVo info(String id);

    /** 新建（返回 id） */
    WorkflowVo add(WorkflowAddValidate validate);

    /** 改名称/描述 */
    void editMeta(WorkflowMetaValidate validate);

    /** 保存流程图 */
    void save(SaveWorkflowValidate validate);

    /** 删除（级联清 runtime/context） */
    void delete(String id);

    /** 复制流程（返回新 id） */
    WorkflowVo copy(String id);

    /** 执行详情（按 step 排序） */
    List<RuntimeContextVo> runtimeDetail(long runtimeId);
}
