package com.kotlinconf23.pathofluckych.classes.items

abstract class Weapon : Item() {
    open var critChance: Int = 0
    open var critMultiplier: Int = 0

    override fun getStats(): String {
        var statistics = ""
        if (dmg != null && dmg != 0)
            statistics += "Damage: $dmg\n"
        if (critChance != 0)
            statistics += "Critical chance: ${critChance}%\n"
        if (critMultiplier != 0)
            statistics += "Critical multiplier: ${critMultiplier}x\n"
        return statistics
    }
}