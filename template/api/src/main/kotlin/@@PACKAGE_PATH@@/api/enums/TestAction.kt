package @@PACKAGE_NAME@@.api.enums

import @@PACKAGE_NAME@@.api.request.AddTestRequest
import @@PACKAGE_NAME@@.api.request.TestHandleRequest
import @@PACKAGE_NAME@@.api.request.UpdateTestRequest
import com.mixframework.common.api.enums.ValueEnum

/** 定义测试记录的写入动作及其请求类型，供 Mix Handle 注册和分发。 */
enum class TestAction(
    /** Handler 注册和分发使用的稳定动作键。 */
    private val code: String,
    /** 动作的展示文案。 */
    private val text: String,
    /** HTTP 路由绑定的具体请求类型。 */
    private val requestClass: Class<out TestHandleRequest>,
) : ValueEnum<String> {
    ADD_TEST("ADD_TEST", "新增测试记录", AddTestRequest::class.java),
    UPDATE_TEST("UPDATE_TEST", "更新测试记录", UpdateTestRequest::class.java);

    /** 返回 Mix Handle 使用的动作键。 */
    override fun getValue(): String = code

    /** 返回动作的中文展示文案。 */
    override fun getText(): String = text

    companion object {
        /** 初始化请求类索引时检查重复绑定。 */
        private val byRequestClass = entries.associateBy(TestAction::requestClass).also {
            check(it.size == entries.size) { "一个请求类只能绑定一个 TestAction" }
        }

        /** 从具体请求类取得分发键，缺少绑定时报告配置错误。 */
        internal fun fromRequestClass(requestClass: Class<out TestHandleRequest>): String =
            checkNotNull(byRequestClass[requestClass]) { "请求类未绑定 TestAction ${requestClass.name}" }.getValue()
    }
}
