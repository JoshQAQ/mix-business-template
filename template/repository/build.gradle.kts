dependencies {
    api(projects.api)

    // 由下方显式指定的 MyBatis-Plus Starter 提供运行时实现。
    api(libs.mix.mybatis.plus.spring.boot.starter) {
        exclude(group = "com.baomidou", module = "mybatis-plus-spring-boot3-starter")
    }
    api(libs.example.mybatis.plus.starter) {
        // Spring Boot JDBC 版本由 Mix BOM 提供。
        exclude(group = "org.springframework.boot")
    }
    implementation(libs.spring.boot.starter.jdbc)
    // 编译使用 JDBC 接口，运行时加载 MySQL 驱动。
    runtimeOnly(libs.mysql.connector.j)
}

description = "数据访问模块"
