package com.kotlinconf23.pathofluckych.interfaces

import android.annotation.SuppressLint
import android.widget.ScrollView
import android.widget.TextView
import com.kotlinconf23.pathofluckych.Setup

interface StoryLogic {
    var storyScroll: ScrollView
    var storyName: TextView
    var storyText: TextView

    fun turn() {
        if (Setup.Preferences.storyWindowLogic.textPosition < Setup.Preferences.storyWindowLogic.fullText.size)
            runText()
        else
            nextText()
        storyScroll.fullScroll(ScrollView.FOCUS_DOWN)
    }

    fun nextText() {
        Setup.Preferences.storyWindowLogic.thisStory++
        Setup.Preferences.storyWindowLogic.textPosition = 0
        Setup.Preferences.storyWindowLogic.text = ""
        Setup.Preferences.storyWindowLogic.getStory = Setup.Preferences.storyWindowLogic.getNextStory()
    }
    @SuppressLint("SetTextI18n")
    fun runText() {
        Setup.Preferences.storyWindowLogic.runLine()
        storyName.text = Setup.Preferences.storyWindowLogic.getStory.second
        storyText.text = Setup.Preferences.storyWindowLogic.text
    }
}