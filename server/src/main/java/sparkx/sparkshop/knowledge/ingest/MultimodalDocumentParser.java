package sparkx.sparkshop.knowledge.ingest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 多模态文档解析 。
 *
 * LangChain4j 的 Document 目前仅支持文本。本类负责：
 *  1. 从解析后的纯文本中识别图片引用（Markdown ![](...)）
 *  2. 收集图片 URL 列表，交由 ImageOcrService 异步处理
 */
@Service
public class MultimodalDocumentParser {

    private static final Logger log = LoggerFactory.getLogger(MultimodalDocumentParser.class);

    private static final Pattern MD_IMAGE = Pattern.compile("!\\[[^\\]]*\\]\\(([^)]+)\\)");

    /**
     * 提取文本中所有图片 URL。
     */
    public List<String> extractImageUrls(String text) {
        List<String> urls = new ArrayList<>();
        if (text == null) return urls;
        Matcher m = MD_IMAGE.matcher(text);
        while (m.find()) {
            urls.add(m.group(1));
        }
        log.info("[Multimodal] 提取到 {} 个图片引用", urls.size());
        return urls;
    }
}
