package view;

import edu.uniandes.ecos.model.Simpson;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        
        // les coloque mas valores 4, por q tengo por el momento un constructor con 3 parametros.
        Simpson calculos = new Simpson(6, 0.2, 1, 1);
        System.out.println("integral Definitiva:" + calculos.ObtenerParameterX());
        Simpson calculoss = new Simpson(15, 0.45, 1,1);
        System.out.println("integral Definitiva:" + calculoss.ObtenerParameterX());
        Simpson calculosss = new Simpson(4, 0.495, 4,1);
        System.out.println("integral Definitiva:" + calculosss.ObtenerParameterX());
    }
}
