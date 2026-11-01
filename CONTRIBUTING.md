# Contributing

This project uses [Stonecraft](https://stonecraft.meza.gg) - Which essentially uses Architectury & Stonecutter for simpler multi-loader multi-version mod development.

## Requirements

- JDK 25
- IntelliJ IDEA
- Basic understanding of Stonecutter & Architectury

## Build and verify

Run the available commands from the project root:

```shell
./gradlew buildAndCollect
```

`buildAndCollect` builds every configured Minecraft-version and loader pair, then collects the resulting JARs under `build/libs`.

## Before Comitting

Before committing, run the following command to ensure that the code is formatted correctly:

```shell
./gradlew "Reset active project"
```

This will reset the project back to the defined default state.
This is necessary because Stonecutter modifies source files (mainly for IDE support) and these changes should not be committed.

## Publishing configuration

Stonecraft's `publishMods` task performs network publication only when the `DO_PUBLISH` environment variable is `true`. Otherwise it prints the publication payload as a dry run. Modrinth and CurseForge are configured independently: a platform is skipped unless every required value for it is present, and an incomplete platform does not prevent publishing to the other one. Set the variables for the platform you want to publish to in the shell that runs Gradle, and treat both token values as secrets.

```shell
./gradlew publishMods
```

| Name             | Purpose                                                   |
| ---------------- | --------------------------------------------------------- |
| `DO_PUBLISH`     | Enables network publication when set to `true`            |
| `RELEASE_TYPE`   | Selects `stable`, `beta`, or `alpha` publication metadata |
| `MODRINTH_ID`    | Modrinth project ID                                       |
| `MODRINTH_TOKEN` | Modrinth API token                                        |

For example, this PowerShell session publishes a stable release to Modrinth:

```powershell
$env:DO_PUBLISH = "true"
$env:RELEASE_TYPE = "stable"
$env:MODRINTH_ID = "your-project-id"
$env:MODRINTH_TOKEN = "your-api-token"
./gradlew publishMods
```
