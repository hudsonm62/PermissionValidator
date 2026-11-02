# Permission Validator

A drop-in solution for integrating permission nodes to your multi-loader mod.

It works by providing a single, unified API for defining and checking permissions, handling essentially all the heavy lifting for both
Fabric and NeoForge. On Fabric, it also determines if the [`fabric-permissions-api`](https://github.com/lucko/fabric-permissions-api) mod is present, then uses it if so.

You'll most commonly be using this for permission checks in commands, but it can be used for literally any
kind of "permission" you want to check for, such as whether a player can use a certain item or access a certain area.

**For Users:** This is a library mod, you don't need to install it unless a mod requires it.
If you're a developer, keep on reading!

## Usage Sample

### 1. Add Dependency

You should just be able to Jar in Jar this into your mod (it's only 16~Kb!).

However, if you really want to you can add it as a dependency in your `fabric.mod.json` or `neoforge.mods.toml` file
and require users to install it separately.

```groovy
repositories {
    maven("https://maven.ziroau.com")
}

// For Fabric & Architectury Loom
dependencies {
    modApi "com.ziroau:permissionvalidator:${permissionvalidator_version}:fabric-mc${minecraft_version}"
    include "com.ziroau:permissionvalidator:${permissionvalidator_version}:fabric-mc${minecraft_version}"
}

// For NeoForge
dependencies {
    jarJar(implementation("com.ziroau:permissionvalidator:${permissionvalidator_version}:neoforge-mc${minecraft_version}")) {
        version {
            strictly "[${permissionvalidator_version},)"
            prefer "${permissionvalidator_version}"
        }
    }
}

// Multiloader 'Common' Project
dependencies {
    modCompileOnlyApi "com.ziroau:permissionvalidator:${permissionvalidator_version}:fabric-mc${minecraft_version}"
}
```

### 2. Define & Initialize Permissions

```kotlin
object ModPermissions {
    val ROOT = PermissionManager.create(
        "mod",
        PermissionLevel.CHEATS_AND_COMMAND_BLOCKS
    )
    val RELOAD = PermissionManager.create(
        "mod.reload",
        PermissionLevel.CHEATS_AND_COMMAND_BLOCKS
    )

    // Call just before command registration
    fun initialize() {
        PermissionManager.initialize(listOf(ROOT, RELOAD))
    }
}
```

### 3. Check Permissions

Within your Brigadier structure, you can use the `.requires` overload:

```kotlin
import com.ziroau.permissionvalidator.api.PermissionManager.requires

object ModComands {
    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        ModPermissions.initialize()

        val root = Commands.literal("commandroot")
            .requires(ModPermissions.ROOT)
            .executes { ctx -> modInfo(ctx.source) }
            .then(
                //** /commandroot reload */
                Commands.literal("reload")
                    .requires(ModPermissions.RELOAD)
                    .executes { ctx -> reload(ctx.source) }
            )
    }
}
```

Or in some other random context:

```kotlin
fun someRandomFunction(player: ServerPlayerEntity) {
    if (PermissionManager.hasPermission(player, ModPermissions.RELOAD)) {
        // do something
    } else {
        // did not have permission to do something :(
    }
}
```
