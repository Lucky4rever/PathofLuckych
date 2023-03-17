package com.kotlinconf23.pathofluckych.android.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.kotlinconf23.pathofluckych.R
import com.kotlinconf23.pathofluckych.Setup
import com.kotlinconf23.pathofluckych.classes.items.TwoHand
import com.kotlinconf23.pathofluckych.objects.Person

class InventoryWindowActivity : AppCompatActivity() {
    private lateinit var itemInfo: Button
    private lateinit var useButton: Button
    private lateinit var toStory: Button
    private lateinit var itemContainer: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
//        startActivity(Intent(this, MainActivity::class.java))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inventory_window)

        Setup.Preferences.inventoryWindowLogic.outputText = findViewById(R.id.PersonText)
        toStory = findViewById(R.id.toStory)
        itemContainer = findViewById(R.id.items)
        itemInfo = findViewById(R.id.ItemInfo)
        useButton = findViewById(R.id.Use)

        initItemSpinner()
        Setup.Preferences.inventoryWindowLogic.outputText.text = Person.getStats()

        Setup.Preferences.inventoryWindowLogic.weaponIcon = findViewById(R.id.weaponIcon)
        Setup.Preferences.inventoryWindowLogic.weaponIcon.setImageResource(Person.inventory.weapon.icon!!)
        Setup.Preferences.inventoryWindowLogic.weaponIcon.setOnClickListener {
            Setup.Preferences.inventoryWindowLogic.setOnUnequip(Person.inventory.weapon)
        }
        Setup.Preferences.inventoryWindowLogic.weaponIcon.setOnLongClickListener {
            Setup.Preferences.inventoryWindowLogic.createToast(baseContext, Person.inventory.weapon.getStats())
            return@setOnLongClickListener true
        }


        Setup.Preferences.inventoryWindowLogic.shieldIcon = findViewById(R.id.shieldIcon)
        if(Person.inventory.weapon is TwoHand){
            Setup.Preferences.inventoryWindowLogic.shieldIcon.setImageResource(Person.inventory.weapon.icon!!)
            Setup.Preferences.inventoryWindowLogic.shieldIcon.alpha = 0.5F
        }
        else
            Setup.Preferences.inventoryWindowLogic.shieldIcon.setImageResource(Person.inventory.shield.icon!!)
        Setup.Preferences.inventoryWindowLogic.shieldIcon.setOnClickListener {
            Setup.Preferences.inventoryWindowLogic.setOnUnequip(Person.inventory.shield)
        }
        Setup.Preferences.inventoryWindowLogic.shieldIcon.setOnLongClickListener {
            Setup.Preferences.inventoryWindowLogic.createToast(baseContext, Person.inventory.shield.getStats())
            return@setOnLongClickListener true
        }


        Setup.Preferences.inventoryWindowLogic.armorIcon = findViewById(R.id.armorIcon)
        Setup.Preferences.inventoryWindowLogic.armorIcon.setImageResource(Person.inventory.armor.icon!!)
        Setup.Preferences.inventoryWindowLogic.armorIcon.setOnClickListener {
            Setup.Preferences.inventoryWindowLogic.setOnUnequip(Person.inventory.armor)
        }
        Setup.Preferences.inventoryWindowLogic.armorIcon.setOnLongClickListener {
            Setup.Preferences.inventoryWindowLogic.createToast(baseContext, Person.inventory.armor.getStats())
            return@setOnLongClickListener true
        }


        toStory.setOnClickListener { startActivity(Intent(this, StoryWindowActivity::class.java)) }
        itemInfo.setOnClickListener {
            if (!itemContainer.isEmpty())
                showFilterDialog()
        }
        useButton.setOnClickListener {
            if (!itemContainer.isEmpty()) {
                Setup.Preferences.inventoryWindowLogic.setOnEquip(itemContainer.selectedItem.toString())

                Person.reloadInventoryItemNames()
                initItemSpinner()
            }
        }
    }

    private fun initItemSpinner() {
        val itemList: MutableList<String> = mutableListOf()
        for (item in Person.inventory.Items) itemList += listOf(item.key.first)
        val itemSpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemList)
        itemSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        itemContainer.adapter = itemSpinnerAdapter
    }

    private fun showFilterDialog() {
        val thisItemCount = Person.inventory.Items[Pair(itemContainer.selectedItem, Setup.ItemSetup.allItems[itemContainer.selectedItem])]
        val dialog = MaterialDialog(this).noAutoDismiss().customView(R.layout.dialog_view)

        dialog.findViewById<TextView>(R.id.itemName).text = buildString {
            append("${itemContainer.selectedItem} ")
            append(
                if(thisItemCount!=1)
                    "(${thisItemCount.toString()})"
                else ""
            )
        }
        dialog.findViewById<TextView>(R.id.itemInfoText).text = Setup.ItemSetup.allItems[itemContainer.selectedItem]?.getInfo()
        dialog.findViewById<TextView>(R.id.itemCountText).text = Setup.ItemSetup.allItems[itemContainer.selectedItem]?.getStats()

        dialog.findViewById<Button>(R.id.Equip).setOnClickListener {
            Setup.Preferences.inventoryWindowLogic.setOnEquip(itemContainer.selectedItem.toString())

            Person.reloadInventoryItemNames()
            initItemSpinner()
            dialog.cancel()
        }
        dialog.findViewById<Button>(R.id.backDialogButton2).setOnClickListener { dialog.cancel() }

        if(Setup.ItemSetup.allItems[itemContainer.selectedItem]?.icon!=null) {
            dialog.findViewById<ImageView>(R.id.itemIcon).setImageURI(null)
            dialog.findViewById<ImageView>(R.id.itemIcon)
                .setImageURI(Uri.parse("android.resource://$packageName/${Setup.ItemSetup.allItems[itemContainer.selectedItem]?.icon!!}"))
        } else {
            dialog.findViewById<ImageView>(R.id.itemIcon).setImageResource(R.mipmap.unknown_foreground)
        }

        dialog.show()
    }
}