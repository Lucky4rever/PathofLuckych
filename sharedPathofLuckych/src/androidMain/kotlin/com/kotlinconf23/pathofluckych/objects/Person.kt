package com.kotlinconf23.pathofluckych.objects

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.Setup.*
import com.kotlinconf23.pathofluckych.classes.GameUnit
import com.kotlinconf23.pathofluckych.classes.Race
import com.kotlinconf23.pathofluckych.classes.Stats
import com.kotlinconf23.pathofluckych.classes.items.HealingItem
import com.kotlinconf23.pathofluckych.classes.items.Item
import com.kotlinconf23.pathofluckych.classes.logic.InventoryWindowLogic
import com.kotlinconf23.pathofluckych.classes.logic.StoryWindowLogic

object Person : GameUnit() {
    lateinit var inventoryItemNames: MutableSet<String>
    var isNowFight: Boolean = false
    init {
        name = "Hero"
        race = Race.Hero
        stats = Stats(race.hp, race.def, race.dmg)
        log = ""
        reloadInventoryItemNames()
        race.icon = R.mipmap.hero_foreground
    }

    fun reloadInventoryItemNames() {
        inventoryItemNames = mutableSetOf()
        for (i in Person.inventory.Items)
            inventoryItemNames.add("${i.key.first}=${i.value}")
    }

    fun reloadInventoryByNames() {
        for (inventoryItem in inventoryItemNames) {
            var thisItemName = ""
            var thisItemValue = ""
            var isNameAlreadyDone = false
            for (symbol in inventoryItem) {
                if (symbol == '=')
                    isNameAlreadyDone = true
                else if (!isNameAlreadyDone)
                    thisItemName += symbol
                else
                    thisItemValue += symbol
            }
            if (thisItemName != "none")
                Person.inventory.Items[Pair(thisItemName, ItemSetup.allItems[thisItemName]!!)] =
                    thisItemValue.toInt()
        }
    }

    override fun getInfo() = "Hero, that came to new world"

    override fun dead() {
        log += "You died\n"
        Preferences.storyWindowLogic = StoryWindowLogic()
        Preferences.inventoryWindowLogic = InventoryWindowLogic()
        Preferences.storiesNames = StorySetup.storiesNames
    }
    override fun respawn() {
        stats = Stats(race.hp, race.def, race.dmg)
        unequip(inventory.weapon)
        unequip(inventory.shield)
        unequip(inventory.armor)
        inventory.Items = mutableMapOf()
        reloadInventoryItemNames()
    }

    override fun drop(vararg item: Item) {
        super.drop(*item)
        reloadInventoryItemNames()
    }

    override fun obtain(vararg item: Item) {
        super.obtain(*item)
        reloadInventoryItemNames()
    }
    fun use(thisItemName: String) {
        val thisItem = ItemSetup.getItemByName(thisItemName)
        if (thisItem != null) {
            when (thisItem) {
                is HealingItem -> {
                    heal(thisItem.hp!!)
                    drop(thisItem)
                }
            }
        }
    }
    fun use(thisItem: Item?) {
        if (thisItem != null) {
            when (thisItem) {
                is HealingItem -> {
                    heal(thisItem.hp!!)
                    drop(thisItem)
                }
            }
        }
    }
    fun heal(HP: Int) {
        stats.hp += if(HP < (race.hp-stats.hp))
            HP
        else
            race.hp-stats.hp

        if(stats.hp <= 0) {
            dead()
            respawn()
        }
    }
}

