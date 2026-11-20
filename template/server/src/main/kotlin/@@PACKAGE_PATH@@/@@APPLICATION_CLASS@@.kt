package @@PACKAGE_NAME@@

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/** Spring Boot 应用入口，扫描 @@PACKAGE_NAME@@ 下各模块的组件。 */
@SpringBootApplication
class @@APPLICATION_CLASS@@

/** 启动应用并转交命令行参数。 */
fun main(args: Array<String>) {
    runApplication<@@APPLICATION_CLASS@@>(*args)
}
