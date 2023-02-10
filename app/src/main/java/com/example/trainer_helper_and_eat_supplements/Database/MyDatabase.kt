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
    ApproachData::class,
    ApproachPartOfApproachData::class,
    ComplexData::class,
    ComplexExerciseData::class,
    ExerciseData::class,
    ExerciseMeasureData::class,
    FoodAdditiveData::class,
    FoodAdditiveTakingTimeAndDoseData::class,
    MeasureData::class,
    MeasureOfFoodAdditiveData::class,
    PartOfApproachData::class,
    ScheduleData::class,
    TakingTimeAndDoseData::class,
    TrainData::class,
    TrainExerciseData::class,
    WeekDayData::class,
    WeekDayFoodAdditiveData::class
],
version = 3, exportSchema = false)
@TypeConverters(Converters::class)// TODO доделать(сделать правильный вызов
abstract class MyDatabase : RoomDatabase(), Serializable{
    abstract fun ApproachDao():ApproachDao
    abstract fun ApproachPartOfApproachDao():ApproachPartOfApproachDao
    abstract fun ComplexDao():ComplexDao
    abstract fun ComplexExerciseDao(): ComplexExerciseDao
    abstract fun ExerciseDao(): ExerciseDao
    abstract fun ExerciseMeasureDao():ExerciseMeasureDao
    abstract fun FoodAdditiveDao(): FoodAdditiveDao
    abstract fun FoodAdditiveTakingTimeAndDoseDao(): FoodAdditiveTakingTimeAndDoseDao
    abstract fun MeasureDao(): MeasureDao
    abstract fun MeasureOfFoodAdditiveDao(): MeasureOfFoodAdditiveDao
    abstract fun PartOfApproachDao(): PartOfApproachDao
    abstract fun ScheduleDao(): ScheduleDao
    abstract fun TakingTimeAndDoseDao(): TakingTimeAndDoseDao
    abstract fun TrainDao(): TrainDao
    abstract fun TrainExerciseDao(): TrainExerciseDao
    abstract fun WeekDayDao(): WeekDayDao
    abstract fun WeekDayFoodAdditiveDao(): WeekDayFoodAdditiveDao


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
                            database.MeasureDao(),
                            database.WeekDayDao(),
                            database.MeasureOfFoodAdditiveDao(),
                            database.ScheduleDao()
                        )
                    }
                }
            }

        }
        suspend fun populateDatabase(
            mesureDao: MeasureDao,
            weekDaysDao: WeekDayDao,
            mesureOfFoodAdditiveDao: MeasureOfFoodAdditiveDao,
            scheduleDao: ScheduleDao
        ) {
            if(mesureDao.getAllMeasures().size == 0) {
                mesureDao.addAllMeasure(
                    MeasureData("Вес (кг)"),
                    MeasureData("Время (с)"),
                    MeasureData("Повторения (раз)"),
                    MeasureData("Расстояние (м)")
                )
            }

            if(weekDaysDao.getAllWeekDaysObj().size == 0){
                weekDaysDao.insertAllWeekDays(
                    WeekDayData("Понедельник"),
                    WeekDayData("Вторник"),
                    WeekDayData("Среда"),
                    WeekDayData("Четверг"),
                    WeekDayData("Пятница"),
                    WeekDayData("Суббота"),
                    WeekDayData("Воскресенье"),
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