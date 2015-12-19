package com.softtechdesign.ga.examples;

import com.softtechdesign.ga.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/*
    GABinaryOnes extends GAString.
  This class solves the trivial problem of finding the bitstring with the largest
  value (namely all ones, e.g., 11111111).
  As a very simple GA, it is easy for beginners to follow.
*/

public class belka3 extends GAString
{
    static String[] simpleArray = null;
    static String[] dlugosci = {"40","50","49","110","75"};
    private static int dlugosc;
    private static int ilosc;
    private static final ArrayList<Integer> elementy = new ArrayList<>();

    /* given the chromosome identified by iChromIndex, calculate and return its fitness */
    protected double getFitness(int iChromIndex)
    {
        float scinki = 0;
        float current_dl = 0;
        String s = this.getChromosome(iChromIndex).getGenesAsStr();
        char[] chs = s.toCharArray();
        for(int i = 0; i < chs.length; i++)
        {
            if(chs[i] == '1')
            {
                    current_dl = current_dl + elementy.get(i);
                    //System.out.println(obj);
                    if (current_dl > dlugosc){
                        return 0.0;   }                           
            }            
        }
        return (dlugosc - current_dl);
    }

    public belka3() throws GAException
    {
        super(ilosc, //chromosome has 20 chars
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

    public static void main(String[] args) throws FileNotFoundException
    {
        
        
        try
        {
            ArrayList<String> array = new ArrayList<>();
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
                elementy.add(Integer.parseInt(i));
                s=i.toString().split("");
                
                //strArray = new String[] {i};
            }             
            }
            //simpleArray = new String[ elementy.size() ];
            //elementy.toArray( simpleArray );       
            //System.out.println(simpleArray[1]);
            System.out.println(dlugosc);
            System.out.println(ilosc);
            System.out.println(elementy);
            belka3 Belka3 = new belka3();
            Thread threadbelka = new Thread(Belka3);
            threadbelka.start();
        }
        catch (GAException gae)
        {
            System.out.println(gae.getMessage());
        }
    }

}