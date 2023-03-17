package com.kotlinconf23.pathofluckych.classes

import com.kotlinconf23.pathofluckych.R

enum class Race (var raceName: String, var hp: Int, var def: Int, var dmg: Int, var xp: Int) {
    Nothing("Nothing", -1, -1, -1, -1),
    Hero("Hero", 20, 1, 2, 100),
    Orc("Orc", 18, 1, 3, 200),
    Spider("Spider", 8, 2, 1, 500),
    Wolf("Wolf", 10, 1, 2, 300),
    Elf("Elf", 13, 5, 1, 50),
    Bear("Bear", 15, 0, 2, 300);
    var icon: Int = R.mipmap.unknown_foreground
}