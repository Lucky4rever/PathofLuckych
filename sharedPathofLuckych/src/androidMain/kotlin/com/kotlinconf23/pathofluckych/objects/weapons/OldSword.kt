package com.kotlinconf23.pathofluckych.objects.weapons

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.classes.items.TwoHand

object OldSword : TwoHand.Sword() {
    init {
        icon = R.mipmap.old_sword_foreground
        name = "Old sword"
        dmg = 3
        critChance = 5
    }

    override fun getInfo() = buildString {
        append("This sword has already outlived its usefulness. Now it can only be used as a sharp club.")
    }
}