package agh.ics.oop.gui;

import agh.ics.oop.*;
import agh.ics.oop.map.AbstractWorldMap;
import agh.ics.oop.map.GeneratorType;
import agh.ics.oop.map.GlobeMap;
import agh.ics.oop.map.HellPortal;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class App extends Application implements IMapRefreshObserver{

    private SimulationEngine engine;
    private AbstractWorldMap map;
    private double mapHeight;
    private double mapWidth;
    private GeneratorType generator;
    private int initialGrassNumber;
    private int energyFromGrass;
    private int grassRespawnNumber;
    private MovementType movementType;
    private MutationType mutationType;
    private int initialAnimalNumber;
    private int initialAnimalEnergy;
    private int animalReproductionEnergy;
    private int animalReproductionCost;
    private int genotypeLength;
    private int minMutationNumber;
    private int maxMutationNumber;
    private GridPane gridPane;
    private double windowWidth;
    private double windowHeight;
    private double cellWidth;
    private double cellHeight;
    private Scene scene;
    private Thread engineThread;
    private boolean start = true;
    private Label animalGenotype;
    private Label animalCurrentGene;
    private Label animalEnergy;
    private Label animalGrassEaten;
    private Label animalChilds;
    private Label animalAge;
    private Label animalDeath;
    private Animal selectedAnimal;
    private Label simulationAnimals;
    private Label simulationGrass;
    private Label simulationFreeFields;
    private Label simulationGenotypes;
    private Label simulationEnergy;
    private Label simulationAge;

    public void init(){
        this.mapHeight = 50;
        this.mapWidth = 50;
        this.initialGrassNumber = 100;
        this.grassRespawnNumber = 10;
        this.generator = GeneratorType.FORESTED_EQUATOR;
        this.map = new GlobeMap((int)this.mapWidth,(int)this.mapHeight,this.initialGrassNumber, this.generator);
        this.initialAnimalNumber = 100;
        this.genotypeLength = 10;
        this.movementType = MovementType.FULL_PREDESTINATION;
        this.mutationType = MutationType.BLESSRNG;
        this.animalReproductionEnergy = 15;
        this.animalReproductionCost = 10;
        this.minMutationNumber = 0;
        this.maxMutationNumber = 3;
        this.initialAnimalEnergy = 30;
        this.energyFromGrass = 4;
        this.engine = new SimulationEngine(this.map,this.initialAnimalNumber,this.genotypeLength,this.movementType,this.initialAnimalEnergy,
                this.energyFromGrass, this.grassRespawnNumber, this.animalReproductionEnergy, this.animalReproductionCost, this.mutationType, this.minMutationNumber, this.maxMutationNumber);
        this.engine.addObserver(this);
        this.engineThread = new Thread(this.engine);
        this.engineThread.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.gridPane = new GridPane();
        this.gridPane.setGridLinesVisible(true);
        this.gridPane.setHgap(0);
        this.gridPane.setVgap(0);

        if( this.mapWidth > this.mapHeight ){
            this.windowWidth = 800;
            this.windowHeight = (this.mapHeight/this.mapWidth)*this.windowWidth;
        }
        else if( this.mapWidth < this.mapHeight ){
            this.windowHeight = 800;
            this.windowWidth = (this.mapWidth/this.mapHeight)*this.windowHeight;
        }
        else{
            this.windowHeight = 800;
            this.windowWidth = 800;
        }

        this.cellWidth = this.windowWidth/this.mapWidth;
        this.cellHeight = this.windowHeight/this.mapHeight;

        for (int i = 0; i < this.mapHeight; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(this.cellHeight));
        }
        for (int i = 0; i < this.mapWidth; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(this.cellWidth));
        }

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        Button button = new Button("Stop");
        button.setOnAction(event -> {
            this.start = !this.start;
            if(this.start){
                button.setText("Stop");
                this.engineThread.resume();
            }
            else{
                button.setText("Start");
                this.engineThread.suspend();
            }
        });
        GridPane.setHalignment(button, HPos.CENTER);
        gridPane.add(button, 0,10 , 1, 1);

        root.getChildren().add(button);
        root.getChildren().add(this.gridPane);
        HBox info = new HBox();
        info.setAlignment(Pos.CENTER);
        info.setSpacing(50);
        VBox animalInfo = new VBox();
        animalInfo.setAlignment(Pos.TOP_LEFT);
        this.animalGenotype = new Label("Genotyp: ");
        this.animalCurrentGene = new Label("Aktualny Gen");
        this.animalEnergy = new Label("Energia: ");
        this.animalGrassEaten = new Label("Zjedzona trawka: ");
        this.animalChilds = new Label("Ilość dzieci: ");
        this.animalAge = new Label("Wiek: ");
        this.animalDeath = new Label("Data śmierci: ");
        animalInfo.getChildren().addAll(this.animalGenotype, this.animalCurrentGene, this.animalEnergy, this.animalGrassEaten, this.animalChilds, this.animalAge, this.animalDeath);
        VBox simulationInfo = new VBox();
        simulationInfo.setAlignment(Pos.TOP_RIGHT);
        this.simulationAnimals = new Label("Ilość zwierząt: ");
        this.simulationGrass = new Label("Ilość trawy: ");
        this.simulationFreeFields = new Label("Ilość wolnych pól: ");
        this.simulationGenotypes = new Label("Najpopularniejszy Genotyp: ");
        this.simulationEnergy = new Label("Średni poziom energii: ");
        this.simulationAge = new Label("Średnia długość życia: ");
        simulationInfo.getChildren().addAll(this.simulationAnimals, this.simulationGrass, this.simulationFreeFields, this.simulationGenotypes, this.simulationEnergy, this.simulationAge);
        info.getChildren().addAll(animalInfo, simulationInfo);
        root.getChildren().add(info);

        this.renderScene();

        this.scene = new Scene(root, this.windowWidth, this.windowHeight+150);
        primaryStage.setScene(this.scene);
        primaryStage.show();
    }

    void renderScene(){
        this.getAnimalInfo();
        this.getSimulationInfo();
        for (int x = 0; x < this.mapWidth; x++) {
            for (int y = 0; y < this.mapHeight; y++) {
                Vector2d position = new Vector2d(x, y);
                if (this.map.isOccupied(position)) {
                    Object element = this.map.objectAt(position);
                    if( element instanceof Animal){
                        int animalEnergy = ((Animal) element).getHealth();
                        Color animalColor;
                        if(animalEnergy >= this.initialAnimalEnergy){
                            animalColor = Color.rgb(255,0,0);
                        }
                        else{
                            animalColor = Color.rgb(255,(255/this.initialAnimalEnergy)*(this.initialAnimalEnergy-animalEnergy),(255/this.initialAnimalEnergy)*(this.initialAnimalEnergy-animalEnergy));
                        }
                        Circle animal = new Circle(this.cellWidth/2, animalColor);
                        GridPane.setHalignment(animal, HPos.CENTER);
                        gridPane.add(animal, x, (int)this.mapHeight-y-1, 1, 1);
                        animal.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                            selectedAnimal = ((Animal) element);
                            this.getAnimalInfo();
                        });
                    }
                    else{
                        Rectangle grass = new Rectangle(this.cellWidth-2, this.cellHeight-2, Color.GREEN);
                        GridPane.setHalignment(grass, HPos.CENTER);
                        gridPane.add(grass, x, (int)this.mapHeight-y-1, 1, 1);
                    }
                }
            }
        }

        this.gridPane.setGridLinesVisible(true);
    }

    @Override
    public void refresh() {
        Platform.runLater( () -> {
            this.gridPane.getChildren().clear();
            this.gridPane.setGridLinesVisible(false);
            this.renderScene();
        });
    }

    public void getAnimalInfo(){
        if( this.selectedAnimal != null) {
            this.animalGenotype.setText("Genotyp: "+this.selectedAnimal.getGenotype().toString());
            this.animalCurrentGene.setText("Aktualny Gen: "+this.selectedAnimal.getGenotype().getCurrentGene());
            this.animalEnergy.setText("Energia: " + this.selectedAnimal.getHealth());
            this.animalGrassEaten.setText("Zjedzona trawka: "+this.selectedAnimal.getEatenGrass());
            this.animalChilds.setText("Ilość dzieci: "+this.selectedAnimal.getChildren());
            this.animalAge.setText("Wiek: "+this.selectedAnimal.getAge());
            if( this.selectedAnimal.getDeathDate() == 0)
            {
                this.animalDeath.setText("Data śmierci: ");
            }
            else{
                this.animalDeath.setText("Data śmierci: "+this.selectedAnimal.getDeathDate());
            }
        }
    }

    public void getSimulationInfo(){
        this.simulationAnimals.setText("Ilość zwierząt: "+this.map.getNumOfAnimals());
        this.simulationGrass.setText("Ilość trawy: "+this.map.getNumOfGrass());
        this.simulationFreeFields.setText("Ilość wolnych pól: "+this.map.getNumOfFreeFields());
        this.simulationGenotypes.setText("Najpopularniejszy Genotyp: "+this.map.getMostPopularGenotype());
        this.simulationEnergy.setText("Średni poziom energii: "+this.map.getAverageEnergy());
        this.simulationAge.setText("Średnia długość życia: "+this.map.getAverageLifespan());
    }
}
