package @@PACKAGE_NAME@@.api.request

import com.mixframework.common.tools.SerializableBean
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/** 新增测试记录的请求体，数据库在写入时生成主键。 */
class AddTestRequest(
    /** 记录名称，须含非空白字符，长度最多 100。 */
    @field:NotBlank
    @field:Size(max = 100)
    val name: String,
) : SerializableBean(), TestHandleRequest
