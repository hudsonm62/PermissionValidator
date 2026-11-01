package com.ziroau.permissionvalidator.api.validators

/*? if fabric {*/
import com.ziroau.permissionvalidator.api.Permission
import com.ziroau.permissionvalidator.api.Validator
import me.lucko.fabric.api.permissions.v0.Permissions
import net.minecraft.commands.CommandSourceStack
import net.minecraft.server.level.ServerPlayer

object FabricPermissionValidator : Validator {
    override fun initialize() {}

    override fun hasPermission(player: ServerPlayer, permission: Permission) = Permissions.check(player, permission.name, permission.level.level)

    override fun hasPermission(source: CommandSourceStack, permission: Permission) = Permissions.check(source, permission.name, permission.level.level)

    override fun hasPermission(player: ServerPlayer, permission: String, level: Int) = Permissions.check(player, permission, level)

    override fun hasPermission(source: CommandSourceStack, permission: String, level: Int) = Permissions.check(source, permission, level)
}
/*?}*/
