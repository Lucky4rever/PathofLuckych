package com.kotlinconf23.pathofluckych.objects.armors

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.classes.items.Defend

object Body : Defend.Armor() {
    init {
        icon = R.mipmap.body_foreground
        name = "none"
        Def = 0
        isBody = false
    }
}