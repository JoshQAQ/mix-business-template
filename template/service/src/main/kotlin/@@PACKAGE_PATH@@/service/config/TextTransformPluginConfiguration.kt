package @@PACKAGE_NAME@@.service.config

import @@PACKAGE_NAME@@.service.plugin.text.TextTransformPlugin
import com.mixframework.plugin.promax.EnablePluginProMax
import org.springframework.context.annotation.Configuration

/** 注册文本转换扩展点，由 Mix 为 Spring 中的转换插件创建注册表。 */
@Configuration(proxyBeanMethods = false)
@EnablePluginProMax(TextTransformPlugin::class)
class TextTransformPluginConfiguration
