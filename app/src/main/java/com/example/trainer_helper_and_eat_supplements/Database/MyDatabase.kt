package com.example.trainer_helper_and_eat_supplements.Database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces.*
import com.example.trainer_helper_and_eat_supplements.Database.Data.*
import java.io.File
import java.io.Serializable
import java.util.*

@Database(entities = [
    ComplexesData::class,
    ComplexesExercisesData::class,
    DoneExercisesData::class,
    ExerciseMeasuresData::class,
    ExercisesData::class,
    MeasuresData::class,
    TrainsData::class,
    TrainsDoneExerciseData::class
],
version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase(), Serializable{
    abstract fun ComplexesDao():ComplexesDao
    abstract fun ComplexesExercisesDao(): ComplexesExercisesDao
    abstract fun DoneExercisesDao():DoneExercisesDao
    abstract fun ExerciseMeasuresDao():ExerciseMeasuresDao
    abstract fun ExercisesDao(): ExercisesDao
    abstract fun MeasuresDao(): MeasuresDao
    abstract fun TrainsDao(): TrainsDao
    abstract fun TrainsDoneExercisesDao(): TrainsDoneExercisesDao

    companion object{
        @Volatile
        private var MYINSTANCE: MyDatabase? = null
        @JvmStatic
        fun getDatabase(
            context:Context
        ):MyDatabase? {
            if(MYINSTANCE==null){
                synchronized(this){
                    MYINSTANCE = Room.databaseBuilder(
                        context,
                        MyDatabase::class.java,
                        "mydatabase.db"
                    ).build()
                }
            }
            deleteAllData()
            initFirstData()
            return MYINSTANCE
        }



        fun initFirstData(){
            if (MYINSTANCE?.MeasuresDao()?.getAllMeasures()==null) {
                // Меры по умолчанию
                MYINSTANCE?.MeasuresDao()?.addAllMeasure(
                    MeasuresData("Вес (кг)"),
                    MeasuresData("Время (с)"),
                    MeasuresData("Повторения (раз)"),
                    MeasuresData("Расстояние (м)")
                )
            }
        }

        fun deleteAllData(){
            MYINSTANCE?.ComplexesDao()?.deleteAll()
            MYINSTANCE?.ComplexesExercisesDao()?.deleteAll()
            MYINSTANCE?.DoneExercisesDao()?.deleteAll()
            MYINSTANCE?.ExerciseMeasuresDao()?.deleteAll()
            MYINSTANCE?.ExercisesDao()?.deleteAll()
            MYINSTANCE?.MeasuresDao()?.deleteAll()
            MYINSTANCE?.TrainsDao()?.deleteAll()
            MYINSTANCE?.TrainsDoneExercisesDao()?.deleteAll()
        }

        fun destroyInstance() {
            MYINSTANCE = null
        }
    }
}