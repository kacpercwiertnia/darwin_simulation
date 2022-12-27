package agh.ics.oop.map;

import agh.ics.oop.Animal;
import agh.ics.oop.Grass;
import agh.ics.oop.Grave;
import agh.ics.oop.Vector2d;

import java.util.*;

public abstract class AbstractWorldMap {
    Comparator<Grave> comparator = new Comparator<Grave>() {
        @Override
        public int compare(Grave o1, Grave o2) {
            if(o1.corpses> o2.corpses){
                return 1;
            }else{
                return -1;
            }
        }
    };
    protected Grave[] graveyard;
    protected int width,height,grassnum,age=0;
    GrassGenerator grassField;
    Map<Vector2d, ArrayList<Animal>> animals = new HashMap<>();
    Map<Vector2d, Grass> plants = new HashMap<>();

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
            return this.animals.get(position);
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
            System.out.println("Coś tu jest");
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
                Arrays.sort(graveyard,comparator);
            }
        }
    }
    public void eatingTime(int energy){
        HashMap<Vector2d,Grass> toDelete=new HashMap<>();
        for (Map.Entry<Vector2d,Grass> mapentry:this.plants.entrySet()){
            if (this.animals.containsKey(mapentry.getKey())){
                if (this.animals.get(mapentry.getKey()).size()>1){
                    int idx=this.getWinner(this.animals.get(mapentry.getKey()));
                    this.animals.get(mapentry.getKey()).get(idx).eat(energy);
                }else{
                    this.animals.get(mapentry.getKey()).get(0).eat(energy);
                }
            }
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
}
