package @@PACKAGE_NAME@@.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import @@PACKAGE_NAME@@.api.exception.code.DemoErrorCode
import @@PACKAGE_NAME@@.api.response.TestResponse
import @@PACKAGE_NAME@@.repository.entity.mysql.Test
import @@PACKAGE_NAME@@.repository.jdbc.TestRepository
import @@PACKAGE_NAME@@.service.TestService
import org.springframework.stereotype.Service

/** 通过 MyBatis-Plus 的 baseMapper 访问 test 表，并组装对外响应。 */
@Service
open class TestServiceImpl : ServiceImpl<TestRepository, Test>(), TestService {
    /** 写入记录，并使用数据库回填的自增 ID 构建响应。 */
    override fun add(name: String): TestResponse {
        val record = Test().apply { this.name = name }
        check(baseMapper.insert(record) == 1)
        return TestResponse(requireNotNull(record.id), record.name)
    }

    /** 通过 XML 中的主键查询语句读取记录。 */
    override fun get(id: Long): TestResponse {
        val record = baseMapper.selectById(id) ?: throw DemoErrorCode.TEST_NOT_FOUND.exception()
        return TestResponse(id, record.name)
    }

    /** 按主键更新名称，更新行数为零时报告记录不存在。 */
    override fun update(id: Long, name: String): TestResponse {
        val record = Test().apply {
            this.id = id
            this.name = name
        }
        if (baseMapper.updateById(record) == 0) {
            throw DemoErrorCode.TEST_NOT_FOUND.exception()
        }
        return TestResponse(id, name)
    }

    /** 物理删除一条记录，重复删除返回 false。 */
    override fun delete(id: Long): Boolean = baseMapper.deleteById(id) == 1
}
