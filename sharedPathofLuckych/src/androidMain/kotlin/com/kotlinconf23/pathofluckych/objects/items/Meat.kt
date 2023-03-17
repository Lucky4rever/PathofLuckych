package com.kotlinconf23.pathofluckych.objects.items

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.classes.items.HealingItem

object Meat : HealingItem() {
    init {
        icon = R.mipmap.meat_foreground
        name = "Meat"
        hp = 2
    }
    override fun getInfo() = buildString {
        append("Meat that was obtained from the enemy. Heals 2 HP. I hope you can eat it.")
    }
}