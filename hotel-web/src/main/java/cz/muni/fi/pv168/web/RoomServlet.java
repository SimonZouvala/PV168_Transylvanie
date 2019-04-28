package cz.muni.fi.pv168.web;

import cz.muni.fi.pv168.hotel.Room;
import cz.muni.fi.pv168.hotel.RoomManager;
import cz.muni.fi.pv168.hotel.exception.ServiceFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lýdie Hemalová {433757@mail.muni.cz}
 */
@WebServlet(RoomServlet.URL_MAPPING + "/*")
public class RoomServlet extends HttpServlet {

    public static final String URL_MAPPING = "/room";
    private static final String LIST_JSP = "/list_1.jsp";
 
    private final static Logger log = LoggerFactory.getLogger(RoomServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("GET ...");
        showRoomList(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //support non-ASCII characters in form
        request.setCharacterEncoding("utf-8");
        //action specified by pathInfo
        String action = request.getPathInfo();
        log.debug("POST ... {}",action);
        switch (action) {
            case "/add":
                //getting POST parameters from form
                String price = request.getParameter("price");
                String capacity = request.getParameter("capacity");
                String number = request.getParameter("number");
                //form data validity check
                if (price == null || price.length() == 0) {
                    request.setAttribute("chyba", "Je nutné vyplnit všechny hodnoty !");
                    log.debug("form data invalid - price is empty");
                    showRoomList(request, response);
                    return;
                }
                
                int price_int;
                try {
                    price_int = Integer.parseInt(price);
                } catch (NumberFormatException e) {
                    request.setAttribute("chyba", "Zadaná hodnota není validní číslo");
                    log.debug("form data invalid - price can not parse");
                    showRoomList(request, response);
                    return;
                } catch (NullPointerException e ) {
                    request.setAttribute("chyba", "Je nutné vyplnit všechny hodnoty !");
                    log.debug("form data invalid - price is empty");
                    showRoomList(request, response);
                    return;
                }          
                int capacity_int;
                try {
                    capacity_int = Integer.parseInt(capacity);
                } catch (NumberFormatException e) {
                    request.setAttribute("chyba", "Zadaná hodnota Kapacity není validní číslo");
                    log.debug("form data invalid - capacity can not parse");
                    showRoomList(request, response);
                    return;
                } catch (NullPointerException e ) {
                    request.setAttribute("chyba", "Je nutné vyplnit všechny hodnoty !");
                    log.debug("form data invalid - capacity is empty");
                    showRoomList(request, response);
                    return;
                }
                int number_int;
                try {
                    number_int = Integer.parseInt(number);
                } catch (NumberFormatException e) {
                    request.setAttribute("chyba", "Zadaná hodnota číslo pokoje není validní číslo");
                    log.debug("form data invalid - number can not parse");
                    showRoomList(request, response);
                    return;
                } catch (NullPointerException e ) {
                    request.setAttribute("chyba", "Je nutné vyplnit všechny hodnoty !");
                    log.debug("form data invalid - number is empty");
                    showRoomList(request, response);
                    return;
                }
                       
                
                //form data processing - storing to database
                try {
                    Room room = new Room(price_int, capacity_int, number_int);
                    getRoomManager().createRoom(room);
                    //redirect-after-POST protects from multiple submission
                    log.debug("redirecting after POST");
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (ServiceFailureException e) {
                    log.error("Cannot add room", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }
            case "/delete":
                try {
                    Long id = Long.valueOf(request.getParameter("id"));
                    Room room = getRoomManager().getRoom(id);
                    getRoomManager().deleteRoom(room);
                    log.debug("redirecting after POST");
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (ServiceFailureException e) {
                    log.error("Cannot delete room", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }
            case "/update":
                //TODO
                return;
            default:
                log.error("Unknown action " + action);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void showRoomList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            log.debug("showing table of rooms");
            request.setAttribute("room", getRoomManager().findAllRooms());
            request.getRequestDispatcher(LIST_JSP).forward(request, response);
        } catch (ServiceFailureException e) {
            log.error("Cannot show rooms", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
    private RoomManager getRoomManager() {
        return (RoomManager) getServletContext().getAttribute("roomManager");
    }
}
