package com.example.trainer_helper_and_eat_supplements.MainList.ListAdapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.trainer_helper_and_eat_supplements.MyDataModel
import com.example.trainer_helper_and_eat_supplements.R
import com.example.trainer_helper_and_eat_supplements.databinding.MainListItemBinding

class MainListComplexAdapter (
    myList:List<String>,
    myModel: MyDataModel,
    context: Context
)
    : RecyclerView.Adapter<MainListComplexAdapter.ComplexesHolder>(){
    var complexesList = myList
    val myDataModel = myModel
    val context = context

    class ComplexesHolder (item: View,
                           var parent: ViewGroup,
                           itemModel:MyDataModel,
                           context: Context)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplexesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_list_item, parent, false)
        val binding = MainListItemBinding.bind(view)
        return MainListComplexAdapter.ComplexesHolder(binding.root, parent, myDataModel, context)
    }

    override fun onBindViewHolder(holder: ComplexesHolder, position: Int) {
        holder.bind(complexesList.get(position))
    }

    override fun getItemCount(): Int {
        return complexesList.size
    }
}