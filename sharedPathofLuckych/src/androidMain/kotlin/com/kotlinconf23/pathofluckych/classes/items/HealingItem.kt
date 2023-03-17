package com.kotlinconf23.pathofluckych.classes.items

abstract class HealingItem: Item() {
    override fun getStats(): String {
        var statistics = ""
        if (hp != null)
            statistics += buildString {
                append("Heal: ")
                append(hp)
                append("\n")
            }
        if (dmg != null)
            statistics += buildString {
                append("Dmg: ")
                append(dmg)
                append("\n")
            }
        return statistics
    }
}