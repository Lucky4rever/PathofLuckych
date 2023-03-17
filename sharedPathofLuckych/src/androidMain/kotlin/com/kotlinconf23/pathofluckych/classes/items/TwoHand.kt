package com.kotlinconf23.pathofluckych.classes.items

sealed class TwoHand : Weapon() {
    override var critMultiplier: Int = 5
    init {isLeftHand = true; isRightHand = true}

    abstract class Sword : TwoHand() {
        override fun getInfo() = "This big ${this.name} can hit ${this.dmg} Dmg and deal ${this.critMultiplier}x critical hit with a ${this.critChance}% chance"
    }
    abstract class Spear : TwoHand() {
        override var critMultiplier: Int = 2
        override fun getInfo() = "This ${this.name} is needed to deal piercing damage, It can hit ${this.dmg} Dmg and deal ${this.critMultiplier}x critical hit with a ${this.critChance}% chance"
    }
}