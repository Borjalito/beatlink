package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import beans.*;

public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Database database;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		request.getSession().invalidate();
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/pages/ViewLogin.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void init() {
		 database = (Database)getServletContext().getAttribute("database");
	}
}
