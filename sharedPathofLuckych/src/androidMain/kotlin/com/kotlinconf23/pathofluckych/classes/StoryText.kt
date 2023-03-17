package com.kotlinconf23.pathofluckych.classes

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.classes.items.Item
import com.kotlinconf23.pathofluckych.objects.Person
import kotlin.random.*

class StoryText(val name: String) {
    lateinit var story: Map<Pair<Int, Int>, Item?>

    private fun isPersonGotQuestItem(questItem: Item?) : Boolean {
        return if (questItem != null)
            Person.inventory.Items.containsKey(questItem.name to questItem)
        else true
    }

    fun getRandomText() : Int {
        val thisChance = Random.nextInt(0..100)
        var storyChance = 0
        for (i in story) {
            storyChance += i.key.second
            if (storyChance >= thisChance)
                if (isPersonGotQuestItem(i.value))
                    return i.key.first
                else
                    getRandomText()
        }
        return R.raw.end
    }
}