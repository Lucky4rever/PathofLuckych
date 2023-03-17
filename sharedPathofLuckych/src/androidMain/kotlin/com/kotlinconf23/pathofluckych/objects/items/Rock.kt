package com.kotlinconf23.pathofluckych.objects.items

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.classes.items.OtherItem

object Rock : OtherItem() {
    init {
        icon = R.mipmap.rock_foreground
        name = "Rock"
    }
    override fun getInfo() = buildString {
        append("A cool round stone that was found on the road.")
    }
}