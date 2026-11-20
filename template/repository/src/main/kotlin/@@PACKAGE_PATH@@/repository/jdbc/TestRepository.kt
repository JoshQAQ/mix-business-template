package @@PACKAGE_NAME@@.repository.jdbc

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import @@PACKAGE_NAME@@.repository.entity.mysql.Test
import org.apache.ibatis.annotations.Mapper

/** 提供 test 表的持久化操作，主键查询 SQL 定义在 TestRepository.xml。 */
@Mapper
interface TestRepository : BaseMapper<Test>
