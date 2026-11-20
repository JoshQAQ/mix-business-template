package @@PACKAGE_NAME@@.endpoint.support

import com.mixframework.handle.api.MixHandleApplicationContext
import com.mixframework.handle.api.MixHandleRequest
import org.springframework.web.context.request.async.DeferredResult

/**
 * 将写入请求交给 Mix Handle，并保留端点为 Spring MVC 和 OpenAPI 声明的响应类型。
 *
 * @param request 按具体 DTO 类型提供分发动作的写入请求
 */
internal fun <T> MixHandleApplicationContext.dispatch(request: MixHandleRequest): DeferredResult<T> {
    val result = DeferredResult<Any?>()
    handle(request, result)
    // Mix 写入对应 Handler 的结果，T 由调用端点的返回类型声明。
    @Suppress("UNCHECKED_CAST")
    return result as DeferredResult<T>
}
