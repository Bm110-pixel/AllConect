package com.example.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.FeatureRequestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FeatureRequestDao {

    @Query("SELECT * FROM feature_requests ORDER BY isWinningFeature DESC, requestVotes DESC, submittedTimestamp DESC")
    fun getAllRequests(): Flow<List<FeatureRequestEntity>>

    @Query("SELECT * FROM feature_requests WHERE id = :id LIMIT 1")
    suspend fun getRequestById(id: String): FeatureRequestEntity?

    @Query("SELECT COUNT(*) FROM feature_requests")
    suspend fun getRequestCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(request: FeatureRequestEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(requests: List<FeatureRequestEntity>)

    @Update
    suspend fun update(request: FeatureRequestEntity)

    @Delete
    suspend fun delete(request: FeatureRequestEntity)

    @Query("UPDATE feature_requests SET requestVotes = requestVotes + :increment, userHasVoted = :hasVoted WHERE id = :id")
    suspend fun updateVote(id: String, increment: Int, hasVoted: Boolean)

    @Query("UPDATE feature_requests SET isWinningFeature = 0")
    suspend fun resetWinningFeatures()

    @Query("UPDATE feature_requests SET isWinningFeature = 1, status = 'ADDED_TO_ALLCONNECT' WHERE id = :id")
    suspend fun setWinningFeature(id: String)
}
