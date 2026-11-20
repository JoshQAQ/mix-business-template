package @@PACKAGE_NAME@@.service.handle

import @@PACKAGE_NAME@@.api.enums.TestAction
import @@PACKAGE_NAME@@.api.request.AddTestRequest
import @@PACKAGE_NAME@@.api.response.TestResponse
import @@PACKAGE_NAME@@.service.TestService
import com.mixframework.handle.api.MixHandleHandler
import org.springframework.beans.factory.ObjectProvider
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/** 接收 Mix 分发的新增请求，为写入提供事务边界。 */
@Component
open class AddTestHandler(
    /** 保存记录并返回数据库生成的主键。 */
    private val service: TestService,
    /**
     * 获取当前 Handler 的 Spring 代理，用于触发事务拦截。
     */
    private val thisService: ObjectProvider<AddTestHandler>
) : MixHandleHandler<AddTestRequest> {
    /** 注册测试记录新增动作。 */
    override fun supportType(): String = TestAction.ADD_TEST.getValue()

    /** 经由当前 Handler 的 Spring 代理进入事务方法。 */
    override fun handle(request: AddTestRequest, requestType: String): TestResponse =
        thisService.getObject().doHandle(request)

    /** 在事务中新增记录，异常时回滚写入。 */
    @Transactional(rollbackFor = [Exception::class])
    open fun doHandle(request: AddTestRequest): TestResponse =
        service.add(request.name)
}
