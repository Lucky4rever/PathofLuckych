open class Item {
    var name: String = "unknown"
    var hp: Int? = nil
    var dmg: Int? = nil

    var icon: String? = "tree_ico_large"
    var isLeftHand: Bool = false
    var isRightHand: Bool = false
    var isBody: Bool = false

    var def: Int = 0

    func getInfo() -> String {
        return "Some strange item"
    }

    func getStats() -> String {
        return "You can't use it."
    }
}

class HealingItem: Item {
    override func getStats() -> String {
        var statistics = ""
        if (hp != nil && hp != 0) {
            statistics += "Heal: \(self.hp!) \n"
        }
        if (dmg != nil && dmg != 0) {
            statistics += "Dmg: \(self.dmg!) \n"
        }
        return statistics
    }
}

class OtherItem: Item {
    override func getInfo() -> String {
        super.getInfo() + "\nMaybe, you'll use it later."
    }
}

open class Weapon: Item {
    var critChance: Int = 0
    var critMultiplier: Int = 0

    override func getStats() -> String {
        var statistics = ""
        if (dmg != nil && dmg != 0) {
            statistics += "Damage: \(self.dmg!)\n"
        }
        if (critChance != 0) {
            statistics += "Critical chance: \(self.critChance)%\n"
        }
        if (critMultiplier != 0) {
            statistics += "Critical multiplier: \(self.critMultiplier)x\n"
        }
        return statistics
    }
}


open class OneHand : Weapon {
    override init() {
        super.init()
        isRightHand = true
        critMultiplier = 3
    }
}


class Sword : OneHand {
    override func getInfo() -> String {
        return "This \(self.name) can hit \(self.dmg!) Dmg and deal \(self.critMultiplier)x critical hit with a \(self.critChance)% chance"
    }
}

class Axe : OneHand {
    override init() {
        super.init()
        critMultiplier = 4
    }
    override func getInfo() -> String {
        return "This \(self.name) can hit \(self.dmg!) Dmg and deal \(self.critMultiplier)x critical hit with a \(self.critChance)% chance, inflicting heavy wounds"
    }
}

open class TwoHand : Weapon {
    override init() {
        super.init()
        isLeftHand = true
        isRightHand = true
        critMultiplier = 5
    }
}
open class TwoHandSword : TwoHand {
    override func getInfo() -> String {
        return "This big \(self.name) can hit \(self.dmg!) Dmg and deal \(self.critMultiplier)x critical hit with a \(self.critChance)% chance"
    }
}
open class Spear : TwoHand {
    override init() {
        super.init()
        critMultiplier = 5
    }
    override func getInfo() -> String {
        return "This \(self.name) is needed to deal piercing damage, It can hit \(self.dmg!) Dmg and deal \(self.critMultiplier)x critical hit with a \(self.critChance)% chance"
    }
}

//open class Defend : Item {
//    var def: Int = 0
//}

class Shield: Item {
    var defChance: Int = 0
    override init() {
        super.init()
        isLeftHand = true
    }
    override func getInfo() -> String {
        return "This \(self.name) can protect against \(self.def) Dmg with a \(self.defChance)% chance"
    }
    override func getStats() -> String {
        var statistics = ""
        if (def != 0) {
            statistics += "defend: \(self.def)\n"
        }
        if (defChance != 0) {
            statistics += "defend chance: \(self.defChance)%\n"
        }
        return statistics
    }
}

class Armor: Item {
    override func getInfo() -> String {
        return "This \(self.name) can protect against \(self.def) Dmg"
    }
    override init() {
        super.init()
        isBody = true
    }

    override func getStats() -> String {
        var statistics = ""
        if (def != 0) {
            statistics += "defend: \(self.def)\n"
        }
        return statistics
    }
}

class Race {
    static let this = Race()

    var raceName: String
    var hp: Int
    var def: Int
    var dmg: Int
    var xp: Int
    var icon: String = "unknown"

    init(_ raceName: String,_ hp: Int,_ def: Int,_ dmg: Int,_ xp: Int) {
        self.raceName = raceName
        self.hp = hp
        self.def = def
        self.dmg = dmg
        self.xp = xp

        Nothing = nil
        Hero = nil
        Orc = nil
        Spider = nil
        Wolf = nil
        Elf = nil
        Bear = nil
    }

    var Nothing: Race?
    var Hero: Race?
    var Orc: Race?
    var Spider: Race?
    var Wolf: Race?
    var Elf: Race?
    var Bear: Race?

    init() {
        raceName = ""
        hp = 0
        def = 0
        dmg = 0
        xp = 0

        Nothing = Race("Nothing", -1, -1, -1, -1)
        Hero = Race("Hero", 20, 1, 2, 100)
        Orc = Race("Orc", 18, 1, 3, 200)
        Spider = Race("Spider", 8, 2, 1, 500)
        Wolf = Race("Wolf", 10, 1, 2, 300)
        Elf = Race("Elf", 13, 5, 1, 50)
        Bear = Race("Bear", 15, 0, 2, 300)

        allRaces = [Nothing ?? nil, Hero ?? nil, Orc ?? nil, Spider ?? nil, Wolf ?? nil, Elf ?? nil, Bear ?? nil]
    }
    private var allRaces: Array<Race?> = []

    func getRace(_ raceName: String) -> Race {
        for i in allRaces {
            if (i != nil) {
                if (i!.raceName == raceName) {
                    return i!
                }
            }
        }
        return Nothing!
    }
}

struct Stats {
    var hp, def, dmg: Int
    init() {
      hp = 0
      def = 0
      dmg = 0
    }

    init(_ hp: Int, _ def: Int, _ dmg: Int) {
        self.hp = hp
        self.def = def
        self.dmg = dmg
    }
}

class Inventory {
    var weapon: Weapon = RightHand.this
    var weaponName: String
    var shield: Shield = LeftHand.this
    var shieldName: String
    var armor: Armor = Body.this
    var armorName: String
    init() {
        weaponName = weapon.name
        shieldName = shield.name
        armorName = armor.name
    }

    var isLeftHand: Bool = false
    var isRightHand: Bool = false
    var isBody: Bool = false
    var Items: [((key: String, value: Item), count: Int?)] = []

    func addElements(_ item: (key: String, value: Item)...){
        var thisItems: [(key: String, value: Item)] = []
        for (i_key, i_value) in item {
            thisItems.append((i_key, i_value))
        }
        for (i_keys, i_value) in thisItems {
            var index = 0
            for ((j_keys, _), j_count) in Items {
                if(j_keys == i_keys) {
                    let count = j_count ?? 0
                    Items.remove(at: index)
                    Items += [((i_keys, i_value), count+1)]
                }
                index += 1
            }
        }
    }

    func dropElements(_ item: (key: String, value: Item)...){
        var thisItems: [(key: String, value: Item)] = []
        for (i_key, i_value) in item {
            thisItems.append((i_key, i_value))
        }
        for (i_keys, i_value) in thisItems {
            var index = 0
            for ((j_keys, _), j_count) in Items {
                if(j_keys == i_keys) {
                    let count = j_count ?? 1
                    Items.remove(at: index)
                    if (count > 1) {
                        Items += [((i_keys, i_value), count-1)]
                    }
                }
                index += 1
            }
        }
    }
}


class RightHand : Sword {
    static let this = RightHand()

    private override init() {
        super.init()
        icon = "right_hand"
        name = "none"
        dmg = 0
        critChance = 0
        critMultiplier = 0
        isRightHand = false
    }
}

class ScoutSword : Sword {
    static let this = ScoutSword()

    private override init() {
        super.init()
        icon = "scout_sword"
        name = "Scout sword"
        dmg = 8
        critChance = 15
    }
}

class WoodenSword : Sword {
    static let this = WoodenSword()

    private override init() {
        super.init()
        icon = "wooden_sword"
        name = "Wooden sword"
        dmg = 5
        critChance = 5
    }
}

class WoodCutterAxe : Axe {
    static let this = WoodCutterAxe()

    private override init() {
        super.init()
        icon = "wood_cutters_axe"
        name = "Wood cutter's axe"
        dmg = 4
        critChance = 25
    }
}

class OldSword : TwoHandSword {
    static let this = OldSword()

    private override init() {
        super.init()
        icon = "old_sword"
        name = "Old sword"
        dmg = 3
        critChance = 5
    }

    override func getInfo() -> String {
        return "This sword has already outlived its usefulness. Now it can only be used as a sharp club."
    }
}

class BigSword : TwoHandSword {
    static let this = BigSword()

    private override init() {
        super.init()
        icon = "big_sword"
        name = "Big sword"
        dmg = 12
        critChance = 5
    }
}

class GarudSpear : Spear {
    static let this = GarudSpear()

    private override init() {
        super.init()
        icon = "garud_spear"
        name = "Garud spear"
        dmg = 20
        critChance = 50
    }

    override func getInfo() -> String {
        return "This mystical spear came from the Garud desert in the southwest. Deals an incredible amount of damage. Weapons from the developer's arsenal."
    }
}



class LeftHand: Shield {
    static let this = LeftHand()

    private override init() {
        super.init()
        icon = "left_hand"
        name = "none"
        def = 0
        defChance = 0
        isLeftHand = false
    }
}

class ParrySword: Shield {
    static let this = ParrySword()

    private override init() {
        super.init()
        icon = "parry_sword"
        name = "Parry sword"
        def = 10
        defChance = 20
    }
}

class WoodenShield: Shield {
    static let this = WoodenShield()

    private override init() {
        super.init()
        icon = "wooden_shield"
        name = "Wooden shield"
        def = 3
        defChance = 50
    }

    override func getInfo() -> String {
        return "A wooden shield that can break after 3 hits (maybe)."
    }
}



class Body: Armor {
    static let this = Body()

    private override init() {
        super.init()
        icon = "body"
        name = "none"
        def = 0
        isBody = false
    }
}

class ChainMailPlate: Armor {
    static let this = ChainMailPlate()

    private override init() {
        super.init()
        icon = "chainmail_plate"
        name = "Chain-mail plate"
        def = 15
    }
}

class FancyTunic: Armor {
    static let this = FancyTunic()

    private override init() {
        super.init()
        icon = "fancy_tunic"
        name = "Fancy tunic"
        def = 4
    }

    override func getInfo() -> String {
        return "Cheap tunic. I don't think, that it should wear for battle."
    }
}



class Meat: HealingItem {
    static let this = Meat()

    private override init() {
        super.init()
        icon = "meat"
        name = "Meat"
        hp = 2
    }

    override func getInfo() -> String {
        return "Meat that was obtained from the enemy. Heals 2 HP. I hope you can eat it."
    }
}

class Herb: HealingItem {
    static let this = Herb()

    private override init() {
        super.init()
        icon = "herb"
        name = "Herb"
        hp = 3
    }

    override func getInfo() -> String {
        return "A medicinal herb that sometimes occurs in the forest. Heals 3 HP."
    }
}

class Rock: OtherItem {
    static let this = Rock()

    private override init() {
        super.init()
        icon = "rock"
        name = "Rock"
    }

    override func getInfo() -> String {
        return "A cool round rock that was found on the road."
    }
}

class ThrowingKnife: OtherItem {
    static let this = ThrowingKnife()

    private override init() {
        super.init()
        icon = "throwing_knife"
        name = "Throwing knife"
    }

    override func getInfo() -> String {
        return "A throwing knife that was obtained from the enemy."
    }
}




class GameUnit {
    var name: String
    var race: Race = Race()
    var stats: Stats = Stats()
    var xp: Int
    var lvl: Int
    var inventory: Inventory
    var log: String

    init() {
        name = "Unit"
        xp = 0
        lvl = 1
        inventory = Inventory()
        log = ""
    }

    func getStats() -> String {
        var statistics = "Name: \(self.name)\nLvl: \(self.lvl)\nHP: \(self.stats.hp)/\(self.race.hp)\nXP: \(self.xp)/\(self.race.xp)\n"
        if (race.dmg == stats.dmg) {
            statistics += "Damage: \(self.race.dmg)\n"
        } else {
            statistics += "Damage: \(self.race.dmg)-\(self.stats.dmg)\n"
        }
        if (race.def == stats.def) {
            statistics += "defence: \(self.race.def)\n"
        } else {
            statistics += "defence: \(self.race.def)-\(self.stats.def)\n"
        }
        statistics += "Equipped: "
        if (!inventory.isBody && !inventory.isRightHand && !inventory.isLeftHand) {
            statistics += "none."
        } else {
            if (inventory.isRightHand) {
                statistics += "\n\(inventory.weapon.name)"
            }
            if (inventory.isLeftHand && inventory.shield.name != "none") {
                statistics += "\n\(inventory.shield.name)"
            }
            if (inventory.isBody) {
                statistics += "\n\(inventory.armor.name)"
            }
        }
        return statistics
    }


    func equip(_ item: Weapon) {
        switch (item) {
            case is OneHand:
                for (key, _) in ItemSetup.this.allOneHandWeapons {
                    if(key == item.name) {
                        inventory.isRightHand = true
                        inventory.weapon = item
                        stats.dmg = race.dmg + item.dmg!
                        Person.this.inventory.weaponName = item.name
                    }
                }
            case is TwoHand:
                for (key, _) in ItemSetup.this.allTwoHandWeapons {
                    if(key == item.name) {
                        inventory.isLeftHand = true
                        inventory.isRightHand = true
                        inventory.weapon = item
                        stats.dmg = race.dmg + item.dmg!
                        inventory.shield = LeftHand.this
                        stats.def = race.def + inventory.armor.def
                        Person.this.inventory.weaponName = item.name
                    }
                }
            default:
                break
        }
    }

    func equip(_ item: Item) {
        switch (item) {
            case is OneHand:
                for (key, _) in ItemSetup.this.allOneHandWeapons {
                    if(key == item.name) {
                        inventory.isRightHand = true
                        inventory.weapon = item as! Weapon
                        stats.dmg = race.dmg + item.dmg!
                        Person.this.inventory.weaponName = item.name
                    }
                }
            case is TwoHand:
                for (key, _) in ItemSetup.this.allTwoHandWeapons {
                    if(key == item.name) {
                        inventory.isLeftHand = true
                        inventory.isRightHand = true
                        inventory.weapon = item as! Weapon
                        stats.dmg = race.dmg + item.dmg!
                        inventory.shield = LeftHand.this
                        stats.def = race.def + inventory.armor.def
                        Person.this.inventory.weaponName = item.name
                    }
                }
        case is Shield:
            for (key, _) in ItemSetup.this.allShields {
                if(key == item.name) {
                    inventory.isLeftHand = true
                    inventory.shield = item as! Shield
                    stats.def = race.def + item.def + inventory.armor.def
                    if (inventory.weapon is TwoHand) {
                        inventory.isRightHand = false
                        inventory.weapon = RightHand.this
                        stats.dmg = race.dmg + inventory.weapon.dmg!
                    }
                    Person.this.inventory.shieldName = item.name
                }
            }
        case is Armor:
            for (key, _) in ItemSetup.this.allShields {
                if(key == item.name) {
                    inventory.isLeftHand = true
                    inventory.shield = item as! Shield
                    stats.def = race.def + item.def + inventory.armor.def
                    if (inventory.weapon is TwoHand) {
                        inventory.isRightHand = false
                        inventory.weapon = RightHand.this
                        stats.dmg = race.dmg + inventory.weapon.dmg!
                    }
                    Person.this.inventory.shieldName = item.name
                }
            }
            default:
                break
        }
    }

    func equip(_ item: OneHand) {
        for (key, _) in ItemSetup.this.allTwoHandWeapons {
            if(key == item.name) {
                inventory.isRightHand = true
                inventory.weapon = item
                stats.dmg = race.dmg + item.dmg!
                Person.this.inventory.weaponName = item.name
            }
        }
    }
    func equip(item: TwoHand) {
        for (key, _) in ItemSetup.this.allTwoHandWeapons {
            if(key == item.name) {
                inventory.isLeftHand = true
                inventory.isRightHand = true
                inventory.weapon = item
                stats.dmg = race.dmg + item.dmg!
                inventory.shield = LeftHand.this
                stats.def = race.def + inventory.armor.def
                Person.this.inventory.weaponName = item.name
            }
        }
    }
    func equip(_ item: Shield) {
        for (key, _) in ItemSetup.this.allShields {
            if(key == item.name) {
                inventory.isLeftHand = true
                inventory.shield = item
                stats.def = race.def + item.def + inventory.armor.def
                if (inventory.weapon is TwoHand) {
                    inventory.isRightHand = false
                    inventory.weapon = RightHand.this
                    stats.dmg = race.dmg + inventory.weapon.dmg!
                }
                Person.this.inventory.shieldName = item.name
            }
        }
    }
    func equip(_ item: Armor) {
        for (key, _) in ItemSetup.this.allArmors {
            if(key == item.name) {
                inventory.isBody = true
                inventory.armor = item
                stats.def = race.def + item.def + inventory.shield.def
                Person.this.inventory.armorName = item.name
            }
        }
    }
    func unequip(_ item: Weapon) {
        switch item {
            case is OneHand:
                for (key, _) in ItemSetup.this.allOneHandWeapons {
                    if(key == item.name) {
                        inventory.isRightHand = false
                        inventory.weapon = RightHand.this
                        stats.dmg = race.dmg
                        inventory.isRightHand = false
                        Person.this.inventory.weaponName = "none"
                    }
                }
            case is TwoHand:
                for (key, _) in ItemSetup.this.allTwoHandWeapons {
                    if(key == item.name) {
                        inventory.isLeftHand = false
                        inventory.isRightHand = false
                        inventory.shield = LeftHand.this
                        inventory.weapon = RightHand.this
                        stats.dmg = race.dmg
                        inventory.isLeftHand = false
                        inventory.isRightHand = false
                        Person.this.inventory.weaponName = "none"
                    }
                }
            default:
                break
        }
    }
    func unequip(_ item: OneHand) {
        for (key, _) in ItemSetup.this.allOneHandWeapons {
            if(key == item.name) {
                inventory.isRightHand = false
                inventory.weapon = RightHand.this
                stats.dmg = race.dmg
                inventory.isRightHand = false
                Person.this.inventory.weaponName = "none"
            }
        }
    }
    func unequip(_ item: TwoHand) {
        for (key, _) in ItemSetup.this.allTwoHandWeapons {
            if(key == item.name) {
                inventory.isLeftHand = false
                inventory.isRightHand = false
                inventory.shield = LeftHand.this
                inventory.weapon = RightHand.this
                stats.dmg = race.dmg
                inventory.isLeftHand = false
                inventory.isRightHand = false
                Person.this.inventory.weaponName = "none"
            }
        }
    }
    func unequip(_ item: Shield) {
        for (key, _) in ItemSetup.this.allShields {
            if(key == item.name) {
                inventory.isLeftHand = false
                inventory.shield = LeftHand.this
                stats.def = race.def + inventory.armor.def
                inventory.isLeftHand = false
                Person.this.inventory.shieldName = "none"
            }
        }
    }
    func unequip(_ item: Armor) {
        for (key, _) in ItemSetup.this.allArmors {
            if(key == item.name) {
                inventory.isBody = false
                inventory.armor = Body.this
                stats.def = race.def + inventory.shield.def
                inventory.isBody = false
                Person.this.inventory.armorName = "none"
            }
        }
    }




    func obtain(_ item: Item...) {
        for i in item {
            self.inventory.addElements((i.name, i))
        }
    }
    func drop(_ item: Item...) {
        for i in item {
            self.inventory.dropElements((i.name, i))
        }
    }


    func lvlUp() {
        let statsUp = Int.random(in: 1..<4)
        xp += Int.random(in: 1..<100)
        if (xp >= race.xp) {
            switch (Int.random(in: 1..<4)) {
                case 1:
                    self.race.hp += statsUp
                    self.stats.hp += statsUp
                case 2:
                    self.race.dmg += statsUp
                    self.stats.dmg += statsUp
                case 3:
                    self.race.def += statsUp
                    self.stats.def += statsUp
                default:
                    break
            }
            self.xp -= self.race.xp
            self.race.xp += 100 + Int.random(in: 0..<100)
            self.lvl+=1
        }
    }
    func attack(_ enemy: GameUnit) {
        var crit: Int
        var dmg: Int
        var def: Int
        if(Int.random(in: 0..<100) < self.inventory.weapon.critChance) {
            crit = inventory.weapon.critMultiplier
        } else {
            crit = 1
        }
        if(self.race.dmg == self.stats.dmg) {
            dmg = self.race.dmg
        } else {
            dmg = Int.random(in: self.race.dmg...(self.stats.dmg + 1))
        }
        dmg *= crit

        if(enemy.race.def == enemy.stats.def) {
            def = enemy.race.def
        } else {
            def = Int.random(in: self.race.def...(self.stats.def + 1))
        }

        if (dmg > enemy.stats.def) {
            Person.this.log += "\(name) deal \(dmg - def) dmg to \(enemy.name)\n"
            enemy.stats.hp -= (dmg - def)
        } else {
            Person.this.log += "\(enemy.name) block all damage from \(self.name)\n"
        }

        if(enemy.stats.hp <= 0) {
            enemy.dead()
            Person.this.isNowFight = false
        }
    }
    func dead() {}

    func loot(_ enemy: GameUnit) {
        if (enemy.stats.hp <= 0) {
            let dropChance: Int = Int.random(in: 0..<100)
            if (dropChance < 10) {
                switch(Bool.random()) {
                    case true:
                        if (enemy.inventory.weapon.name != (RightHand.this.name)) {
                            Person.this.obtain(enemy.inventory.weapon)
                            self.log += "Drop: " + "\(Meat.this.name)\n"
                        }
                    case false:
                        if (enemy.inventory.shield.name != LeftHand.this.name) {
                            Person.this.obtain(enemy.inventory.shield)
                            self.log += "Drop: " + "\(Meat.this.name)\n"
                        }
                }
            } else if (dropChance < 50) {
                let num = Int.random(in: 1..<4)
                for _ in [1...num] {
                    Person.this.obtain(Meat.this)
                }
                self.log += "\(num) \(Meat.this.name)\n"
            } else {
                self.log += "nothing\n"
            }
        }
    }
}

class Person : GameUnit {
    static let this = Person()

    var inventoryItemNames: Set<String> = []
    var isNowFight: Bool = false
    var isFightAlreadyOver: Bool = false
    override init() {
        super.init()
        name = "Hero"
        race = Race.this.Hero!
        stats = Stats(race.hp, race.def, race.dmg)
        log = ""
        reloadInventoryItemNames()
        race.icon = "hero"
    }

    func reloadInventoryItemNames() {
        inventoryItemNames = []
        for ((key, _), count) in self.inventory.Items {
            inventoryItemNames.insert("\(key)=\(count as Optional)")
        }
    }

    func reloadInventoryByNames() {
        for inventoryItem in inventoryItemNames {
            var thisItemName = ""
            var thisItemCount = ""
            var isNameAlreadyDone = false
            for symbol in inventoryItem {
                if (symbol == "="){
                    isNameAlreadyDone = true}
                else if (!isNameAlreadyDone) {
                    thisItemName += String(symbol)
                } else{
                    thisItemCount += String(symbol)
                }
            }
            if (thisItemName != "none") {
                for ((key, _), _) in Person.this.inventory.Items {
                    if (key == thisItemName) {
                        for _ in [0...Int(thisItemCount)!] {
                            Person.this.inventory.addElements((thisItemName, ItemSetup.this.getItemByName(thisItemName)!))
                        }
                    }
                }
            }
        }
    }

    func getInfo() -> String {
        return "Hero, that came to new world"
    }
    override func dead() {
        log += "You died\n"
        Preferences.this.storyWindowLogic = StoryWindowLogic()
        Preferences.this.inventoryWindowLogic = InventoryWindowLogic()
        Preferences.this.storiesNames = StorySetup.this.storiesNames
    }
    func respawn() {
        stats = Stats(race.hp, race.def, race.dmg)
        unequip(self.inventory.weapon)
        unequip(self.inventory.shield)
        unequip(self.inventory.armor)
        inventory.Items = []
        reloadInventoryItemNames()
    }

    override func drop(_ item: Item...) {
        for i in item {
            self.inventory.dropElements((i.name, i))
        }
        reloadInventoryItemNames()
    }

    override func obtain(_ item: Item...) {
        for i in item {
            self.inventory.addElements((i.name, i))
        }
        reloadInventoryItemNames()
    }
    func use(_ thisItemName: String) {
        let thisItem = ItemSetup.this.getItemByName(thisItemName)
        if (thisItem != nil) {
            switch (thisItem) {
                case is HealingItem:
                    heal(thisItem!.hp!)
                    drop(thisItem!)
                default:
                    break
            }
        }
    }
    func use(thisItem: Item?) {
        if (thisItem != nil) {
            switch (thisItem) {
                case is HealingItem:
                    heal(thisItem!.hp!)
                    drop(thisItem!)
                default:
                    break
            }
        }
    }
    func heal(_ HP: Int) {
        if(HP < (race.hp-stats.hp)) {
            stats.hp += HP
        } else {
            stats.hp += race.hp-stats.hp
        }
        if(stats.hp <= 0) {
            dead()
            respawn()
        }
    }
}

class Spawn : GameUnit {
    static let this = Spawn()

    override init() {
        super.init()
    }
    init(_ Name: String, _ race: Race, _ weapon: Weapon?, _ shield: Shield?) {
        super.init()
        self.name = Name
        self.race = race
        self.stats = Stats(race.hp, race.def, race.dmg)
        if(weapon != nil) {
            inventory.weapon = weapon!
            stats.dmg = race.dmg + weapon!.dmg!
        }
        if(shield != nil) {
            inventory.shield = shield!
            stats.def = race.def + shield!.def
        }
        self.race.icon = getIcon(self.race.raceName)
    }
    init (_ Name: String, _ race: Race) {
        super.init()
        self.name = Name
        self.race = race
        self.stats = Stats(race.hp, race.def, race.dmg)
        self.race.icon = getIcon(self.race.raceName)
    }
    func enemy(_ Name: String, _ race: Race, _ weapon: Weapon?, _ shield: Shield?) -> Spawn {
        return Spawn(Name, race, weapon, shield)
    }
    func mob(_ Name: String, _ race: Race) -> Spawn {
        return Spawn(Name, race)
    }

    private var mobIcons = [
        ("orc1", "Orc"),
        ("orc2", "Orc"),
        ("orc3", "Orc"),
        ("orc4", "Orc"),
        ("elf1", "Elf"),
        ("elf2", "Elf"),
        ("elf3", "Elf"),
        ("wolf1", "Wolf"),
        ("wolf2", "Wolf"),
        ("wolf3", "Wolf"),
        ("bear1", "Bear"),
        ("bear2", "Bear")
    ]
    func getInfo() -> String {
        return "Mob"
    }
    private func getIcon(_ name: String) -> String {
        var idList: Array<String> = []
        for (key, value) in mobIcons {
            if (value == name) {
                idList += [key]
            }
        }
        let randomId = Int.random(in: 0..<idList.count)
        return idList[randomId]
    }
    override func dead() {
        Person.this.log += "\(self.name) dead\n"
        Person.this.loot(self)
        Person.this.lvlUp()
    }
    func respawn() {
        Preferences.this.enemy = Spawn()
    }
}

class StoryText {
    var story: [((key: String, chance: Int), item: Item?)] = []
    let name: String

    init(_ name: String) {
        self.name = name
    }
    func isPersonGotQuestItem(_ questItem: Item?) -> Bool {
        if (questItem != nil) {
            for ((key, _), _) in story {
                if (questItem!.name == key) {
                    return true
                }
            }
            return false
        }
        return true
    }

    func getRandomText() -> String {
        let thisChance = Int.random(in: 0..<100)
        var storyChance = 0
        for ((key, chance), item) in story {
            storyChance += chance
            if (storyChance >= thisChance) {
                if (isPersonGotQuestItem(item)) {
                    return key
                } else {
                    return getRandomText()
                }
            }
        }
        return "end"
    }
}

class ItemSetup {
        static let this = ItemSetup()

        var allOneHandWeapons: [(String, OneHand)] = [
            ("Wooden sword", WoodenSword.this),
            ("Scout sword", ScoutSword.this),
            ("Wood cutter's axe", WoodCutterAxe.this)
        ]
        var allTwoHandWeapons: [(String, TwoHand)] = [
            ("Garud spear", GarudSpear.this),
            ("Old sword", OldSword.this),
            ("Big sword", BigSword.this)
        ]
        var allShields: [(String, Shield)] = [
            ("Wooden shield", WoodenShield.this),
            ("Parry sword", ParrySword.this)
        ]
        var allArmors: [(String, Armor)] = [
            ("Fancy tunic", FancyTunic.this),
            ("Chain-mail plate", ChainMailPlate.this)
        ]
        var allItems: [(String, Item)] = [
            ("Herb", Herb.this),
            ("Meat", Meat.this),
            ("Rock", Rock.this),
            ("Throwing knife", ThrowingKnife.this)
        ]

        init() {
            allItems += allOneHandWeapons
            allItems += allTwoHandWeapons
            allItems += allShields
            allItems += allArmors
        }

        func getItemByName(_ key: String) -> Item? {
            for (name, value) in allItems {
                if (name == key) {
                    return value
                }
            }
            return nil
        }
    }
    class StorySetup {
        static let this = StorySetup()

        private let prolog = StoryText("?")
        private let cave1 = StoryText("Cave")
        private let cave2 = StoryText("Escape from cave")
        private let firstPathChoice = StoryText("First choice a path")
        private let forest = StoryText("Forest")
        private let end = StoryText("End")

        private var storyLibrary: Array<StoryText>
        var story: [StoryText] = []
        let storiesNames = "?+Cave+Escape from cave+First choice a path"

        init() {
            prolog.story = [(("prolog", 100), nil)]
            cave1.story = [
                (("cave1_1", 40), nil),
                (("cave1_2", 50), nil),
                (("cave1_3", 10), nil)
            ]
            cave2.story = [
                (("cave2_1", 30), nil),
                (("cave2_2", 60), nil),
                (("cave2_3", 10), nil)
            ]
            firstPathChoice.story = [
//                (("first_path_choice_1", 60), nil),
//                (("first_path_choice_2", 40), nil)
                (("first_path_choice_1", 100), nil)
            ]
            forest.story = [
                (("forest1", 5), nil),
                (("forest2", 10), Herb.this),
                (("forest3", 15), nil),
                (("forest4", 10), nil),
                (("forest5", 20), nil),
                (("forest6", 15), nil),
                (("forest7", 10), nil),
                (("forest8", 10), nil),
                (("forest9", 5), nil)
            ]
//            mountains.story = [(("prolog", 100), nil)]
            end.story = [(("end", 100), nil)]

            storyLibrary = [prolog, cave1, cave2, firstPathChoice, forest, end]


            for i in story {
                Preferences.this.storiesNames += i.name
            }
        }

        func findStoryByName(_ name: String) -> StoryText {
            for i in storyLibrary {
                if (i.name == name) {
                    return i
                }
            }
            return end
        }

        func fillStory() {
            story = []
            for i in Preferences.this.storiesNames.toStoryList() {
                story.append(findStoryByName(i))
            }
        }
    }
extension String {
    func toStoryList() -> Array<String> {
        var storyList: Array<String> = []
        var storyName = ""
        for i in self {
            if (i == "+") {
                storyList.append(storyName)
                storyName = ""
            } else {
                storyName += String(i)
            }
        }
        if (storyName != "") {
            storyList.append(storyName)
        }
        return storyList
    }

    func substring(_ position: Int) -> String {
        return String(self.suffix(self.count - position))
    }
}

class Preferences {
    static let this = Preferences()

    let sharedPrefStoryFile = "story_statistic"
    let sharedPrefPersonFile = "person_statistic"

    var storiesNames = StorySetup.this.storiesNames
    var storyWindowLogic = StoryWindowLogic()

    var inventoryWindowLogic = InventoryWindowLogic()
    var enemy = Spawn()
}

class StoryWindowLogic {
    var fullText: Array<String>
    var textPosition: Int
    var thisStory: Int
    var text: String
    var isFirstProgramStart: Bool
    var getStory: (String, String) {
        return getNextStory()
    }

    init() {
        fullText = []
        textPosition = 0
        thisStory = -1
        text = ""
        isFirstProgramStart = true
    }

    func runLine() {
        if (!fullText.isEmpty) {
            while fullText[textPosition] == "" {
                textPosition += 1
            }

            switch (fullText[textPosition].prefix(1)) {
                case "@":
                    if (fullText[textPosition].prefix(2).suffix(1) == "+") {
                        Person.this.obtain(ItemSetup.this.getItemByName(fullText[textPosition].substring(2))!)
                        text += "Obtained ${fullText[textPosition].substring(2)}\n"
                    } else if (fullText[textPosition].prefix(2).suffix(1) == "-") {
                        Person.this.drop(ItemSetup.this.getItemByName(fullText[textPosition].substring(2))!)
                        text += "Dropped ${fullText[textPosition].substring(2)}\n"
                    }

                case "%":
                    let nextTextName = StorySetup.this.findStoryByName(fullText[textPosition].substring(1))
                    StorySetup.this.story.append(nextTextName)
                    Preferences.this.storiesNames += "+${nextTextName.name}"

                case "#":
                    switch(fullText[textPosition].substring(1)) {
                        case "mob":
                            Preferences.this.enemy = Spawn.this.mob(
                                fullText[textPosition + 1].substring(1),
                                Race.this.getRace(fullText[textPosition + 2].substring(1))
                            )
                            textPosition += 2

                        case "enemy":
                            Preferences.this.enemy = Spawn.this.enemy(
                                fullText[textPosition + 1].substring(1),
                                Race.this.getRace(fullText[textPosition + 2].substring(1)),
                                ItemSetup.this.getItemByName(fullText[textPosition + 3]
                                    .substring(1)) as! Weapon?,
                                ItemSetup.this.getItemByName(fullText[textPosition + 4]
                                    .substring(1)) as! Shield?
                            )
                            textPosition += 4

                        default:
                            break
                    }
                    Person.this.isNowFight = true

                case "$":
                    let changeInfo = fullText[textPosition].substring(1)
                    if(changeInfo.prefix(2) == "hp") {
                        if (changeInfo.prefix(2).suffix(1) == "+") {
                            Person.this.heal(Int(changeInfo.substring(3))!)
                            text += "Life increased by ${changeInfo.substring(3)} points\n"
                        } else {
                            Person.this.heal(-Int(changeInfo.substring(3))!)
                            text += "Life reduced by ${changeInfo.substring(3)} points\n"
                        }
                    }
                default:
                    text += "${fullText[textPosition]}\n"
            }
            textPosition += 1
        }
    }

    func getNextStory() -> (String, String) {
        if (StorySetup.this.story.count > thisStory) {
            return (StorySetup.this.story[thisStory].getRandomText(), StorySetup.this.story[thisStory].name)
        }
        return ("end", "The end!")
    }
}

 class InventoryWindowLogic {
    var weaponIcon: ImageButton
    var shieldIcon: ImageButton
    var armorIcon: ImageButton
    var outputText: TextView

    init() {
        weaponIcon = ImageButton()
        shieldIcon = ImageButton()
        armorIcon = ImageButton()
        outputText = TextView()
    }

    func setOnEquip(_ itemName: String) {
        let getItem = ItemSetup.this.getItemByName(itemName)
        if (getItem != nil) {
            switch(getItem) {
                case is OneHand:
                    Person.this.equip(ItemSetup.this.getItemByName(itemName) as! OneHand)
                    weaponIcon.setImageResource(Person.this.inventory.weapon.icon!)
                    shieldIcon.setImageResource(Person.this.inventory.shield.icon!)
                    shieldIcon.alpha = 1.0

                case is TwoHand:
                    Person.this.equip(ItemSetup.this.getItemByName(itemName) as! TwoHand)
                    weaponIcon.setImageResource(Person.this.inventory.weapon.icon!)
                    shieldIcon.alpha = 0.5
                    shieldIcon.setImageResource(Person.this.inventory.weapon.icon!)

                case is Shield:
                    Person.this.equip(ItemSetup.this.getItemByName(itemName) as! Shield)
                    shieldIcon.setImageResource(Person.this.inventory.shield.icon!)
                    shieldIcon.alpha = 1.0
                    weaponIcon.setImageResource(Person.this.inventory.weapon.icon!)

                case is Armor:
                    Person.this.equip(ItemSetup.this.getItemByName(itemName) as! Armor)
                    armorIcon.setImageResource(Person.this.inventory.armor.icon!)

                default:
                    Person.this.use(itemName)
            }
        }
        outputText.text = Person.this.getStats()
    }
    func setOnUnequip(_ itemName: String) {
        let getItem = ItemSetup.this.getItemByName(itemName)
        if (getItem != nil) {
            switch(getItem) {
                case is OneHand:
                    Person.this.unequip(ItemSetup.this.getItemByName(itemName) as! OneHand)
                    weaponIcon.setImageResource(Person.this.inventory.weapon.icon!)

                case is TwoHand:
                    Person.this.unequip(ItemSetup.this.getItemByName(itemName) as! TwoHand)
                    shieldIcon.alpha = 1.0
                    weaponIcon.setImageResource(Person.this.inventory.weapon.icon!)
                    shieldIcon.setImageResource(Person.this.inventory.shield.icon!)

                case is Shield:
                    Person.this.unequip(ItemSetup.this.getItemByName(itemName) as! Shield)
                    shieldIcon.setImageResource(Person.this.inventory.shield.icon!)

                case is Armor:
                    Person.this.unequip(ItemSetup.this.getItemByName(itemName) as! Armor)
                    armorIcon.setImageResource(Person.this.inventory.armor.icon!)

                default:
                    break
            }
        }
        outputText.text = Person.this.getStats()
    }

    func setOnUnequip(_ item: Weapon) {
        if (Person.this.inventory.weapon.name != RightHand.this.name) {
            switch (item) {
                case is OneHand:
                    Person.this.unequip(ItemSetup.this.getItemByName(item.name) as! OneHand)
                    weaponIcon.setImageResource(Person.this.inventory.weapon.icon!)
                case is TwoHand:
                    Person.this.unequip(ItemSetup.this.getItemByName(item.name) as! TwoHand)
                    shieldIcon.alpha = 1.0
                    weaponIcon.setImageResource(Person.this.inventory.weapon.icon!)
                    shieldIcon.setImageResource(Person.this.inventory.shield.icon!)
                default:
                    break
            }
        }
        outputText.text = Person.this.getStats()
    }

    func setOnUnequip(_ item: Shield){
        if (Person.this.inventory.shield.name != LeftHand.this.name) {
            if (Person.this.inventory.weapon is TwoHand) {
                Person.this.unequip(ItemSetup.this.getItemByName(item.name) as! Shield)
                shieldIcon.setImageResource(Person.this.inventory.shield.icon!)
            }
        }
        outputText.text = Person.this.getStats()
    }

    func setOnUnequip(_ item: Armor) {
        if (Person.this.inventory.armor.name != Body.this.name) {
            Person.this.unequip(ItemSetup.this.getItemByName(item.name) as! Armor)
            armorIcon.setImageResource(Person.this.inventory.armor.icon!)
        }
        outputText.text = Person.this.getStats()
    }
}

struct ImageButton {
    var alpha: Float
    init() {alpha=1.0}
    func setImageResource(_ x: String) {}
}

struct TextView {
    var text: String
    init() {text=""}
    func setImageResource(_ x: String) {}
}
