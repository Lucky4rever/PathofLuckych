package com.kotlinconf23.pathofluckych

import android.annotation.SuppressLint
import com.kotlinconf23.pathofluckych.classes.Spawn
import com.kotlinconf23.pathofluckych.classes.StoryText
import com.kotlinconf23.pathofluckych.classes.items.Item
import com.kotlinconf23.pathofluckych.classes.logic.*
import com.kotlinconf23.pathofluckych.objects.armors.*
import com.kotlinconf23.pathofluckych.objects.items.*
import com.kotlinconf23.pathofluckych.objects.shields.*
import com.kotlinconf23.pathofluckych.objects.weapons.*

sealed class Setup {
    object ItemSetup {
        var allOneHandWeapons = mapOf(
            Pair("Wooden sword", WoodenSword),
            Pair("Scout sword", ScoutSword),
            Pair("Wood cutter's axe", WoodCutterAxe)
        )
        var allTwoHandWeapons = mapOf(
            Pair("Garud spear", GarudSpear),
            Pair("Old sword", OldSword),
            Pair("Big sword", BigSword)
        )
        var allShields = mapOf(
            Pair("Wooden shield", WoodenShield),
            Pair("Parry sword", ParrySword)
        )
        var allArmors = mapOf(
            Pair("Fancy tunic", FancyTunic),
            Pair("Chain-mail plate", ChainMailPlate)
        )
        var allItems = mutableMapOf(
            Pair("Herb", Herb),
            Pair("Meat", Meat),
            Pair("Rock", Rock),
            Pair("Throwing knife", ThrowingKnife)
        )

        init {
            allItems.putAll(allOneHandWeapons)
            allItems.putAll(allTwoHandWeapons)
            allItems.putAll(allShields)
            allItems.putAll(allArmors)
        }

        fun getItemByName(key: String): Item? {
            for (i in allItems)
                if (i.key == key)
                    return i.value
            return null
        }
    }
    object StorySetup {
        private val prolog = StoryText("?")
        private val cave1 = StoryText("Cave")
        private val cave2 = StoryText("Escape from cave")
        private val firstPathChoice = StoryText("First choice a path")
        private val forest = StoryText("Forest")
//        private val mountains = StoryText("Mountains")
        private val end = StoryText("End")

        private val storyLibrary = listOf(prolog, cave1, cave2, firstPathChoice, forest, end)
        var story = mutableListOf<StoryText>()
        const val storiesNames = "?+Cave+Escape from cave+First choice a path"

        init {
            prolog.story = mapOf(Pair(R.raw.prolog, 100) to null)
            cave1.story = mapOf(
                Pair(R.raw.cave1_1, 40) to null,
                Pair(R.raw.cave1_2, 50) to null,
                Pair(R.raw.cave1_3, 10) to null
            )
            cave2.story = mapOf(
                Pair(R.raw.cave2_1, 30) to null,
                Pair(R.raw.cave2_2, 60) to null,
                Pair(R.raw.cave2_3, 10) to null
            )
            firstPathChoice.story = mapOf(
//                Pair(R.raw.first_path_choice_1, 60) to null,
//                Pair(R.raw.first_path_choice_2, 40) to null
                Pair(R.raw.first_path_choice_1, 100) to null
            )
            forest.story = mapOf(
                Pair(R.raw.forest1, 5) to null,
                Pair(R.raw.forest2, 10) to Herb,
                Pair(R.raw.forest3, 15) to null,
                Pair(R.raw.forest4, 10) to null,
                Pair(R.raw.forest5, 20) to null,
                Pair(R.raw.forest6, 15) to null,
                Pair(R.raw.forest7, 10) to null,
                Pair(R.raw.forest8, 10) to null,
                Pair(R.raw.forest9, 5) to null
            )
//            mountains.story = mapOf(Pair(R.raw.prolog, 100) to null)
            end.story = mapOf(Pair(R.raw.end, 100) to null)



            for (i in story)
                Preferences.storiesNames += i.name
        }

        fun findStoryByName(name: String): StoryText {
            for (i in storyLibrary)
                if (i.name == name)
                    return i
            return end
        }

        fun fillStory() {
            story = mutableListOf()
            for (i in Preferences.storiesNames.toStoryList()) {
                story += findStoryByName(i)
            }
        }

        private fun String.toStoryList(): MutableList<String> {
            val storyList = mutableListOf<String>()
            var storyName = ""
            for (i in this) {
                if (i == '+') {
                    storyList += storyName
                    storyName = ""
                }
                else
                    storyName += i
            }
            if (storyName != "")
                storyList += storyName
            return storyList
        }
    }

    object Preferences {
        const val sharedPrefStoryFile = "story_statistic"
        const val sharedPrefPersonFile = "person_statistic"

        var storiesNames = StorySetup.storiesNames
        var storyWindowLogic = StoryWindowLogic()
        @SuppressLint("StaticFieldLeak")
        var inventoryWindowLogic = InventoryWindowLogic()
        var enemy = Spawn()
    }
}