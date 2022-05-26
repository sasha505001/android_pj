package com.example.trainer_helper_and_eat_supplements.MainList.ListAdapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.trainer_helper_and_eat_supplements.Database.Data.ExercisesData
import com.example.trainer_helper_and_eat_supplements.Database.MyDatabase
import com.example.trainer_helper_and_eat_supplements.R
import com.example.trainer_helper_and_eat_supplements.databinding.MainListItemBinding

class MainListExerciseAdapter(database: MyDatabase?): RecyclerView.Adapter<MainListExerciseAdapter.ExercisesHolder>() {
    var exercises = listOf<ExercisesData>()

    // TODO инициализировать данные

    class ExercisesHolder (item: View) : RecyclerView.ViewHolder(item){
        var binding = MainListItemBinding.bind(item)
        fun bind(str:String){
            binding.textView.text = str
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesHolder {
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
                        //val intent = Intent(parent.context, EditAddExercise::class.java)
                        //parent.context.startActivity(intent)
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
        return MainListExerciseAdapter.ExercisesHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ExercisesHolder, position: Int) {
        holder.bind(exercises[position].name)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }


}