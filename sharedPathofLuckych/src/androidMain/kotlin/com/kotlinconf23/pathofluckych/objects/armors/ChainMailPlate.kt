package com.kotlinconf23.pathofluckych.objects.armors

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.classes.items.Defend

object ChainMailPlate : Defend.Armor() {
    init {
        icon = R.mipmap.chainmail_plate_foreground
        name = "Chain-mail plate"
        Def = 15
    }
}