package com.example.trainer_helper_and_eat_supplements.MainList.ListAdapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.trainer_helper_and_eat_supplements.Database.MyDatabase
import com.example.trainer_helper_and_eat_supplements.R
import com.example.trainer_helper_and_eat_supplements.databinding.MainListItemBinding

class MainListComplexAdapter (database: MyDatabase?) : RecyclerView.Adapter<MainListComplexAdapter.ComplexesHolder>(){
    var stringList = arrayListOf<String>()

    class ComplexesHolder (item: View) : RecyclerView.ViewHolder(item){
        var binding = MainListItemBinding.bind(item)
        fun bind(str:String){
            binding.textView.text = str
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplexesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_list_item, parent, false)
        var binding = MainListItemBinding.bind(view)

        binding.imageButton.setOnClickListener(){
            var popupMenu = PopupMenu(parent.context,it)
            popupMenu.inflate(R.menu.popup_menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menu_delete_btn -> {
                        Log.d("MyLog", "delete")
                    }
                    R.id.menu_edit_btn ->{
                        //val intent = Intent(parent.context, EditAddCompex::class.java)
                        //parent.context.startActivity(intent)
                        // TODO с интентом передавать имя комплекса
                        Log.d("MyLog", "edit")
                    }
                }
                true
            }
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val myMenu = popup.get(popupMenu)
            myMenu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java).invoke(myMenu, true)
            popupMenu.show()
        }
        return MainListComplexAdapter.ComplexesHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ComplexesHolder, position: Int) {
        holder.bind(stringList[position])
    }

    override fun getItemCount(): Int {
        return stringList.size
    }
}