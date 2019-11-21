package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.FragmentTitleBinding
import com.example.android.navigation.databinding.MenuBinding


/**
 * Binds the submit button for navigation
 */

/**
 * @property currentSandwich The text data for the current sandwich
 * @property currentBread The text data for the current bread
 * @property currentCost The current and eventually total cost
 * @property canSubmit doesn't allow the user to submit until it is confirmed that all requirements are filled
 * @property baseCost The starting cost of each sandwhich before toppings
 * @property checkboxes An array of the checkboxes containing the toppings information
 * @property toppings is
 *
 *
 */
class MenuFragment : Fragment() {

    var currentSandwich = "Sandwich"
    var currentCost = 0.00
    var currentBread = "Bread"
    var canSubmit = false;
    var baseCost = 0.00
    var checkboxes = arrayListOf<CheckBox>();

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: MenuBinding = DataBindingUtil.inflate(
                inflater, R.layout.menu, container, false)
        binding.submitButton.visibility = View.INVISIBLE
        binding.submitButton.setOnClickListener { v: View ->

            if(canSubmit){
                v.findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToReciptFragment(sandwhichData(currentSandwich, baseCost, currentBread, checkboxes, currentCost)))
            }
        }
        // Inflate the layout for this fragment
        canSubmit = false

        //test button is the calculate button
        binding.testButton.setOnClickListener() {
            var sTypeId = binding.sandwichType.checkedRadioButtonId
            var bTypeId = binding.breadType.checkedRadioButtonId

            checkboxes = arrayListOf<CheckBox>(binding.checkBox1, binding.checkBox2, binding.checkBox3, binding.checkBox4)
            // Do nothing if nothing is checked (id == -1)

            //First RadioButton Functionality
            if (-1 != sTypeId) {
                //Toast.makeText(context, checkedId.toString(), Toast.LENGTH_SHORT).show();
                when (sTypeId) {
                    R.id.philly -> { currentCost = 6.50; currentSandwich = "Philly"; baseCost = 6.50;}
                    R.id.blt -> {currentCost = 5.00; currentSandwich = "BLT"; baseCost = 5.00;}
                    R.id.spicyItalian -> {currentCost = 5.75; currentSandwich = "Spicy Italian"; baseCost = 5.75}
                }
                //Toast.makeText(context, currentSandwich, Toast.LENGTH_SHORT).show();
            }

            //Second RadioButton Functionality
            if (-1 != bTypeId) {
                //Toast.makeText(context, checkedId.toString(), Toast.LENGTH_SHORT).show();
                when (bTypeId) {
                    R.id.white -> { currentBread = "White";}
                    R.id.wheat -> { currentBread = "Wheat";}
                }
            }
            //Detects if either of the radio groups are empty and displays appropriate warning
            if(sTypeId == -1){
                Toast.makeText(context, "You must choose a type of sandwich before submitting", Toast.LENGTH_SHORT).show();
            } else if(bTypeId == -1){
                Toast.makeText(context, "You must choose a type of bread before submitting", Toast.LENGTH_SHORT).show();
            }else{
                canSubmit = true
                binding.submitButton.visibility = View.VISIBLE
            }
            var toppings = 0;
            //Detects Which toppings were selected
            if(canSubmit){
            for(i in 0..3){
                if(checkboxes[i].isChecked==true) {
                    toppings++
                }
            } }
            currentCost = currentCost+toppings*0.5

            binding.currentSelection.text = currentSandwich;
            binding.currentPrice.text = String.format("$%.2f", currentCost)

        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,
                view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }
}
