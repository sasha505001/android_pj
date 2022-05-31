package com.example.trainer_helper_and_eat_supplements.MainList.ListAdapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.trainer_helper_and_eat_supplements.MyDataModel
import com.example.trainer_helper_and_eat_supplements.R
import com.example.trainer_helper_and_eat_supplements.databinding.MainListItemBinding

class MainListFoodAdditiveAdapter (myList:List<String>, myModel: MyDataModel)
    : RecyclerView.Adapter<MainListFoodAdditiveAdapter.FoodAdditivesHolder>(){
    var foodAdditiveList = myList
    val myDataModel = myModel




    class FoodAdditivesHolder(item: View, var parent: ViewGroup, itemModel:MyDataModel)
        : RecyclerView.ViewHolder(item){
        var binding = MainListItemBinding.bind(item)
        val myDataModel = itemModel

        fun bind(str:String){
            binding.textView.text = str

            binding.imageButton.setOnClickListener(){
                val popupMenu = PopupMenu(parent.context,it)
                popupMenu.inflate(R.menu.popup_menu)
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_delete_btn -> {
                            // TODO удаление
                            Log.d("MyLog", "delete")
                        }
                        R.id.menu_edit_btn ->{
                            // TODO редактирование
                            Log.d("MyLog", "edit")
                        }
                    }
                    true
                }
                popupMenu.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodAdditivesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_list_item, parent, false)
        var binding = MainListItemBinding.bind(view)
        return MainListFoodAdditiveAdapter.FoodAdditivesHolder(binding.root, parent, myDataModel)
    }

    override fun onBindViewHolder(holder: FoodAdditivesHolder, position: Int) {
        holder.bind(foodAdditiveList.get(position))
    }

    override fun getItemCount(): Int {

        return foodAdditiveList.size
    }


}