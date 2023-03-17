package com.kotlinconf23.pathofluckych.classes

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.Setup
import com.kotlinconf23.pathofluckych.classes.items.Defend
import com.kotlinconf23.pathofluckych.classes.items.Weapon
import com.kotlinconf23.pathofluckych.objects.Person
import kotlin.random.Random

class Spawn() : GameUnit() {
    constructor(Name: String, race: Race, weapon: Weapon?, shield: Defend.Shield?) : this() {
        println("Spawned ${race.raceName} $Name")
        this.name = Name
        this.race = race
        this.stats = Stats(race.hp, race.def, race.dmg)
        if(weapon != null) {
            inventory.weapon = weapon
            stats.dmg = race.dmg + weapon.dmg!!
        }
        if(shield != null) {
            inventory.shield = shield
            stats.def = race.def + shield.Def
        }
        this.race.icon = getIcon(this.race.raceName)
    }
    constructor (Name: String, race: Race) : this() {
        println("Spawned ${race.raceName}")
        this.name = Name
        this.race = race
        this.stats = Stats(race.hp, race.def, race.dmg)
        this.race.icon = getIcon(this.race.raceName)
    }
    companion object {
        fun enemy(Name: String, race: Race, weapon: Weapon?, shield: Defend.Shield?) = Spawn(Name, race, weapon, shield)
        fun mob(Name: String, race: Race) = Spawn(Name, race)
    }
    private var mobIcons = mutableMapOf(
        Pair(R.mipmap.orc1_foreground, "Orc"),
        Pair(R.mipmap.orc2_foreground, "Orc"),
        Pair(R.mipmap.orc3_foreground, "Orc"),
        Pair(R.mipmap.orc4_foreground, "Orc"),
        Pair(R.mipmap.elf1_foreground, "Elf"),
        Pair(R.mipmap.elf2_foreground, "Elf"),
        Pair(R.mipmap.elf3_foreground, "Elf"),
        Pair(R.mipmap.wolf1_foreground, "Wolf"),
        Pair(R.mipmap.wolf2_foreground, "Wolf"),
        Pair(R.mipmap.wolf3_foreground, "Wolf"),
        Pair(R.mipmap.bear1_foreground, "Bear"),
        Pair(R.mipmap.bear2_foreground, "Bear")
    )
    override fun getInfo() = "Mob"
    private fun getIcon(name: String) : Int {
        val idList = mutableListOf<Int>()
        for(i in mobIcons)
            if (i.component2() == name)
                idList += i.component1()
        return idList[Random.nextInt(idList.size)]
    }
    override fun dead() {
        Person.log += "${this.name} dead\n"
        Person.loot(this)
        Person.lvlUp()
    }
    override fun respawn() {
        Setup.Preferences.enemy = Spawn()
    }
}