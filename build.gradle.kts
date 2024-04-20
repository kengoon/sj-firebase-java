import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    // id("com.android.application") apply false
    id("com.android.library") apply false
    // kotlin("android") apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.versions)
    base
    id("com.google.gms.google-services") version "4.4.1" apply false
}

allprojects {
    group = PUBLISHING_GROUP
}

val detektFormatting = libs.detekt.formatting

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }

    detekt {
        config.from(rootProject.files("config/detekt/detekt.yml"))
    }

    dependencies {
        detektPlugins(detektFormatting)
    }
}

tasks {
    withType<DependencyUpdatesTask>().configureEach {
        rejectVersionIf {
            candidate.version.isStableVersion().not()
        }
    }
}
