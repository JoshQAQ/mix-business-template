package @@PACKAGE_NAME@@.endpoint

import @@PACKAGE_NAME@@.api.response.TextTransformResponse
import @@PACKAGE_NAME@@.service.TextTransformService
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/** 提供文本转换接口。 */
@RestController
@RequestMapping("/api/text")
@Validated
class TextTransformEndpoint(private val service: TextTransformService) {
    /**
     * 按指定模式转换文本。
     *
     * @param mode 转换插件标识，例如 UPPERCASE 或 LOWERCASE
     * @param text 待转换的非空白文本，最多 200 个字符
     */
    @GetMapping("/transform")
    fun transform(
        @RequestParam("mode") mode: String,
        @NotBlank @Size(max = 200) @RequestParam("text") text: String,
    ): TextTransformResponse = service.transform(mode, text)
}
