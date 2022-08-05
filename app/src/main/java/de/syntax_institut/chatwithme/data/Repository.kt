package de.syntax_institut.chatwithme.data // ktlint-disable package-name

import de.syntax_institut.chatwithme.R
import de.syntax_institut.chatwithme.data.model.Contact

/**
 * Diese Klasse sorgt für die Aufbereitung der Daten
 */
class Repository {

    // Die Variable contactList ruft einmal die Funktion loadContacts() auf und speichert das Ergebnis
    private var _contactList = loadContacts()
    val contactList: List<Contact>
        get() = _contactList

    /**
     * Diese Funktion gibt eine Liste aus Contact Objekten zurück.
     * Jedes Contact Objekt enthält die Informationen für den Namen und
     * die Bild Ressource und eine leere veränderliche Liste
     */
    private fun loadContacts(): List<Contact> {
        return listOf(
            Contact("Brad", R.drawable.brad, mutableListOf()),
            Contact("Emma", R.drawable.emma, mutableListOf()),
            Contact("Jennifer", R.drawable.jennifer, mutableListOf()),
            Contact("Johnny", R.drawable.johnny, mutableListOf()),
            Contact("Keanu", R.drawable.keanu, mutableListOf()),
            Contact("Leo", R.drawable.leo, mutableListOf()),
            Contact("Margot", R.drawable.margot, mutableListOf()),
            Contact("Reese", R.drawable.reese, mutableListOf()),
            Contact("Sandra", R.drawable.sandra, mutableListOf()),
            Contact("Scarlett", R.drawable.scarlett, mutableListOf()),
            Contact("Tom", R.drawable.tom, mutableListOf()),
            Contact("Will", R.drawable.will, mutableListOf())
        )
    }
}
