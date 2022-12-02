package com.example.trainer_helper_and_eat_supplements.Adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trainer_helper_and_eat_supplements.MyDataModel
import com.example.trainer_helper_and_eat_supplements.R
import com.example.trainer_helper_and_eat_supplements.databinding.ItemEditAddNewApproachBinding

class EditAddApproachAdapter(
    mesuresList:List<String>,
    datamodel: MyDataModel,
    context: Context
): RecyclerView.Adapter<EditAddApproachAdapter.EditAddNewApproachHolder>() {
    val allMesuresOfExercise = mesuresList
    val curDataModel = datamodel
    val curContext = context
    var listOfValuesOfMesures:MutableList<String> = MutableList(mesuresList.size){""}
    var mesureValueList = hashMapOf<String, String>()

    init {
        allMesuresOfExercise.forEach(){ mesure ->
            mesureValueList.put(mesure, "")
        }
    }
    // создаю лист для хранения данных подходе



    class EditAddNewApproachHolder(
        item: View,
        holdDatamodel:MyDataModel,
        holdContext:Context
    ) : RecyclerView.ViewHolder(item){
        var binding = ItemEditAddNewApproachBinding.bind(item)
        var curDataModel = holdDatamodel
        var curContext = holdContext

        fun bind(str:String, pos:Int){
            binding.headerOfMesure.text = str

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditAddNewApproachHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_edit_add_new_approach, parent, false)

        val binding = ItemEditAddNewApproachBinding.bind(view)
        return EditAddNewApproachHolder(
            binding.root,
            curDataModel,
            curContext
        )
    }

    override fun onBindViewHolder(holder: EditAddNewApproachHolder, position: Int) {
        holder.bind(allMesuresOfExercise.get(position), position)
        holder.binding.valueOfMesure.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                listOfValuesOfMesures.set(holder.adapterPosition, p0.toString())
                mesureValueList.put(allMesuresOfExercise.get(holder.adapterPosition), p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    override fun getItemCount(): Int {
        return allMesuresOfExercise.size
    }


}