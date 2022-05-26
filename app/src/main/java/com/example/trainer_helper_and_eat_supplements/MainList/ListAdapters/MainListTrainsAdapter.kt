package com.example.trainer_helper_and_eat_supplements.MainList.ListAdapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.trainer_helper_and_eat_supplements.Database.MyDatabase
import com.example.trainer_helper_and_eat_supplements.R
import com.example.trainer_helper_and_eat_supplements.databinding.MainListItemBinding

class MainListTrainsAdapter (myList:List<String>?) : RecyclerView.Adapter<MainListTrainsAdapter.TrainsHolder>(){

    var stringList = myList

    class TrainsHolder (item: View) : RecyclerView.ViewHolder(item) {
        var binding = MainListItemBinding.bind(item)
        fun bind(str:String){
            binding.textView.text = str
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainsHolder {

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
                        Log.d("MyLog", "edit")
                    }
                }
                true
            }
            // Для картинок
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val myMenu = popup.get(popupMenu)
            myMenu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java).invoke(myMenu, true)
            popupMenu.show()
        }
        return TrainsHolder(binding.root)

    }

    override fun onBindViewHolder(holder: TrainsHolder, position: Int) {
        holder.bind(stringList?.get(position)!!)
    }

    override fun getItemCount(): Int {
        if (stringList==null){
            return 0
        }
        return stringList?.size!!
    }
}