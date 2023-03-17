package com.kotlinconf23.pathofluckych.classes.items

abstract class OtherItem: Item() {
    override fun getInfo(): String = super.getInfo() + "\nMaybe, you'll use it later."
}