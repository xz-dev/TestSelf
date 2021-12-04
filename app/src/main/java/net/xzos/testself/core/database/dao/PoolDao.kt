package net.xzos.testself.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import net.xzos.testself.core.database.table.PoolAndQuestion
import net.xzos.testself.core.database.table.PoolEntity
import net.xzos.testself.core.database.table.QuestionEntity

@Dao
interface PoolDao : BaseDao<PoolEntity> {
    @Query("SELECT * FROM pool WHERE id = :id")
    suspend fun getItem(id: Long): PoolEntity?

    @Query("SELECT * FROM pool WHERE name = :name")
    suspend fun getItemByName(name: String): PoolEntity?

    @Query("SELECT * FROM pool WHERE enable")
    suspend fun getEnableItem(): PoolEntity?

    @Query("SELECT * FROM pool")
    suspend fun loadAll(): List<PoolEntity>

    @Query("DELETE FROM pool WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Transaction
    @Query("SELECT * FROM pool WHERE id = :id")
    fun getPoolAndQuestion(id: Long): PoolAndQuestion

    @Transaction
    @Query("SELECT * FROM pool")
    fun getPoolAndQuestion(): List<PoolAndQuestion>
}