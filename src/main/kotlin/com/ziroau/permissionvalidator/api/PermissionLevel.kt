package com.ziroau.permissionvalidator.api

/**
 * Vanilla Permission Levels - These are used as a fallback.
 */
enum class PermissionLevel(val level: Int) {
    NONE(0),
    SPAWN_PROTECTION_BYPASS(1),
    CHEATS_AND_COMMAND_BLOCKS(2),
    MULTIPLAYER_MANAGEMENT(3),
    ALL(4);

    companion object {
        fun byNumericValue(value: Int): PermissionLevel {
            return entries.first { it.level == value }
        }
    }
}
