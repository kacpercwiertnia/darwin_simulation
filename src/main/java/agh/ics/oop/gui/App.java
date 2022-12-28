package agh.ics.oop.gui;

import agh.ics.oop.MovementType;
import agh.ics.oop.MutationType;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.map.AbstractWorldMap;
import agh.ics.oop.map.GeneratorType;
import agh.ics.oop.map.GlobeMap;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class App extends Application {

    private SimulationEngine engine;
    private AbstractWorldMap map;
    private int mapHeight;
    private int mapWidth;
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
    private int windowWidth;
    private int windowHeight;
    private int cellWidth;
    private int cellHeight;
    private Scene scene;

    public void init(){
        this.mapHeight = 20;
        this.mapWidth = 20;
        this.initialGrassNumber = 20;
        this.generator = GeneratorType.FORESTED_EQUATOR;
        this.map = new GlobeMap(this.mapWidth,this.mapHeight,this.initialGrassNumber, this.generator);
        this.initialAnimalNumber = 8;
        this.genotypeLength = 5;
        this.movementType = MovementType.FULL_PREDESTINATION;
        this.initialAnimalEnergy = 5;
        this.energyFromGrass = 10;
        this.engine = new SimulationEngine(this.map,this.initialAnimalNumber,this.genotypeLength,this.movementType,this.initialAnimalEnergy,this.energyFromGrass);

        this.engine.run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.gridPane = new GridPane();
        this.gridPane.setGridLinesVisible(true);
        this.gridPane.setHgap(0);
        this.gridPane.setVgap(0);

        if( this.mapWidth > this.mapHeight ){
            this.windowWidth = 1000;
            this.windowHeight = (int) Math.ceil((this.mapHeight/this.mapWidth)*this.windowWidth);
        }
        else if( this.mapWidth < this.mapHeight ){
            this.windowHeight = 1000;
            this.windowWidth = (int) Math.ceil((this.mapWidth/this.mapHeight)*this.windowHeight);
        }
        else{
            this.windowHeight = 1000;
            this.windowWidth = 1000;
        }

        this.cellWidth = (int) Math.floor(this.windowWidth/this.mapWidth);
        this.cellHeight = (int) Math.floor(this.windowHeight/this.mapHeight);

        Label xyLabel = new Label("y\\x");
        GridPane.setHalignment(xyLabel, HPos.CENTER);
        this.gridPane.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        this.gridPane.getRowConstraints().add(new RowConstraints(cellHeight));
        this.gridPane.add(xyLabel, 0, 0, 1, 1);

        this.scene = new Scene(this.gridPane, this.windowWidth, this.windowHeight);
        primaryStage.setScene(this.scene);
        primaryStage.show();
    }
}
