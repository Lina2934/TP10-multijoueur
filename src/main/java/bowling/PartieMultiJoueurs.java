package bowling;

import java.util.*;

public class PartieMultiJoueurs implements IPartieMultiJoueurs {
	private List<String> joueurs; // Liste des joueurs par ordre de jeu
	private Map<String, PartieMonoJoueur> parties; // Associe un joueur à sa partie
	private int indexJoueurCourant; // Indice du joueur en cours dans la liste des joueurs
	private boolean partieDemarree; // Indique si une partie est en cours

	/**
	 * Constructeur par défaut
	 */
	public PartieMultiJoueurs() {
		joueurs = new ArrayList<>();
		parties = new HashMap<>();
		indexJoueurCourant = 0;
		partieDemarree = false;
	}

	@Override
	public String demarreNouvellePartie(String[] nomsDesJoueurs) {
		if (nomsDesJoueurs == null || nomsDesJoueurs.length == 0) {
			throw new IllegalArgumentException("Il faut au moins un joueur !");
		}

		joueurs.clear();
		parties.clear();
		indexJoueurCourant = 0;
		partieDemarree = true;

		for (String joueur : nomsDesJoueurs) {
			joueurs.add(joueur);
			parties.put(joueur, new PartieMonoJoueur());
		}

		String joueur = joueurs.get(indexJoueurCourant);
		return "Prochain tir : joueur " + joueur + ", tour n° 1, boule n° 1";
	}

	@Override
	public String enregistreLancer(int nombreDeQuillesAbattues) {
		if (!partieDemarree) {
			throw new IllegalStateException("La partie n'est pas démarrée !");
		}

		String joueurCourant = joueurs.get(indexJoueurCourant);
		PartieMonoJoueur partie = parties.get(joueurCourant);

		boolean doitRejouer = partie.enregistreLancer(nombreDeQuillesAbattues);

		// Si la partie est terminée pour le joueur courant, passe au joueur suivant
		if (partie.estTerminee()) {
			indexJoueurCourant++;
			if (indexJoueurCourant >= joueurs.size()) {
				indexJoueurCourant = 0; // Retourne au premier joueur
			}
		}

		// Si tous les joueurs ont terminé, la partie est terminée
		if (tousLesJoueursOntTermine()) {
			return "Partie terminée";
		}

		// Affiche le message pour le prochain joueur et le prochain tour
		String prochainJoueur = joueurs.get(indexJoueurCourant);
		PartieMonoJoueur partieProchain = parties.get(prochainJoueur);
		int tourCourant = partieProchain.numeroTourCourant();
		int prochainLancer = partieProchain.numeroProchainLancer();

		return "Prochain tir : joueur " + prochainJoueur + ", tour n° " + tourCourant + ", boule n° " + prochainLancer;
	}

	@Override
	public int scorePour(String nomDuJoueur) {
		if (!parties.containsKey(nomDuJoueur)) {
			throw new IllegalArgumentException("Joueur inconnu");
		}
		return parties.get(nomDuJoueur).score();
	}

	/**
	 * Vérifie si tous les joueurs ont terminé leurs parties
	 *
	 * @return true si tous les joueurs ont terminé, false sinon
	 */
	private boolean tousLesJoueursOntTermine() {
		for (String joueur : joueurs) {
			if (!parties.get(joueur).estTerminee()) {
				return false;
			}
		}
		return true;
	}

}
