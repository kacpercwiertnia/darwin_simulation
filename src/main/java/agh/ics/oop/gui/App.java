package agh.ics.oop.gui;

import agh.ics.oop.*;
import agh.ics.oop.map.AbstractWorldMap;
import agh.ics.oop.map.GeneratorType;
import agh.ics.oop.map.GlobeMap;
import agh.ics.oop.map.HellPortal;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.util.ArrayList;

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
    private GridPane gridPane;
    private double windowWidth;
    private double windowHeight;
    private double cellWidth;
    private double cellHeight;
    private Scene scene;
    private Thread engineThread;

    public void init(){
        this.mapHeight = 20;
        this.mapWidth = 20;
        this.initialGrassNumber = 50;
        this.grassRespawnNumber = 10;
        this.generator = GeneratorType.TOXIC_CORPSES;
        this.map = new HellPortal((int)this.mapWidth,(int)this.mapHeight,this.initialGrassNumber, this.generator);
        this.initialAnimalNumber = 30;
        this.genotypeLength = 5;
        this.movementType = MovementType.FULL_PREDESTINATION;
        this.initialAnimalEnergy = 5;
        this.energyFromGrass = 4;
        this.engine = new SimulationEngine(this.map,this.initialAnimalNumber,this.genotypeLength,this.movementType,this.initialAnimalEnergy,this.energyFromGrass, this.grassRespawnNumber);
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

        this.renderScene();

        this.scene = new Scene(this.gridPane, this.windowWidth, this.windowHeight);
        primaryStage.setScene(this.scene);
        primaryStage.show();
    }

    void renderScene(){
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
}
