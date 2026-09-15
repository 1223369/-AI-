# spark-x 技术栈与项目架构

企业级 AI 智能体开发平台：覆盖知识库入库、RAG 检索增强、智能体对话、工作流编排、MCP 工具调用。

---

## 1. 仓库结构

```
spark-x/
├── admin/          Vue3 管理后台
├── server/         Spring Boot 后端（包根 sparkx.sparkshop）
├── docker/         一键编排与初始化 SQL
│   ├── docker-compose.yml          拉预构建镜像
│   ├── docker-compose-local.yml    本地构建镜像
│   └── sql/sparkx.sql
└── README.md
```

前后端分离。后端按业务域分包，知识库（RAG）子系统自包含。

---

## 2. 技术栈

### 2.1 后端

| 层级 | 技术 | 版本 | 用途 |
|---|---|---|---|
| 语言 | Java | 17 | 运行时 |
| 框架 | Spring Boot | 3.4.13 | Web / 校验 / AOP / Actuator |
| ORM | MyBatis-Plus | 3.5.7 | PostgreSQL CRUD |
| LLM 编排 | LangChain4j | 1.18.1 | 对话 / 嵌入 / 重排统一封装 |
| MCP | langchain4j-mcp | 1.18.1-beta28 | 外部工具协议 |
| 图谱 | langchain4j-community-neo4j | 1.18.0-beta28 | 实体关系存储与检索 |
| 文档解析 | Tika / PDFBox / POI | LangChain4j 集成 | PDF / Office / 通用文档 |
| 中文分词 | HanLP | portable-1.8.6 | `tsvector` 关键词检索预分词 |
| 缓存 / 锁 | Redis 7 + Redisson | 3.31.0 | 验证码、权限缓存、会话摘要锁 |
| 对象存储 | MinIO | 8.5.12 | 企业云盘 `sparkx-drive` |
| HTTP | OkHttp | 4.12.0 | OpenAI 兼容 SSE |
| 上下文透传 | TransmittableThreadLocal | 2.14.5 | 线程池透传租户上下文 |
| 工具 | Hutool / Lombok | 5.8.27 | JWT、验证码、JSON |
| API 文档 | Springdoc OpenAPI | 2.8.17 | `/swagger-ui.html` |
| 可观测 | Micrometer + OTel bridge | SB 管理 | traceId 写入 MDC |

本地开发端口：`7126`。Docker 后端端口：`7026`。

### 2.2 前端

| 层级 | 技术 | 版本 | 用途 |
|---|---|---|---|
| 框架 | Vue 3 + TypeScript | 3.5 | SPA |
| 构建 | Vite | 5.4 | 开发 / 打包 |
| UI | Naive UI | 2.43 | 管理后台 |
| 状态 | Pinia | 2.3 | 全局状态 |
| 路由 | Vue Router | 4.5 | 页面路由 |
| HTTP | Alova | 3.3 | 接口请求 |
| 编排画布 | AntV X6 + x6-vue-shape | 2.18 | 工作流可视化 |
| 图谱可视化 | AntV G6 | 5.1 | 知识图谱展示 |
| 样式 | Tailwind CSS + Less | 3.4 / 4.4 | 布局与主题 |
| 对话渲染 | marked + highlight.js + KaTeX + Mermaid | — | Markdown / 代码 / 公式 / 图 |
| 文件预览 | @file-viewer | 2.1 | 文档预览 |

本地开发：`http://localhost:9911`，代理到 `http://localhost:7126`。  
Docker 前端：Nginx `http://localhost:8189`。

### 2.3 基础设施

| 组件 | 镜像 / 版本 | 端口 | 职责 |
|---|---|---|---|
| PostgreSQL + pgvector | pgvector:15 | 5432 | 业务库 + 向量检索 |
| Redis | redis:7-alpine | 16379→6379 | 缓存 / Redisson 锁 |
| MinIO | quay.io/minio | 9000 / 9001 | 对象存储 / 控制台 |
| Neo4j + APOC | neo4j:5-community | 7474 / 7687 | 知识图谱 |
| MinerU（可选，需 GPU） | opendatalab/mineru | 8000 | 复杂 PDF 版面解析 |
| Backend | sparkx-backend:2.0 | 7026 | Java 服务 |
| Frontend | sparkx-frontend:2.0 | 8189→80 | Nginx 静态站点 |

向量检索直接走 PostgreSQL `vector` 扩展，不另引独立向量库。

---

## 3. 总体架构

```
浏览器
  │  HTTP / SSE
  ▼
admin (Vue3 + Naive UI + X6)
  │  REST / SSE  →  :7126 本地  /  :7026 Docker
  ▼
server (Spring Boot)
  ├── system/        登录、JWT、用户权限
  ├── knowledge/     RAG / 智能体 / 意图 / 图谱 / MCP
  ├── workflow/      可视化节点图执行
  └── evaluation/    意图分类评测
  │
  ├─ PostgreSQL(pgvector)   业务表 + chunks 向量 / 关键词
  ├─ Redis + Redisson       缓存、分布式锁
  ├─ MinIO                  文档原件
  ├─ Neo4j                  实体 / 关系 / 社区
  ├─ LLM Provider           OpenAI 兼容 / Ollama
  ├─ Rerank Provider        Cohere 兼容
  └─ MinerU（可选）         复杂版面解析
```

认证：Hutool JWT（`sparkx.jwt-secret`，默认 168 小时）。  
对话输出：SSE 流式。模型配置优先读 `ai_model` 表，yml 仅作兜底。

---

## 4. 后端模块

包根：`sparkx.sparkshop`，启动类 `SparkxApplication`。

| 模块 | 路径 | 职责 |
|---|---|---|
| common | `common/` | 全局配置、异常、常量、工具 |
| config | `config/` | 应用级 Bean |
| system | `system/` | 用户 / 鉴权 / 通用系统接口 |
| knowledge | `knowledge/` | RAG 与智能体（最大子系统） |
| workflow | `workflow/` | 编排引擎与运行时 |
| evaluation | `evaluation/` | 意图分类评估面板 |

分层约定：`controller` → `service` / `service/impl` → `mapper` + `entity`，入参 `validate`，出参 `vo`。

### 4.1 knowledge 子系统

```
knowledge/
├── pipeline/ + stages/   RAG 流水线（@Order 自动装配）
├── retrieval/            检索通道 + 后处理
├── ingest/ + mineru/     文档入库
├── intent/               意图树 / 规则快路径 / LLM 分类
├── graph/                Neo4j 抽取与检索
├── infra/chat + model/   LLM 路由、熔断、首包探测
├── mcp/                  MCP 注册与执行
├── memory/               L1 滑动窗口 + L2 话题摘要
├── agent/                对话入口
├── prompt/               场景路由与模板
├── query/                改写 / 扩展 / 拆分
├── fallback/             模型降级
└── controller / service / mapper / entity / vo / validate
```

HTTP 入口（节选）：

- `KnowledgeController` / `KnowledgeDocumentController` / `ParagraphController`
- `KnowledgeAgentController` / `AgentTestController` / `ChatSessionController`
- `AiModelController` / `ExtServiceConfigController`
- `IntentNodeController` / `SampleQueryController` / `QuestionController`
- `KnowledgeGraphController` / `McpServerController` / `PipelineController`

### 4.2 workflow 引擎

```
workflow/engine/
├── WorkflowChatService     编排对话入口
├── FlowNodeParser          解析画布节点图
├── NodeProvider            节点实现注册
├── IWorkflowNode           节点契约
├── WorkflowSseHelper       SSE 推送
└── node/
    ├── LlmNode             大模型节点
    ├── AgentNode           智能体节点
    ├── DatasetNode         知识库检索节点
    ├── GraphNode           图谱节点
    ├── PurposeNode         意图节点
    ├── SwitchNode          条件分支
    └── AnswerNode          答复汇聚
```

### 4.3 RAG 提问链路

一次用户提问按 `@Order` 串行（任一步可 `COMPLETE` 短路）：

```
SampleQueryStage(5)        样例库向量命中 → 零 LLM 直返
  → RewriteSplitStage(10)  查询改写 / 多子问题拆分
  → IntentStage(20)
  → TreeIntentStage(30)    规则快路径 + 低温 LLM 归类（KB / SYSTEM / MCP）
  → VagueQueryClarifyStage(40)
  → GuidanceStage(50)      歧义引导澄清
  → RetrieveStage(60)      多通道并行检索
  → RerankStage(70)        重排
  → MergeStage(80)         RRF 融合 + 父子扩展 + MMR
  → GenerateStage(90)      按 KB_ONLY / MCP_ONLY / MIXED / EMPTY 组提示词生成
  ↘ FallbackStage          异常兜底
```

检索通道：`VectorKeywordHybridChannel`（向量 + 关键词）、`IntentDirectedChannel`、`KnowledgeGraphChannel`。  
后处理：去重 → 重排 → MMR → 父子块扩展 → RRF 融合。

### 4.4 文档入库

```
fetch → parse → chunk → enrich → enhance → index
```

- 解析：Tika / PDFBox / POI；复杂 PDF 走 MinerU（自建 `mineru_self` 或云端 `mineru_cloud`）
- 分块：自适应 / 父子分块 / 表格按行；版面块保留溯源
- 索引：chunk 向量写入 pgvector；关键词 `tsv` 由 HanLP 预分词；可选抽取写入 Neo4j

### 4.5 模型容错

```
RoutingLLMService
  → ModelSelector（优先级候选）
  → OpenAICompatibleChatClient / OllamaChatClient
  → ModelHealthStore（CLOSED / OPEN / HALF_OPEN）
  → LlmFirstPacketProbe（首包探测，切换对用户无感）
```

### 4.6 会话记忆

| 层 | 机制 | 状态 |
|---|---|---|
| L1 | 最近 N 轮原样消息 | 启用 |
| L2 | 话题摘要（只记话题不记答案） | 启用 |
| L3 | 跨会话持久记忆 | 代码预研，默认关闭 |

---

## 5. 前端模块

基于 naive-ui-admin。页面按业务域拆在 `admin/src/views/`：

| 目录 | 功能 |
|---|---|
| `login/` | 登录 |
| `agent/` | 智能体配置与调试 |
| `knowledge/` | 知识库、文档、分块、图谱、意图、样例 |
| `ai/model` `ai/mcp` `ai/service` | 模型 / MCP / 外部服务 |
| `chat/` | SSE 对话调试 |
| `workflow/` | X6 画布编排 |
| `error/` `redirect/` | 错误页与跳转 |

配套：`api/` 按域封装请求，`store/` Pinia，`router/` 动态路由，`layout/` 后台壳。

---

## 6. 数据存储

PostgreSQL 业务表（约 26 张），按域：

| 域 | 表 |
|---|---|
| 知识库 | `knowledge_base` `document` `chunks` `parent_chunks` `knowledge_question` |
| 智能体 | `knowledge_agent` `t_chat_session` `t_chat_message` |
| 记忆 | `t_conversation_message` `t_conversation_summary` |
| 意图 / 样例 | `t_intent_node` `sample_query` `sample_query_config` |
| 入库 | `t_ingestion_task_node` `t_ingestion_pipeline_node` |
| 模型 / 外部 | `ai_model` `ext_service_config` |
| 图谱 | `kg_config` `kg_entity` `kg_extraction_record` |
| MCP | `mcp_server` `mcp_tool` |
| 工作流 | `workflow` `workflow_runtime` `workflow_runtime_context` |

Neo4j 存实体、关系、社区摘要；MinIO 存原文件。

---

## 7. 扩展点

| 扩展 | 做法 |
|---|---|
| 新检索通道 | 实现 `ConditionalRetrievalChannel`，注册为 Bean |
| 新 RAG 阶段 | 实现 `PipelineStage`，`@Component @Order(n)` |
| 新 MCP 工具 | 后台配置 MCP Server，协议自动发现 |
| 新模型供应商 | 实现 ChatClient，加入候选列表即可参与路由 |
| 新工作流节点 | 实现 `IWorkflowNode`，由 `NodeProvider` 注册 |

---

## 8. 运行入口

Docker 全量：

```bash
cd spark-x/docker
docker compose up -d
# http://localhost:8189   admin / 123456
```

本地开发：

- 依赖：Java 17、Node 22、PostgreSQL 15（pgvector）、Redis 7（16379）、MinIO、可选 Neo4j / MinerU
- 后端：`server/` Maven 启动，端口 `7126`
- 前端：`admin/` `pnpm dev`，端口 `9911`，`VITE_GLOB_API_URL=http://localhost:7126`
