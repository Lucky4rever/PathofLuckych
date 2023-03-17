package com.kotlinconf23.pathofluckych.objects.weapons

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.classes.items.TwoHand

object BigSword : TwoHand.Sword() {
    init {
        icon = R.mipmap.big_sword_foreground
        name = "Big sword"
        dmg = 12
        critChance = 5
    }
}