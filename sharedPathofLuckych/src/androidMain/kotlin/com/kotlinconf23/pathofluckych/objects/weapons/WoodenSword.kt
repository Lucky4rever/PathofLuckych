package com.kotlinconf23.pathofluckych.objects.weapons

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.classes.items.OneHand

object WoodenSword : OneHand.Sword() {
    init {
        icon = R.mipmap.wooden_sword_foreground
        name = "Wooden sword"
        dmg = 5
        critChance = 5
    }
}