package com.kotlinconf23.pathofluckych.objects.weapons

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.classes.items.TwoHand

object GarudSpear : TwoHand.Spear() {
    init {
        icon = R.mipmap.garud_spear_foreground
        name = "Garud spear"
        dmg = 20
        critChance = 50
    }
    override fun getInfo() = buildString {
        append("This mystical spear came from the Garud desert in the southwest. Deals an incredible amount of damage. Weapons from the developer's arsenal.")
    }
}