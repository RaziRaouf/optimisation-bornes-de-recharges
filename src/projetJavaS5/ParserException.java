package projetJavaS5;
/**
 * Exception personnalisée pour gérer les erreurs spécifiquement liées aux opérations de sauvegarde et de lecture de fichiers pour les Communautés.
 * 
 * @author Alicia TCHEMO, Mohamed Raouf RAZI
 */
public class ParserException extends Exception {
    private int codeErreur;

    /**
     * Constructeur avec message explicite et code d'erreur.
     *
     * @param message Le message d'erreur.
     * @param code Le code d'erreur associé.
     */
    public ParserException(String message, int code){
        super(message);
        codeErreur = code;
    }

    /**
     * Constructeur avec message explicite.
     *
     * @param message Le message d'erreur.
     */
    public ParserException(String message){
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