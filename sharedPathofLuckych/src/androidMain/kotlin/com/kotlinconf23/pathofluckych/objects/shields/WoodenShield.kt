package com.kotlinconf23.pathofluckych.objects.shields

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.classes.items.Defend

object WoodenShield : Defend.Shield() {
    init {
        icon = R.mipmap.wooden_shield_foreground
        name = "Wooden shield"
        Def = 3
        DefChance = 50
    }
    override fun getInfo() = buildString {
        append("A wooden shield that can break after 3 hits (maybe).")
    }
}