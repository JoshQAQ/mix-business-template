package @@PACKAGE_NAME@@.repository.entity.mysql

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

/** 映射 MySQL 的 test 表。 */
@TableName("test")
class Test {
    /** 自增主键，插入前为空，写入后由 MyBatis 回填。 */
    @TableId(type = IdType.AUTO)
    var id: Long? = null

    /** 保存到 name 列的记录名称。 */
    var name: String = ""
}
