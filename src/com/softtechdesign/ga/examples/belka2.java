/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softtechdesign.ga.examples;

import com.softtechdesign.ga.ChromStrings;
import com.softtechdesign.ga.Crossover;
import com.softtechdesign.ga.GAException;
import com.softtechdesign.ga.GASequenceList;
import com.softtechdesign.ga.GAStringsSeq;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author tomekrajzer
 */
public class belka2 extends GAStringsSeq
{
    //static int iloscEl = 4;
    //static int dlugoscMaterialu = 200;
    static String[] simpleArray = null;
    static String[] dlugosci = {"40","50","49","110","75"};
    private static int dlugosc;
    static int ilosc;
    private static final ArrayList<String> elementy = new ArrayList<>();

    

    public belka2() throws GAException
    {
        super(  (ilosc*simpleArray.length), //size of chromosome
                140, //population has N chromosomes
                0.7, //crossover probability
                10, //random selection chance % (regardless of fitness)
                6000000, //max generations
                0, //num prelim runs (to build good breeding stock for final/full run)
                20, //max generations per prelim run
                0.06, //chromosome mutation prob.
                0, //number of decimal places in chrom
                simpleArray, //gene space (possible gene values)
                Crossover.ctTwoPoint, //crossover type
                true);
        //setInitialSequence();
    }
    
    /*setInitialSequence()
    {
        //initialize one dimensional locations (e.g. traveling salesman's cities)
        for (int i = 0; i < chromosomeDim; i++)
            this.sequence[i] = i + 1;
    }*/

    
    protected double getFitness(int iChromIndex)
    {
        ChromStrings chromosome = (ChromStrings)this.getChromosome(iChromIndex);
        int scinki = 0;
        int DlugoscAktualna=0;
        ArrayList<Integer> sc = new ArrayList<>();
        ArrayList<Integer> cc = new ArrayList<>();
        int deski = 1;
        int licznik = 0;
        int licznik2 = 0;

        for (int i = 0; i < chromosomeDim; i++)
        {
            cc.add(Integer.parseInt(chromosome.getGene(i)));
            if (DlugoscAktualna + Integer.parseInt(chromosome.getGene(i))<= dlugosc){
                DlugoscAktualna+= Integer.parseInt(chromosome.getGene(i));
            }
            else {
                sc.add(dlugosc - DlugoscAktualna);
                DlugoscAktualna = Integer.parseInt(chromosome.getGene(i));
                deski ++;
            }
            if ((i+1)== chromosomeDim){
                sc.add(dlugosc - DlugoscAktualna);
                for(int x=0; x<elementy.size(); x++){
                   licznik += Collections.frequency(cc,Integer.parseInt(elementy.get(x)));
                   if (licznik==ilosc){
                       licznik2 ++;
                   }
                   else
                       licznik2 = 0;
                   licznik = 0;
                }
                if (licznik2 != elementy.size())
                    return -10000;
            }
        }
        for (int i=0; i< sc.size(); i++ ){
            scinki+=sc.get(i);
        }
        if (deski == 1){
            return -(dlugosc-DlugoscAktualna);
        }
        else
        return -scinki;
    }
    public static void main(String[] args) throws FileNotFoundException
    {
        
        
        try
        {
            //ArrayList<String> array = new ArrayList<>();
            String[] s = null;
            File dane = new File("dane.txt");
            Scanner scnr = new Scanner(dane);
            int lines = 0;
            while (scnr.hasNextLine()) {
            lines ++;    
            String i = scnr.nextLine();
            System.out.println(i);
            if (lines==1){
                dlugosc = Integer.parseInt(i);     
            }
            else if (lines==2){
                ilosc = Integer.parseInt(i);
            }
            else {
                elementy.add(i);
                s=i.toString().split("");
                
                //strArray = new String[] {i};
            }             
            }
            simpleArray = new String[ elementy.size() ];
            elementy.toArray( simpleArray );       
            //System.out.println(simpleArray[1]);
            System.out.println(dlugosc);
            System.out.println(ilosc);
            System.out.println(elementy);
            belka2 Belka2 = new belka2();
            Thread threadbelka = new Thread(Belka2);
            threadbelka.start();
        }
        catch (GAException gae)
        {
            System.out.println(gae.getMessage());
        }
    }

}
