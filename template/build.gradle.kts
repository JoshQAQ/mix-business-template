import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.spring.boot) apply false
}

// 统一所有模块的发布坐标和项目版本。
allprojects {
    group = "@@GROUP_ID@@"
    version = providers.gradleProperty("projectVersion").get()
}

// 子模块共享 Kotlin、Java 工具链及 Mix 管理的依赖版本。
subprojects {
    apply(plugin = "java-library")
    apply(plugin = rootProject.libs.plugins.kotlin.jvm.get().pluginId)
    apply(plugin = rootProject.libs.plugins.kotlin.spring.get().pluginId)

    extensions.configure<KotlinJvmProjectExtension> {
        jvmToolchain(providers.gradleProperty("javaVersion").get().toInt())
    }
    tasks.withType<KotlinCompile>().configureEach {
        // 在编译时严格处理 Java 依赖的空值注解。
        kotlinOptions.freeCompilerArgs += "-Xjsr305=strict"
    }
    dependencies {
        "api"(platform(rootProject.libs.mix.framework.bom))
    }
}

// 根项目任务汇总对应的模块任务。
tasks.named("check") { dependsOn(subprojects.map { "${it.path}:check" }) }
tasks.named("assemble") { dependsOn(subprojects.map { "${it.path}:assemble" }) }
tasks.named("clean") { dependsOn(subprojects.map { "${it.path}:clean" }) }
