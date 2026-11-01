package com.ziroau.permissionvalidator.api.validators

import com.ziroau.permissionvalidator.PermissionValidatorMod
import com.ziroau.permissionvalidator.api.Permission
import com.ziroau.permissionvalidator.api.PermissionManager
import com.ziroau.permissionvalidator.api.Validator
import net.minecraft.commands.CommandSourceStack
import net.minecraft.server.level.ServerPlayer

/*? if >=1.21.11 {*/
import net.minecraft.server.permissions.PermissionSet
import net.minecraft.server.permissions.Permissions as McPerms
/*?}*/

/*? if neoforge {*/
/*import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.server.permission.PermissionAPI
import net.neoforged.neoforge.server.permission.events.PermissionGatherEvent
import net.neoforged.neoforge.server.permission.nodes.PermissionNode
import net.neoforged.neoforge.server.permission.nodes.PermissionTypes
*//*?}*/

object VanillaPermissionValidator : Validator {
    /*? if neoforge {*/
    /*private val nodes = mutableMapOf<String, PermissionNode<Boolean>>()

    init {
        NeoForge.EVENT_BUS.addListener<PermissionGatherEvent.Nodes> { event ->
            event.addNodes(PermissionManager.all().map { permission ->
                nodes.getOrPut(permission.name) {
                    PermissionNode(
                        permission.name.substringBefore('.'),
                        permission.name.substringAfter('.'),
                        PermissionTypes.BOOLEAN,
                        { player, _, _ -> player != null && vanillaCheck(player, permission.level.level) }
                    )
                }
            })
        }
    }
    *//*?}*/

    override fun initialize() {
        /*? if neoforge {*/
        /*PermissionValidatorMod.LOGGER.debug("Using NeoForge's PermissionAPI")
        *//*?} else {*/
        PermissionValidatorMod.LOGGER.debug("Fabric Permissions API not detected - using Vanilla Permissions")
        /*?}*/
    }

    override fun hasPermission(player: ServerPlayer, permission: Permission): Boolean {
        /*? if neoforge {*/
        /*nodes[permission.name]?.let { return PermissionAPI.getPermission(player, it) }
        *//*?}*/
        return vanillaCheck(player, permission.level.level)
    }

    override fun hasPermission(source: CommandSourceStack, permission: Permission): Boolean {
        /*? if neoforge {*/
        /*val player = source.player
        val node = nodes[permission.name]
        if (player != null && node != null) return PermissionAPI.getPermission(player, node)
        *//*?}*/
        return vanillaCheck(source, permission.level.level)
    }

    /*? if >=1.21.11 {*/
    private fun vanillaCheck(player: ServerPlayer, level: Int) = checkLevel(player.permissions(), level)
    private fun vanillaCheck(source: CommandSourceStack, level: Int) = checkLevel(source.permissions(), level)

    private fun checkLevel(permissions: PermissionSet, level: Int): Boolean = when (level) {
        0 -> true
        1 -> permissions.hasPermission(McPerms.COMMANDS_MODERATOR)
        2 -> permissions.hasPermission(McPerms.COMMANDS_GAMEMASTER)
        3 -> permissions.hasPermission(McPerms.COMMANDS_ADMIN)
        4 -> permissions.hasPermission(McPerms.COMMANDS_OWNER)
        else -> false
    }
    /*?} else {*/
    /*private fun vanillaCheck(player: ServerPlayer, level: Int) = player.hasPermissions(level)
    private fun vanillaCheck(source: CommandSourceStack, level: Int) = source.hasPermission(level)
    *//*?}*/
}
