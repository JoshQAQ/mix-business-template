package @@PACKAGE_NAME@@.service.plugin.text

import org.springframework.stereotype.Component

/** 将文本中的字母转换为大写。 */
@Component
internal class UppercaseTextTransformPlugin : TextTransformPlugin {
    override val mode = "UPPERCASE"

    override fun transform(text: String): String = text.uppercase()
}
