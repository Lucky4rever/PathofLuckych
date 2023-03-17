import SwiftUI

struct StoryWindowActivity: View {
    @State var width: Length = 1
    @State var toInventory = false
    var body: some View {
        ZStack {
            GeometryReader {
                geometry in ScrollView(isScrollEnabled: true, alwaysBounceHorizontal: false, alwaysBounceVertical: true, showsHorizontalIndicator: false, showsVerticalIndicator: true) {
                VStack {
                        Text(Preferences.this.storyWindowLogic.getStory.1)
                            .lineLimit(nil)
                            .frame(
                                minWidth: geometry.size.width-10,
                                idealWidth: geometry.size.width-10,
                                maxWidth: geometry.size.width-10,
                                minHeight: geometry.size.height-10,
                                idealHeight: geometry.size.height-10,
                                maxHeight: .infinity,
                                alignment: .topLeading)
                    }
                }
            }
            HStack {
                Button(action: {
                    if (Preferences.this.storyWindowLogic.isFirstProgramStart) {
                        turn()
                        Preferences.this.storyWindowLogic.isFirstProgramStart = false
                    } else {
                        storyText.text = Preferences.storyWindowLogic.text
                    }
                }, label: {
                    Text("Next")
                }).padding().background(RoundedRectangle(cornerRadius: 8).fill(Color.green))

                Button(action: { toInventory = true }, label: {
                    Text("Inventory")
                }).padding().background(RoundedRectangle(cornerRadius: 8).fill(Color.green))
            }
        }
        
        if (toInventory) {
            InventoryWindow()
        }
        
        if (Person.this.isNowFight) {
            FightWindowActivity(FightLogic(unit1: Person.this, unit2: Preferences.this.enemy))
        }
    }

    func turn() {
        if (Preferences.this.storyWindowLogic.textPosition < Preferences.this.storyWindowLogic.fullText.size) {
            runText()
        } else {
            nextText()
        }
    }

    func nextText() {
        Preferences.this.storyWindowLogic.thisStory++
        Preferences.this.storyWindowLogic.textPosition = 0
        Preferences.this.storyWindowLogic.text = ""
        Preferences.this.storyWindowLogic.getStory = Preferences.this.storyWindowLogic.getNextStory()

        Preferences.storyWindowLogic.fullText += fullText(Preferences.this.storyWindowLogic.getStory.0)
        Person.this.reloadInventoryItemNames()
        savePreferences()
        runText()
    }

    func runText() {
        Preferences.this.storyWindowLogic.runLine()
        storyName.text = Preferences.this.storyWindowLogic.getStory.second
        storyText.text = Preferences.this.storyWindowLogic.text

        if (Preferences.this.storyWindowLogic.textPosition == Preferences.this.storyWindowLogic.fullText.size) {
            self.showToast(message: "To continue press 'Next'", font: .systemFont(ofSize: 20.0))
        }
    }

    func fullText(_ filename: String) -> String {
        return String(contentsOfFile: filename + ".txt")
    }

    func savePreferences() {
        var user = UserDefaults.standard
        user.set((Preferences.this.storyWindowLogic.thisStory-1), forKey: "Story_number")
        user.set(Preferences.this.storyWindowLogic.text, forKey: "This_story")
        user.set(Person.this.storiesNames, forKey: "Story")

        user.set(Person.this.name, forKey: "Person_Name")
        user.set(Person.this.xp, forKey: "Person_XP")
        user.set(Person.this.lvl, forKey: "Person_Lvl")
        user.set(Person.this.stats.hp, forKey: "Person_stats_HP")
        user.set(Person.this.stats.dmg, forKey: "Person_stats_Dmg")
        user.set(Person.this.stats.def, forKey: "Person_stats_Def")
        user.set(Person.this.race.hp, forKey: "Person_race_HP")
        user.set(Person.this.race.dmg, forKey: "Person_race_Dmg")
        user.set(Person.this.race.def, forKey: "Person_race_Def")
        user.set(Person.this.race.xp, forKey: "Person_race_XP")

        user.set(Person.this.inventoryItemNames, forKey: "Person_inventory_names")
        user.set(Person.this.weaponName, forKey: "Person_weapon_name")
        user.set(Person.this.shieldName, forKey: "Person_shield_name")
        user.set(Person.this.armorName, forKey: "Person_armor_name")
    }
}

extension UIViewController {
    func showToast(message : String, font: UIFont) {
        let toastLabel = UILabel(frame: CGRect(x: self.view.frame.size.width/2 - 75, y: self.view.frame.size.height-100, width: 150, height: 35))
        toastLabel.backgroundColor = UIColor.black.withAlphaComponent(0.6)
        toastLabel.textColor = UIColor.white
        toastLabel.font = font
        toastLabel.textAlignment = .center;
        toastLabel.text = message
        toastLabel.alpha = 1.0
        toastLabel.layer.cornerRadius = 10;
        toastLabel.clipsToBounds  =  true
        self.view.addSubview(toastLabel)
        UIView.animate(withDuration: 4.0, delay: 0.1, options: .curveEaseOut, animations: {
            toastLabel.alpha = 0.0
        }, completion: {(isCompleted) in
            toastLabel.removeFromSuperview()
        })
    }
}
