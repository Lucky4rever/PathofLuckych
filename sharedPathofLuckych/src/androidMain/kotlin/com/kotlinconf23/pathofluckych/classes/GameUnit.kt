package com.kotlinconf23.pathofluckych.classes

import com.kotlinconf23.pathofluckych.Setup.ItemSetup
import com.kotlinconf23.pathofluckych.classes.items.*
import com.kotlinconf23.pathofluckych.interfaces.ShowStatistics
import com.kotlinconf23.pathofluckych.interfaces.UnitActions
import com.kotlinconf23.pathofluckych.objects.Person
import com.kotlinconf23.pathofluckych.objects.armors.Body
import com.kotlinconf23.pathofluckych.objects.items.Meat
import com.kotlinconf23.pathofluckych.objects.shields.LeftHand
import com.kotlinconf23.pathofluckych.objects.weapons.RightHand
import kotlin.random.Random
import kotlin.random.nextInt

abstract class GameUnit : UnitActions, ShowStatistics {
    lateinit var name: String
    open lateinit var race: Race
    lateinit var stats: Stats
    var xp: Int = 0
    var lvl: Int = 1
    var inventory = Inventory()
    override var log: String = ""

    override fun getStats(): String {
        var statistics = "Name: ${name}\nLvl: ${lvl}\nHP: ${stats.hp}/${race.hp}\nXP: ${xp}/${race.xp}\n"
        statistics += if (race.dmg == stats.dmg)
            "Damage: ${race.dmg}\n"
        else
            "Damage: ${race.dmg}-${stats.dmg}\n"
        statistics += if (race.def == stats.def)
            "Defence: ${race.def}\n"
        else
            "Defence: ${race.def}-${stats.def}\n"
        statistics += "Equipped: "
        if (!inventory.isBody && !inventory.isRightHand && !inventory.isLeftHand)
            statistics += "none."
        else {
            if (inventory.isRightHand)
                statistics += "\n${inventory.weapon.name}"
            if (inventory.isLeftHand && inventory.shield.name != "none")
                statistics += "\n${inventory.shield.name}"
            if (inventory.isBody)
                statistics += "\n${inventory.armor.name}"
        }
        return statistics
    }




    override fun equip(item: Weapon) {
        when (item) {
            is OneHand -> if(item.isRightHand && ItemSetup.allOneHandWeapons.containsKey(item.name)) {
                "Equipped ${item.name}"
                inventory.isRightHand = true
                inventory.weapon = item
                stats.dmg = race.dmg + item.dmg!!
                Person.inventory.weaponName = item.name
            }
            is TwoHand -> if(item.isRightHand && ItemSetup.allTwoHandWeapons.containsKey(item.name)) {
                "Equipped ${item.name}"
                inventory.isLeftHand = true
                inventory.isRightHand = true
                inventory.weapon = item
                stats.dmg = race.dmg + item.dmg!!
                inventory.shield = LeftHand
                stats.def = race.def + inventory.armor.Def
                Person.inventory.weaponName = item.name
            }
        }
    }
    override fun equip(item: OneHand) {
        if(item.isRightHand && ItemSetup.allOneHandWeapons.containsKey(item.name)) {
            "Equipped ${item.name}"
            inventory.isRightHand = true
            inventory.weapon = item
            stats.dmg = race.dmg + item.dmg!!
            Person.inventory.weaponName = item.name
        }
    }
    override fun equip(item: TwoHand) {
        if(item.isRightHand && ItemSetup.allTwoHandWeapons.containsKey(item.name)) {
            "Equipped ${item.name}"
            inventory.isLeftHand = true
            inventory.isRightHand = true
            inventory.weapon = item
            stats.dmg = race.dmg + item.dmg!!
            inventory.shield = LeftHand
            stats.def = race.def + inventory.armor.Def
            Person.inventory.weaponName = item.name
        }
    }
    override fun equip(item: Defend.Shield) {
        if(item.isLeftHand && ItemSetup.allShields.containsKey(item.name)) {
            "Equipped ${item.name}"
            inventory.isLeftHand = true
            inventory.shield = item
            stats.def = race.def + item.Def + inventory.armor.Def
            if (inventory.weapon is TwoHand) {
                inventory.isRightHand = false
                inventory.weapon = RightHand
                stats.dmg = race.dmg + inventory.weapon.dmg!!
            }
            Person.inventory.shieldName = item.name
        }
    }
    override fun equip(item: Defend.Armor) {
        if(item.isBody && ItemSetup.allArmors.containsKey(item.name)) {
            "Equipped ${item.name}"
            inventory.isBody = true
            inventory.armor = item
            stats.def = race.def + item.Def + inventory.shield.Def
            Person.inventory.armorName = item.name
        }
    }




    override fun unequip(item: Weapon) {
        when (item) {
            is OneHand -> if (inventory.isRightHand && ItemSetup.allOneHandWeapons.containsKey(item.name)) {
                "Unequipped ${item.name}"
                inventory.isRightHand = false
                inventory.weapon = RightHand
                stats.dmg = race.dmg
                inventory.isRightHand = false
                Person.inventory.weaponName = "none"
            }
            is TwoHand -> if (inventory.isRightHand && ItemSetup.allTwoHandWeapons.containsKey(item.name)) {
                "Unequipped ${item.name}"
                inventory.isLeftHand = false
                inventory.isRightHand = false
                inventory.shield = LeftHand
                inventory.weapon = RightHand
                stats.dmg = race.dmg
                inventory.isLeftHand = false
                inventory.isRightHand = false
                Person.inventory.weaponName = "none"
            }
        }
    }
    override fun unequip(item: OneHand) {
        if(inventory.isRightHand && ItemSetup.allOneHandWeapons.containsKey(item.name)) {
            "Unequipped ${item.name}"
            inventory.isRightHand = false
            inventory.weapon = RightHand
            stats.dmg = race.dmg
            inventory.isRightHand = false
            Person.inventory.weaponName = "none"
        }
    }
    override fun unequip(item: TwoHand) {
        if(inventory.isRightHand && ItemSetup.allTwoHandWeapons.containsKey(item.name)) {
            "Unequipped ${item.name}"
            inventory.isLeftHand = false
            inventory.isRightHand = false
            inventory.shield = LeftHand
            inventory.weapon = RightHand
            stats.dmg = race.dmg
            inventory.isLeftHand = false
            inventory.isRightHand = false
            Person.inventory.weaponName = "none"
        }
    }
    override fun unequip(item: Defend.Shield) {
        if(inventory.isLeftHand && ItemSetup.allShields.containsKey(item.name)) {
            "Unequipped ${item.name}"
            inventory.isLeftHand = false
            inventory.shield = LeftHand
            stats.def = race.def + inventory.armor.Def
            inventory.isLeftHand = false
            Person.inventory.shieldName = "none"
        }
    }
    override fun unequip(item: Defend.Armor) {
        if(inventory.isBody && ItemSetup.allArmors.containsKey(item.name)) {
            "Unequipped ${item.name}"
            inventory.isBody = false
            inventory.armor = Body
            stats.def = race.def + inventory.shield.Def
            inventory.isBody = false
            Person.inventory.armorName = "none"
        }
    }




    override fun obtain(vararg item: Item) {
        for(i in item)
            this.inventory.addElements(Pair(i.name, i))
    }
    override fun drop(vararg item: Item) {
        for(i in item)
            this.inventory.dropElements(Pair(i.name, i))
    }


    override fun lvlUp() {
        val statsUp = Random.nextInt(1, 4)
        xp += Random.nextInt(1, 100)
        if (xp >= race.xp) {
            when (Random.nextInt(1..3)) {
                1 -> {
                    race.hp += statsUp
                    stats.hp += statsUp
                }
                2 -> {
                    race.dmg += statsUp
                    stats.dmg += statsUp
                }
                3 -> {
                    race.def += statsUp
                    stats.def += statsUp
                }
            }
            xp -= race.xp
            race.xp += 100 + Random.nextInt(100)
            lvl++
        }
    }


    override fun attack(enemy: GameUnit) {
        val crit = if(Random.nextInt(100) < this.inventory.weapon.critChance)
            inventory.weapon.critMultiplier
        else 1
        var dmg = if(this.race.dmg == this.stats.dmg)
            this.race.dmg
        else
            Random.nextInt(this.race.dmg, this.stats.dmg + 1)
        dmg *= crit

        val def = if(enemy.race.def == enemy.stats.def)
            enemy.race.def
        else
            Random.nextInt(enemy.race.def, enemy.stats.def + 1)

        if (dmg > enemy.stats.def) {
            Person.log += "$name deal ${dmg - def} dmg to ${enemy.name}\n"
            enemy.stats.hp -= (dmg - def)
        }
        else
            Person.log += "${enemy.name} block all damage from $name\n"

        if(enemy.stats.hp <= 0) {
            enemy.dead()

            Person.isNowFight = false
        }
    }


    override fun loot(enemy: GameUnit) {
        if (enemy.stats.hp <= 0) {
            val dropChance: Int = Random.nextInt(100)
            this.log += "Drop: " + if (dropChance < 10) {
                when(Random.nextBoolean()) {
                    true -> {
                        if (enemy.inventory.weapon != RightHand)
                            Person.obtain(enemy.inventory.weapon)
                        "${Meat.name}\n"
                    }
                    false -> {
                        if (enemy.inventory.shield != LeftHand)
                            Person.obtain(enemy.inventory.shield)
                        "${Meat.name}\n"
                    }
                }
            }
            else if (dropChance < 50) {
                val num = Random.nextInt(1, 4)
                for (i in 1..num)
                    Person.obtain(Meat)
                "$num ${Meat.name}\n"
            }
            else "nothing\n"
        }
    }
}