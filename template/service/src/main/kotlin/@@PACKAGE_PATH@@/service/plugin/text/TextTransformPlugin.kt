package @@PACKAGE_NAME@@.service.plugin.text

import com.mixframework.plugin.promax.KeyedPlugin
import com.mixframework.plugin.promax.UniquePluginKeys

/** 每个插件提供一种文本转换方式。 */
@UniquePluginKeys
interface TextTransformPlugin : KeyedPlugin<String> {
    /** 转换模式，在此扩展点内须唯一。 */
    val mode: String

    /** 将转换模式提供给 Mix 注册表，用于匹配与重复键检查。 */
    override fun keys(): Set<String> = setOf(mode)

    /** 返回按当前模式转换后的文本。 */
    fun transform(text: String): String
}
