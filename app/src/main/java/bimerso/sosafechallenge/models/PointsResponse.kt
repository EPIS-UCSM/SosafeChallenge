package bimerso.sosafechallenge.models

import java.io.Serializable

data class PointsResponse(
    val html_attributions: List<Any>,
    val results: List<Lugares>,
    val status: String
):Serializable