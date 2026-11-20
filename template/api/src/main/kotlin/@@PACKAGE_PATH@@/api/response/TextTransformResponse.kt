package @@PACKAGE_NAME@@.api.response

import com.mixframework.common.tools.SerializableBean

/** 文本转换的结果。 */
data class TextTransformResponse(
    /** 选中的转换模式，也是插件的注册键。 */
    val mode: String,
    /** 转换后的文本。 */
    val text: String,
) : SerializableBean()
