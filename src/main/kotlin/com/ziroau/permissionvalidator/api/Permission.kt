package com.ziroau.permissionvalidator.api

/**
 * Represents a permission node with a Permission Node and a vanilla permission level.
 */
open class Permission (
    /** The Permission Node - i.e. `mymod.reload` */
    open val name: String,

    /** The vanilla permission level to fall back to */
    open val level: PermissionLevel
)
