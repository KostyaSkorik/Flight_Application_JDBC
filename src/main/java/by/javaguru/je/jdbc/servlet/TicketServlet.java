package by.javaguru.je.jdbc.servlet;

import by.javaguru.je.jdbc.service.TicketService;
import by.javaguru.je.jdbc.utils.JSPHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/tickets")
public class TicketServlet extends HttpServlet {
    private static final TicketService ticketService = TicketService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        if(req.getParameter("flightId")==null){
            req.setAttribute("tickets", null);
        }else {
            Long flightId = Long.valueOf(req.getParameter("flightId"));
            req.setAttribute("tickets", ticketService.findAllByFlightId(flightId));
        }

        req.getRequestDispatcher(JSPHelper.getPath("tickets")).forward(req, resp);

    }
}
