package fr.uga.miashs.album.control;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.uga.miashs.album.util.Pages;

@WebFilter("*.xhtml")
public class LoginFilter implements Filter {

	
	public String[] publicPages;
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }
    
	public void init(FilterConfig fConfig) throws ServletException {
		publicPages = new String[] {
				Pages.login
		};
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		String requestedUri = ((HttpServletRequest) request).getRequestURI();
		boolean isPublic = false;
		
		for (String page : publicPages) {
			if (requestedUri.contains(page) || requestedUri.contains("javax.faces.resource")) {
				isPublic = true;
			}
		}
		
		if(!isPublic){
			HttpSession session = ((HttpServletRequest) request).getSession(false);
			AppUserSession userSession = null;
			if(session != null){
				userSession = (AppUserSession) session.getAttribute("appUserSession");
			}
			
			if(session == null || userSession == null || userSession.getConnectedUser() == null){
				HttpServletResponse res = (HttpServletResponse) response;
				res.sendRedirect(((HttpServletRequest) request).getContextPath() + "/login.xhtml");
			}
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}



}
