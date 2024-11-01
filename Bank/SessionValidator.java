package Bank;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionValidator {

    private HttpServletRequest request;

    public SessionValidator(HttpServletRequest request)
    {
        this.request = request;
    }

    public boolean isUserLoggedIn()
    {
        HttpSession session = request.getSession(false);  // Don't create a new session
        return session != null && session.getAttribute("accountnumber") != null && session.getAttribute("pinnumber") != null;
    }

    public String getSessionAccountNumber()
    {
        HttpSession session = request.getSession(false);
        return (session != null) ? (String) session.getAttribute("accountnumber") : null;
    }

    public String getSessionPin()
    {
        HttpSession session = request.getSession(false);
        return (session != null) ? (String) session.getAttribute("pinnumber") : null;
    }
}
