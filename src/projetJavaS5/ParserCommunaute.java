package projetJavaS5;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

/**
 * Classe pour la sauvegarde et la lecture de fichier pour la transcription d'une Communauté en fichier texte.
 * 
 * Format à respecter pour les fichiers.ca :
 * ville(A)
 * route(A,B)
 * recharge(A)
 * 
 * @author Mohamed Raouf RAZI, Alicia TCHEMO
 */


public class ParserCommunaute {

    /**
     * Cree une Communaute a partir d'un fichier texte
     * 
     * @param file le chemin vers le fichier
     * @return la Communaute qui est decrit par file
     */
    public static Communaute parser(String file) throws ParserException{
        String line = null;
        Communaute agglo = null;

        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
 
            List <Ville> rechargeList = new ArrayList <Ville>();
            int n = 0;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ville(")) {
                    String [] splitedLine = line.split("\\(|\\)");
                    char nomVille = Algorithme.stringToChar(splitedLine[1]); //String nomVille = splitedLine[1]; (char -> String)
                    n++;
                } else if (line.startsWith("route(")) {
                    if (n == 0) throw new ParserException("La ligne est incorrecte '" + line + "' Le format n'est pas respect\u00E9. route() doit \u00EAtre apr\u00E8s ville() et avant recharge()");
                    if (agglo == null) agglo = new Communaute(n);
                    
                    String [] splitedLine = line.split("\\(|\\)");
                    String [] nomVilles = splitedLine[1].split(",");
                    char nomVilleA = Algorithme.stringToChar(nomVilles[0]);
                    char nomVilleB = Algorithme.stringToChar(nomVilles[1]);
                    agglo.ajouterRoute(nomVilleA, nomVilleB);
                }
                else if (line.startsWith("recharge(")) {
                    if (n == 0) throw new ParserException("La ligne est incorrecte '" + line + "' Le format n'est pas respect\u00E9. recharge() doit \u00EAtre apr\u00E8s ville() et route()");
                    String [] splitedLine = line.split("\\(|\\)");
                    char nomVille = Algorithme.stringToChar(splitedLine[1]);
                    Ville v = agglo.getVilleFromNom(nomVille);
                    rechargeList.add(v);
                }
                else {
                    throw new ParserException("La ligne est incorrecte '" + line + "' Le format n'est pas respect\u00E9.");
                }
            }

            reader.close();

            if (!rechargeList.isEmpty()){
                for (Ville v : agglo.getListeVilles()){
                    if (!rechargeList.contains(v))
                            agglo.retirerZoneCharge(v.getNom());
                }
            }

        } catch (FileNotFoundException e) {
            throw new ParserException("Le fichier n'a pas \u00E9t\u00E9 trouv\u00E9. Relancer le programme.");
        } catch (IOException e) {
            throw new ParserException("Probl\u00E8me entr\u00E9e/sortie. Relancer le programme.");
        }catch(IllegalArgumentException e){
            throw new ParserException("La ligne est incorrecte '" + line + "'. Le format du fichier n'est pas respect\u00E9.");
        }catch(NullPointerException e){
            throw new ParserException("La ligne est incorrecte '" + line + "'. Le format du fichier n'est pas respect\u00E9. Il n'y a pas de route entre les villes.");
        }catch(ArrayIndexOutOfBoundsException e){
            throw new ParserException("La ligne est incorrecte '" + line + "' Le format du fichier n'est pas respect\u00E9. exemple: ville(A) route(A,B) recharge(A)");
        }catch(VilleZCException e){
            throw new ParserException("Probl\u00E8me avec l'agglomeration du fichier. "+e.getMessage());
        }

        //System.out.println("fonction parser(String file): complete");
        return agglo;
    }


    /**
    * Permet de sauvegarder une Communaute dans un fichier texte, en incluant les informations sur les villes, les liaisons entre celles-ci, ainsi que les emplacements des zones de recharge.
    * 
    * @param agglo la Communaute qui doit être sauvegardé
    * @param file le chemin du fichier
    */
    public static void save(Communaute agglo, String file) throws ParserException {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            
            // Ecriture des ville()
            List <Ville> villes = agglo.getListeVilles();
            for (Ville ville : villes) {
                writer.write("ville(" + ville.getNom() + ").");
                writer.newLine();
            }

            // Ecriture des route()
            List <String> visited = new ArrayList<>();

            for (Ville v : villes){
                char nomVilleA = v.getNom();

                List<Ville> villesVoisins = agglo.getVoisins(v);
                for (Ville voisin : villesVoisins){
                    char nomVilleB = voisin.getNom();
                    String route1 = nomVilleA + "," + nomVilleB;
                    String route2 = nomVilleB + "," + nomVilleA;

                    if (!visited.contains(route1) || !visited.contains(route2)) {
                        writer.write("route(" + nomVilleA + "," + nomVilleB + ").");
                        writer.newLine();
                        visited.add(route1);
                        visited.add(route2);
                    }
                }
            }

            // Ecriture des recharge()
            List <Ville> villesZC = agglo.getVillesZoneCharge();
            for (Ville v : villesZC){
                writer.write("recharge(" + v.getNom() + ").");
                writer.newLine();
            }

            writer.close();

            //System.out.println("fonction save(Communaute agglo, String file): complete");

        } catch (IOException e) {
            throw new ParserException("Probl\u00E8me entr\u00E9e/sortie. Recommencez.");
        }
    }
}