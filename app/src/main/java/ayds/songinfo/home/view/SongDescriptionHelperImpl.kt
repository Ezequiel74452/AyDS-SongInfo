package ayds.songinfo.home.view

import ayds.songinfo.home.model.entities.Song.EmptySong
import ayds.songinfo.home.model.entities.Song
import ayds.songinfo.home.model.entities.Song.SpotifySong

interface SongDescriptionHelper {
    fun getSongDescriptionText(song: Song = EmptySong): String
}

internal class SongDescriptionHelperImpl : SongDescriptionHelper {
    override fun getSongDescriptionText(song: Song): String {
        return when (song) {
            is SpotifySong ->
                "${
                    "Song: ${song.songName} " +
                            if (song.isLocallyStored) "[*]" else ""
                }\n" +
                        "Artist: ${song.artistName}\n" +
                        "Album: ${song.albumName}\n" +
                        "Release date: ${when (song.releaseDatePrecision) {
                            "year" -> song.releaseDate
                            "month" -> when (song.releaseDate.split("-").last()) {
                                "1" -> "January, "
                                "2" -> "February, "
                                "3" -> "March, "
                                "4" -> "April, "
                                "5" -> "May, "
                                "6" -> "June, "
                                "7" -> "July, "
                                "8" -> "August, "
                                "9" -> "September, "
                                "10" -> "October, "
                                "11" -> "November, "
                                "12" -> "December, "
                                else -> ""
                            } + song.releaseDate.split("-").first()
                            "day" -> song.releaseDate.split("-").reduceRight { str, acc ->
                                "$acc/$str"
                            }
                            else -> "Unknown"
                        }}\n"
            else -> "Song not found"
        }
    }
}