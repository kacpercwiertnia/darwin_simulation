package agh.ics.oop.map;

import agh.ics.oop.*;

import java.util.*;

public abstract class AbstractWorldMap {
    public Grave[] graveyard;
    protected int width,height,grassnum,age=0;
    GrassGenerator grassField;
    Map<Vector2d, ArrayList<Animal>> animals = new HashMap<>();
    Map<Vector2d, Grass> plants = new HashMap<>();

    abstract public void generateGrass(int n);
    public void increaseAge(){
        this.age++;
    }
    public int getAge(){
        return this.age;
    }
    public void place(Animal animal,Vector2d position){
        if (!this.animals.containsKey(position)){
            ArrayList<Animal> newanimal = new ArrayList<>();
            newanimal.add(animal);
            this.animals.put(position,newanimal);
        }else{
            ArrayList<Animal> anims = new ArrayList<>();
            anims=this.animals.get(position);
            anims.add(animal);
            this.animals.replace(position,this.animals.get(position),anims);
        }
    }
    public boolean isOccupied(Vector2d position){
        return animals.containsKey(position) || plants.containsKey(position);
    }
    public Object objectAt(Vector2d position){
        if (this.animals.containsKey(position)){
            return this.animals.get(position).get(0);
        }
        if (this.plants.containsKey(position)){
            return this.plants.get(position);
        }
        return null;
    }
    abstract public Vector2d canMoveTo(Vector2d position,Vector2d movement);
    public Vector2d getLowerLeft(){
        return new Vector2d(0,0);
    }
    public Vector2d getUpperRight(){
        return new Vector2d(this.width-1,this.height-1);
    }

    public void moveAnimal(Animal animal,Vector2d position){
        ArrayList<Animal> aanimals= this.animals.get(animal.getPosition());
        aanimals.remove(animal);
        if (aanimals.isEmpty()){
            this.animals.remove(animal.getPosition());
        }else{
            this.animals.replace(animal.getPosition(),aanimals);
        }
        if (animals.containsKey(position)){
            aanimals = this.animals.get(position);
            aanimals.add(animal);
            this.animals.put(position,aanimals);
        }else{
            aanimals = new ArrayList<>();
            aanimals.add(animal);
            this.animals.put(position,aanimals);
        }
    }
    public ArrayList<Animal> clearCorpses(){
        HashMap<Vector2d,Animal> toDelete = new HashMap<>();
        ArrayList<Animal> anims= new ArrayList<>();
        for (ArrayList<Animal> animal:this.animals.values()){
            for (Animal one:animal){
                if (one.getHealth()==0){
                    toDelete.put(one.getPosition(),one);
                    addCorpse(one.getPosition());
                }else{
                    anims.add(one);
                }
            }
        }
        for (Map.Entry<Vector2d,Animal> entry:toDelete.entrySet()){
            ArrayList<Animal> animallist=new ArrayList<>();
            animallist=this.animals.get(entry.getKey());
            animallist.remove(entry.getValue());
            if (animallist.isEmpty()){
                this.animals.remove(entry.getKey());
            }else{
                this.animals.put(entry.getKey(),animallist);
            }

        }
        return anims;
    }
    private void addCorpse(Vector2d position){
        for (int i=0;i<graveyard.length;i++){
            if (graveyard[i].position.equals(position)){
                graveyard[i].corpses++;
                Arrays.sort(graveyard,new Comparator<Grave>() {
                    @Override
                    public int compare(Grave o1, Grave o2) {
                        if(o1.corpses> o2.corpses){
                            return 1;
                        }else{
                            return -1;
                        }
                    }
                });
            }
        }
    }
    public void eatingTime(int energy){
        ArrayList<Vector2d> toDelete=new ArrayList<>();
        for (Map.Entry<Vector2d,Grass> mapentry:this.plants.entrySet()){
            if (this.animals.containsKey(mapentry.getKey())){
                if (this.animals.get(mapentry.getKey()).size()>1){
                    int idx=this.getWinner(this.animals.get(mapentry.getKey()));
                    this.animals.get(mapentry.getKey()).get(idx).eat(energy);
                }else{
                    this.animals.get(mapentry.getKey()).get(0).eat(energy);
                }
                toDelete.add(mapentry.getKey());
            }
        }
        for (Vector2d position:toDelete){
            this.plants.remove(position);
        }
    }
    private int getWinner(ArrayList<Animal> animals){
        int best=0;
        boolean flag=true;
        for (int i=0;i<animals.size();i++){
            if (animals.get(i).getHealth()>animals.get(best).getHealth()){
                best=i;
                flag=false;
            }else if(animals.get(i).getHealth()==animals.get(best).getHealth()){
                flag=true;
            }
        }
        if (flag){return getWinner2(animals);}
        return 0;
    }

    private int getWinner2(ArrayList<Animal> animals){
        int best=0;
        boolean flag=true;
        for (int i=0;i<animals.size();i++){
            if (animals.get(i).getAge()>animals.get(best).getAge()){
                best=i;
                flag=false;
            }else if(animals.get(i).getAge()==animals.get(best).getAge()){
                flag=true;
            }
        }
        if (flag){return getWinner3(animals);}
        return best;
    }

    private int getWinner3(ArrayList<Animal> animals){

        Random rn=new Random();
        return rn.nextInt(animals.size());
    }
    public ArrayList<Animal> reproduction(int minhealth,int genlen,MutationType mutation,MovementType movement){
        ArrayList<Animal> newanimals = new ArrayList<>();
        for (Map.Entry<Vector2d,ArrayList<Animal>> entry:this.animals.entrySet()){
            if (entry.getValue().size()==1){
                newanimals.add(entry.getValue().get(0));
            }
            else{
                ArrayList<Animal> potential = new ArrayList<>();
                for (Animal animal:entry.getValue()){
                    if (animal.getHealth()>=minhealth){
                        potential.add(animal);
                    }
                    newanimals.add(animal);
                }
                potential.sort((Animal o1,Animal o2)-> {
                    if(o1.getHealth() < o2.getHealth()){
                        return 1;
                    }else if (o1.getHealth()==o2.getHealth()){
                        return 0;
                    }else{
                        return -1;
                    }

                });
                if (potential.size()>1){
                    Animal mlode=new Animal(new Genotype(genlen,potential.get(0),potential.get(1),mutation,movement),entry.getKey(),2*minhealth,this);
                    newanimals.add(mlode);
                    this.place(mlode,mlode.getPosition());
                    potential.get(0).eat(-minhealth);
                    potential.get(1).eat(-minhealth);
                }
                newanimals.addAll(potential);
            }
        }

        return newanimals;
    }
}
