package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import beans.*;
import model.Utente;

public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Database database;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		
		String username = request.getParameter("userName");
		String password = request.getParameter("password");
		User u = database.getUser(username);
		if(u==null) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/pages/userDoesNotExist.jsp");
			try {
				dispatcher.forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
			
		} 
		String logged = (String)request.getSession().getAttribute("logged");
		
		if(u!=null) {
			RequestDispatcher dispatcher;
			//con questo if evito null pointer a runtime (che teoricamente non dovrebbe dare comunque problemi)
			if(logged==null || logged.equals("no")) {
				if(database.userAndPasswordCheck(username, password)) {
					
					if(u.getUsername().contains("admin")) {
						dispatcher = getServletContext().getRequestDispatcher("/pages/ViewAnalizzaLog.jsp");
					} else {
						request.getSession().setAttribute("logged", "yes");
						request.getSession().setAttribute("user", u);
						Utente utente=database.getUtente(username);
						request.getSession().setAttribute("utente", utente);

						
					}
				} else {
					dispatcher = getServletContext().getRequestDispatcher("/pages/ViewLogin.jsp");
					try {
						dispatcher.forward(request, response);
					} catch (ServletException | IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			if(u.getUsername().contains("admin")) {
				dispatcher = getServletContext().getRequestDispatcher("/pages/ViewAnalizzaLog.jsp");
			} else {
				
				dispatcher = getServletContext().getRequestDispatcher("/pages/HomeUtente.jsp");
			}
			try {
				dispatcher.forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void init() {
		 database = (Database)getServletContext().getAttribute("database");
		 
	}
}
