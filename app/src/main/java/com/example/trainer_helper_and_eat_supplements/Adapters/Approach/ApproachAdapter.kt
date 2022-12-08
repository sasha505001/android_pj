package com.example.trainer_helper_and_eat_supplements.Adapters.Approach

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainer_helper_and_eat_supplements.MyDataModel
import com.example.trainer_helper_and_eat_supplements.R
import com.example.trainer_helper_and_eat_supplements.databinding.ItemApproachOfTrainBinding

class ApproachAdapter(
    nameOfExercise:String,
    apprachesOfExercise: ArrayList<HashMap<String, Float>>,
    datamodel: MyDataModel,
    context: Context
): RecyclerView.Adapter<ApproachAdapter.ApproachHolder> (){
    val curDataModel = datamodel
    var approaches = datamodel.myApproachesOfTrain.value!!.get(nameOfExercise)!!

    val curContext = context
    val curExercise = nameOfExercise

    inner class ApproachHolder(
        item: View, var parent: ViewGroup,
        holdDatamodel:MyDataModel,
        holdContext:Context
    ): RecyclerView.ViewHolder(item){
        var binding = ItemApproachOfTrainBinding.bind(item)
        var curDataModel = holdDatamodel
        var curContext = holdContext

        fun bind(mesuresAndValues:HashMap<String, Float>, position: Int){

            // Порядковый номер
            binding.curNumber.text = (position + 1).toString()

            // Кнопка для редактирования / удаления
            binding.popupBtn.setOnClickListener(){
                val popupMenu = PopupMenu(parent.context,it)
                popupMenu.inflate(R.menu.popup_menu)
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_delete_btn -> {
                            curDataModel.deleteApproachOfTrain(curExercise, position)
                            notifyDataSetChanged()
                        }
                        R.id.menu_edit_btn ->{

                        }
                    }

                    true
                }
                popupMenu.show()
            }

            // Инфа о подходе
            var adapter = PartOfApproachAdapter(mesuresAndValues,curDataModel, curContext)
            binding.mesuresAndValues.layoutManager =  LinearLayoutManager(curContext)
            binding.mesuresAndValues.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApproachHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_approach_of_train, parent, false)

        val binding = ItemApproachOfTrainBinding.bind(view)
        return ApproachHolder(
            binding.root,
            parent,
            curDataModel,
            curContext
        )
    }

    override fun onBindViewHolder(holder: ApproachHolder, position: Int) {
        holder.bind(approaches.get(position), position)
    }

    override fun getItemCount(): Int {
        return approaches.size
    }

}