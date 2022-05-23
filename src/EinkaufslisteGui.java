import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.stage.StageStyle;

public class EinkaufslisteGui extends Application {

    Stage window;
    Scene sceneShopping;
    Button eingang = new Button("Your Shopping Helper");
    private double xOffset;
    private double yOffset;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;

        //Button Bar Main
        ButtonBar buttonBar = new ButtonBar();
        ButtonBar.setButtonData(eingang, ButtonBar.ButtonData.APPLY);
        buttonBar.getButtons().addAll(eingang);

        //Fenster Aufbau Main
        HBox box = new HBox(5);
        box.setPadding(new Insets(50, 50, 50, 55));
        box.getChildren().addAll(buttonBar);
        box.setStyle("-fx-background-color: BEIGE");
        eingang.setStyle("-fx-font : normal bold 20px 'serif'; -fx-background-radius : 20; ");

        //Scene Switch
        eingang.setOnAction(e -> window.setScene(sceneShopping));

        //Fenster Aufbau Shopping
        Button newList = new Button("New List");
        newList.setStyle("-fx-font : normal 17px 'serif'; -fx-background-radius : 20;");
        Button yourList = new Button("Your List");
        yourList.setStyle("-fx-font : normal 17px 'serif'; -fx-background-radius : 20;");
        Button yourRecipes = new Button("Your Recipes");
        yourRecipes.setStyle("-fx-font : normal 17px 'serif'; -fx-background-radius : 20;");
        Button newRecipe = new Button("New Recipe");
        newRecipe.setStyle("-fx-font : normal 17px 'serif'; -fx-background-radius : 20;");
        BorderPane mainMenu = new BorderPane();

        //Main Menu Aufbau
        mainMenu.setPrefSize(800,400);
        VBox layoutList = new VBox(20);
        mainMenu.setLeft(layoutList);
        mainMenu.setPadding(new Insets(110, 50, 50, 50));
        layoutList.getChildren().addAll(eingang, newList, yourList,  yourRecipes, newRecipe);

        //Speichern der Methoden in neue Comboboxen für den Aufruf der Items
        ListView list = new ListView();
        ComboBox fruitBox = fruitBox();
        ComboBox bathroomBox = bathroomBox();
        ComboBox cookingBox = cookingBox();
        ComboBox snackBox = snackBox();
        ComboBox cleaningBox = cleaningBox();
        TextField others = new TextField();

        //Buttons zum abspeichern der Items
        Button submitFruits = new Button("Add");
        submitFruits.setStyle("-fx-font : normal 13px 'serif' ");
        Button submitBathroom = new Button("Add");
        submitBathroom.setStyle("-fx-font : normal 13px 'serif' ");
        Button submitCooking = new Button("Add");
        submitCooking.setStyle("-fx-font : normal 13px 'serif' ");
        Button submitSnacks= new Button("Add");
        submitSnacks.setStyle("-fx-font : normal 13px 'serif' ");
        Button submitCleaning = new Button("Add");
        submitCleaning.setStyle("-fx-font : normal 13px 'serif' ");
        Button submitOthers = new Button("Add Other Items");
        submitOthers.setStyle("-fx-font : normal 13px 'serif' ");

        //Aufbau des Item Selection Fenster
        VBox shoppingItems = new VBox(5);
        shoppingItems.setPadding(new Insets(0, 50, 50, 200));
        shoppingItems.setSpacing(10);
        HBox fruitsList = new HBox(fruitBox, submitFruits);
        fruitsList.setSpacing(5);
        HBox bathroomList = new HBox(bathroomBox, submitBathroom);
        bathroomList.setSpacing(5);
        HBox cookingList = new HBox(cookingBox, submitCooking);
        cookingList.setSpacing(5);
        HBox snackList = new HBox(snackBox, submitSnacks);
        snackList.setSpacing(5);
        HBox cleaningList = new HBox(cleaningBox, submitCleaning);
        cleaningList.setSpacing(5);
        HBox otherList = new HBox(others, submitOthers);
        otherList.setSpacing(5);

        shoppingItems.getChildren().addAll( fruitsList, bathroomList, cookingList, snackList, cleaningList, otherList);
        newList.setOnAction(e -> mainMenu.setCenter(shoppingItems));

        //Aufbau des Listen Fensters
        VBox table = new VBox(5);
        table.setPadding(new Insets(0, 50, 100, 50));
        Button delete = new Button("Delete");

        //Überträgt die ausgewählten Items zu der Liste
        submitFruits.setOnAction(e-> list.getItems().addAll(fruitBox.getItems().get(fruitBox.getSelectionModel().getSelectedIndex())));
        submitBathroom.setOnAction(e-> list.getItems().addAll(bathroomBox.getItems().get(bathroomBox.getSelectionModel().getSelectedIndex())));
        submitCooking.setOnAction(e-> list.getItems().addAll(cookingBox.getItems().get(cookingBox.getSelectionModel().getSelectedIndex())));
        submitSnacks.setOnAction(e-> list.getItems().addAll(snackBox.getItems().get(snackBox.getSelectionModel().getSelectedIndex())));
        submitCleaning.setOnAction(e-> list.getItems().addAll(cleaningBox.getItems().get(cleaningBox.getSelectionModel().getSelectedIndex())));
        submitOthers.setOnAction(e-> list.getItems().addAll(others.getText()));

        table.getChildren().addAll(list, delete);
        yourList.setOnAction(e -> mainMenu.setCenter(table));

        //Delete Button
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int selectedItem = list.getSelectionModel().getSelectedIndex();
                if (selectedItem != -1){
                    String itemToRemove = (String) list.getSelectionModel().getSelectedItem();

                    int newSelectedIdx =
                            (selectedItem == list.getItems().size()-1)
                                    ? selectedItem -1
                                    : selectedItem;

                    list.getItems().remove(selectedItem);
                    list.getSelectionModel().select(newSelectedIdx);
                }
            }
        });

        //Aufbau Rezepte Eintragen
        VBox rezepteFenster = new VBox(5);
        TextArea rezeptEintrag = new TextArea();
        rezepteFenster.getChildren().addAll(rezeptEintrag);
        newRecipe.setOnAction(e-> mainMenu.setCenter(rezepteFenster));



        sceneShopping = prepareScene(mainMenu);
        mainMenu.setStyle("-fx-background-color: BEIGE");
        Scene scene = prepareScene(box);

        window = new Stage();
        window.initStyle(StageStyle.UNDECORATED);
        window.setTitle("Shopping Helper");
        window.setScene(scene);
        window.show();


    }
    //ComboBoxen für die New List
    private static ComboBox fruitBox() {
        ComboBox fruits = new ComboBox();
        fruits.getItems().addAll(
                "Apple", "Banana",
                "Orange", "Strawberry",
                "Mango", "Dragonfruit",
                "Peach", "Lemon"
        );

        fruits.setPromptText("Fruits");
        return fruits;
    }

    private static ComboBox bathroomBox() {
        ComboBox bathroom = new ComboBox();
        bathroom.getItems().addAll(
                "Toilet Paper", "Shampoo",
                "Cream", "Lotion",
                "Parfume", "Toilet Cleaner",
                "Toothpaste", "Toothbrush",
                "Soap", "Washing Pads"
        );

        bathroom.setPromptText("Bathroom Article");
        return bathroom;
    }

    private static ComboBox cookingBox() {
        ComboBox cooking = new ComboBox();
        cooking.getItems().addAll(
                "Minced Meat", "Noodles",
                "Sour Cream", "Bread",
                "Toast", "Sauce",
                "Salt", "Pepper",
                "Seasoning", "Pan",
                "Casserole Form", "Bowls", "Milk"
        );

        cooking.setPromptText("Cooking");
        return cooking;
    }

    private static ComboBox snackBox() {
        ComboBox snacks = new ComboBox();
        snacks.getItems().addAll(
                "Sandwiches", "Crisp",
                "Chocolate", "Gummies",
                "Cookies", "Dried Fruits"

        );

        snacks.setPromptText("Snacks");
        return snacks;
    }

    private static ComboBox cleaningBox(){
        ComboBox Cleaning = new ComboBox();
        Cleaning.getItems().addAll(
                "Toilet Cleaner", "Brushes",
                "Swiffer", "Dust Towels",
                "Oven Cleaner", "Dish Washer Pads",
                "Dishsoap"
        );

        Cleaning.setPromptText("Cleaning Products");
        return Cleaning;
    }

    private Scene prepareScene(Node sourceNode) {
        BorderPane toolPane = new BorderPane();
        toolPane.setMinHeight(30.0d);
        toolPane.setStyle("-fx-background-color:#444444");
        toolPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        toolPane.setOnMouseDragged(event -> {
            window.setX(event.getScreenX() - xOffset);
            window.setY(event.getScreenY() - yOffset);
        });

        BorderPane sceneCore = new BorderPane(sourceNode);
        sceneCore.setTop(toolPane);
        return new Scene(sceneCore);
    }


}
