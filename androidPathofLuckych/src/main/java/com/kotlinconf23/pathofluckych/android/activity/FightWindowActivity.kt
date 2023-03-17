package com.kotlinconf23.pathofluckych.android.activity

import android.content.*
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlin.random.Random
import androidx.appcompat.app.AppCompatActivity
import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.Setup
import com.kotlinconf23.pathofluckych.interfaces.SavePreferences
import com.kotlinconf23.pathofluckych.objects.Person
import com.kotlinconf23.pathofluckych.classes.GameUnit

class FightWindowActivity : SavePreferences, AppCompatActivity() {
    private lateinit var turn: Button
    private lateinit var run: Button
    private lateinit var heroName: TextView
    private lateinit var heroIcon: ImageView
    private lateinit var enemyName: TextView
    private lateinit var outputText: TextView
    private lateinit var enemyIcon: ImageView
    private lateinit var battleScrollLog: ScrollView

    private var turnNum = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fight_window)
        turn = findViewById(R.id.turn)
        run = findViewById(R.id.run)
        heroName = findViewById(R.id.heroName)
        heroIcon = findViewById(R.id.heroIcon)
        enemyName = findViewById(R.id.enemyName)
        enemyIcon = findViewById(R.id.enemyIcon)
        outputText = findViewById(R.id.battleLog)
        battleScrollLog = findViewById(R.id.battleScrollLog)
        run.visibility = View.GONE
        run.isClickable = false

        fight(Person, Setup.Preferences.enemy)
    }

    private fun fight(enemy1: GameUnit, enemy2: GameUnit) {
        Person.log = ""
        heroIcon.setImageResource(enemy1.race.icon)
        enemyIcon.setImageResource(enemy2.race.icon)
        heroName.text = enemy1.name
        enemyName.text = enemy2.name
        turn.setOnClickListener {
            when (Random.nextBoolean()) {
                true -> {
                    enemy1.attack(enemy2)
                    if (enemy2.stats.hp <= 0) {
                        enemy2.respawn()
                        startActivity(Intent(this, StoryWindowActivity::class.java))
                    }
                }
                false -> {
                    enemy2.attack(enemy1)
                    if (enemy1.stats.hp <= 0) {
                        enemy1.respawn()
                        startActivity(Intent(this, StoryWindowActivity::class.java))
                    }
                }
            }
            outputText.text = Person.log
            battleScrollLog.fullScroll(ScrollView.FOCUS_DOWN)
            if(turnNum > 20) {
                run.isClickable = true
                run.visibility = View.VISIBLE
            }
            turnNum++
            savePreferences()
        }

        run.setOnClickListener {
            if (Random.nextInt() > 30)
                startActivity(Intent(this, StoryWindowActivity::class.java))
            else
                Person.log += "You can't run now\n"
            outputText.text = Person.log
        }
    }

    override fun savePreferences() {
        val sharedPersonPreferences: SharedPreferences = this.getSharedPreferences(Setup.Preferences.sharedPrefPersonFile, Context.MODE_PRIVATE)
        val editorPerson: SharedPreferences.Editor = sharedPersonPreferences.edit()
        editorPerson.putString("Person_Name", Person.name)
        editorPerson.putInt("Person_Lvl", Person.lvl)
        editorPerson.putInt("Person_stats_HP", Person.stats.hp)

        editorPerson.apply()
    }
}