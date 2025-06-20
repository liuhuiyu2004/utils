package com.liuhuiyu.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 功能<p>
 * Created on 2025/4/16 20:39
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class App extends SpringBootServletInitializer {
    private static final Logger LOG= LogManager.getLogger(App.class);
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        //TIP 当文本光标位于高亮显示的文本处时按 <shortcut actionId="ShowIntentionActions"/>
        return application.sources(this.getClass());
    }

    public static void main(String[] args) {
        // 查看 IntelliJ IDEA 建议如何修正。
        //TIP 按 <shortcut actionId="Debug"/> 开始调试代码。我们已经设置了一个 <icon src="AllIcons.Debugger.Db_set_breakpoint"/> 断点
        // 但您始终可以通过按 <shortcut actionId="ToggleLineBreakpoint"/> 添加更多断点。
        SpringApplication.run(App.class, args);
        LOG.trace("默认日志输出。");
        LOG.debug("默认日志输出。");
        LOG.info("默认日志输出。");
        LOG.warn("默认日志输出。");
        LOG.error("默认日志输出。");
    }
}