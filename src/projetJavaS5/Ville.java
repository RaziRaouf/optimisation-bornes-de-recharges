package projetJavaS5;

/**
 * Représente une ville.
 * 
 * Cette classe permet de créer et gérer des objets représentant des villes.
 *
 * @author Alicia TCHEMO, Mohamed Raouf RAZI
 */

public class Ville {
    /**
     * Le nom de la ville.
     */
    private char nom;

    /**
     * L'id de la ville, soit son indice dans la matrice dans Communauté
     */
    private int id;
    
    /**
     * Un booléen indiquant si la zone de la ville est chargée.
     */
    private boolean zoneCharge;

    // Code ASCII pour la première lettre 'A'
    final int CODEASCII = 65;

    /**
     * Constructeur de la classe Ville avec nom et état de la zone.
     * 
     * @param nom        Le nom de la ville.
     * @param zoneCharge Un booléen indiquant si la zone est chargée.
     */
    public Ville(char nom, boolean zoneCharge) {
        this.nom = nom;
        this.zoneCharge = zoneCharge;
    }

    /**
     * Constructeur par défaut de la classe Ville.
     */
    public Ville() {
        //this("", false);
    }

    /**
     * Définit si la zone est chargée ou non.
     * 
     * @param zoneCharge Un booléen indiquant si la zone est chargée.
     */
    public void setZoneCharge(boolean zoneCharge) {
        this.zoneCharge = zoneCharge;
    }

    /**
     * Définit le nom de la ville.
     * 
     * @param nom Le nom de la ville.
     */
    public void setNom(char nom) {
        this.nom = nom;
    }

    /**
     * Obtient l'état de la zone (chargée ou non).
     * 
     * @return Vrai si la zone est chargée, sinon faux.
     */
    public boolean getZoneCharge() {
        return this.zoneCharge;
    }

    /**
     * Obtient le nom de la ville.
     * 
     * @return Le nom de la ville.
     */
    public char getNom() {
        return this.nom;
    }

    /**
     * Obtient l'identifiant de la ville dans la matrice.
     * 
     * @return L'identifiant de la ville dans la matrice.
     */
    public int getID() {
        return nom - CODEASCII;
        // return this.id;
    }

    /**
     * Retourne une représentation en chaine de charactères de la ville.
     * 
     * @return Le nom de la ville et l'état de la zone.
     */
    public String toString() {
        return nom + ": " + zoneCharge;
    }
}