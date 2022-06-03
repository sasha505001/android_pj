package com.example.trainer_helper_and_eat_supplements.Adapters.MainList

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.trainer_helper_and_eat_supplements.*
import com.example.trainer_helper_and_eat_supplements.databinding.MainListItemBinding

class MainListExerciseAdapter(
    myList:List<String>,
    myModel:MyDataModel,
    context: Context
): RecyclerView.Adapter<MainListExerciseAdapter.ExercisesHolder>() {
    var exercises = myList
    val myDataModel = myModel
    val curContext = context


    class ExercisesHolder (item: View, var parent: ViewGroup,
                           itemModel:MyDataModel,
                           context: Context)
        : RecyclerView.ViewHolder(item){
        var binding = MainListItemBinding.bind(item)
        val myDataModel = itemModel
        val curContext = context

        fun bind(str:String, position: Int){
            binding.textView.text = str

            binding.itemConstraint.setOnClickListener(){
                val intent = Intent(curContext, ObserverOfExerciseActivity::class.java)
                intent.putExtra(CONSTANTS.NAMEOFOBSERVE, str)
                curContext.startActivity(intent)
            }

            binding.imageButton.setOnClickListener(){
                val popupMenu = PopupMenu(parent.context,it)
                popupMenu.inflate(R.menu.popup_menu)
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_delete_btn -> {
                            // TODO удаление из ExerciseMesure
                            myDataModel.deleteFullyExerciseByName(str)
                            Log.d("MyLog", "delete")
                        }
                        R.id.menu_edit_btn ->{
                            val intent = Intent(parent.context, EditAddExerciseActivity::class.java)
                            intent.putExtra(CONSTANTS.NAMEOFEDITOBJ, str)
                            parent.context.startActivity(intent)
                            Log.d("MyLog", "edit")
                        }
                    }
                    true
                }
                popupMenu.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_list_item, parent, false)
        val binding = MainListItemBinding.bind(view)
        return ExercisesHolder(binding.root, parent, myDataModel, curContext)
    }

    override fun onBindViewHolder(holder: ExercisesHolder, position: Int) {
        holder.bind(exercises.get(position), position)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }


}