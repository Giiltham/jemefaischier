package io.fairflix.jemefaischier.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey
    val uid : Int,

    @ColumnInfo(name="osm_id")
    val osmId : Int,
)