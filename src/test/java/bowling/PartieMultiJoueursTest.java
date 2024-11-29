package bowling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartieMultiJoueursTest {

	@Test
	void testDemarreNouvellePartieNominal() {
		PartieMultiJoueurs partie = new PartieMultiJoueurs();
		String message = partie.demarreNouvellePartie(new String[]{"Alice", "Bob", "Charlie"});

		assertEquals("Prochain tir : joueur Alice, tour n° 1, boule n° 1", message);
	}

	@Test
	void testDemarreNouvellePartieAvecTableauVide() {
		PartieMultiJoueurs partie = new PartieMultiJoueurs();
		assertThrows(IllegalArgumentException.class, () -> partie.demarreNouvellePartie(new String[]{}));
	}

	@Test
	void testDemarreNouvellePartieAvecNull() {
		PartieMultiJoueurs partie = new PartieMultiJoueurs();
		assertThrows(IllegalArgumentException.class, () -> partie.demarreNouvellePartie(null));
	}

	@Test
	void testScorePourNominal() {
		PartieMultiJoueurs partie = new PartieMultiJoueurs();
		partie.demarreNouvellePartie(new String[]{"Alice"});

		// Alice joue : un strike, suivi de 3 quilles et 5 quilles
		partie.enregistreLancer(10); // Strike
		partie.enregistreLancer(3);
		partie.enregistreLancer(5);

		// Score attendu : 10 (strike) + (3+5) + 3 + 5 = 26
		assertEquals(26, partie.scorePour("Alice"));
	}

	@Test
	void testScorePourJoueurInexistant() {
		PartieMultiJoueurs partie = new PartieMultiJoueurs();
		partie.demarreNouvellePartie(new String[]{"Alice"});

		// "Bob" ne fait pas partie de la partie
		assertThrows(IllegalArgumentException.class, () -> partie.scorePour("Bob"));
	}

	@Test
	void testEnregistreLancerSansDemarrerPartie() {
		PartieMultiJoueurs partie = new PartieMultiJoueurs();

		// On tente d'enregistrer un lancer alors qu'aucune partie n'a commencé
		assertThrows(IllegalStateException.class, () -> partie.enregistreLancer(5));
	}
}
