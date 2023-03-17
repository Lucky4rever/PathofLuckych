import SwiftUI
import Combine


class FightLogic: ObservableObject {
    @Published var unit1: GameUnit
    @Published var unit2: GameUnit

    func savePreferences() {
        var user = UserDefaults.standard
        user.set(Person.this.name, forKey: "Person_Name")
        user.set(Person.this.lvl, forKey: "Person_Lvl")
        user.set(Person.this.stats.hp, forKey: "Person_stats_HP")
    }
}

struct FightWindowActivity: View {
    var fightView: FightLogic
    @State var width: Length = 1

    var body: some View {
        ZStack {
            HStack {
                VStack {
                    Image("hero_icon").frame(width: 100,height: 100)
                    Text(Person.this.name).font(Font.custom("akaya_telivigala", size: 18))
                }

                Image("swords").frame(width: 60,height: 60).padding(.bottom, 12)

                VStack {
                    Image(Preferences.this.enemy.race.icon).frame(width: 100,height: 100)
                    Text(Preferences.this.enemy.name).font(Font.custom("akaya_telivigala", size: 18))
                }
            }

            GeometryReader {
                geometry in ScrollView(isScrollEnabled: true, alwaysBounceHorizontal: false, alwaysBounceVertical: true, showsHorizontalIndicator: false, showsVerticalIndicator: true) {
                VStack {
                        Text(Person.this.log).font(Font.custom("akaya_telivigala", size: 13))
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

            VStack {
                Button(action: {
                    switch (Bool.random()) {
                        case true:
                            enemy1.attack(enemy2)
                            if (enemy2.stats.hp <= 0) {
                                enemy2.respawn()
                                StoryWindowActivity()
                            }

                        case false:
                            enemy2.attack(enemy1)
                            if (enemy1.stats.hp <= 0) {
                                enemy1.respawn()
                                StoryWindowActivity()
                            }
                    }
                    if(turnNum > 20) {
                        run.isEnabled = true
                        run.isHidden = false
                    }
                    turnNum++
                    fightView.savePreferences()
                 }, label: {
                    Text("Turn")
                }).font(Font.custom("akaya_telivigala", size: 18)).padding().background(RoundedRectangle(cornerRadius: 8).fill(Color.green))

                Button(action: {
                    if (Int.random(in: 0..<100) > 30) {
                        StoryWindowActivity()
                    } else {
                        Person.this.log += "You can't run now\n"
                    }
                }, label: {
                    Text("Run")
                }).font(Font.custom("akaya_telivigala", size: 18)).padding().background(RoundedRectangle(cornerRadius: 8).fill(Color.green))
            }
        }
        if (Person.this.isFightAlreadyOver) {
            StoryWindowActivity()
        }
    }
}