package com.kotlinconf23.pathofluckych.objects.shields

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.classes.items.Defend

object LeftHand : Defend.Shield() {
    init {
        icon = R.mipmap.left_hand_foreground
        name = "none"
        Def = 0
        DefChance = 0
        isLeftHand = false
    }
}