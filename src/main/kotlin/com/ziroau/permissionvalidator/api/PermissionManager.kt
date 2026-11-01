package com.ziroau.permissionvalidator.api

import net.minecraft.commands.CommandSourceStack
import net.minecraft.server.level.ServerPlayer

object PermissionManager {
    private lateinit var validator: Validator
    private val permissions = linkedMapOf<String, Permission>()

    /**
     * Called by PermissionValidator's loader entrypoint.
     * You should not call this method directly, unless you know what you're doing
     */
    fun initializePermissionValidator(validator: Validator) {
        this.validator = validator
        validator.initialize()
    }

    /**
     * Your Mod should call this method to initialize the permissions.
     * You probably don't need to call this more than once, unless permissions can appear/disappear at runtime
     */
    @JvmStatic
    fun initialize(permissions: Collection<Permission>) {
        permissions.forEach { this.permissions[it.name] = it }
    }

    /**
     * Creates a new [Permission] object with the given node and vanilla level.
     * This does not register the permission, you should call [initialize] to register it.
     */
    @JvmStatic
    fun create(node: String, level: PermissionLevel): Permission = Permission(node, level)

    @JvmStatic
    fun all(): List<Permission> = permissions.values.toList()

    @JvmStatic
    fun hasPermission(player: ServerPlayer, permission: Permission): Boolean =
        validator.hasPermission(player, permission)

    @JvmStatic
    fun hasPermission(player: ServerPlayer, permission: String, level: Int): Boolean =
        validator.hasPermission(player, permission, level)

    @JvmStatic
    fun hasPermission(source: CommandSourceStack, permission: Permission): Boolean =
        validator.hasPermission(source, permission)

    @JvmStatic
    fun hasPermission(source: CommandSourceStack, permission: String, level: Int): Boolean =
        validator.hasPermission(source, permission, level)
}
