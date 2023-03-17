package com.kotlinconf23.pathofluckych.interfaces

import com.kotlinconf23.pathofluckych.classes.GameUnit
import com.kotlinconf23.pathofluckych.classes.items.*

interface UnitActions {
    fun equip(item: Weapon)
    fun equip(item: OneHand)
    fun equip(item: TwoHand)
    fun equip(item: Defend.Shield)
    fun equip(item: Defend.Armor)

    fun unequip(item: Weapon)
    fun unequip(item: OneHand)
    fun unequip(item: TwoHand)
    fun unequip(item: Defend.Shield)
    fun unequip(item: Defend.Armor)

    var log: String
    fun obtain(vararg item: Item)
    fun drop(vararg item: Item)
    fun lvlUp()
    fun attack(enemy: GameUnit)
    fun loot(enemy: GameUnit)
    fun dead()
    fun respawn()
}