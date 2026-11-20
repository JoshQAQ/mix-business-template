package @@PACKAGE_NAME@@.api.request

import com.mixframework.common.tools.SerializableBean
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

/** 修改指定测试记录名称的请求体。 */
class UpdateTestRequest(
    /** 已存在记录的数据库主键，须为正整数。 */
    @field:Positive
    val id: Long,

    /** 更新后的记录名称，须含非空白字符，长度最多 100。 */
    @field:NotBlank
    @field:Size(max = 100)
    val name: String,
) : SerializableBean(), TestHandleRequest
