# Intégration Android Studio — Kotlin natif

## Ce que contiennent ces fichiers

- `Models.kt` — les "formats" de données (véhicule, dossier VROOM)
- `ApiService.kt` — la liste des adresses de ton backend que l'app peut appeler
- `RetrofitClient.kt` — la configuration technique pour parler au backend
- `VroomScreen.kt` — l'écran affiché à l'utilisateur (bouton + zone de texte + résultat)

## Comment les ajouter à ton projet

1. Dans Android Studio, ouvre ton projet
2. Copie les 4 fichiers `.kt` dans un dossier `com/tonapp/vehicules/` sous
   `app/src/main/java/` (remplace "tonapp" par le vrai nom de ton package)
3. Ajoute les dépendances listées dans `DEPENDANCES_A_AJOUTER.txt` à ton
   fichier `app/build.gradle`, puis clique sur "Sync Now"
4. Ajoute la permission Internet dans `AndroidManifest.xml` (voir le fichier ci-dessus)
5. Pour afficher l'écran, appelle `VroomScreen()` depuis ta MainActivity :

```kotlin
setContent {
    VroomScreen()
}
```

## Important : l'adresse de ton serveur

Dans `RetrofitClient.kt`, l'adresse est réglée par défaut sur `10.0.2.2:3000`
— c'est l'adresse spéciale qui permet à l'**émulateur** Android de contacter
ton PC en local (le serveur Node.js que tu as déjà).

- Si tu testes sur l'émulateur avec ton serveur lancé sur ton PC (`npm start`) :
  aucune modification à faire.
- Si tu testes sur un **vrai téléphone** connecté au même Wi-Fi que ton PC :
  remplace par l'adresse IP locale de ton PC (ex: `http://192.168.1.25:3000/`)
- Une fois ton backend en ligne (hébergé quelque part) : remplace par
  l'adresse publique (ex: `https://tonapi.com/`)

## Pour aller plus loin

Le même `ApiService.kt` peut aussi servir à appeler les 4 API véhicules
(France/Europe/USA/Asie) exactement de la même façon que VROOM — regarde
les fonctions `rechercherParVin` et `rechercherParPlaque` déjà prêtes dedans.
