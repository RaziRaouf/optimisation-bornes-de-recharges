package projetJavaS5;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Représente une communauté de plusieurs villes connecter
 * 
 * @author Mohamed Raouf RAZI, Alicia TCHEMO
 *
 */

public class Communaute {
	private List <Ville> villes;
	private boolean matrice [][];

	final int CODEASCII = 65;

	/**
	 * Constructeur de la classe Communaute.
	 *
	 * @param n Le nombre de villes dans la communauté.
	 * @throws IllegalArgumentException Si le nombre de villes est supérieur à 26.
	 */
	public Communaute(int n) throws IllegalArgumentException{
	    if (n > 26) throw new IllegalArgumentException("Le nombre de villes doit tre inf\u00E9rieur \u00E0 26");
	    if (n < 0) throw new IllegalArgumentException("Le nombre de villes doit \u00EAtre sup\u00E9rieur \u00E0 0");
        if (n == 0) System.out.println("Attention, l'agglom\u00E9ration ne contient aucune ville.");

        this.villes = new ArrayList<>();
        this.matrice = new boolean[n][n];
        initialisationMatrice(n);
        initialisationVilles(n);
	}


	/**
	 * Initialise la matrice des connexions entre les villes dans la matrice matrice.
	 *
	 * @param n Le nombre de villes dans la communauté.
	 */
	private void initialisationMatrice(int n) {
	    for(int i = 0; i < n; i++) {
	        for(int j = 0; j < n; j++) {
	            matrice[i][j] = false;
	        }
	    }
	}


	/**
	 * Initialise les villes de la communauté dans la liste villes.
	 *
	 * @param n Le nombre de villes dans la communauté.
	 */
	private void initialisationVilles(int n){
	    for (char i = 0; i < n; i++){
	        char nomVille = (char) (i + CODEASCII);
	        villes.add(new Ville(nomVille, true));
	    }
	}


	/**
	 * Renvoie la liste des villes de la communauté.
	 *
	 * @return La liste des villes.
	 */
	public List<Ville> getListeVilles(){
	    return this.villes;
	}

	/**
	 * Retourne une représentation en chaîne de caractères des noms de toutes les villes de la communauté.
	 *
	 * @return La chaîne de caractères représentant les noms des villes.
	 */
	public String toString(){
	    StringBuffer str = new StringBuffer();
	    for (Ville v : villes)
	        str.append(v.getNom()+" ");
	    return str.toString();
	}


	/**
	 * Renvoie la matrice associée à la communauté.
	 *
	 * @return La matrice.
	 */
	public boolean [][] getMatrice(){
	    return this.matrice;
	}


	/**
	 * Renvoie le nombre total de villes dans la communauté.
	 *
	 * @return Le nombre de villes.
	 */
	public int getNbVilles(){
	    return this.villes.size();
	}


	/**
	 * Affiche et renvoie les noms des villes de la communauté qui ont une zone de charge.
	 *
	 * @return La liste des villes avec une zone de charge.
	 */
	public List<Ville> getVillesZoneCharge(){
	    List<Ville> liste = new ArrayList<Ville>();
	    for (Ville v : villes){
	        if (v.getZoneCharge()){
	            liste.add(v);
	        }
	    }
	    return liste;
	}


	/**
	 * Affiche les noms des villes de la communauté ayant une zone de charge.
	 */
	public void afficherVillesZoneCharge(){
	    StringBuffer str = new StringBuffer();
	    for (Ville v : this.getVillesZoneCharge()){
	        str.append(v.getNom() + " ");
	    }
	    str.toString();
	    System.out.println(str);
	}


	/** 
	 * Renvoie la liste des voisins d'une ville donnée.
	 *
	 * @param v La ville pour laquelle on veut les voisins.
	 * @return La liste des voisins de la ville.
	 */
	public List<Ville> getVoisins(Ville v){
	    List<Ville> liste = new ArrayList<Ville>();
	    int n = v.getID();
	    for (int i = 0; i < villes.size(); i++){
	        if (matrice[n][i] == true){
	            Ville voisin = getVille(i);
	            liste.add(voisin);
	        }
	    }
	    return liste;
	}


	/** 
	 * Affiche les noms des voisins d'une ville donnée.
	 *
	 * @param ville La ville pour laquelle on veut afficher les voisins.
	 */
	public void afficherVoisins(Ville ville){
	    StringBuffer str = new StringBuffer();
	    for (Ville v : this.getVoisins(ville)){
	        str.append(v.getNom() + " ");
	    }
	    str.toString();
	    System.out.println(str);
	}


	/**
	 * Calcule le nombre de villes avec une zonne de charge.
	 *
	 * @return Le nombre de villes dont la zone est chargée.
	 */
	private int scoreCommunaute() {
	    int score = 0;
	    for (Ville ville : villes) {
	        if (ville.getZoneCharge()) {
	            score++;
	        }
	    }
	    return score;
	}


	/**
	 * Récupère une ville par son identifiant dans la matrice.
	 *
	 * @param id L'identifiant de la ville dans la matrice.
	 * @return La ville correspondant à l'identifiant.
	 * @throws NullPointerException Si la ville avec l'identifiant spécifié n'existe pas dans l'agglomération.
	 */
	public Ville getVille(int id) throws NullPointerException{
	    for (Ville v : villes){
	        if( v.getID() == id ) {
	            return v;
	        }
	    }
	    throw new NullPointerException("La ville avec l'id '"+id+"' n'existe pas dans l'agglom\u00E9ration.");
	}


	/**
	 * Récupère une ville par son nom.
	 *
	 * @param nomVille Le nom de la ville.
	 * @return La ville correspondant au nom.
	 * @throws NullPointerException Si la ville avec le nom spécifié n'existe pas dans l'agglomération.
	 */
	public Ville getVilleFromNom(char nomVille) throws NullPointerException{
	    for(Ville v : villes) {
	        if( nomVille == v.getNom() ) {
	            return v;
	        }
	    }
	    throw new NullPointerException("La ville '"+nomVille+"' n'existe pas dans l'agglom\u00E9ration.");
	}


	/**
	 * Récupère l'identifiant d'une ville par son nom.
	 *
	 * @param nomVille Le nom de la ville.
	 * @return L'identifiant de la ville dans la matrice.
	 * @throws NullPointerException Si la ville avec le nom spécifié n'existe pas dans l'agglomération.
	 */
	public int getIdFromNom(char nomVille) throws NullPointerException{
	    Ville v = this.getVilleFromNom(nomVille);
	    return v.getID();
	}


	/**
	 * Ajoute une route entre deux villes de la communauté.
	 *
	 * @param nomVilleA Le nom de la première ville.
	 * @param nomVilleB Le nom de la deuxième ville.
	 * @throws NullPointerException Si l'une des villes spécifiées n'existe pas dans l'agglomération.
	 * @throws IllegalArgumentException Si les noms des villes spécifiées sont identiques.
	 */
	public void ajouterRoute(char nomVilleA, char nomVilleB) throws NullPointerException, IllegalArgumentException{
	    if (nomVilleA == nomVilleB) throw new IllegalArgumentException("Saisie de clavier erron\u00E9e. Il est impossible d'ajouter une route entre une ville et elle-m\u00EAme.");
	    matrice[this.getIdFromNom(nomVilleA)][this.getIdFromNom(nomVilleB)] = true;
	    matrice[this.getIdFromNom(nomVilleB)][this.getIdFromNom(nomVilleA)] = true;

	    //System.out.println("ajouterRoute("+nomVilleA+", "+nomVilleB+") termin\u00E9");
	}


	/**
	 * Ajoute une zone de charge à la ville spécifiée.
	 *
	 * @param nomVille Le nom de la ville à laquelle ajouter une zone de charge.
	 * @throws VilleZCException Si la ville n'existe pas dans l'agglomération.
	 */
	public void ajouterZoneCharge(char nomVille) throws VilleZCException, NullPointerException{
		Ville villeCourante = getVilleFromNom(nomVille);

		if (villeCourante ==  null) throw new NullPointerException("La ville '"+nomVille+"' n'existe pas dans l'agglomeration.");
		if (villeCourante.getZoneCharge() == true) throw new VilleZCException("La ville '"+nomVille+"' a d\u00E9j\u00E0 une zone de charge");
		
		villeCourante.setZoneCharge(true);

		//System.out.println("ajouterZoneCharge("+nomVille+") termin\u00E9");
	}


	/**
	 * Retire la zone de charge de la ville spécifiée, en respectant les contraintes d'accessibilité.
	 *
	 * @param nomVille Le nom de la ville à laquelle retirer la zone de charge.
	 * @throws VilleZCException Si la ville n'existe pas dans l'agglomération.
	 */
	public void retirerZoneCharge(char nomVille) throws VilleZCException, NullPointerException{
		Ville villeCourante = getVilleFromNom(nomVille);

		if (villeCourante ==  null) throw new NullPointerException("La ville '"+nomVille+"' n'existe pas dans l'agglomeration.");
		if (villeCourante.getZoneCharge() == false) throw new VilleZCException("La ville '"+nomVille+"' n'a d\u00E9j\u00E0 pas de zone de charge");

		if (estAccessible(villeCourante)){
			villeCourante.setZoneCharge(false);
			//System.out.println("retirerZoneCharge("+nomVille+") termin\u00E9");
		}
	}


	/**
	 * Vérifie si une ville est accessible en fonction des contraintes d'accessibilité.
	 *
	 * @param ville La ville à vérifier.
	 * @return true si la ville est accessible, sinon false.
	 * @throws VilleZCException Si la condition d'accessibilité n'est pas respectée.
	 */
	private boolean estAccessible(Ville ville) throws VilleZCException{
		boolean nbZCVoisins;
		int id = ville.getID();
		int [] result = new int [matrice.length]; // result est un tableau avec -1 si index est avec une ville qui n'est pas voisine, 0 si il n'y a pas de ZC à proximité, <1 si il y a 1 ou plusieurs ZC
		ville.setZoneCharge(false);

		for (int i = 0; i < matrice[id].length; i++)
		{
			result[i] = -1;
			
			// Pour toutes les villes voisines de 'ville'...
			if (matrice[id][i] == true | i == id){
				result[i] = 0;

				// ... on véririfie si elle ou ses voisines ont une ZC
				if(getVille(i).getZoneCharge() == true)
					result[i] = 1; // la ville est connecté à une zone de charge voisine
				else{
					for (int j = 0; j < matrice[i].length; j++)
					{
						// Compte de toutes les ZC de chaque ville voisine de la ville voisine
						if (j != id && matrice[i][j] == true && getVille(j).getZoneCharge() == true)
							result[i] += 1;
					}
				}
			}
		}

		ville.setZoneCharge(true);

		// Recherche de tous les noms des villes qui n'ont pas de ZC
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < result.length; i++) if (result[i] == 0) str.append(getVille(i).getNom()+" ");

		nbZCVoisins = str.isEmpty();

		// Création de l'exception, si il existe des villes sans ZC
		if (!nbZCVoisins) throw new VilleZCException("La condition d'accessibilit\u00E9 n'est pas respecter pour la ville '"+ville.getNom()+"'. Ces villes pourrait ne plus avoir de ZC : "+str);

		return nbZCVoisins; // return true;
	}


	/**
	 *  Algorithme naïf introduit dans les consignes du sujet.
	 *
	 * @param k Le nombre de zones de recharge à sélectionner.
	 * @return La communaute après l'application de l'algorithme.
	 * @throws VilleZCException Si une erreur liée aux zones de recharge des villes survient.
	 * @throws NullPointerException Si une référence null est rencontrée.
	 * @throws IllegalArgumentException Si le paramètre k est invalide (k <= 0).
	 */
	public Communaute algorithmeNaif(int k) throws IllegalArgumentException, NullPointerException, VilleZCException{
		if(k<=0) {
			throw new IllegalArgumentException("Param\u00E8tre de l'algorithme na\u00EFf invalide");
		}
		else {
			Random random=new Random();
			for(int i=0;i<k;i++) {
				int nbr=random.nextInt(villes.size());
				Ville villeAleatoire=villes.get(nbr);
				if(villeAleatoire.getZoneCharge()) {
					try {
						this.retirerZoneCharge(villeAleatoire.getNom());
					}catch(VilleZCException e){
						// ne rien faire
					}

				}
				else {
					this.ajouterZoneCharge(villeAleatoire.getNom());
				}
			}
		}
		return this;
	}


	/**
	 * Algorithme d'approximation introduit dans les consignes du sujet.
	 *
	 * @param k Le nombre d'itérations pour l'algorithme.
	 * @return La communaute après l'application de l'algorithme.
	 * @throws VilleZCException Si une erreur liée aux zones de recharge des villes survient.
	 * @throws NullPointerException Si une référence null est rencontrée.
	 */
	public Communaute algorithmeApproximation(int k) throws NullPointerException, VilleZCException {
		Communaute com = this;
		int i = 0;
		int scoreCourant = this.scoreCommunaute();
		while (i<k) {
			Ville villeAleatoire = this.choisirVilleAleatoire();
			boolean zoneChargeInitiale = villeAleatoire.getZoneCharge();

			if (zoneChargeInitiale) {
				try{
					com.retirerZoneCharge(villeAleatoire.getNom());
				}catch(VilleZCException e){
						// ne rien faire
				}
			} else {
				com.ajouterZoneCharge(villeAleatoire.getNom());
			}

			int nouveauScore = scoreCommunaute();

			if (nouveauScore < scoreCourant) {
				// Si le nouveau score est meilleur, réinitialiser le compteur
				i = 0;
				scoreCourant = nouveauScore;
			} else {
				// Sinon, incrémenter le compteur d'itérations sans amélioration
				i++;
				// Revenir à l'état initial en cas de non-amélioration
				if (zoneChargeInitiale == false){
					com.retirerZoneCharge(villeAleatoire.getNom());
				}
			}
		}
		return com;
	}


	/**
	 * Choisi une ville de manière aléatoire en prenant en compte les villes avec une zone de recharge.
	 *
	 * @return La ville choisie aléatoirement.
	 * @throws NullPointerException Si une référence null est rencontrée.
	 */
	private Ville choisirVilleAleatoire() {
		Random random = new Random();

		// La probabilité de choisir une ville avec une zone de recharge est plus élevée (par exemple, 70%)
		if (random.nextDouble() < 0.7) {
			List<Ville> villesAvecZoneCharge = villes.stream()
			.filter(Ville::getZoneCharge)
			.collect(Collectors.toList());

			if (!villesAvecZoneCharge.isEmpty()) {
				int indexVille = random.nextInt(villesAvecZoneCharge.size());
				return villesAvecZoneCharge.get(indexVille);
			}
		}

		// Si aucune ville avec une zone de recharge n'est trouvée ou selon la probabilité
		int indexVille = random.nextInt(villes.size());

		return villes.get(indexVille);
	}
}