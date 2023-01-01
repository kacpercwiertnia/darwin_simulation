package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class Genotype {
    private final int length;
    private final List<Integer> genotype = new ArrayList<>();
    private final MovementType movementType;
    private MutationType mutationType;
    private int currentGene = -1;

    public Genotype(int length, Animal parent1, Animal parent2, MutationType mutationType, MovementType movementType, int minMutation, int maxMutation){
        this.length = length;
        this.movementType = movementType;
        this.mutationType = mutationType;
        int side = (int) ((Math.random() * (1 - 0)) + 0);
        Animal strongerParent;
        Animal weakerParent;
        Genotype strongerGenotype;
        Genotype weakerGenotype;
        int numOfMutation = (int) ((Math.random() * (maxMutation - minMutation)) + minMutation);

        if( parent1.getHealth() > parent2.getHealth() ){
            strongerParent = parent1;
            weakerParent = parent2;
            strongerGenotype = parent1.getGenotype();
            weakerGenotype = parent2.getGenotype();
        }
        else{
            strongerParent = parent2;
            weakerParent = parent1;
            strongerGenotype = parent2.getGenotype();
            weakerGenotype = parent1.getGenotype();
        }

        int sumHealth = strongerParent.getHealth() + weakerParent.getHealth();
        double strongerGenesRatio = (double)strongerParent.getHealth() / (double)sumHealth;
        double weakerGenesRatio = 1 - strongerGenesRatio;
        int numOfStrongerGenes = (int) Math.ceil(this.length * strongerGenesRatio);
        int numOfWeakerGenes = (int) Math.floor(this.length * weakerGenesRatio);

        if(numOfStrongerGenes+numOfWeakerGenes < this.length){
            numOfStrongerGenes+=1;
        }

        if( side == 0 ){
            for( int i = 0; i < numOfStrongerGenes; i++){
                this.genotype.add(strongerGenotype.getGene(i));
            }
            for( int i = 0; i < numOfWeakerGenes; i++ ){
                this.genotype.add(weakerGenotype.getGene(this.length-i-1));
            }
        }
        else{
            for( int i = 0; i < numOfStrongerGenes; i++){
                this.genotype.add(strongerGenotype.getGene(this.length-i-1));
            }
            for( int i = 0; i < numOfWeakerGenes; i++ ){
                this.genotype.add(weakerGenotype.getGene(i));
            }
        }

        int pickedGene = 0;
        int newGene = 0;

        for( int i = 0; i < numOfMutation; i++){
            pickedGene = (int) ((Math.random() * ((this.length-1) - 0)) + 0);

            if( mutationType == MutationType.BLESSRNG ){
                newGene = (int) ((Math.random() * (7 - 0)) + 0);
            }
            else{
               int addOrSub = (int) ((Math.random() * (1 - 0)) + 0);

               if( addOrSub == 0 ){
                   newGene = (this.genotype.get(pickedGene) + 1) % 8;
               }
               else{
                   if( this.genotype.get(pickedGene) == 0 )
                       newGene = 7;
                   else{
                       newGene = this.genotype.get(pickedGene)-1;
                   }
               }
            }

            this.genotype.set(pickedGene, newGene);
        }

    }

    public Genotype(int length, MovementType movementType){
        this.length = length;
        this.movementType = movementType;

        for( int i = 0; i < length; i++ ) {
            int gene = (int) ((Math.random() * (7 - 0)) + 0);
            genotype.add(gene);
        }

    }

    public Integer getGene(int index){
        return this.genotype.get(index);
    }

    public int getLength(){
        return this.length;
    }

    public String toString(){
        return this.genotype.toString();
    }

    public int getCurrentGene(){
        return this.currentGene;
    }

    public int nextGene(){
        if( this.movementType == MovementType.FULL_PREDESTINATION ){
            if( this.currentGene == this.length-1 ){
                this.currentGene = 0;
            }
            else{
                this.currentGene += 1;
            }
        }
        else{
            int ifRandMovement = (int) ((Math.random() * (9 - 0)) + 0);
            if( ifRandMovement <= 7 ){
                if( this.currentGene == this.length-1 ){
                    this.currentGene = 0;
                }
                else{
                    this.currentGene += 1;
                }
            }
            else{
                this.currentGene = (int) ((Math.random() * (this.length-1 - 0)) + 0);
            }
        }

        return this.genotype.get(this.currentGene);
    }

}
