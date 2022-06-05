package com.example.trainer_helper_and_eat_supplements.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces.*
import com.example.trainer_helper_and_eat_supplements.Database.Data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Serializable

@Database(entities = [
    ComplexesData::class,
    ComplexesExercisesData::class,
    DoneExercisePartOfItData::class,
    ExerciseMeasuresData::class,
    ExercisesData::class,
    FoodAdditiveData::class,
    MeasureOfFoodAdditiveData::class,
    MeasuresData::class,
    PartOfDoneExercisesData::class,
    ScheduleData::class,
    TakingTimeData::class,
    TakingTimeFoodAdditiveData::class,
    TrainsData::class,
    TrainsDoneExerciseData::class,
    WeekDaysData::class,
    WeekDaysFoodAdditiveData::class
],
version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase(), Serializable{
    abstract fun ComplexesDao():ComplexesDao
    abstract fun ComplexesExercisesDao(): ComplexesExercisesDao
    abstract fun DoneExercisePartOfItDao():DoneExercisePartOfItDao
    abstract fun ExerciseMeasuresDao():ExerciseMeasuresDao
    abstract fun ExercisesDao(): ExercisesDao
    abstract fun FoodAdditiveDao(): FoodAdditiveDao
    abstract fun MeasureOfFoodAdditiveDao(): MeasureOfFoodAdditiveDao
    abstract fun MeasuresDao(): MeasuresDao
    abstract fun PartOfDoneExercisesDao(): PartOfDoneExercisesDao
    abstract fun ScheduleDao(): ScheduleDao
    abstract fun TakingTimeDao(): TakingTimeDao
    abstract fun TakingTimeFoodAdditiveDao(): TakingTimeFoodAdditiveDao
    abstract fun TrainsDao(): TrainsDao
    abstract fun TrainsDoneExercisesDao(): TrainsDoneExercisesDao
    abstract fun WeekDaysDao(): WeekDaysDao
    abstract fun WeekDaysFoodAdditiveDao(): WeekDaysFoodAdditiveDao


    companion object{
        @Volatile
        private var INSTANCE: MyDatabase? = null
        @JvmStatic
        fun getDatabase(
            context:Context,
            scope: CoroutineScope
        ):MyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "mydata"
                )
                    .addCallback(MyDatabasCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }


        private class MyDatabasCallback(
            private val scope: CoroutineScope
        ): RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(
                            database.MeasuresDao(),
                            database.WeekDaysDao(),
                            database.MeasureOfFoodAdditiveDao(),
                            database.ScheduleDao()
                        )
                    }
                }
            }

        }
        suspend fun populateDatabase(
            mesureDao: MeasuresDao,
            weekDaysDao: WeekDaysDao,
            mesureOfFoodAdditiveDao: MeasureOfFoodAdditiveDao,
            scheduleDao: ScheduleDao
        ) {
            if(mesureDao.getAllMeasures().size == 0) {
                mesureDao.addAllMeasure(
                    MeasuresData("Вес (кг)"),
                    MeasuresData("Время (с)"),
                    MeasuresData("Повторения (раз)"),
                    MeasuresData("Расстояние (м)")
                )
            }
            if(weekDaysDao.getAllWeekDaysObj().size == 0){
                weekDaysDao.insertAllWeekDays(
                    WeekDaysData("Понедельник"),
                    WeekDaysData("Вторник"),
                    WeekDaysData("Среда"),
                    WeekDaysData("Четверг"),
                    WeekDaysData("Пятница"),
                    WeekDaysData("Суббота"),
                    WeekDaysData("Воскресенье"),
                )
            }
            if(mesureOfFoodAdditiveDao.getAllFoodAdditiveMesuresObj().size == 0){
                mesureOfFoodAdditiveDao.insertAllFoodAdditiveMesures(
                    MeasureOfFoodAdditiveData("табл."),
                    MeasureOfFoodAdditiveData("мл."),
                    MeasureOfFoodAdditiveData("гр."),
                )
            }
            if(scheduleDao.getAllScheduleObj().size == 0){
                scheduleDao.insertAllSchedule(
                    ScheduleData("Каждый день"),
                    ScheduleData("В определенный день"),
                    ScheduleData("По определенным дням недели"),
                )
            }
        }
    }

}