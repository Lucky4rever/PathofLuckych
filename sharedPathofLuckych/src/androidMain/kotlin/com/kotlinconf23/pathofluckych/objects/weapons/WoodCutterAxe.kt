package com.kotlinconf23.pathofluckych.objects.weapons

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.classes.items.OneHand

object WoodCutterAxe : OneHand.Axe() {
    init {
        icon = R.mipmap.wood_cutters_axe_foreground
        name = "Wood cutter's axe"
        dmg = 4
        critChance = 25
    }
}