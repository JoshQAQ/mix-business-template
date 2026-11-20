plugins {
    alias(libs.plugins.spring.boot)
}

springBoot {
    // Kotlin 顶层 main 函数编译在 @@APPLICATION_CLASS@@Kt 中。
    mainClass.set("@@PACKAGE_NAME@@.@@APPLICATION_CLASS@@Kt")
}

// 启动模块只产出可执行的 Spring Boot JAR。
tasks.bootJar {
    archiveFileName.set("@@PROJECT_NAME@@.jar")
}

tasks.jar {
    enabled = false
}

// 使用 Undertow 作为嵌入式容器，排除 Web Starter 默认引入的 Tomcat。
configurations.configureEach {
    val tomcat = libs.spring.boot.starter.tomcat.get().module
    exclude(group = tomcat.group, module = tomcat.name)
}

dependencies {
    implementation(projects.endpoint)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.undertow)
    implementation(libs.springdoc.openapi.starter.webmvc.ui)
}

description = "Spring Boot 启动模块"
