package com.kotlinconf23.pathofluckych.classes

import com.kotlinconf23.pathofluckych.classes.items.Defend
import com.kotlinconf23.pathofluckych.classes.items.Item
import com.kotlinconf23.pathofluckych.classes.items.Weapon
import com.kotlinconf23.pathofluckych.objects.armors.Body
import com.kotlinconf23.pathofluckych.objects.shields.LeftHand
import com.kotlinconf23.pathofluckych.objects.weapons.RightHand

class Inventory {
    var weapon: Weapon = RightHand
    var weaponName = weapon.name
    var shield: Defend.Shield = LeftHand
    var shieldName = shield.name
    var armor: Defend.Armor = Body
    var armorName = armor.name

    var isLeftHand: Boolean = false
    var isRightHand: Boolean = false
    var isBody: Boolean = false
    var Items = mutableMapOf<Pair<String, Item>, Int?>()

    fun addElements(vararg item: Pair<String, Item>){
        val thisItems = mutableListOf<Pair<String, Item>>()
        for (i in item) thisItems += listOf(i)
        for (i in thisItems) {
            if(Items.containsKey(i))
                Items[i] = Items[i]!!+1
            else
                Items[i] = 1
        }
    }

    fun dropElements(vararg item: Pair<String, Item>){
        val thisItems = mutableListOf<Pair<String, Item>>()
        for (i in item) thisItems += listOf(i)
        for (i in thisItems) {
            if(Items.containsKey(i)) {
                Items[i] = Items[i]!! - 1
                if (Items[i]!! <= 0)
                    Items.remove(i)
            }
        }
    }
}