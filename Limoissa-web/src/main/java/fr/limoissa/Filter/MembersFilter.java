/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.limoissa.Filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Adrien
 */
public class MembersFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpSession Session = ((HttpServletRequest)request).getSession();
        
        fr.limoissa.Model.Member M = (fr.limoissa.Model.Member)Session.getAttribute("Member");        
        
        /* Si l'utilisateur n'est pas connecté */
        if(null == M)
            ((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath()+"?page=Signin");
        else
        {
            /* S'il tente d'accéder à une page admin */
            if(null != request.getParameter("page") && !M.IsAdmin())
                ((HttpServletResponse)response).sendRedirect("Library");
            else
                chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        
    }
    
}
