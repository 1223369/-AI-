package sparkx.sparkshop.knowledge.infra.chat;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

/**
 * Ollama 本地供应商薄壳（文档 5.10.1）。
 *
 * Ollama 兼容 OpenAI 协议（/v1/chat/completions），但无需 API Key，故覆写 {@link #requiresApiKey()}。
 * 默认按配置项启用，未配置时不注册 Bean（不影响单候选 openai 默认链路）。
 */
@Component
@ConditionalOnProperty(prefix = "app.ai.ollama", name = "enabled", havingValue = "true")
public class OllamaChatClient extends AbstractOpenAIChatClient {

    private final OkHttpClient syncClient;
    private final OkHttpClient streamingClient;
    private final ExecutorService streamExec;

    public OllamaChatClient(@Qualifier("modelSyncHttpClient") OkHttpClient syncClient,
                            @Qualifier("modelStreamingHttpClient") OkHttpClient streamingClient,
                            @Qualifier("streamingExecutor") ExecutorService streamExec) {
        this.syncClient = syncClient;
        this.streamingClient = streamingClient;
        this.streamExec = streamExec;
    }

    @Override
    public String provider() { return "ollama"; }

    /** Ollama 本地部署无需 API Key */
    @Override
    protected boolean requiresApiKey() { return false; }

    @Override
    protected OkHttpClient syncHttpClient() { return syncClient; }

    @Override
    protected OkHttpClient streamingHttpClient() { return streamingClient; }

    @Override
    protected ExecutorService streamExecutor() { return streamExec; }
}
