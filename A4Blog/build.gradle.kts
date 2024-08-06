plugins {
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("kapt")
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"

}

group = "com.shch"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {

    maven { url = uri("https://maven.aliyun.com/nexus/content/groups/public/") }
    maven { url = uri("https://maven.aliyun.com/nexus/content/repositories/jcenter") }
    maven { url = uri("https://maven.aliyun.com/nexus/content/repositories/gradle-plugin") }
    maven { url = uri("https://jcenter.bintray.com") }
    maven { url = uri("https://maven.aliyun.com/repository/spring") }

    mavenCentral()
}

dependencies {
    implementation(project(":starter-web-ext"))

    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.springframework.boot:spring-boot-starter-webflux")


    implementation("com.baomidou:mybatis-plus-spring-boot3-starter:3.5.7")
    implementation("com.h2database:h2")
    implementation("org.mapstruct:mapstruct:1.6.0.Beta1")
    kapt("org.mapstruct:mapstruct-processor:1.6.0.Beta1")
    /**
     * 雪花id
     */
    implementation("cn.hutool:hutool-all:5.8.11")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
