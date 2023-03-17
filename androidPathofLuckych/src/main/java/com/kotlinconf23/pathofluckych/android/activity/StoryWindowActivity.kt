package com.kotlinconf23.pathofluckych.android.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.Setup.Preferences
import com.kotlinconf23.pathofluckych.interfaces.*
import com.kotlinconf23.pathofluckych.objects.Person

class StoryWindowActivity : StoryLogic, SavePreferences, AppCompatActivity() {
    override lateinit var storyName: TextView
    override lateinit var storyText: TextView
    override lateinit var storyScroll: ScrollView

    private lateinit var toInventoryButton: Button
    private lateinit var nextTurn: Button

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.story_window)
        storyName = findViewById(R.id.storyName)
        storyText = findViewById(R.id.storyText)
        nextTurn = findViewById(R.id.nextTurn)
        storyScroll = findViewById(R.id.storyScroll)

        storyName.text = Preferences.storyWindowLogic.getStory.second
        if (Preferences.storyWindowLogic.isFirstProgramStart) {
            turn()
            Preferences.storyWindowLogic.isFirstProgramStart = false
        }
        else
            storyText.text = Preferences.storyWindowLogic.text


//        if (Preferences.storyWindowLogic.textPosition == Preferences.storyWindowLogic.fullText.size) {
//            storyText.text = Preferences.storyWindowLogic.text
//            createToast(baseContext, "To continue press 'Next'")
//        } else
//            storyText.text = Preferences.storyWindowLogic.text

        nextTurn.setOnClickListener { turn() }

        toInventoryButton = findViewById(R.id.toInventoryButton)
        toInventoryButton.setOnClickListener { startActivity(Intent(this, InventoryWindowActivity::class.java)) }
    }

    override fun turn() {
        super.turn()
        if(Person.isNowFight)
            startActivity(Intent(this, FightWindowActivity::class.java))
        storyScroll.fullScroll(ScrollView.FOCUS_DOWN)
    }

    override fun nextText() {
        super.nextText()
        Preferences.storyWindowLogic.fullText = application.resources.openRawResource(Preferences.storyWindowLogic.getStory.first)
            .bufferedReader().use { it.readLines() }
        Person.reloadInventoryItemNames()
        savePreferences()
        runText()
    }
    @SuppressLint("SetTextI18n")
    override fun runText() {
        super.runText()
        if (Preferences.storyWindowLogic.textPosition == Preferences.storyWindowLogic.fullText.size)
            createToast(baseContext, "To continue press 'Next'")
    }

    override fun savePreferences() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(Preferences.sharedPrefStoryFile, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()

        editor.putInt("Story_number", Preferences.storyWindowLogic.thisStory-1)
        editor.putString("This_story", Preferences.storyWindowLogic.text)
        editor.putString("Story", Preferences.storiesNames)

        val sharedPersonPreferences: SharedPreferences = this.getSharedPreferences(Preferences.sharedPrefPersonFile, Context.MODE_PRIVATE)
        val editorPerson: SharedPreferences.Editor = sharedPersonPreferences.edit()

        editorPerson.putString("Person_Name", Person.name)
        editorPerson.putInt("Person_XP", Person.xp)
        editorPerson.putInt("Person_Lvl", Person.lvl)
        editorPerson.putInt("Person_stats_HP", Person.stats.hp)
        editorPerson.putInt("Person_stats_Dmg", Person.race.dmg)
        editorPerson.putInt("Person_stats_Def", Person.race.def)
        editorPerson.putInt("Person_race_HP", Person.race.hp)
        editorPerson.putInt("Person_race_Dmg", Person.race.dmg)
        editorPerson.putInt("Person_race_Def", Person.race.def)
        editorPerson.putInt("Person_race_XP", Person.race.xp)

        editorPerson.putStringSet("Person_inventory_names", Person.inventoryItemNames)
        editorPerson.putString("Person_weapon_name", Person.inventory.weaponName)!!
        editorPerson.putString("Person_shield_name", Person.inventory.shieldName)!!
        editorPerson.putString("Person_armor_name", Person.inventory.armorName)!!

        editorPerson.apply()
        editor.apply()
    }

    @Suppress("DEPRECATION")
    private fun createToast(context: Context, text: String) {
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        val viewGroup = toast.view as ViewGroup?
        val textView = viewGroup!!.getChildAt(0) as TextView
        textView.setBackgroundResource(R.color.my_transparent)
        textView.textSize = 20f
        toast.show()
    }
}