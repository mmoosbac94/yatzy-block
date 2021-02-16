package com.example.yatzyblock.models

class Player(val name: String) {

    var einser: Int = 0
    var zweier: Int = 0
    var dreier: Int = 0
    var vierer: Int = 0
    var fuenfer: Int = 0
    var sechser: Int = 0
    var summeOben: Int = 0
    var hatBonus: Boolean = false

    var einPaar: Int = 0
    var zweiPaar: Int = 0
    var dreiGleiche: Int = 0
    var vierGleiche: Int = 0
    var kleineStrasse: Int = 0
    var grosseStrasse: Int = 0
    var vollesHaus: Int = 0
    var chance: Int = 0
    var yatzy: Int = 0
    var endSumme: Int = 0

}