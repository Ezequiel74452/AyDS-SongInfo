package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.ArtistCard
import java.util.Locale

interface ArtistCardDescriptionHelper {
    fun getDescription(artistCard: ArtistCard): String
}

private const val HEADER = "<html><div width=400><font face=\"arial\">"
private const val FOOTER = "</font></div></html>"
private const val LOCAL_MARKER = "[*]"

internal class ArtistCardDescriptionHelperImpl : ArtistCardDescriptionHelper {
    override fun getDescription(artistCard: ArtistCard): String {
        val text = getTextDescription(artistCard)
        return textToHtml(text, artistCard.artistName)
    }

    private fun getTextDescription(artistCard: ArtistCard): String {
        val prefix = if (artistCard.isLocallyStored) LOCAL_MARKER else ""
        val text = artistCard.description
        return "$prefix$text"
    }

    private fun textToHtml(text: String, term: String): String {
        val builder = StringBuilder()
        builder.append(HEADER)
        val textWithBold = text
            .replace("'", " ")
            .replace("\\n", "\n")
            .replace("\n", "<br>")
            .replace(
                "(?i)$term".toRegex(),
                "<b>" + term.uppercase(Locale.getDefault()) + "</b>"
            )
        builder.append(textWithBold)
        builder.append(FOOTER)
        return builder.toString()
    }
}