import SwiftUI

struct InventoryWindow: View {
    @State var section = 0
    @State var toStory = false
    var body: some View {
        VStack {
            HStack {
                Image("hero_body")
                VStack {
                    Button(action: {
                        setOnUnequip(Person.this.inventory.weapon)
                    }, label: { Image(Person.this.inventory.weapon.icon!).frame(width: 100, height: 100) })
                    Button(action: {
                        setOnUnequip(Person.this.inventory.shield)
                    }, label: { Image(Person.this.inventory.shield.icon!).frame(width: 100, height: 100) })
                    Button(action: {
                        setOnUnequip(Person.this.inventory.armor)
                    }, label: { Image(Person.this.inventory.armor.icon!).frame(width: 100, height: 100) })
                }
            }
            NavigationView {
                Form {
                    Picker(selection: $section, label: Text("")) {
                        ForEach(0..<Person.this.inventory.Items.count) {
                            Text(Person.this.inventory.Items[$0])
                        }
                    }
                }
            }
            HStack {
                VStack {
                    Button(action: {

                    }, label: {
                        Text("Item info")
                    }).padding().background(RoundedRectangle(cornerRadius: 8).fill(Color.green))

                    Button(action: {
                        setOnEquip(Person.this.inventory.Items[$0])
                    }, label: {
                        Text("Use")
                    }).padding().background(RoundedRectangle(cornerRadius: 8).fill(Color.green))

                    Button(action: {
                        toStory = true
                    }, label: {
                        Text("Back")
                    }).padding().background(RoundedRectangle(cornerRadius: 8).fill(Color.green))
                }
                Text(action: { Person.this.getStats() }, label: {  })
            }
        }

        if (toStory) {
            StoryWindowActivity()
        }
    }

    func setOnEquip(_ itemName: String) {
        let getItem = ItemSetup.this.getItemByName(itemName)
        if (getItem != nil) {
            switch(getItem) {
                case is OneHand:
                    Person.this.equip(ItemSetup.this.getItemByName(itemName) as! OneHand)

                case is TwoHand:
                    Person.this.equip(ItemSetup.this.getItemByName(itemName) as! TwoHand)

                case is Shield:
                    Person.this.equip(ItemSetup.this.getItemByName(itemName) as! Shield)

                case is Armor:
                    Person.this.equip(ItemSetup.this.getItemByName(itemName) as! Armor)

                default:
                    Person.this.use(itemName)
            }
        }
    }
    func setOnUnequip(_ itemName: String) {
        let getItem = ItemSetup.this.getItemByName(itemName)
        if (getItem != nil) {
            switch(getItem) {
                case is OneHand:
                    Person.this.unequip(ItemSetup.this.getItemByName(itemName) as! OneHand)

                case is TwoHand:
                    Person.this.unequip(ItemSetup.this.getItemByName(itemName) as! TwoHand)

                case is Shield:
                    Person.this.unequip(ItemSetup.this.getItemByName(itemName) as! Shield)

                case is Armor:
                    Person.this.unequip(ItemSetup.this.getItemByName(itemName) as! Armor)

                default:
                    break
            }
        }
    }

    func setOnUnequip(_ item: Weapon) {
        if (Person.this.inventory.weapon.name != RightHand.this.name) {
            switch (item) {
                case is OneHand:
                    Person.this.unequip(ItemSetup.this.getItemByName(item.name) as! OneHand)

                case is TwoHand:
                    Person.this.unequip(ItemSetup.this.getItemByName(item.name) as! TwoHand)
                default:
                    break
            }
        }
    }

    func setOnUnequip(_ item: Shield){
        if (Person.this.inventory.shield.name != LeftHand.this.name) {
            if (Person.this.inventory.weapon is TwoHand) {
                Person.this.unequip(ItemSetup.this.getItemByName(item.name) as! Shield)
            }
        }
    }

    func setOnUnequip(_ item: Armor) {
        if (Person.this.inventory.armor.name != Body.this.name) {
            Person.this.unequip(ItemSetup.this.getItemByName(item.name) as! Armor)
        }
    }
}
