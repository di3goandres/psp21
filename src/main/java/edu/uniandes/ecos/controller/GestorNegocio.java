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
package edu.uniandes.ecos.controller;

import edu.uniandes.ecos.model.Simpson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Diego Andres Montealegre Garcia
 * @version 1.1, 03/02/05
 * @since 1.0
 */
public class GestorNegocio {

    /**
     * En el constructor de la clase negocio, instanciamos la clase simpson para
     * generar los calculos correspondientes
     *
     * @param parameterX parametro de tipo double
     * @param gradosLibertad parametro de tipo double
     * @param numSegmentos parametro de tipo double
     * @return una instancia de la clase Simpson
     */
    public Simpson GestorNegocio(double parameterX, double gradosLibertad, double numSegmentos) {
        Simpson calculotIntegral = new Simpson(gradosLibertad, parameterX);
        return calculotIntegral;
    }

    /**
     * Metodo que muestra en web la pantalla de inicio
     *
     * @param req parametro tipo HttpServletRequest
     * @param resp parametro tipo HttpServletResponse
     * @throws ServletException lanza una excepcion de este tipo
     * ServletException
     * @throws IOException lanza una excepcion de este tipo IOException
     */
    public static void showHome(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PrintWriter pw = resp.getWriter();
        pw.write("<html>");
        pw.println("<h1>Taller No 6 - PSP 2</h1>");
        pw.println("<p>ECOS 2015 <br>Diego Andres Montealegre Garcia</p>");
        pw.write("<html>");
        pw.println("<br>los decimales con punto (10.9), no con coma<br>");
        pw.write("<hr>");
        pw.write("<form action=\"calc\" method=\"post\">"
                + "<table>"
                + "<tr><td>Parametro P: </td><td><input type=\"text\" name=\"parameterP\"  value=\"\"> </td></tr>"
                + "<tr><td>Grados de libertad(dof): </td><td><input type=\"text\" name=\"parameterDOF\"  value=\"\"> </td></tr>"
                + "</table> <input type=\"submit\" value=\"realizar calculos\">"
                + "</form> ");
        pw.println("<h2></h2>");
        pw.write("</html>");

    }

    /**
     * Metodo para mostrar en la pantalla web el resultado de los calculos
     *
     * @param req parametro tipo HttpServletRequest
     * @param resp parametro tipo HttpServletResponse
     * @param resultado instancia de la clase Simpson
     * @throws ServletException lanza una excepcion de este tipo
     * ServletException
     * @throws IOException lanza una excepcion de este tipo IOException
     */
    public static void showResults(HttpServletRequest req, HttpServletResponse resp,
            Simpson resultado)
            throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.getWriter().println("<hr>");
        resp.getWriter().println("<h2>Resultados</h2>");

        pw.write("<table  border=\"1\">"
                + "<tr><th>Resultados</th><th>Test Utilizados</th></th>"
                + "<tr><td>"
                + "<table>"
                + "<tr><td>0 to X: </td><td>" + resultado.ObtenerParameterX() + " </td></tr>"
                + "<tr><td>Grados de libertad (dof): </td><td>" + resultado.ObtenerDof() + "</td></tr>"
              
                + "</table></td> "
                + "<td>"
                + "<table border=\"1\">"
                + "<tr><th>P: </th><th>Grados de libertad (dof): </th><th>Valor esperado x</th></tr>"
                + "<tr><td>0.20</td><td>6</td><td>0.55338</td></tr>"
                + "<tr><td>0.45</td><td>15</td><td>1.75305</td></tr>"
                + "<tr><td>0.495</td><td>4</td><td>4.60409</td></tr>"
                + "</table> "
                + "</td></tr>"
                + "</table> "
        );
        resp.getWriter().println("<hr>");

    }

    /**
     * Metod que en caso de error es ejecutado
     *
     * @param req parametro tipo HttpServletRequest
     * @param resp parametro tipo HttpServletResponse
     * @throws ServletException lanza una excepcion de este tipo
     * ServletException
     * @throws IOException lanza una excepcion de este tipo IOException
     */
    public static void error(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getWriter().println("Error!!!");
    }
}
