package com.ziroau.permissionvalidator

import com.ziroau.permissionvalidator.api.PermissionManager
import com.ziroau.permissionvalidator.api.validators.VanillaPermissionValidator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/*? if fabric {*/
import com.ziroau.permissionvalidator.api.validators.FabricPermissionValidator
import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader
/*?}*/

/*? if neoforge {*/
/*import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
@Mod(PermissionValidatorMod.MOD_ID)
*//*?}*/
class PermissionValidatorMod /*? if fabric {*/ : ModInitializer /*?}*/ {
    companion object {
        const val MOD_ID = "permissionvalidator"
        @JvmField
        val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)
    }

    /*? if neoforge {*/
    /*constructor(modEventBus: IEventBus) {
        PermissionManager.initializePermissionValidator(VanillaPermissionValidator)
    }
    *//*?}*/

    /*? if fabric {*/
    override fun onInitialize() {
        if (FabricLoader.getInstance().isModLoaded("fabric-permissions-api-v0"))
            PermissionManager.initializePermissionValidator(FabricPermissionValidator)
        else
            PermissionManager.initializePermissionValidator(VanillaPermissionValidator)
    }
    /*?}*/
}
