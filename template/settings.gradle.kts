import com.mixframework.catalog.bom.createWithBomSupport

pluginManagement {
    // 先解析 Mix 版本目录插件，再加载依赖目录。
    val mixVersion = providers.gradleProperty("mixVersion").get()
    plugins {
        id("io.github.qaqframework.version-catalog") version mixVersion
    }

    repositories {
        // 可指定 Mix 专用仓库，仓库过滤仅允许解析 Mix 发布组。
        val mixRepositoryUrl = providers.gradleProperty("mixRepositoryUrl").orNull?.trim()
        if (!mixRepositoryUrl.isNullOrEmpty()) {
            maven(mixRepositoryUrl) {
                content { includeGroupByRegex("""io\.github\.qaqframework(?:\..*)?""") }
            }
        }
        mavenCentral {
            content { includeGroupByRegex("""io\.github\.qaqframework(?:\..*)?""") }
        }
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        maven("https://maven.aliyun.com/repository/public")
        gradlePluginPortal()
    }
}

plugins {
    id("io.github.qaqframework.version-catalog")
}

rootProject.name = "@@PROJECT_NAME@@"
// 为跨模块依赖提供 projects.api 等类型安全访问器。
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    versionCatalogs {
        createWithBomSupport("libs") {
            // Mix 公共组件及其导入的 Kotlin、Spring Boot、Spring Cloud 依赖清单。
            fromBomAlias("mix-framework-bom")
            // 修复未注册 IDdl 时 MyBatis-Plus 3.5.4.1 的启动异常。
            library("example-mybatis-plus-starter", "com.baomidou", "mybatis-plus-spring-boot3-starter").version("3.5.7")
        }
    }

    repositories {
        val mixRepositoryUrl = providers.gradleProperty("mixRepositoryUrl").orNull?.trim()
        if (!mixRepositoryUrl.isNullOrEmpty()) {
            maven(mixRepositoryUrl) {
                content { includeGroupByRegex("""io\.github\.qaqframework(?:\..*)?""") }
            }
        }
        maven("https://maven.aliyun.com/repository/public")
        mavenCentral()
    }
}

include(":api", ":repository", ":service", ":endpoint", ":server")
