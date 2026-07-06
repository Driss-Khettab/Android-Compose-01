package com.example.profildeveloppeur

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.profildeveloppeur.ui.theme.ProfilDeveloppeurTheme

// Point d'entrée : setContent + thème du projet. L'innerPadding du Scaffold
// est un padding EXTERNE (le parent décide de l'espace autour de l'écran).
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProfilDeveloppeurTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProfilScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Écran principal : décide des données et du placement de la fiche.
@Composable
fun ProfilScreen(modifier: Modifier = Modifier) {
    ProfilCard(
        name = "Driss Khettab",
        role = "Développeur Android — Jetpack Compose",
        description = "Étudiant en informatique passionné par le développement mobile. " +
            "J'aime construire des interfaces propres et réutilisables avec Compose.",
        skills = listOf("Kotlin", "Jetpack Compose", "Android SDK", "Git", "POO"),
        availabilityText = "Disponible pour une alternance",
        contactButtonText = "Contacter",
        onContactClick = { /* callback vide : ouvrir un e-mail plus tard */ },
        onAvailabilityClick = { /* callback vide */ },
        // padding EXTERNE : marge entre la fiche et les bords de l'écran.
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

// Fiche de profil : reçoit toutes ses données en paramètres (rien de figé).
@Composable
fun ProfilCard(
    name: String,
    role: String,
    description: String,
    skills: List<String>,
    availabilityText: String,
    contactButtonText: String,
    onContactClick: () -> Unit,
    onAvailabilityClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 2.dp
    ) {
        // padding INTERNE : espace entre le contenu et les bords de la carte.
        Column(modifier = Modifier.padding(20.dp)) {
            ProfilHeader(name = name, role = role, modifier = Modifier.padding(bottom = 12.dp))
            ProfilDescription(description = description, modifier = Modifier.padding(bottom = 16.dp))

            SectionTitle(title = "Compétences", modifier = Modifier.padding(bottom = 6.dp))
            // On réutilise SkillItem pour chaque compétence.
            skills.forEach { skill ->
                SkillItem(skill = skill, modifier = Modifier.padding(bottom = 4.dp))
            }

            AvailabilityBadge(
                text = availabilityText,
                onClick = onAvailabilityClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 12.dp)
            )
            ContactButton(
                text = contactButtonText,
                onClick = onContactClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// En-tête : nom + rôle.
@Composable
fun ProfilHeader(name: String, role: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = name, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 4.dp))
        Text(text = role, style = MaterialTheme.typography.titleMedium)
    }
}

// Courte description.
@Composable
fun ProfilDescription(description: String, modifier: Modifier = Modifier) {
    Text(text = description, style = MaterialTheme.typography.bodyMedium, modifier = modifier)
}

// Titre de section (ex. "Compétences").
@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(text = title, style = MaterialTheme.typography.titleSmall, modifier = modifier)
}

// Une compétence.
@Composable
fun SkillItem(skill: String, modifier: Modifier = Modifier) {
    Text(text = "•  $skill", style = MaterialTheme.typography.bodyMedium, modifier = modifier)
}

// Zone cliquable personnalisée : accessibilité via Role.Button + contentDescription.
@Composable
fun AvailabilityBadge(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        // chaîne de modifiers : comportement (clic) puis accessibilité (semantics).
        modifier = modifier
            .clickable(role = Role.Button, onClick = onClick)
            .semantics { contentDescription = "$text. Appuyez pour en savoir plus." }
    ) {
        // padding INTERNE du badge.
        Text(text = text, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(12.dp))
    }
}

// Action : un vrai Button, déjà explicite pour l'accessibilité. Signale le clic.
@Composable
fun ContactButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = onClick, modifier = modifier) {
        Text(text = text)
    }
}

// Preview 1 : profil Android junior, disponible.
@Preview(showBackground = true, name = "Profil Android junior (disponible)")
@Composable
fun ProfilAndroidJuniorPreview() {
    ProfilDeveloppeurTheme {
        ProfilCard(
            name = "Driss Khettab",
            role = "Développeur Android — Jetpack Compose",
            description = "Étudiant en informatique passionné par le développement mobile. " +
                "J'aime construire des interfaces propres et réutilisables avec Compose.",
            skills = listOf("Kotlin", "Jetpack Compose", "Android SDK", "Git", "POO"),
            availabilityText = "Disponible pour une alternance",
            contactButtonText = "Contacter",
            onContactClick = {},
            onAvailabilityClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

// Preview 2 : profil full-stack, non disponible, compétences différentes.
@Preview(showBackground = true, name = "Profil full-stack (non disponible)")
@Composable
fun ProfilFullStackPreview() {
    ProfilDeveloppeurTheme {
        ProfilCard(
            name = "Amina Bernard",
            role = "Développeuse full-stack senior",
            description = "Développeuse full-stack avec 6 ans d'expérience. " +
                "Actuellement en poste, mais ouverte à des échanges techniques.",
            skills = listOf("TypeScript", "React", "Node.js", "PostgreSQL", "Docker"),
            availabilityText = "Poste actuel — non disponible",
            contactButtonText = "Voir le portfolio",
            onContactClick = {},
            onAvailabilityClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

// Preview 3 : l'écran principal complet.
@Preview(showBackground = true, name = "Écran principal complet")
@Composable
fun ProfilScreenPreview() {
    ProfilDeveloppeurTheme {
        ProfilScreen()
    }
}
