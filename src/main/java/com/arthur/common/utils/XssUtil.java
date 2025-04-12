package com.arthur.common.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

/**
 * Xss 工具类
 * @author Arthur
 */
public class XssUtil {

    /**
     * 使用自带的 basicWithImages 白名单
     */
    private static final Safelist WHITE_LIST = Safelist.relaxed();

    /** 配置过滤化参数, 不对代码进行格式化 */
    private static final Document.OutputSettings OUTPUT_SETTINGS = new Document.OutputSettings().prettyPrint(false);

    static {
        // 富文本编辑时一些样式是使用 style 来进行实现的
        // 比如红色字体 style="color:red;"
        // 所以需要给所有标签添加 style 属性
        WHITE_LIST.addAttributes(":all", "style");
    }

    /**
     * 清理字符串，去除HTML标签
     */
    public static String clean(String content) {
        // 使用Jsoup库的clean方法，传入content、空字符串、WHITE_LIST和OUTPUT_SETTINGS参数
        return Jsoup.clean(content, "", WHITE_LIST, OUTPUT_SETTINGS);
    }

}
