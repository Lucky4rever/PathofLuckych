package com.kotlinconf23.pathofluckych.android.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.Setup
import com.kotlinconf23.pathofluckych.classes.items.Defend
import com.kotlinconf23.pathofluckych.classes.items.Weapon
import com.kotlinconf23.pathofluckych.classes.logic.InventoryWindowLogic
import com.kotlinconf23.pathofluckych.classes.logic.StoryWindowLogic
import com.kotlinconf23.pathofluckych.objects.Person

class MainActivity : AppCompatActivity() {
    private lateinit var startButton: Button
    private lateinit var restartButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_general)
        startButton = findViewById(R.id.start)
        restartButton = findViewById(R.id.restart)
        openOptionsMenu()

        startButton.setOnClickListener {
            openPreferences()
            quickSetup()
        }

        restartButton.setOnClickListener {
            Setup.Preferences.storyWindowLogic = StoryWindowLogic()
            Setup.Preferences.inventoryWindowLogic = InventoryWindowLogic()
            Setup.Preferences.storiesNames = Setup.StorySetup.storiesNames
            quickSetup()
        }
    }

    private fun openPreferences() {
        val sharedPrefPerson: SharedPreferences = this.getSharedPreferences(Setup.Preferences.sharedPrefPersonFile, Context.MODE_PRIVATE)
        Person.name = sharedPrefPerson.getString("Person_Name", Person.name)!!
        Person.xp = sharedPrefPerson.getInt("Person_XP", Person.xp)
        Person.lvl = sharedPrefPerson.getInt("Person_Lvl", Person.lvl)
        Person.stats.hp = sharedPrefPerson.getInt("Person_stats_HP", Person.stats.hp)
        Person.stats.dmg = sharedPrefPerson.getInt("Person_stats_Dmg", Person.race.dmg)
        Person.stats.def = sharedPrefPerson.getInt("Person_stats_Def", Person.race.def)
        Person.race.hp = sharedPrefPerson.getInt("Person_race_HP", Person.race.hp)
        Person.race.dmg = sharedPrefPerson.getInt("Person_race_Dmg", Person.race.dmg)
        Person.race.def = sharedPrefPerson.getInt("Person_race_Def", Person.race.def)
        Person.race.xp = sharedPrefPerson.getInt("Person_race_XP", Person.race.xp)

        Person.inventory.weaponName = sharedPrefPerson.getString("Person_weapon_name", Person.inventory.weaponName)!!
        Person.inventory.shieldName = sharedPrefPerson.getString("Person_shield_name", Person.inventory.shieldName)!!
        Person.inventory.armorName = sharedPrefPerson.getString("Person_armor_name", Person.inventory.armorName)!!
        Person.inventoryItemNames = sharedPrefPerson.getStringSet("Person_inventory_names", Person.inventoryItemNames)!!

        val sharedPrefStory: SharedPreferences = this.getSharedPreferences(Setup.Preferences.sharedPrefStoryFile, Context.MODE_PRIVATE)
        Setup.Preferences.storyWindowLogic.thisStory = sharedPrefStory.getInt("Story_number", Setup.Preferences.storyWindowLogic.thisStory)
        Setup.Preferences.storyWindowLogic.text = sharedPrefStory.getString("This_story", Setup.Preferences.storyWindowLogic.text)!!
        Setup.Preferences.storiesNames = sharedPrefStory.getString("Story", Setup.Preferences.storiesNames)!!
    }
    private fun quickSetup() {
        Setup.StorySetup.fillStory()

        Person.reloadInventoryByNames()
        Person.reloadInventoryItemNames()

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        if(Setup.ItemSetup.allItems.contains(Person.inventory.weaponName))
            Person.equip(Setup.ItemSetup.allItems[Person.inventory.weaponName] as Weapon)
        if(Setup.ItemSetup.allShields.contains(Person.inventory.shieldName))
            Person.equip(Setup.ItemSetup.allShields[Person.inventory.shieldName] as Defend.Shield)
        if(Setup.ItemSetup.allArmors.contains(Person.inventory.armorName))
            Person.equip(Setup.ItemSetup.allArmors[Person.inventory.armorName] as Defend.Armor)

        startActivity(Intent(this, StoryWindowActivity::class.java))
    }
}