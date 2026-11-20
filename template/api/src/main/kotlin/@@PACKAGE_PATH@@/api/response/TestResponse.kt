package @@PACKAGE_NAME@@.api.response

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.mixframework.common.tools.SerializableBean
import io.swagger.v3.oas.annotations.media.Schema

/** 查询或写入成功后返回的测试记录。 */
data class TestResponse(
    /** 数据库主键，JSON 中用字符串传输以保留 Long 精度。 */
    @get:JsonSerialize(using = ToStringSerializer::class)
    @get:Schema(type = "string")
    val id: Long,
    /** 当前保存的记录名称。 */
    val name: String,
) : SerializableBean()
