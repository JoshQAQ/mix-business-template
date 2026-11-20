package @@PACKAGE_NAME@@.service

import @@PACKAGE_NAME@@.api.exception.code.DemoErrorCode
import @@PACKAGE_NAME@@.api.response.TextTransformResponse
import @@PACKAGE_NAME@@.service.plugin.text.TextTransformPlugin
import org.springframework.plugin.core.PluginRegistry
import org.springframework.stereotype.Service

/** 按模式查找插件并转换文本。 */
@Service
class TextTransformService(private val registry: PluginRegistry<TextTransformPlugin, String>) {
    /**
     * 交由匹配的插件转换文本，模式未注册时抛出业务异常。
     *
     * @param mode 转换模式，与插件注册键一致
     * @param text 待转换的文本
     */
    fun transform(mode: String, text: String): TextTransformResponse {
        val plugin = registry.getPluginFor(mode)
            .orElseThrow { DemoErrorCode.UNSUPPORTED_TEXT_MODE.exception() }
        return TextTransformResponse(mode, plugin.transform(text))
    }
}
