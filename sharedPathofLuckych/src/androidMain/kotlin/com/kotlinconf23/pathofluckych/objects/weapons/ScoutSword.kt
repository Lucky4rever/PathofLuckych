package com.kotlinconf23.pathofluckych.objects.weapons

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.classes.items.OneHand

object ScoutSword : OneHand.Sword() {
    init {
        icon = R.mipmap.scout_sword_foreground
        name = "Scout sword"
        dmg = 8
        critChance = 15
    }
}