/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softtechdesign.ga.examples;
import com.softtechdesign.ga.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author Pawel
 */
public class Plecak extends GAString
{
    private static float pojemnosc;
    private static final ArrayList<Integer> nr_przedmiotu = new ArrayList<>();
    private static final ArrayList<Float> objetosc = new ArrayList<>();
    private static final ArrayList<Float> wartosc = new ArrayList<>();
    
    /* given the chromosome identified by iChromIndex, calculate and return its fitness */
//    protected double getFitness(int iChromIndex)
//    {
//        String s = this.getChromosome(iChromIndex).getGenesAsStr();
//        return (getChromValAsDouble(s));
//    }
    @Override
    protected double getFitness(int iChromIndex)
    {
        float wart = 0;
        float obj = 0;
        String s = this.getChromosome(iChromIndex).getGenesAsStr();
        char[] chs = s.toCharArray();
        for(int i = 0; i < chs.length; i++)
        {
            if(chs[i] == '1')
            {
                wart += wartosc.get(i);
                obj += objetosc.get(i);
                if(obj > pojemnosc)
                    return 0.0;                              
            }            
        }
//        System.out.println(chs);
//        System.out.println("******");
//        System.out.println(s.split(""));
        
        //return (getChromValAsDouble(s));
        return wart;
    }

    public Plecak(int liczba_przedmiotow) throws GAException
    {
        super(liczba_przedmiotow, //chromosome has 20 chars
              50, //population of N chromosomes
              0.7, //crossover probability (0.7 = 70%)
              5, //random selection chance % (regardless of fitness)
              50, //stop after N generations
              0, //num prelim runs
              10, //max prelim generations
              0.01, //chromosome mutation prob.
              0, //number of decimal places in chrom (0 means treat chrom as integer)
              "01", //gene space (possible gene values '0' or '1')
              Crossover.ctTwoPoint, //crossover type
              true); //compute statistics?
    }

    public static void main(String[] args)
    {
        try
        {
            Scanner sc = new Scanner(new FileReader("lista_rzeczy.txt"));            
        
            //String[] buffer = null;
            ArrayList<String> lista = new ArrayList<>();


            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Scanner scl = new Scanner(line);
                //int i = 0;
                while (scl.hasNext()) {
                    //System.out.println(scl.next());
                    //buffer[i] = scl.next();
                    lista.add(scl.next());
                }
                //System.out.println(lista);
                //System.out.println(lista.size());
                if (lista.size() == 1) {
                    Plecak.pojemnosc = Float.parseFloat(lista.get(0));
                } else if (lista.size() == 3) {
                    nr_przedmiotu.add(Integer.parseInt(lista.get(0)));
                    objetosc.add(Float.parseFloat(lista.get(1)));
                    wartosc.add(Float.parseFloat(lista.get(2)));                                    
                }
                lista.clear();
            }
            System.out.println(pojemnosc);
            System.out.println(nr_przedmiotu);
            System.out.println(objetosc);
            System.out.println(wartosc);

            sc.close();

            Plecak plecak = new Plecak(nr_przedmiotu.size());
            Thread threadPlecak = new Thread(plecak);
            threadPlecak.start();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("blad pliku");            
        }
        catch (GAException gae)
        {
            System.out.println(gae.getMessage());
        }
    }
}





