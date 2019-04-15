package cz.muni.fi.pv168.web;

import cz.muni.fi.pv168.hotel.GuestManager;
import cz.muni.fi.pv168.hotel.exception.ServiceFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Servlet for managing hotel
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */
@WebServlet(HotelServlet.URL_MAPPING + "/*")
public class HotelServlet extends HttpServlet {

    public static final String URL_MAPPING = "/";
    private static final String LIST_JSP = "/list.jsp";
 
    private final static Logger log = LoggerFactory.getLogger(HotelServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("GET ...");
        showGuestList(request, response);
    }

    private void showGuestList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            log.debug("showing table of guest");
            request.setAttribute("guests", getGuestManager().findAllGuest());
            request.getRequestDispatcher(LIST_JSP).forward(request, response);
        } catch (ServiceFailureException e) {
            log.error("Cannot show guests", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private GuestManager getGuestManager() {
        return (GuestManager) getServletContext().getAttribute("guestManager");
    }
}
