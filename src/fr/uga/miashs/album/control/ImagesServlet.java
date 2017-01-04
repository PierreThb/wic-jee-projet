package fr.uga.miashs.album.control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/images/*")
public class ImagesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public ImagesServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	    
		String filename = request.getPathInfo().substring(1);
    	System.out.println("serving image : "+filename);
        File file = new File(getServletContext().getInitParameter("directory"), filename);
        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
	}

}