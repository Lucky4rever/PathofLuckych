package com.kotlinconf23.pathofluckych.classes.logic

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.Setup.*
import com.kotlinconf23.pathofluckych.classes.items.*
import com.kotlinconf23.pathofluckych.interfaces.*
import com.kotlinconf23.pathofluckych.objects.Person
import com.kotlinconf23.pathofluckych.objects.armors.*
import com.kotlinconf23.pathofluckych.objects.shields.*
import com.kotlinconf23.pathofluckych.objects.weapons.*

 class InventoryWindowLogic : InventoryLogic {
    lateinit var weaponIcon: ImageButton
    lateinit var shieldIcon: ImageButton
    lateinit var armorIcon: ImageButton
    lateinit var outputText: TextView

    override fun setOnEquip(itemName: String) {
        when {
            ItemSetup.allOneHandWeapons.containsKey(itemName) -> {
                Person.equip(ItemSetup.allOneHandWeapons[itemName] as OneHand)
                weaponIcon.setImageResource(Person.inventory.weapon.icon!!)
                shieldIcon.setImageResource(Person.inventory.shield.icon!!)
                shieldIcon.alpha = 1F
            }
            ItemSetup.allTwoHandWeapons.containsKey(itemName) -> {
                Person.equip(ItemSetup.allTwoHandWeapons[itemName] as TwoHand)
                weaponIcon.setImageResource(Person.inventory.weapon.icon!!)
                shieldIcon.alpha = 0.5F
                shieldIcon.setImageResource(Person.inventory.weapon.icon!!)
            }
            ItemSetup.allShields.containsKey(itemName) -> {
                Person.equip(ItemSetup.allShields[itemName] as Defend.Shield)
                shieldIcon.setImageResource(Person.inventory.shield.icon!!)
                shieldIcon.alpha = 1F
                weaponIcon.setImageResource(Person.inventory.weapon.icon!!)
            }
            ItemSetup.allArmors.containsKey(itemName) -> {
                Person.equip(ItemSetup.allArmors[itemName] as Defend.Armor)
                armorIcon.setImageResource(Person.inventory.armor.icon!!)
            }
            else -> { Person.use(itemName) }
        }
        outputText.text = Person.getStats()
    }
    override fun setOnUnequip(itemName: String) {
        when {
            ItemSetup.allOneHandWeapons.containsKey(itemName) -> {
                Person.unequip(ItemSetup.allOneHandWeapons[itemName] as OneHand)
                weaponIcon.setImageResource(Person.inventory.weapon.icon!!)
            }
            ItemSetup.allTwoHandWeapons.containsKey(itemName) -> {
                Person.unequip(ItemSetup.allTwoHandWeapons[itemName] as TwoHand)
                shieldIcon.alpha = 1F
                weaponIcon.setImageResource(Person.inventory.weapon.icon!!)
                shieldIcon.setImageResource(Person.inventory.shield.icon!!)
            }
            ItemSetup.allShields.containsKey(itemName) -> {
                Person.unequip(ItemSetup.allShields[itemName] as Defend.Shield)
                shieldIcon.setImageResource(Person.inventory.shield.icon!!)
            }
            ItemSetup.allArmors.containsKey(itemName) -> {
                Person.unequip(ItemSetup.allArmors[itemName] as Defend.Armor)
                armorIcon.setImageResource(Person.inventory.armor.icon!!)
            }
        }
        outputText.text = Person.getStats()
    }

    override fun setOnUnequip(item: Weapon) {
        if (Person.inventory.weapon != RightHand)
            when (item) {
                is OneHand -> {
                    Person.unequip(ItemSetup.allOneHandWeapons[item.name] as OneHand)
                    weaponIcon.setImageResource(Person.inventory.weapon.icon!!)
                }
                is TwoHand -> {
                    Person.unequip(ItemSetup.allTwoHandWeapons[item.name] as TwoHand)
                    shieldIcon.alpha = 1F
                    weaponIcon.setImageResource(Person.inventory.weapon.icon!!)
                    shieldIcon.setImageResource(Person.inventory.shield.icon!!)
                }
            }
        outputText.text = Person.getStats()
    }
    override fun setOnUnequip(item: Defend.Shield){
        if (Person.inventory.shield != LeftHand)
            if (Person.inventory.weapon !is TwoHand) {
                Person.unequip(ItemSetup.allShields[item.name] as Defend.Shield)
                shieldIcon.setImageResource(Person.inventory.shield.icon!!)
            }
        outputText.text = Person.getStats()
    }
    override fun setOnUnequip(item: Defend.Armor) {
        if (Person.inventory.armor != Body) {
            Person.unequip(ItemSetup.allArmors[item.name] as Defend.Armor)
            armorIcon.setImageResource(Person.inventory.armor.icon!!)
        }
        outputText.text = Person.getStats()
    }

    @Suppress("DEPRECATION")
    fun createToast(context: Context, text: String) {
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        val viewGroup = toast.view as ViewGroup?
        val textView = viewGroup!!.getChildAt(0) as TextView
        textView.setBackgroundResource(R.color.my_transparent)
        textView.textSize = 20f
        toast.show()
    }
}