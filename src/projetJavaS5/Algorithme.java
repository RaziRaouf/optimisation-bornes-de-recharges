package projetJavaS5;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

/**
 * Classe avec la fonction main
 * 
 * @author Mohamed Raouf RAZI, Alicia TCHEMO
 *
 */


public class Algorithme{

	public static final int K_CONSTANTE = 5;
	public static final String FILE_PATH = "saves\\";

	public static void main(String [] args){
		//System.setProperty("file.encoding", "UTF-8");
		mainPhase2(args);
		//resolutionManuelle(null);
	}

	public static void mainPhase2(String [] args) {
		System.out.println("--- DEBUT ---");
		Communaute agglomeration = null;
		try{
			if (args.length > 0) agglomeration = ParserCommunaute.parser(args[0]); // FILE_PATH + args[0]
		}catch (ParserException e){
			System.out.println("Erreur/Op\u00E9ration annul\u00E9e : " + e.getMessage());
			System.exit(1);
		}

		Scanner sc = new Scanner(System.in);
		int choix = -1;

		do {
			try{
			affichageMenu();
			choix = sc.nextInt();

			switch (choix) {
			case 0:
				// fin
				break;
			case 1:
				agglomeration = resolutionManuelle(agglomeration);
				break;
			case 2:
				if (agglomeration != null) agglomeration = resolutionAutomatique(agglomeration);
				else System.out.println("Il n'y a pas d'agglom\u00E9ration entr\u00E9e pour la r\u00E9solution automatique.");
				break;

			case 3:
				if (agglomeration != null) sauvegarder(agglomeration);
				else System.out.println("Il n'y a pas d'agglom\u00E9ration entr\u00E9e pour la sauvegarde.");
				break;

			default:
				System.out.println("Erreur : l'option " + choix + " n'est pas valable.");
			}
			
			}catch(ParserException e){
				System.out.println("Erreur/Op\u00E9ration annul\u00E9e : " + e.getMessage());
			}

		} while (choix != 0);

		sc.close();

		System.out.println("---- FIN ----");
	}

	public static void sauvegarder(Communaute agglomeration) throws ParserException{
		Scanner sc = new Scanner(System.in);
		String nomFichier = null;
		do{
			affichageSaisieFichier();
			nomFichier = FILE_PATH + sc.next();
		}while (nomFichier == null || nomFichier.isEmpty());
		ParserCommunaute.save(agglomeration, nomFichier);

		//sc.close();
		System.out.println("Sauvegarde effectu\u00E9e");
	}

	public static Communaute resolutionAutomatique(Communaute agglomeration){
		Scanner sc = new Scanner(System.in);
		int choix = -1;
		int nbVilles = 0;

		if (agglomeration != null) do {
			try{
			affichageResolution();
			choix = sc.nextInt();

			switch (choix) {
			case 0:
				// fin
				break;
			case 1:
				nbVilles = agglomeration.getNbVilles();
				agglomeration.algorithmeApproximation(nbVilles);
				break;
			case 2:
				nbVilles = agglomeration.getNbVilles();
				agglomeration.algorithmeNaif(nbVilles);
				break;

			default:
				System.out.println("Erreur : l'option " + choix + " n'est pas valable.");
			}
			
			}catch(NullPointerException e){
				System.out.println("Erreur/Op\u00E9ration annul\u00E9e : " + e.getMessage());
			}catch(VilleZCException e){
				System.out.println("Erreur/Op\u00E9ration annul\u00E9e : " + e.getMessage());
				// Erreur dans algorithmeApproximation() ou algorithmeNaif()
			}

			agglomeration.afficherVillesZoneCharge();
			System.out.println("R\u00E9solution automatique effectu\u00E9e");

		} while (choix != 0);

		//sc.close();

		return agglomeration;
	}

	public static Communaute resolutionManuelle(Communaute agglomeration){
		Scanner sc = new Scanner(System.in);
		
		final int CODEASCII = 65;
		int choix = -1;
		int nbVilles = 0;
		char ville, ville1, ville2 = '\u0000';

		if (agglomeration == null) do{
			try{
			System.out.println("Initialisation du nombre de ville (< 26): ");
			nbVilles = sc.nextInt();
			agglomeration = new Communaute(nbVilles);

			}catch(IllegalArgumentException e){
				System.out.println("Erreur/Op\u00E9ration annul\u00E9e : " + e.getMessage());
				nbVilles = -1;
			}
			catch(InputMismatchException e){
				// Exception throws de nextInt() 
				System.out.println("Erreur/Op\u00E9ration annul\u00E9e : Veuillez entrer un entier");
				System.exit(1);
			}
			catch(NoSuchElementException e){
				// Exception throws de nextInt() et next()
				System.out.println("Erreur/Op\u00E9ration annul\u00E9e : Veuillez entrer juste un entier");
				//System.exit(1);
				nbVilles = -1;
			}
			catch(IllegalStateException e){
				// Exception throws de nextInt() et next()
				System.out.println("Erreur/Op\u00E9ration annul\u00E9e : Flux de saisie ferm\u00E9 (re d\u00E9marrer le programme)");
				System.exit(1);
			}
		}while(nbVilles == -1);

		/* ETAPE 1 */
		do {
			try{
			choix = -1;
			affichageEtape1();
			choix = sc.nextInt();

			switch (choix) {
			case 0:
				// fin
				break;
			case 1:
				affichageSaisieVille();
				ville1 = stringToChar(sc.next());
				affichageSaisieVille();
				ville2 = stringToChar(sc.next());
				agglomeration.ajouterRoute(ville1, ville2);
				break;

			default:
				System.out.println("Erreur : l'option " + choix + " n'est pas valable.");
			}
			
			}catch(NullPointerException e){
				System.out.println("Erreur/Op\u00E9ration annul\u00E9e : " + e.getMessage());
			}
			catch(IllegalArgumentException e){
				System.out.println("Erreur/Op\u00E9ration annul\u00E9e : " + e.getMessage());
			}
			catch(InputMismatchException e){
				// Exception throws de nextInt()
				System.out.println("Erreur/Op\u00E9ration annul\u00E9e : Veuillez entrer un entier");
				choix = -1;
			}
			catch(NoSuchElementException e){
				// Exception throws de nextInt() et next()
				System.out.println("Erreur/Op\u00E9ration annul\u00E9e : Veuillez entrer juste un entier");
				choix = -1;
			}
			catch(IllegalStateException e){
				// Exception throws de nextInt() et next()
				System.out.println("Erreur/Op\u00E9ration annul\u00E9e : Flux de saisie ferm\u00E9 (re d\u00E9marrer le programme)");
				choix = -1;
			}

		} while (choix != 0 | choix == -1);

		/* ETAPE 2 */
		do {
			try{
			choix = -1;
			affichageEtape2();
			choix = sc.nextInt();

			switch (choix) {
			case 0:
				// fin
				break;
			case 1:
				affichageSaisieVille();
				ville = stringToChar(sc.next());
				agglomeration.ajouterZoneCharge(ville);
				agglomeration.afficherVillesZoneCharge();
				break;
			case 2:
				affichageSaisieVille();
				ville = stringToChar(sc.next());
				agglomeration.retirerZoneCharge(ville);
				agglomeration.afficherVillesZoneCharge();
				break;

			default:
				System.out.println("Erreur/Op\u00E9ration annul\u00E9e : l'option " + choix + " n'est pas valable.");
			}

			}catch(VilleZCException e){
				System.out.println("Erreur/Op\u00E9ration annul\u00E9e : " + e.getMessage());
			}
			catch(IllegalArgumentException e){
				System.out.println("Erreur/Op\u00E9ration annul\u00E9e : " + e.getMessage());
			}
			catch(NullPointerException e){
				System.out.println("Erreur/Op\u00E9ration annul\u00E9e : " + e.getMessage());
			}
			catch(InputMismatchException e){
				// Exception throws de nextInt()
				System.out.println("Erreur/Op\u00E9ration annul\u00E9e : Veuillez entrer un entier");
				choix = -1;
			}
			catch(NoSuchElementException e){
				// Exception throws de nextInt() et next()
				System.out.println("Erreur/Op\u00E9ration annul\u00E9e : Veuillez entrer juste un entier");
				choix = -1;
			}
			catch(IllegalStateException e){
				// Exception throws de nextInt() et next()
				System.out.println("Erreur/Op\u00E9ration annul\u00E9e : Flux de saisie ferm\u00E9 (re d\u00E9marrer le programme)");
				choix = -1;
			}

		} while (choix != 0 | choix == -1);

		return agglomeration;
		//sc.close();
	}

	public static char stringToChar(String text) throws IllegalArgumentException{
		if (text.length() > 1 || text.isEmpty()) throw new IllegalArgumentException("Saisie de clavier erron\u00E9e. Veuillez \u00E9crire une seule lettre.");
		return text.charAt(0);
	}

	public static void affichageMenu() {
		System.out.println("Programme : Que souhaitez vous faire ?");
		System.out.println("0 Fin");
		System.out.println("1 R\u00E9soudre manuellement");
		System.out.println("2 R\u00E9soudre automatiquement");
		System.out.println("3 Sauvegarder");
	}

	public static void affichageEtape1() {
		System.out.println("Etape 1 : Que souhaitez vous faire ?");
		System.out.println("0 Fin");
		System.out.println("1 Ajouter une route");
	}

	public static void affichageEtape2() {
		System.out.println("Etape 2 : Que souhaitez vous faire ?");
		System.out.println("0 Fin");
		System.out.println("1 Ajouter une zone de recharge");
		System.out.println("2 Retirer une zone de recharge");
	}

	public static void affichageResolution(){
		System.out.println("Choisir type de r\u00E9solution : Que souhaitez vous faire ?");
		System.out.println("0 Fin");
		System.out.println("1 Algorithme Na\u00EFf");
		System.out.println("2 Algorithme Approximatif");
	}

	public static void affichageSaisieVille(){
		System.out.println("Saisir le nom de la Ville (une seule lettre) :");
		System.out.print("--> ");
	}

	public static void affichageSaisieFichier(){
		System.out.println("Saisir le nom du fichier pour la sauvegarde (exemple.ca) :");
		System.out.print("--> ");
	}

}