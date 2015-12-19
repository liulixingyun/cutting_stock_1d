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
import java.util.Scanner;






public class belka extends GAStringsSeq
{
    static int iloscElemtow = 6;
    static int dlugoscMaterialu = 200;
    static String[] rozmiaryElementow = {"69", "25", "30", "40","99","10", "80"};
    double fitness = 0;
    

    public belka() throws GAException
    {
        super(  iloscElemtow, //size of chromosome
                200, //population has N chromosomes
                0.7, //crossover probability
                10, //random selection chance % (regardless of fitness)
                200, //max generations
                0, //num prelim runs (to build good breeding stock for final/full run)
                20, //max generations per prelim run
                0.06, //chromosome mutation prob.
                0, //number of decimal places in chrom
                rozmiaryElementow, //gene space (possible gene values)
                Crossover.ctTwoPoint, //crossover type
                true); //compute statisitics?
    }

    protected double getFitness(int iChromIndex)
    {
        ChromStrings chromosome = (ChromStrings)this.getChromosome(iChromIndex);
        double odpad = 0;
        int dlugosc=0;
        for (int i = 0; i < chromosomeDim; i++)
        {
            dlugosc=dlugosc+Integer.parseInt(chromosome.getGene(i));
            if (dlugosc<dlugoscMaterialu)
                odpad =dlugoscMaterialu-dlugosc;
           
        }
       
        return (1/odpad);
    }

    public static void main(String[] args)
    {
        System.out.println("Maze GA...");
        try
        {
            belka gaMaze = new belka();
            Thread threadMaze = new Thread(gaMaze);
            threadMaze.start();
        }
        catch (GAException gae)
        {
            System.out.println(gae.getMessage());
        }
    }

}