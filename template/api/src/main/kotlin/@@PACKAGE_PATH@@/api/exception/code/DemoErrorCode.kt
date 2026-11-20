package @@PACKAGE_NAME@@.api.exception.code

import com.mixframework.common.api.mixmicro.BusinessException
import com.mixframework.exception.ErrorCode
import com.mixframework.exception.HttpStatus

/** 统一定义示例业务的错误码、返回文案和 HTTP 状态。 */
enum class DemoErrorCode(
    private val value: Int,
    private val message: String,
    private val status: HttpStatus,
) : ErrorCode {
    TEST_NOT_FOUND(10001_0_001, "记录不存在", HttpStatus.NOT_FOUND),
    UNSUPPORTED_TEXT_MODE(10001_0_002, "不支持的文本转换模式", HttpStatus.BAD_REQUEST);

    /** 返回供调用方识别的业务错误码。 */
    override fun code(): Int = value

    /** 返回响应使用的错误文案。 */
    override fun reasonPhrase(): String = message

    /** 返回 Mix Web 处理此异常时使用的 HTTP 状态。 */
    override fun httpStatus(): HttpStatus = status

    /** 将此错误码包装为 Mix 统一处理的业务异常。 */
    fun exception(): BusinessException = BusinessException(this)
}
