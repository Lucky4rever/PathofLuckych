package com.kotlinconf23.pathofluckych.classes.items

sealed class OneHand : Weapon() {
    override var critMultiplier: Int = 3
    init {isRightHand = true}

    abstract class Sword : OneHand() {
        override fun getInfo() = "This ${this.name} can hit ${this.dmg} Dmg and deal ${this.critMultiplier}x critical hit with a ${this.critChance}% chance"
    }

    abstract class Axe : OneHand() {
        override var critMultiplier: Int = 4
        override fun getInfo() = "This ${this.name} can hit ${this.dmg} Dmg and deal ${this.critMultiplier}x critical hit with a ${this.critChance}% chance, inflicting heavy wounds"
    }
}