package com.kotlinconf23.pathofluckych.objects.shields

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.classes.items.Defend

object ParrySword : Defend.Shield() {
    init {
        icon = R.mipmap.parry_sword_foreground
        name = "Parry sword"
        Def = 10
        DefChance = 20
    }
}