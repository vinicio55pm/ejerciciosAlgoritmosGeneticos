/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplo5;

import algoritmo.genetico.Individuo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Vinnys
 */
public class AlgoritmoGenetico5{
    public static int numeroIndividuos=10;
    public static int numeroInteraciones=25;
    public static int rDeMutacion=10;
    public static int rDeMantenimiento=50;
    //public ArrayList<Individuo>poblacion;
    public static List<Individuo> poblacion;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /*List<String> binarios= new ArrayList<String>();
        System.out.println("Binario:");
        for (int i = 1; i <= 63; i++) {
            String binario=decimalABinario(i);
            binarios.add(binario);
            System.out.println("i: "+i+" = "+binario);
        }
        System.out.println("");
        System.out.println("Decimal:");
        for (int i = 0; i < binarios.size(); i++) {
            long decimal=binarioADecimal(binarios.get(i));
            System.out.println("i: "+binarios.get(i)+" = "+decimal);
        }*/
        
        //String cadena="000000";
        //System.out.println(cadena.length());
        //cadena+="a";
        //cadena+="b";
        //cadena+="c";
        //System.out.println(cadena);
        /*for (int i = 0; i < 10; i++) {
            System.out.println((int)(Math.random() * 25-(-25))-25);
            //System.out.println("i: "+i+", "+decimalABinario(i));
        }*/
        
        
        System.out.println("Inicio...");
        generarPoblacion();
        //System.out.println("Iteracion a travez de generaciones ...");
        for (int i = 0; i < numeroInteraciones; i++) {
            System.out.println("---"+(i+1)+"---");
            generarFitness();
            organizar();
            informacion();
            cruzaMutacion();
        }
        System.out.println("Definicion del criterio de terminacion(N interacciones/generaciones)");
        System.out.println("Fin...");
        System.out.println(miFuncion(55));
        System.out.println(miFuncionDerivada1(55));
        System.out.println(miFuncionDerivada2(55));
        
    }
    
    public static void generarPoblacion(){
        
        poblacion= new ArrayList<Individuo>();
        for (int i = 0; i < numeroIndividuos; i++) {
            
            Individuo individuo= new Individuo();
            individuo.genoma=((int)(Math.random() * 63-1)+1);
            poblacion.add(i, individuo);
            
        }
        
        System.out.println("Generacion de poblacion inicial...");
    }
    public static void generarFitness(){
        
        //System.out.println("Fitness "+tamaño);
        int tamaño=poblacion.size();
        for (int i = 0; i < tamaño; i++) {
            //System.out.println("Fitness: "+i);
            Individuo individuo=poblacion.get(i);
            int geno=individuo.genoma;
            individuo.fitness=(float) miFuncion(geno);
            
            //System.out.println("i:"+i+", G: "+individuo.genoma+", F: "+individuo.fitness);
            poblacion.set(i, individuo);
        }
        //System.out.println("******************************************");
    }
    public static double miFuncion(double x){
        return -12.5*(x*x)+1375*x-1500;
    }
    public static double miFuncionDerivada1(double x){
        return -25*x+1375;
    }
    public static double miFuncionDerivada2(double x){
        return -25;
    }
    public static void organizar(){
        //System.out.println("Organizas");
        int tamaño=poblacion.size();
        boolean sw=true;
        while(sw){
            sw=false;
            for (int i = 0; i < tamaño-1;i++) {
                    if(poblacion.get(i).fitness < poblacion.get(i+1).fitness){
                        Individuo indi1=poblacion.get(i);
                        Individuo indi2=poblacion.get(i+1);
                        
                        poblacion.set(i, indi2);
                        
                        poblacion.set(i+1,indi1);
                        sw=true;
                    }
            }
        }
        /*System.out.println("________________________________________________________");
        for (int i = 0; i < tamaño; i++) {
            Individuo indi=poblacion.get(i);
            System.out.println("i: "+i+", G: "+indi.genoma+", F: "+indi.fitness);
        }*/
    }
    public static void informacion(){
        String s="";
        int tamaño = poblacion.size();
        for (int  i = 0;  i < tamaño;  i++) {
            s=s+" "+"("+i+")"+(poblacion.get(i).genoma)+ " p:"+poblacion.get(i).fitness+"\n";
        }
        System.out.println(s);
    }
    public static void cruzaMutacion(){
        //System.out.println("Aplicación de los operadores genéticos (cruza y mutación).");
        List<Individuo> aptos = quitarNoAptos();//Seleccion
        
        //int variable=(int) (numeroIndividuos*(1f-rDeMantenimiento/100f));
        //System.out.println("Variable: "+variable);
        
        int cantidad=aptos.size();
        for (int i = 0; i < cantidad; i+=2) {
            int indice1 =  (int)(Math.random() * cantidad-1)+1;
            int indice2 = (int) (Math.random() * cantidad-1)+1;
            
            long aux=aptos.get(indice1).genoma;
            String gen1=decimalABinario(aux);
            
            aux=aptos.get(indice2).genoma;
            String gen2=decimalABinario(aux);
            
            int bitMutado=(int)(Math.random() * 6-1)+1;
            String genHijo="";
            
            int eleccionPadre=(int)(Math.random() * 2-1)+1;//Si es 1 sera el padre1(gen1) si es 2 sera padre2(gen2)
            int contadorGen=0;
            
            for (int j = 0; j < gen1.length(); j++) {
                if(contadorGen==3){
                    if(eleccionPadre==1){
                        eleccionPadre=2;
                    }
                    if(eleccionPadre==2){
                        eleccionPadre=1;
                    }
                }
                if(eleccionPadre==1){
                    
                    if(bitMutado==j){
                        String bit=gen1.charAt(j)+"";
                        if(bit.equals("1")){
                            //System.out.println("H: "+genHijo+" + "+"0 MUTADO");
                            genHijo+="0";
                        }else{
                            //System.out.println("H: "+genHijo+" + "+"1 MUTADO");
                            genHijo+="1";
                        }
                    }else{
                        //System.out.println("H: "+genHijo+" + "+gen1.charAt(j));
                        genHijo+=gen1.charAt(j);
                    }
                    contadorGen++;
                }else{
                    if(bitMutado==j){
                        String bit=gen2.charAt(j)+"";
                        if(bit.equals("1")){
                            //System.out.println("H: "+genHijo+" + "+"0 MUTADO");
                            genHijo+="0";
                        }else{
                            //System.out.println("H: "+genHijo+" + "+"1 MUTADO");
                            genHijo+="1";
                        }
                    }else{
                        genHijo+=gen2.charAt(j);
                        //System.out.println("H: "+genHijo+" + "+gen2.charAt(j));
                    }
                    contadorGen++;
                }
                //System.out.println("= "+genHijo+"");
            }
            
            Individuo indi= new Individuo();
            long genomaHijo=binarioADecimal(genHijo);
            //System.out.println("Gh: "+valor);
            indi.genoma=(int)genomaHijo;
            aptos.add(indi);
        }
        poblacion=new ArrayList<>();
        poblacion=aptos;
    }
    public static List<Individuo> quitarNoAptos(){
        List<Individuo> aptos = new ArrayList<Individuo>();
        for (int i = 0; i < numeroIndividuos; i++) {
            aptos.add(poblacion.get(i));
        }
        return aptos;
    }
    public static long binarioADecimal(String binario) {
        long decimal = 0;
        int posicion = 0;
        // Recorrer la cadena...
        for (int x = binario.length() - 1; x >= 0; x--) {
            short digito = 1;
            if (binario.charAt(x) == '0') {
                digito = 0;
            }
            double multiplicador = Math.pow(2, posicion);
            decimal += digito * multiplicador;
            posicion++;
        }
        return decimal;
    }
    public static String decimalABinario(long decimal) {
	StringBuilder binario = new StringBuilder();
	while (decimal > 0) {
		short residuo = (short) (decimal % 2);
		decimal = decimal / 2;
		// Insertar el dígito al inicio de la cadena
		binario.insert(0, String.valueOf(residuo));
	}
        String bn=binario+"";
        if(bn.length()<6){
            while(bn.length()!=6){
                bn="0"+bn;
            }
        }
	return bn;
    }
    
}
