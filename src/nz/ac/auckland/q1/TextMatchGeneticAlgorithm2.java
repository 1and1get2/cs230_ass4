package nz.ac.auckland.q1;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
 
 
public class TextMatchGeneticAlgorithm2 implements Runnable {
        static final double crossOver = 0.87;
        static final double mutation = 0.02;
        TextMatchGenome[] array;
        TextMatchGenome bestCurentGenome;
        static Collection<Callable<Void>> callables = new LinkedList<Callable<Void>>();
 
        GUI GUI;
        TextMatchGeneticAlgorithm2(GUI GUI){
                this.GUI = GUI;
        }
       
        public void run(){
                try {
                        startEvolution(GUI);
                } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }
       
        public static void startEvolution(GUI GUI) throws InterruptedException{
                //create a a TextMatchGenome array of the size population size
                final TextMatchGenome monkeys[] = new TextMatchGenome[GUI.getPopulationSize()];
               
               
                //populate my array of monkeys with their dna.
                for(int i = 0; i<monkeys.length; i++){
                        monkeys[i] = new TextMatchGenome(createDNA(TextMatchGenome._targetText.length()));
                }
               
                //find the best monkey from the generation
                TextMatchGenome currentBestMonkey = findBestMatch(monkeys);
                //start while loop
                while (!Thread.interrupted()){
                       
                        //if the new generations monkey has a better dna, update the GUI.
                        TextMatchGenome generationMonkey = findBestMatch(monkeys);
                        if(generationMonkey.getFitness() > currentBestMonkey.getFitness()){
                                currentBestMonkey = generationMonkey;
                                GUI.updateClosestText(currentBestMonkey.getDNA());
                                GUI.updateGUI();
                               
                                //if the new dna matches the target text break out of the loop
                                if(GUI.getClosestText().equals(GUI.getTargetText())){
                                        break;
                                }
                        }
                       
                        if(GUI.isParallel()){
                               
                                TextMatchGenome c1;
                                TextMatchGenome c2;
                               
                                ExecutorService executor = Executors.newFixedThreadPool (10);
                                final int populationSize = GUI.getPopulationSize();
                                int chunk = 1000;
                               
                                for(int low = 0; low<populationSize; low += chunk){
                                        final int Low = low;
                                        final int High = Math.min(monkeys.length, low + chunk);
                                        //System.out.println("test");
                                        callables.add(new Callable<Void>(){
                                                public Void call() {
                                                       
                                                        for(int i = Low; i<High; i++){
                                                               
                                                               
                                                        }
                                                        return null;
                                                }
                                               
                                                public int getPopulationSize(){
                                                        return Integer.parseInt(population.getText());
                                                }
                                        });
                                }
                               
                                List<Future<Void>> futures = executor.invokeAll(callables);
                        }else{
                       
                        //Create a new generation
                        int populationSize = GUI.getPopulationSize();
                        createNewGeneration(monkeys, crossOver, mutation, populationSize);
                        GUI.generations++;
                        }
                }
               
                //monkeys = null;
                Thread.currentThread().interrupt();
                       
        }
       
        private static String createDNA(int targetLength){
                String randomDNA = "";
                for(int i = 0; i<TextMatchGenome._targetText.length(); i++){
                        randomDNA += MainFrame._validChars[(int)(Math.random()*95)];
                }
                return randomDNA;
        }
       
        private static TextMatchGenome findBestMatch(TextMatchGenome[] monkeys){
                TextMatchGenome best = monkeys[0];
                for(TextMatchGenome t : monkeys){
                        if(t.getFitness() > best.getFitness()){
                                best = t;
                        }
                }
                return best;
        }
       
        private static void createNewGeneration(TextMatchGenome[] monkeys, double crossOver, double mutation, int populationSize){
                TextMatchGenome c1;
                TextMatchGenome c2;
               
                for(int i=0, position = 0; i<populationSize/2; i++, position += 2){
                        TextMatchGenome p1 = findParent(monkeys);
                        TextMatchGenome p2 = findParent(monkeys);
                       
                        if(Math.random()<crossOver){
                                int crossOverPosition = (int)(Math.random()*TextMatchGenome._targetText.length()+1);
                                c1 = crossOver(p1, p2, crossOverPosition);
                                c2 = crossOver(p2, p1, crossOverPosition);
                        }else{
                                c1 = p1;
                                c2 = p2;
                        }
                       
                        if(Math.random() < mutation){
                                c1.changeDNA();
                        }
                        if(Math.random() < mutation){
                                c2.changeDNA();
                        }
                        monkeys[position] = c1;
                        monkeys[position+1] = c2;
                }
        }
        private static TextMatchGenome crossOver(TextMatchGenome p1,
                        TextMatchGenome p2, int crossOverPosition) {
               
                String newDNA = p1.getDNA().substring(0, crossOverPosition)+p2.getDNA().substring(crossOverPosition);
                TextMatchGenome crossOver = new TextMatchGenome(newDNA);
                return crossOver;
        }
        private static TextMatchGenome findParent(TextMatchGenome[] monkeys) {
                TextMatchGenome p = monkeys[0];
                int sumBreedingWeight = 0;
               
                for(TextMatchGenome t: monkeys){
                        t.weightOfBreeding = TextMatchGenome.mostDiff - t.diffCount+1;
                        sumBreedingWeight += t.weightOfBreeding;
                }
               
                int value = (int)(Math.random()*(sumBreedingWeight+1));
                for(int i=0; i<monkeys.length; i++){
                        int weight = monkeys[i].weightOfBreeding;
                        if(value<weight){
                                p = monkeys[i];
                                break;
                        }
                        value = value - weight;
                }
               
                return p;
        }
       
}