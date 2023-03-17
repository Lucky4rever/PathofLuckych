import SwiftUI

struct MainActivity: View {

    @State var presentingAlert = false
    @State var startGame = false

    var body: some View {
        VStack {
            Button(action: {
                openPreferences()
                quickSetup()
            }, label: {
                Text("Continue path")
            }).font(Font.custom("akaya_telivigala", size: 18)).padding().background(RoundedRectangle(cornerRadius: 8).fill(Color.green))

            Button(action: {
                presentingAlert = true
            }, label: {
                Text("Restart")
            }).alert(isPresented: $presentingAlert, content: {
                Alert(title: Text("Are you sure you want to restart your path?"), primaryButton: .destructive(Text("Yes"), action: {
                    Preferences.this.storyWindowLogic = StoryWindowLogic()
                    Preferences.this.inventoryWindowLogic = InventoryWindowLogic()
                quickSetup()
                }), secondaryButton: .cancel(Text("No")))
            })
            .padding()
            .background(RoundedRectangle(cornerRadius: 8)
                .fill(Color.green))

        }.background(
            Image("inner_theme")
           .resizable()
           .edgesIgnoringSafeArea(.all)
           .frame(width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height)
        )
        
        if (startGame) {
            StoryWindowActivity()
        }
    }

    private func openPreferences() {
        var user = UserDefaults.standard

        Person.this.name = user.string(forKey: "Person_Name") ?? "Hero"
        Person.this.xp = user.integer(forKey: "Person_XP")
        Person.this.lvl = user.integer(forKey: "Person_Lvl") ?? 1
        Person.this.stats.hp = user.integer(forKey: "Person_stats_HP") ?? Person.this.race.hp
        Person.this.stats.dmg = user.integer(forKey: "Person_stats_Dmg") ?? Person.this.race.dmg
        Person.this.stats.def = user.integer(forKey: "Person_stats_Def") ?? Person.this.race.def
        Person.this.race.hp = user.integer(forKey: "Person_race_HP") ?? Person.this.race.hp
        Person.this.race.dmg = user.integer(forKey: "Person_race_Dmg") ?? Person.this.race.dmg
        Person.this.race.def = user.integer(forKey: "Person_race_Def") ?? Person.this.race.def
        Person.this.race.xp = user.integer(forKey: "Person_race_XP") ?? Person.this.race.xp

        Person.this.inventory.weaponName = user.object(forKey: "Person_weapon_name") as? String ?? Person.this.inventory.weaponName
        Person.this.inventory.shieldName = user.object(forKey: "Person_shield_name") as? String ?? Person.this.inventory.shieldName
        Person.this.inventory.armorName = user.object(forKey: "Person_armor_name") as? String ?? Person.this.inventory.armorName
        Person.this.inventoryItemNames = user.object(forKey: "Person_inventory_names") as? Set<String> ?? Person.this.inventoryItemNames

//        Person.this.storyWindowLogic.thisStory = user.integer(forKey: "Story_number") ?? Preferences.this.storyWindowLogic.thisStory
        Preferences.this.storyWindowLogic.text = user.object(forKey: "This_story") as? String ?? Preferences.this.storyWindowLogic.text
        Preferences.this.storiesNames = user.object(forKey: "Story") as? String ?? Preferences.this.storiesNames
    }

    private func quickSetup() {
        StorySetup.this.fillStory()

        Person.this.reloadInventoryByNames()
        Person.this.reloadInventoryItemNames()

        if(ItemSetup.this.allItems.contains(where: { str, _ in str == Person.this.inventory.weaponName})) {
            Person.this.equip(ItemSetup.this.getItemByName(Person.this.inventory.weaponName)!)
        }
        if(ItemSetup.this.allShields.contains(where: { str, _ in str == Person.this.inventory.shieldName})) {
            Person.this.equip(ItemSetup.this.getItemByName(Person.this.inventory.shieldName)!)
        }
        if(ItemSetup.this.allArmors.contains(where: { str, _ in str == Person.this.inventory.armor.name})) {
            Person.this.equip(ItemSetup.this.getItemByName(Person.this.inventory.armorName)!)
        }
        startGame = true
    }
}
