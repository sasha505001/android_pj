package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.*


// Упражнение - подход
@Entity(tableName = "approach",
    foreignKeys = [
        ForeignKey(
            entity = TrainExerciseData::class,
            parentColumns = ["id"],
            childColumns = ["id_train_exercise"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
class ApproachData(
    // id связки упражнении / тренировке
    @ColumnInfo(name = "id_train_exercise")
    val id_train_exercise:Int
) {
    // Id подхода
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}