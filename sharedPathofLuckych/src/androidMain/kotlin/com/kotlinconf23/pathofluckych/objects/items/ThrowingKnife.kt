package com.kotlinconf23.pathofluckych.objects.items

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.classes.items.OtherItem

object ThrowingKnife : OtherItem() {
    init {
        icon = R.mipmap.throwing_knife_foreground
        name = "Throwing knife"
    }
    override fun getInfo() = buildString {
        append("A throwing knife that was obtained from the enemy.")
    }
}