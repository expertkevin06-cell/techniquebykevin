package com.tonapp.vehicules

// Résultat renvoyé par la recherche véhicule (France/Europe/USA/Asie)
data class VehiculeResult(
    val vin: String? = null,
    val plaque: String? = null,
    val marque: String? = null,
    val modele: String? = null,
    val annee: String? = null,
    val carrosserie: String? = null,
    val source: String? = null,
    val erreur: String? = null
)

// Résultat structuré renvoyé par l'IA après analyse du texte VROOM collé
data class VroomVehicule(
    val marque: String? = null,
    val modele: String? = null,
    val immatriculation: String? = null,
    val vin: String? = null
)

data class VroomResult(
    val numero_dossier: String? = null,
    val vehicule: VroomVehicule? = null,
    val statut_dossier: String? = null,
    val date: String? = null,
    val assureur: String? = null,
    val reparateur: String? = null,
    val montant: String? = null,
    val notes_libres: String? = null,
    val erreur: String? = null
)

data class VroomRequest(val texte: String)
data class PannesRequest(
    val marque: String,
    val modele: String,
    val motorisation: String? = null,
    val annee: String? = null
)

data class PannesResult(
    val vehicule_identifie: String?,
    val pannes: List<Panne>,
    val fiabilite_recherche: String?,
    val avertissement: String?,
    val sources_web_consultees: List<String>?
)

data class Panne(
    val titre: String,
    val systeme: String,
    val frequence: String,
    val km_apparition_typique: String?,
    val description: String,
    val nb_sources_recoupees: Int?,
    val types_sources: List<String>?
)
