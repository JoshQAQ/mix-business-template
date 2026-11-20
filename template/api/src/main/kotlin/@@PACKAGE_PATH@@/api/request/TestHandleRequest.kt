package @@PACKAGE_NAME@@.api.request

import com.fasterxml.jackson.annotation.JsonIgnore
import @@PACKAGE_NAME@@.api.enums.TestAction
import com.mixframework.handle.api.MixHandleRequest
import io.swagger.v3.oas.annotations.media.Schema

/** 根据具体请求类确定 Mix Handle 的分发动作。 */
interface TestHandleRequest : MixHandleRequest {
    /** 由服务端从请求类推导的动作键，在 JSON 和 OpenAPI 中隐藏。 */
    @get:JsonIgnore
    @get:Schema(hidden = true)
    override val requestType: String
        get() = TestAction.fromRequestClass(javaClass)
}
