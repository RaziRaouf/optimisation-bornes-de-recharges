package projetJavaS5;

/**
 * Exception personnalisée pour les erreurs liées aux zones de charges (ZC) d'une ville.
 * 
 * @author Alicia TCHEMO, Mohamed Raouf RAZI
 */

public class VilleZCException extends Exception {
    private int codeErreur;

    /**
     * Constructeur avec message explicite et code d'erreur.
     *
     * @param message Le message d'erreur.
     * @param code Le code d'erreur associé.
     */
    public VilleZCException(String message, int code){
        super("Zone Charge - " + message);
        codeErreur = code;
    }

    /**
     * Constructeur avec message explicite.
     *
     * @param message Le message d'erreur.
     */
    public VilleZCException(String message){
        this(message, -1);
    }

    /**
     * Récupère le code d'erreur associé à l'exception.
     *
     * @return Le code d'erreur.
     */
    public int getCodeErreur(){
        return codeErreur;
    }
}