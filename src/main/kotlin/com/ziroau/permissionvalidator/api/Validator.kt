package com.ziroau.permissionvalidator.api

import net.minecraft.commands.CommandSourceStack
import net.minecraft.server.level.ServerPlayer

/**
 * A validator is responsible for implementing the permission checking logic for a specific permission system.
 *
 * This technically allows for multiple permission systems to be used at once.
 * For example if you had a different permission system you wanted to use alongside NeoForge, you could implement a validator
 * and then register it with PermissionManager.initializePermissionValidator().
 *
 * Note that this is not recommended, as it can lead to unexpected behavior and conflicts between permission systems.
 * This mod already handles the heavy lifting for the 3 most common systems (Vanilla, Fabric Permissions API, and NeoForge's builtin PermissionAPI).
 */
interface Validator {
    fun initialize()

    fun hasPermission(player: ServerPlayer, permission: Permission): Boolean

    fun hasPermission(player: ServerPlayer, permission: String, level: Int): Boolean =
        hasPermission(player, Permission(permission, PermissionLevel.byNumericValue(level)))

    fun hasPermission(source: CommandSourceStack, permission: Permission): Boolean

    fun hasPermission(source: CommandSourceStack, permission: String, level: Int): Boolean =
        hasPermission(source, Permission(permission, PermissionLevel.byNumericValue(level)))
}
