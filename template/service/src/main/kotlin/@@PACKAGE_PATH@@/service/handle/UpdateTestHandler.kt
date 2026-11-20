package @@PACKAGE_NAME@@.service.handle

import @@PACKAGE_NAME@@.api.enums.TestAction
import @@PACKAGE_NAME@@.api.request.UpdateTestRequest
import @@PACKAGE_NAME@@.api.response.TestResponse
import @@PACKAGE_NAME@@.service.TestService
import com.mixframework.handle.api.MixHandleHandler
import org.springframework.beans.factory.ObjectProvider
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/** 接收 Mix 分发的修改请求，为写入提供事务边界。 */
@Component
open class UpdateTestHandler(
    /** 按主键修改记录。 */
    private val service: TestService,
    /**
     * 获取当前 Handler 的 Spring 代理，用于触发事务拦截。
     */
    private val thisService: ObjectProvider<UpdateTestHandler>
) : MixHandleHandler<UpdateTestRequest> {
    /** 注册测试记录修改动作。 */
    override fun supportType(): String = TestAction.UPDATE_TEST.getValue()

    /** 经由当前 Handler 的 Spring 代理进入事务方法。 */
    override fun handle(request: UpdateTestRequest, requestType: String): TestResponse =
        thisService.getObject().doHandle(request)

    /** 在事务中更新指定记录，异常时回滚写入。 */
    @Transactional(rollbackFor = [Exception::class])
    open fun doHandle(request: UpdateTestRequest): TestResponse =
        service.update(request.id, request.name)
}
