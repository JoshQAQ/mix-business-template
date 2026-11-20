package @@PACKAGE_NAME@@.service

import com.baomidou.mybatisplus.extension.service.IService
import @@PACKAGE_NAME@@.api.response.TestResponse
import @@PACKAGE_NAME@@.repository.entity.mysql.Test

/** 提供测试记录的业务操作，并继承 MyBatis-Plus 的通用持久化能力。 */
interface TestService : IService<Test> {
    /** 保存名称并返回含数据库主键的新记录。 */
    fun add(name: String): TestResponse

    /** 查询指定主键，记录缺失时抛出业务异常。 */
    fun get(id: Long): TestResponse

    /** 修改指定记录的名称，记录缺失时抛出业务异常。 */
    fun update(id: Long, name: String): TestResponse

    /** 删除指定记录，仅在删除一行时返回 true。 */
    fun delete(id: Long): Boolean
}
