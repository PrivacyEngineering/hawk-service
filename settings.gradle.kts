pluginManagement {
    buildscript {
        repositories {
            maven {
                setUrl("https://plugins.gradle.org/m2/")
            }
        }
        dependencies {
            classpath("gradle.plugin.com.avast.gradle:gradle-docker-compose-plugin:0.14.9")
        }
    }
}
rootProject.name = "data-usage-tracing-service"
