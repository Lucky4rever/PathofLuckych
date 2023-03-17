package com.kotlinconf23.pathofluckych.interfaces

import com.kotlinconf23.pathofluckych.classes.items.Defend
import com.kotlinconf23.pathofluckych.classes.items.Weapon

interface InventoryLogic {
    fun setOnEquip(itemName: String)
    fun setOnUnequip(itemName: String)
    fun setOnUnequip(item: Weapon)
    fun setOnUnequip(item: Defend.Shield)
    fun setOnUnequip(item: Defend.Armor)

}