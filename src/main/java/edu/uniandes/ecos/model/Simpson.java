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
package edu.uniandes.ecos.model;

/**
 *
 * @author Diego Andres Montealegre Garcia
 * @version 1.1, 03/02/05
 * @since 1.0
 */
public class Simpson {

    /**
     * variable que guardara los grados de libertad
     */
    public double dof = 0d;

    /**
     * variable que guardara el valor del parametro X ingresado
     */
    public double parameterX = 0d;
    /**
     * variable que guardara el valor del parametro E (error minimo)
     */
    private double parameterE;
    /**
     * variable que guardara el valor del numero de segmentos
     */
    private double num_seg;
    /**
     * variable que guardara el valor del parametro W
     */
    private double parameterW;
    /**
     * variable que guardara el valor de la integral inicial
     */
    private double integralInicialP;
    /**
     * variable que guardara el valor de la integral definitiva
     */
    private double integralDefinitiva;

    private double integralComparar;

    private double parametroD = 0.5;

    /**
     * Constructor de la clase
     *
     * @param dofP son los grados de libertad(degrees of freedom)
     * @param parameterXP parametro de la x que es necesario para los calculos
     * de hasta donde se debe ir
     * @param parameterNumSeg parametro numero de segmentos en los cuales se va
     * dividir
     */
    public Simpson(double dofP, double parameterXP, double parameterNumSeg) {
        this.integralDefinitiva = 0;
        this.integralInicialP = 0;
        this.parameterW = 0;
        this.num_seg = parameterNumSeg;
        this.parameterE = 0.00001;
        this.dof = dofP;
        this.parameterX = parameterXP;
        if (parameterNumSeg == 0) {
            this.integralDefinitiva = 0;
        } else {
            this.integralInicialP = Calcular(parameterXP, parameterNumSeg);
            parameterNumSeg *= 2;
            this.integralDefinitiva = Calcular(parameterXP, parameterNumSeg);

            double diferencia = Math.abs(integralInicialP - integralDefinitiva);

            while (diferencia > this.parameterE) {
                parameterNumSeg *= 2;
                this.integralInicialP = this.integralDefinitiva;
                this.integralDefinitiva = Calcular(parameterXP, parameterNumSeg);
                diferencia = Math.abs(integralInicialP - integralDefinitiva);
            }
        }

    }

    /**
     * Constructor de la clase para encontrar el valor de X
     * @param dofP grados de libertad
     * @param parametroBuscadoP valor del parametro buscado P
     */
    public Simpson(double dofP, double parametroBuscadoP) {
        this.dof = dofP;
        this.parameterE = 0.00001;
        this.integralDefinitiva = 0;
        this.integralInicialP = 0;
        this.parameterW = 0;
        this.num_seg = 30;
        this.dof = dofP;
        this.parameterX = 1;
        double diferencia = 0;
        double parametroBuscadoX = 1;

        this.integralInicialP = Calcular(parametroBuscadoX, num_seg);
        diferencia = Math.abs(this.integralInicialP - parametroBuscadoP);
        if (diferencia > this.parameterE) {
            parameterX = parametroBuscadoX;
        }
        this.integralComparar = this.integralInicialP;

        if (this.integralInicialP < parametroBuscadoP) {
            parametroBuscadoX = parametroBuscadoX + parametroD;
        } else {
            parametroBuscadoX = parametroBuscadoX - parametroD;
        }
       while (diferencia > this.parameterE){
            this.integralDefinitiva = Calcular(parametroBuscadoX, num_seg);
            diferencia = Math.abs(this.integralDefinitiva - parametroBuscadoP);
            if (this.integralDefinitiva < parametroBuscadoP) {
                parametroD = adjustD(parametroD, this.integralDefinitiva, parametroBuscadoP);
                parametroBuscadoX = parametroBuscadoX + parametroD;
            } else {
                parametroD = adjustD(parametroD, this.integralDefinitiva,parametroBuscadoP);
                parametroBuscadoX = parametroBuscadoX - parametroD;
            }
            this.integralComparar = this.integralDefinitiva;
            if (diferencia < this.parameterE) {
                parameterX = parametroBuscadoX;
            }
        }  
    }

    /**
     * Metodo que ajusta el valor de D
     * @param adjustDP valor de D
     * @param parametroIntegral valor de P en la integral
     * @param parametroBuscadoP valor de P que estamos buscando
     * @return adjustDP ajustado
     */
    public double adjustD(double adjustDP, double parametroIntegral, double parametroBuscadoP) {
        if (parametroIntegral < parametroBuscadoP) {
            return adjustDP;
        }
        return adjustDP / 2;
    }

    /**
     * metodo encargado de hacer los calculos de las integrales y devolver el
     * resultado
     *
     * @param parameterXP parametro tipo double
     * @param parameterNumSeg parametro tipo double
     * @return retorna el resultado de los calculos que es de tipo double
     */
    public Double Calcular(double parameterXP, double parameterNumSeg) {
        double calculos = 0d;
        this.num_seg = parameterNumSeg;

        this.parameterW = parameterXP / this.num_seg;
        double sumatorias = 0.0;
        double parameterF = 0;
        for (int i = 0; i <= this.num_seg; i++) {
            parameterF = this.parameterW;
            parameterF = parameterF * i;
            sumatorias += Multiplier(functionFx(parameterF), i);
        }
        calculos = (this.parameterW / 3) * sumatorias;
        return calculos;

    }

    /**
     * Constructor Vacio de la clase
     */
    public Simpson() {
        this.integralDefinitiva = 0;
        this.integralInicialP = 0;
        this.parameterW = 0;
        this.num_seg = 0;
        this.parameterE = 0.00001;
    }

    /**
     * metodo que retorna ya el valor calculado del parametro P
     *
     * @return retorna la variable integral definitiva
     */
    public double ObtenerP() {

        return this.integralDefinitiva;
    }

    /**
     * Retorna el valor del parametro X
     *
     * @return retorna la variable parameterX
     */
    public double ObtenerParameterX() {

        return this.parameterX;
    }

    /**
     * Metodo que retorna el valor de dof, o variable de grados de libertad.
     *
     * @return retorna la variable dof
     */
    public double ObtenerDof() {

        return this.dof;
    }

    /**
     * Metodo que se encarga de calcular las funcion FX
     *
     * @param parameterX, recibe como parametro para el calculo la parte de x
     * que se requiere calcular
     * @return funcinoFx, retorna el resultado de todos los calculos para la
     * funcion Fx
     */
    private double functionFx(double parameterX) {
        double funcionFx = 0d;
        double partOne = 0d;
        double partTwo = 0d;
        partOne = ((CalcularFactorial((dof + 1) / 2))) / (Math.pow((dof * Math.PI), 0.5D) * (CalcularFactorial((dof / 2))));
        partTwo = Math.pow(((1 + ((Math.pow(parameterX, 2)) / this.dof))), ((-1) * ((dof + 1) / 2)));

        funcionFx = partOne * partTwo;
        return funcionFx;
    }

    /**
     * Metodo que se encarga de multiplicar si es par o impar
     *
     * @param parameterXP recibe como parametro el valor de X
     * @param segmentoActual de acuerdo al segmento actual se va calcular si es
     * par o impar para su respectiva multiplicacion
     * @return si es cero o es el ultimo segmento se retorna el parametro
     * parameterXP, tal cual como llego de lo contrario si es impar se
     * multiplica por el numero dos (2) de lo contrario por el numero cuatro (4)
     */
    private double Multiplier(double parameterXP, int segmentoActual) {
        int espar = segmentoActual / 2;
        espar = espar * 2;
        if (segmentoActual == 0 || segmentoActual == this.num_seg) {
            return parameterXP;
        } else {
            if (espar == segmentoActual) {
                return parameterXP * 2;
            } else {
                return parameterXP * 4;
            }
        }
    }

    /**
     * Metodo que encapsula el calculo del factorial
     *
     * @param numero es el numero el cual se le quiere hacer el factorial
     * @return el calculo del factorial de (n-1)!
     */
    public double CalcularFactorial(double numero) {
        return functionFactorial(numero - 1);
    }

    /**
     * Metodo recursivo para el calculo del factorial (n-1)! si el numero es 0.5
     * (1/2) el factorial se multiplca por raiz de pi
     *
     * @param numero numero al cual se le va hacer el calculo del factorial
     *
     * @return factorial de numero
     */
    private double functionFactorial(double numero) {
        double resultado = 0;
        if (numero == 0 || numero == 1) {
            return 1;
        } else if (numero == 0.5D) {
            return (double) numero * (Math.sqrt(Math.PI));
        } else {
            resultado = numero * functionFactorial(numero - 1);
        }
        return resultado;
    }

}
