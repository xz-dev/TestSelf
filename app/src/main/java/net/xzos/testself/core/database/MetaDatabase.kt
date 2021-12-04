package net.xzos.testself.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.xzos.testself.core.database.dao.PoolDao
import net.xzos.testself.core.database.dao.QuestionDao
import net.xzos.testself.core.database.dao.QuestionExtraDao
import net.xzos.testself.core.database.table.PoolEntity
import net.xzos.testself.core.database.table.QuestionEntity
import net.xzos.testself.core.database.table.QuestionExtraEntity

@Database(
    entities = [PoolEntity::class, QuestionEntity::class, QuestionExtraEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class MetaDatabase : RoomDatabase() {
    abstract fun poolDao(): PoolDao
    abstract fun questionDao(): QuestionDao
    abstract fun questionExtraDao(): QuestionExtraDao
}

lateinit var metaDatabase: MetaDatabase
fun initDatabase(context: Context) {
    metaDatabase = getDatabase(context, MetaDatabase::class.java, "question_database.db")
}

fun <E : RoomDatabase> getDatabase(context: Context, less: Class<E>, name: String): E {
    return Room
        .databaseBuilder(context, less, name)
        .build()
}