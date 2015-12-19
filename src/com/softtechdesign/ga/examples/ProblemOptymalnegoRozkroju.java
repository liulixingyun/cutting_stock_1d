package com.softtechdesign.ga.examples;

import com.softtechdesign.ga.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
    Traveling salesman problem. A salesman has to travel to N different cities. In what
  sequence should he visit each city to minimize the total distance traveled. Each city
  is represented in the chromosome string as a letter (e.g. 'A' or 'B').

  To simplify the mathematics of the fitness function, this example reduces the coordinate
  space to one dimension. Each city (or node) has a position given by one coordinate.
  This model can be extrapolated to 2 or 3 dimensions by giving each city (or node) a 2
  dimensional (X,Y) or 3 dimensional (X,Y,Z) coordinate and then modifying the distance
  calculating function accordingly.

  If a chromosome = 'ABCDEFGHIJKLMNOPQRST', then the fitness is evaluated as
    fitness = Dist(A, B) + Dist(B, C) + Dist(C, D)....+ Dist(S, T)
  Higher fitness values mean a higher probability that this chromosome will reproduce.

  The possible combinations (sequences of cities) is N factorial. For 20 cities, the possible
  combinations = 20! or 2.432902008177e+18.
  This is an enormous number. If you tried every combination of sequences and could test
  10,000 of those sequences per second (looking for the minium), it would take your
  computer 8 million years to randomly come up with the minimum (ideal) sequence.
*/

public class ProblemOptymalnegoRozkroju extends GASequenceList
{
	public int[] dlugosciDesek = {1,2,2,3};
	public int dlugoscBelki = 4;
	
    public ProblemOptymalnegoRozkroju(int n, String s) throws GAException
    {    	
        super(  n, //size of chromosome
                300, //population has N chromosomes
                0.7, //crossover probability
                10, //random selection chance % (regardless of fitness)
                2000, //max generations
                0, //num prelim runs (to build good breeding stock for final/full run)
                25, //max generations per prelim run
                0.06, //chromosome mutation prob.
                0, //number of decimal places in chrom
                s, //gene space (possible gene values)
                Crossover.ctTwoPoint, //crossover type
                true); //compute statisitics?

        setInitialSequence();
    }
    
    public int pobierzDlugoscDeski(char c)
    {
    	int index = c - 65;    	
    	return this.dlugosciDesek[index];
    }
    
    void setInitialSequence()
    {
        for (int i = 0; i < chromosomeDim; i++)
            this.sequence[i] = i + 1;
    }
    
    protected double getFitness(int iChromIndex)
    {
        int zuzyteBelki = 0;
        int aktulanyRozmiar = this.dlugoscBelki;
        int dlugoscDeski;

        char genes[] = this.getChromosome(iChromIndex).getGenes(); // pobranie chromosomu 
        int lenChromosome = genes.length;
               
        for (int i = 0; i < lenChromosome; i++)
        {
        	dlugoscDeski = pobierzDlugoscDeski(genes[i]);
        	
        	if ( dlugoscDeski < aktulanyRozmiar )
        	{
        		aktulanyRozmiar = aktulanyRozmiar - dlugoscDeski;
        		
        		if( i == (lenChromosome-1) ) zuzyteBelki++; // jesli ostatnia iteracja
        		
        	}
        	else if ( dlugoscDeski == aktulanyRozmiar )
        	{
        		zuzyteBelki++;
        		aktulanyRozmiar = this.dlugoscBelki;
        	}
        	else 
        	{	
        		zuzyteBelki++;
        		aktulanyRozmiar = this.dlugoscBelki - dlugoscDeski;
        		
        		if( i == (lenChromosome-1) ) zuzyteBelki++; // jesli ostatnia iteracja
        	}      	
        }
        
    	return (1.0/zuzyteBelki);
    }
     
    public static void main(String[] args)
    {
    	int dlugoscBelki = 0;
    	int iloscElementowDoWyciecia = 0;
    	String Deski = "";
    	char c;
    	
        //String startTime = new Date().toString();
        //System.out.println("Problem optymalnego rozkroju" + startTime);
        System.out.println("Problem optymalnego rozkroju");
    
        try
        {
        	Scanner odczyt = new Scanner(new File("deski.txt"));
        	
        	dlugoscBelki = Integer.parseInt(odczyt.nextLine());
        	iloscElementowDoWyciecia = Integer.parseInt(odczyt.nextLine());
        	
        	int[] dlugosciDesek = new int[iloscElementowDoWyciecia];
        	
        	for(int i =0; i<iloscElementowDoWyciecia; i++)
        	{
        		dlugosciDesek[i] = Integer.parseInt(odczyt.nextLine());
        		
        		c = (char)(i+65);
        		Deski += c + "";
        	}
        	
        	/*System.out.println(dlugoscBelki);
        	System.out.println(iloscElementowDoWyciecia);    	
        	for(int i =0; i<iloscElementowDoWyciecia; i++)
        	{
        		System.out.println(dlugosciDesek[i]);
        	}
        	System.out.println(Deski);*/
        	
        }
        
        catch(Exception e)
        {
        	System.out.println("Z plikiem nie jest sehr gut.");
        }
        
        try
        {
            ProblemOptymalnegoRozkroju salesman = new ProblemOptymalnegoRozkroju(4,"ABCD");
            Thread threadSalesman = new Thread(salesman);
            threadSalesman.start();
        }
        catch (GAException gae)
        {
            System.out.println(gae.getMessage());
        }
        
       // System.out.println("Process started at " + startTime + ". Process completed at " +  new Date().toString());
    }

}