package io.fairflix.jemefaischier.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["osm_id"], unique = true)])
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val uid : Int,

    @ColumnInfo(name="osm_id")
    val osmId : Long,

    @ColumnInfo(name="name")
    val name : String,
)