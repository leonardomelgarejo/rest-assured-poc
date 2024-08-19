package br.com.lmelgarejo;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

@Plugin(name = "AllureAppender", category = "Core", elementType = Appender.ELEMENT_TYPE, printObject = true)
public class AllureAppender extends AbstractAppender {

    protected AllureAppender(String name, Layout<?> layout) {
        super(name, null, layout, false);
    }

    @Override
    public void append(LogEvent event) {
        String message = new String(getLayout().toByteArray(event));
        Allure.step(message);
    }

    @PluginFactory
    public static AllureAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<?> layout) {
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new AllureAppender(name, layout);
    }
}
