/*
 * Copyright (C) 2015 Diego Andres Montealegre Garcia
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package edu.uniandes.ecos.view;

import edu.uniandes.ecos.controller.GestorNegocio;
import edu.uniandes.ecos.model.Simpson;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


/**
 *
 * @author Diego Andres Montealegre Garcia
 * @version 1.1, 03/02/05
 * @since 1.0
 */
public class App extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Simpson datos = new Simpson();
        GestorNegocio.showHome(req, resp);
        GestorNegocio.showResults(req, resp, datos);

    }

    public void consoleInput(HttpServletRequest req, HttpServletResponse resp) throws Exception {
       
        Double parameterP = Double.parseDouble(req.getParameter("parameterP"));
        Double parameterDof = Double.parseDouble(req.getParameter("parameterDOF"));
        
        GestorNegocio gestorNegocio = new GestorNegocio();
       

        Simpson resultado = gestorNegocio.GestorNegocio( parameterP, parameterDof,10);
        GestorNegocio.showResults(req, resp, resultado);
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(Integer.valueOf(System.getenv("PORT")));
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new App()), "/*");
        server.start();
        server.join();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            GestorNegocio.showHome(req, resp);
            consoleInput(req, resp);
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
