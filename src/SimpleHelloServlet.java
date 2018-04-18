import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/SimpleHelloServelet") // url pathern
public class SimpleHelloServlet extends HttpServlet {
    public static final String HELLO = "/hello-form.jsp";
    public static final String CUSTOM = "/custom-hello.jsp";
    public static final String PRENOM = "prenom";
    public static final String NOM = "nom";
    public static final String TIME = "time";
    public static final String ATT_ERREURS = "erreurs";
    public static final String ATT_RESULTAT = "resultat";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //j'affiche la page du formulaire
        this.getServletContext().getRequestDispatcher( HELLO ).forward( request, response );
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String resultat;
        Map<String, String> erreurs = new HashMap<String, String>();
        // je récupère les champs du formulaire
        String prenom = request.getParameter(PRENOM);
        String nom = request.getParameter(NOM);
        String time = request.getParameter(TIME);
        String result = traitementTime(time);
        request.setAttribute("result", result);

        try {
            validationNom(nom);
        } catch (Exception e) {
            erreurs.put(NOM, e.getMessage());
        }

        try {
            validationPrenom(prenom);
        } catch (Exception e) {
            erreurs.put(NOM, e.getMessage());
        }

        try {
            validationTime(time);
        } catch (Exception e) {
            erreurs.put(NOM, e.getMessage());
        }


        // initialisation du resultat global de la validation
        if (erreurs.isEmpty()) {
            resultat = "Bravo tes infos sont validées !";
        } else {
            resultat = "Vérifie tes infos stp";
        }

        /* Stockage du résultat et des messages d'erreur dans l'objet request */
        request.setAttribute(ATT_ERREURS, erreurs);
        request.setAttribute(ATT_RESULTAT, resultat);


        /* Transmission de la paire d'objets request/response à notre JSP */
        this.getServletContext().getRequestDispatcher(CUSTOM).forward(request, response);
    }

    public String traitementTime(String time) {
        String[] parts = time.split(":");
        String part1 = parts[0];
        int convertToInt = Integer.parseInt(part1);
        String result = "";
        if (convertToInt <= 12) {
            result = "Good morning";
        } else if (convertToInt > 12 && convertToInt < 20 ) {
            result = "Good afternoon";
        } else {
            result = "Good night";
        }

        return result;
    }

    private void validationNom(String nom) throws Exception {
        if (nom != null && nom.trim().length() < 3) {
            throw new Exception("Le nom doit contenir au moins 3 caractères.");
        }
    }

    private void validationPrenom(String prenom) throws Exception {
        if (prenom != null && prenom.trim().length() < 3) {
            throw new Exception("Le prenom doit contenir au moins 3 caractères.");
        }
    }

    private void validationTime(String time) {

    }
}
