package ayds.songinfo.moredetails.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Database(entities = [CardEntity::class], version = 1)
abstract class CardDatabase : RoomDatabase() {
    abstract fun CardDao(): CardDao
}
@Entity(primaryKeys = ["artistName", "source"])
data class CardEntity(
    val artistName: String,
    val content: String,
    val url: String,
    val source: Int,
    val sourceImg: String
)

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(card: CardEntity)

    @Query("SELECT * FROM CardEntity WHERE artistName = :artistName")
    fun getCardsByArtistName(artistName: String): List<CardEntity>

}