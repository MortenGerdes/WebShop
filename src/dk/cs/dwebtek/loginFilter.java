package dk.cs.dwebtek;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by morten on 2/26/17.
 */
public class loginFilter implements Filter
{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        if(request.getContextPath().contains("login"))
        {
            System.out.println(request.getContextPath());
            filterChain.doFilter(servletRequest, servletResponse);
        }

        boolean loggedIn = (session != null && session.getAttribute("Authed") != null);
        if(loggedIn)
        {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else
        {
            response.sendRedirect("http://localhost:8081/login.xhtml");
        }
    }

    @Override
    public void destroy()
    {

    }
}
