package com.kotlinconf23.pathofluckych.objects.armors

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.classes.items.Defend

object FancyTunic : Defend.Armor() {
    init {
        icon = R.mipmap.fancy_tunic_foreground
        name = "Fancy tunic"
        Def = 4
    }
    override fun getInfo() = buildString {
        append("Cheap tunic. I don't think, that it should wear for battle.")
    }
}