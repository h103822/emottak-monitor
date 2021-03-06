package no.nav.emottak.services

import java.time.LocalDateTime
import no.nav.emottak.aksessering.db.hentMeldinger
import no.nav.emottak.db.DatabaseInterface
import no.nav.emottak.model.MeldingInfo

class MessageQueryService(
    private val databaseInterface: DatabaseInterface,
    private val databasePrefix: String
) {
    fun meldinger(fom: LocalDateTime, tom: LocalDateTime): List<MeldingInfo> =
        databaseInterface.hentMeldinger(databasePrefix, fom, tom)
}
