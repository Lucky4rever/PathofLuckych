package com.kotlinconf23.pathofluckych.classes.logic

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.Setup
import com.kotlinconf23.pathofluckych.Setup.StorySetup.story
import com.kotlinconf23.pathofluckych.classes.Race
import com.kotlinconf23.pathofluckych.classes.Spawn
import com.kotlinconf23.pathofluckych.classes.items.Defend
import com.kotlinconf23.pathofluckych.classes.items.Weapon
import com.kotlinconf23.pathofluckych.objects.Person

class StoryWindowLogic {
    var getStory = getNextStory()
    var fullText: List<String> = emptyList()
    var textPosition = 0
    var thisStory = -1
    var text = ""
    var isFirstProgramStart = true

    fun runLine() {
        if (fullText.isNotEmpty()) {
            while (fullText[textPosition] == "")
                textPosition++
            when (fullText[textPosition][0]) {
                '@' -> {
                    if (fullText[textPosition][1] == '+') {
                        Person.obtain(Setup.ItemSetup.getItemByName(fullText[textPosition].substring(2))!!)
                        text += "Obtained ${fullText[textPosition].substring(2)}\n"
                    }
                    else if (fullText[textPosition][1] == '-') {
                        Person.drop(Setup.ItemSetup.getItemByName(fullText[textPosition].substring(2))!!)
                        text += "Dropped ${fullText[textPosition].substring(2)}\n"
                    }
                }
                '%' -> {
                    val nextTextName = Setup.StorySetup.findStoryByName(fullText[textPosition].substring(1))
                    story.add(nextTextName)
                    Setup.Preferences.storiesNames += "+${nextTextName.name}"
                }
                '#' -> {
                    when(fullText[textPosition].substring(1)) {
                        "mob" -> {
                            Setup.Preferences.enemy = Spawn.mob(
                                fullText[textPosition + 1].substring(1),
                                Race.valueOf(fullText[textPosition + 2].substring(1))
                            )
                            textPosition += 2
                        }
                        "enemy" -> {
                            Setup.Preferences.enemy = Spawn.enemy(
                                fullText[textPosition + 1].substring(1),
                                Race.valueOf(fullText[textPosition + 2].substring(1)),
                                Setup.ItemSetup.getItemByName(fullText[textPosition + 3]
                                    .substring(1)) as Weapon?,
                                Setup.ItemSetup.getItemByName(fullText[textPosition + 4]
                                    .substring(1)) as Defend.Shield?
                            )
                            textPosition += 4
                        }
                    }
                    Person.isNowFight = true
                }
                '$' -> {
                    val changeInfo = fullText[textPosition].substring(1)
                    when {
                        changeInfo.startsWith("hp") -> {
                            text += if (changeInfo[2]=='+') {
                                Person.heal(changeInfo.substring(3).toInt())
                                "Life increased by ${changeInfo.substring(3)} points\n"
                            } else {
                                Person.heal(-changeInfo.substring(3).toInt())
                                "Life reduced by ${changeInfo.substring(3)} points\n"
                            }
                        }
                    }
                }
                else -> text += "${fullText[textPosition]}\n"
            }
            textPosition++
        }
    }

    fun getNextStory() : Pair<Int, String> {
        return if (story.size > thisStory)
            story[thisStory].getRandomText() to story[thisStory].name
        else
            R.raw.end to "The end!"
    }

    private operator fun <E> MutableSet<E>.get(thisStory: Int): E? {
        for ((num, i) in this.withIndex())
            if (num == thisStory)
                return i
        return null
    }
}