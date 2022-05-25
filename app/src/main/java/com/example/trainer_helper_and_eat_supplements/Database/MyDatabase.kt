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
        private var MYINSTANCE: MyDatabase? = null
        @JvmStatic
        fun getDatabase(context:Context):MyDatabase? {
            if(MYINSTANCE==null){
                synchronized(this){
                    MYINSTANCE = Room.databaseBuilder(
                        context,
                        MyDatabase::class.java,
                        "mydatabase.db"
                    ).build()
                }
            }
            //initFirstData() TODO pain
            deleteAllData()
            return MYINSTANCE
        }

        fun destroyInstance() {
            MYINSTANCE = null
        }

        fun initFirstData(){
            // Меры по умолчанию
            MYINSTANCE?.MeasuresDao()?.addAllMeasure(
                MeasuresData("Вес (кг)"),
                MeasuresData("Время (с)"),
                MeasuresData("Повторения (раз)"),
                MeasuresData("Расстояние (м)")
            )
            insertInitData()
        }
        fun insertInitData(){
            MYINSTANCE?.ComplexesDao()?.insertAll(
                ComplexesData("myComplex")
            )
            MYINSTANCE?.ExercisesDao()?.insertAll(
                ExercisesData("Exercise", null, null)
            )

            MYINSTANCE?.ComplexesExercisesDao()?.insertAll(
                ComplexesExercisesData(
                    MYINSTANCE?.ExercisesDao()?.getExerciseByName("Exercise")?.id!!,
                    MYINSTANCE?.ComplexesDao()?.getComplex("myComplex")?.id!!
                )
            )
            MYINSTANCE?.ExerciseMeasuresDao()?.insertAll(
                ExerciseMeasuresData(
                    MYINSTANCE?.ExercisesDao()?.getExerciseByName("Exercise")?.id!!,
                    MYINSTANCE?.MeasuresDao()?.getMeasureByName("Вес (кг)")?.id!!
                )
            )
            MYINSTANCE?.DoneExercisesDao()?.insertAll(
                DoneExercisesData(
                    MYINSTANCE?.ExercisesDao()?.getExerciseByName("Exercise")?.id!!,
                    MYINSTANCE?.MeasuresDao()?.getMeasureByName("Вес (кг)")?.id!!,
                    10.0f
                )
            )

            Log.d("MyLog", "lol")
            MYINSTANCE?.TrainsDao()?.insertAll(
                TrainsData(
                    "MyTrain",
                    MYINSTANCE?.ComplexesDao()?.getComplex("myComplex")?.id!!,
                    Calendar.getInstance().time
                )
            )
            // могу получить список и из него взять сделанное упражнение

            //TODO сделать связь тренировка сделанное упражнение - тренировка
            MYINSTANCE?.TrainsDoneExercisesDao()?.insertAll(
                TrainsDoneExerciseData(
                    MYINSTANCE?.TrainsDao()?.getTrainByName("MyTrain")?.id!!,
                    MYINSTANCE?.DoneExercisesDao()?.getAll()?.last()?.id!!
            )
            )
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
    }
}