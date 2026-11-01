import gg.meza.stonecraft.mod

plugins {
    id("gg.meza.stonecraft")
    id("org.jetbrains.kotlin.jvm") version "2.4.0"
    idea
}

kotlin {
    jvmToolchain(
        if (stonecutter.current.version.startsWith("1.21.")) 21 else 25
    )
}

modSettings {
    clientOptions {
        fov = 90
        guiScale = 3
        narrator = false
        darkBackground = true
        musicVolume = 0.0
    }

    variableReplacements =
        mapOf(
            "minecraftVersionVirtual" to stonecutter.current.version,
            "forgeLoaderVersion" to
                if (project.mod.isForge) {
                    project.mod
                        .prop("forge_version")
                        .substringAfter("-")
                        .substringBefore(".")
                } else {
                    ""
                },
        )
}

repositories {
    mavenCentral()
    maven("https://thedarkcolour.github.io/KotlinForForge/") {
        name = "Kotlin for Forge"
        content { includeGroup("thedarkcolour") }
    }
}

dependencies {
    if (mod.isFabric) {
        val permissionsApi = "me.lucko:fabric-permissions-api:${mod.prop("fabric_permissions_api_version")}"
        if (stonecutter.current.version.startsWith("1.21.")) {
            add("modImplementation", permissionsApi)
        } else {
            implementation(permissionsApi)
        }
    } else {
        implementation("thedarkcolour:kotlinforforge-neoforge:6.4.0")
    }
}

java {
    withSourcesJar()
}

publishMods {
    modrinth {
        if (mod.isFabric) {
            requires("fabric-api")
            optional("fabric-permissions-api")
        } else {
            requires("kotlin-for-forge")
        }
        environment.set(CLIENT_OR_SERVER_PREFERS_BOTH)
    }
}
