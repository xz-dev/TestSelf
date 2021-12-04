package net.xzos.testself.core.database.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pool")
data class PoolEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "enable") var enable: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
)