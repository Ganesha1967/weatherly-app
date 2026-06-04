plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.detekt) apply false
    id("com.diffplug.spotless") version "8.6.0" apply false
}

subprojects {
    plugins.withId("com.diffplug.spotless") {
        configure<com.diffplug.gradle.spotless.SpotlessExtension> {
            kotlin {
                target("**/*.kt")
                targetExclude("**/build/**/*.kt")
                ktlint("1.8.0")
                trimTrailingWhitespace()
                endWithNewline()
            }
            kotlinGradle {
                target("*.gradle.kts")
                ktlint()
            }
            format("misc") {
                target("*.md", ".gitignore", ".gitattributes")
                trimTrailingWhitespace()
                endWithNewline()
            }
        }
    }

    plugins.withId("io.gitlab.arturbosch.detekt") {
        configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
            buildUponDefaultConfig = true
            allRules = false
            autoCorrect = true
            parallel = true
            config.setFrom(files("${rootProject.projectDir}/config/detekt/detekt.yml"))
        }
    }
}

tasks.register<Exec>("configureGitHooksPath") {
    group = "git hooks"
    description = "Configure Git hooks path"
    commandLine("git", "config", "core.hooksPath", "scripts/git-hooks")
}

tasks.register("makeGitHooksExecutable") {
    group = "git hooks"
    description = "Make Git hooks executable"
    val hooksDir = file("$rootDir/scripts/git-hooks")
    doLast {
        if (hooksDir.exists()) {
            hooksDir.listFiles()?.forEach { hook ->
                hook.setExecutable(true)
                println("Executable: ${hook.name}")
            }
        }
    }
}

tasks.register("setupGitHooks") {
    group = "git hooks"
    description = "Full Git hooks setup"
    dependsOn("configureGitHooksPath", "makeGitHooksExecutable")
}
