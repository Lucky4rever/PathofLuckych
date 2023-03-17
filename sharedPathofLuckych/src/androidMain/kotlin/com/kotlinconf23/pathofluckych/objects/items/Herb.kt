package com.kotlinconf23.pathofluckych.objects.items

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.classes.items.HealingItem

object Herb : HealingItem() {
    init {
        icon = R.mipmap.herb
        name = "Herb"
        hp = 3
    }
    override fun getInfo() = buildString {
        append("A medicinal herb that sometimes occurs in the forest. Heals 3 HP.")
    }
}