package com.kotlinconf23.pathofluckych.classes.items

import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.interfaces.ShowStatistics

abstract class Item : ShowStatistics {
    var name: String = "unknown"
    var hp: Int? = null
    var dmg: Int? = null

    var icon: Int? = R.mipmap.unknown_foreground
    var isLeftHand: Boolean = false
    var isRightHand: Boolean = false
    var isBody: Boolean = false

    override fun getInfo(): String = "Some strange item."

    override fun getStats(): String = "You can't use it."
}

