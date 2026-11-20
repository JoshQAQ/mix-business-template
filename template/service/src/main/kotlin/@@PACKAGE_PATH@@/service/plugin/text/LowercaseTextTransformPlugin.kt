package @@PACKAGE_NAME@@.service.plugin.text

import org.springframework.stereotype.Component

/** 将文本中的字母转换为小写。 */
@Component
internal class LowercaseTextTransformPlugin : TextTransformPlugin {
    override val mode = "LOWERCASE"

    override fun transform(text: String): String = text.lowercase()
}
