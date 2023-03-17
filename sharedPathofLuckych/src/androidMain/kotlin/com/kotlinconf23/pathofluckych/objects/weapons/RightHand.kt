package com.kotlinconf23.pathofluckych.objects.weapons

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.classes.items.OneHand

object RightHand : OneHand.Sword() {
    init {
        icon = R.mipmap.right_hand_foreground
        name = "none"
        dmg = 0
        critChance = 0
        critMultiplier = 0
        isRightHand = false
    }
}