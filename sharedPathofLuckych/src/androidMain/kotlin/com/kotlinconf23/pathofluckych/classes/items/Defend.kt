package com.kotlinconf23.pathofluckych.classes.items

sealed class Defend : Item() {
    var Def: Int = 0

    abstract class Shield : Defend() {
        override fun getInfo() = "This ${this.name} can protect against ${this.Def} Dmg with a ${this.DefChance}% chance"
        var DefChance: Int = 0
        init {isLeftHand = true}

        override fun getStats(): String {
            var statistics = ""
            if (Def != 0)
                statistics += "Defend: $Def\n"
            if (DefChance != 0)
                statistics += "Defend chance: $DefChance%\n"
            return statistics
        }
    }

    abstract class Armor : Defend() {
        override fun getInfo() = "This ${this.name} can protect against ${this.Def} Dmg"
        init {isBody = true}

        override fun getStats(): String {
            var statistics = ""
            if (Def != 0)
                statistics += "Defend: $Def\n"
            return statistics
        }
    }
}