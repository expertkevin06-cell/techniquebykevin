package com.tonapp.vehicules

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun PannesScreen() {
    var marque by remember { mutableStateOf("Peugeot") }
    var modele by remember { mutableStateOf("3008") }
    var motorisation by remember { mutableStateOf("1.5 BlueHDi 130") }
    var annee by remember { mutableStateOf("2019") }

    var resultat by remember { mutableStateOf<PannesResult?>(null) }
    var erreur by remember { mutableStateOf<String?>(null) }
    var chargement by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Test — Pannes connues (Gemini)", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = marque,
            onValueChange = { marque = it },
            label = { Text("Marque") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = modele,
            onValueChange = { modele = it },
            label = { Text("Modèle") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = motorisation,
            onValueChange = { motorisation = it },
            label = { Text("Motorisation (optionnel)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = annee,
            onValueChange = { annee = it },
            label = { Text("Année (optionnel)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                erreur = null
                resultat = null
                chargement = true
                scope.launch {
                    try {
                        val requete = PannesRequest(
                            marque = marque,
                            modele = modele,
                            motorisation = motorisation.ifBlank { null },
                            annee = annee.ifBlank { null }
                        )
                        val reponse = RetrofitClient.api.rechercherPannesConnues(requete)
                        resultat = reponse
                    } catch (e: Exception) {
                        erreur = "Erreur : ${e.message}"
                    } finally {
                        chargement = false
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (chargement) "Recherche en cours..." else "Rechercher les pannes connues")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (chargement) {
            CircularProgressIndicator()
        }

        erreur?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        resultat?.let { res ->
            Text("Véhicule : ${res.vehicule_identifie ?: "?"}", style = MaterialTheme.typography.titleMedium)
            Text("Fiabilité recherche : ${res.fiabilite_recherche ?: "?"}")
            Spacer(modifier = Modifier.height(12.dp))

            if (res.pannes.isEmpty()) {
                Text("Aucune panne trouvée.")
            } else {
                res.pannes.forEach { panne ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(panne.titre, style = MaterialTheme.typography.titleSmall)
                            Text("Système : ${panne.systeme}")
                            Text("Fréquence : ${panne.frequence}")
                            Text("KM typique : ${panne.km_apparition_typique ?: "non déterminé"}")
                            Text(panne.description)
                            Text("Sources recoupées : ${panne.nb_sources_recoupees ?: 0}")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            res.avertissement?.let { Text(it, style = MaterialTheme.typography.bodySmall) }

            res.sources_web_consultees?.let { sources ->
                if (sources.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Sources consultées :", style = MaterialTheme.typography.labelMedium)
                    sources.forEach { url -> Text(url, style = MaterialTheme.typography.bodySmall) }
                }
            }
        }
    }
}
