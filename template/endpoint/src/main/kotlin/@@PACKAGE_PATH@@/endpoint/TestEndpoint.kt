package @@PACKAGE_NAME@@.endpoint

import @@PACKAGE_NAME@@.api.request.AddTestRequest
import @@PACKAGE_NAME@@.api.request.UpdateTestRequest
import @@PACKAGE_NAME@@.api.response.TestResponse
import @@PACKAGE_NAME@@.endpoint.support.dispatch
import @@PACKAGE_NAME@@.service.TestService
import com.mixframework.handle.api.MixHandleApplicationContext
import jakarta.validation.Valid
import jakarta.validation.constraints.Positive
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.async.DeferredResult

/** 提供测试记录的查询和 Mix Handle 写入入口。 */
@RestController
@RequestMapping("/api/test")
@Validated
class TestEndpoint(
    /** 读取已保存的测试记录。 */
    private val service: TestService,
    /** 按请求类型分发新增和修改操作。 */
    private val handleContext: MixHandleApplicationContext,
) {
    /** 按正整数主键查询记录，记录缺失时返回业务 404。 */
    @GetMapping
    fun get(@Positive @RequestParam("id") id: Long): TestResponse = service.get(id)

    /** 校验新增请求并交给新增 Handler，返回数据库生成的主键和名称。 */
    @PostMapping("/addTest")
    fun addTest(@Valid @RequestBody request: AddTestRequest): DeferredResult<TestResponse> =
        handleContext.dispatch(request)

    /** 校验修改请求并交给修改 Handler，返回更新后的记录。 */
    @PostMapping("/updateTest")
    fun updateTest(@Valid @RequestBody request: UpdateTestRequest): DeferredResult<TestResponse> =
        handleContext.dispatch(request)
}
