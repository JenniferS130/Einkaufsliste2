import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class EinkaufslisteGui extends Application {

	Stage  window;
	Scene  sceneShopping;
	Button eingang;
	private double xOffset;
	private double yOffset;
	private Button newList;
	private Button yourList;
	private Button yourRecipes;
	private Button newRecipe;


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		window = primaryStage;

		//Scene Switch
		eingang = new Button("Your Shopping Helper");
		eingang.setStyle("-fx-font : normal bold 20px 'serif'; -fx-background-radius : 20; ");
		eingang.setOnAction(e -> window.setScene(sceneShopping));

		//Button Bar Main
		ButtonBar buttonBar = new ButtonBar();
		ButtonBar.setButtonData(eingang, ButtonBar.ButtonData.APPLY);
		buttonBar.getButtons().addAll(eingang);

		//Fenster Aufbau Main
		HBox box = new HBox(5);
		box.setPadding(new Insets(50, 50, 50, 55));
		box.getChildren().addAll(buttonBar);
		box.setStyle("-fx-background-color: BEIGE");


		BorderPane mainMenu = new BorderPane();
		mainMenu.setPrefSize(800, 400);
		mainMenu.setLeft(getShoppingMain(mainMenu));


		//Speichern der Methoden in neue Comboboxen für den Aufruf der Items
		ListView<String> list = new ListView<>();

		ComboBox<String> fruitBox = buildItemComBox("Fruits",
													new String[]{"Apple", "Banana", "Orange", "Strawberry", "Mango",
																 "Dragonfruit", "Peach", "Lemon"});
		ComboBox<String> bathroomBox = buildItemComBox("Bathroom Article",
													   new String[]{"Toilet Paper", "Shampoo", "Cream", "Lotion",
																	"Perfume", "Toilet Cleaner", "Toothpaste",
																	"Toothbrush", "Soap", "Washing Pads"});
		ComboBox<String> cookingBox = buildItemComBox("Cooking",
													  new String[]{"Minced Meat", "Noodles", "Sour Cream", "Bread",
																   "Toast", "Sauce", "Salt", "Pepper", "Seasoning",
																   "Pan", "Casserole Form", "Bowls", "Milk"});
		ComboBox<String> snackBox = buildItemComBox("Snacks",
													new String[]{"Sandwiches", "Crisp", "Chocolate", "Gummy Bears",
																 "Cookies", "Dried Fruits"});
		ComboBox<String> cleaningBox = buildItemComBox("Cleaning Products",
													   new String[]{"Toilet Cleaner", "Brushes", "Swiffer",
																	"Dust Towels", "Oven Cleaner", "Dish Washer Pads",
																	"Dish Soap"});
		TextField others = new TextField();

		//Buttons zum Abspeichern der Items
		String submitStyle = "-fx-font : normal 13px 'serif'";

		Button submitFruits = new Button("Add");
		submitFruits.setStyle(submitStyle);
		Button submitBathroom = new Button("Add");
		submitBathroom.setStyle(submitStyle);
		Button submitCooking = new Button("Add");
		submitCooking.setStyle(submitStyle);
		Button submitSnacks = new Button("Add");
		submitSnacks.setStyle(submitStyle);
		Button submitCleaning = new Button("Add");
		submitCleaning.setStyle(submitStyle);
		Button submitOthers = new Button("Add Other Items");
		submitOthers.setStyle(submitStyle);

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

		shoppingItems.getChildren().addAll(fruitsList, bathroomList, cookingList, snackList, cleaningList, otherList);
		newList.setOnAction(e -> mainMenu.setCenter(shoppingItems));

		//Aufbau des Listen Fensters
		VBox table = new VBox(5);
		table.setPadding(new Insets(0, 50, 100, 50));
		Button delete = new Button("Delete");

		//Überträgt die ausgewählten Items zu der Liste
		submitFruits.setOnAction(e -> {
			if(fruitBox.getSelectionModel().getSelectedIndex() >= 0)
				list.getItems().addAll(fruitBox.getItems().get(fruitBox.getSelectionModel().getSelectedIndex()));
		});
		submitBathroom.setOnAction(e -> {
			if(fruitBox.getSelectionModel().getSelectedIndex() >= 0)
				list.getItems().addAll(bathroomBox.getItems().get(bathroomBox.getSelectionModel().getSelectedIndex()));
		});
		submitCooking.setOnAction(e -> {
			if(fruitBox.getSelectionModel().getSelectedIndex() >= 0)
				list.getItems().addAll(cookingBox.getItems().get(cookingBox.getSelectionModel().getSelectedIndex()));
		});
		submitSnacks.setOnAction(e -> {
			if(fruitBox.getSelectionModel().getSelectedIndex() >= 0)
				list.getItems().addAll(snackBox.getItems().get(snackBox.getSelectionModel().getSelectedIndex()));
		});
		submitCleaning.setOnAction(e -> {
			if(fruitBox.getSelectionModel().getSelectedIndex() >= 0)
				list.getItems().addAll(cleaningBox.getItems().get(cleaningBox.getSelectionModel().getSelectedIndex()));
		});
		submitOthers.setOnAction(e -> list.getItems().addAll(others.getText()));

		table.getChildren().addAll(list, delete);
		yourList.setOnAction(e -> mainMenu.setCenter(table));

		//Delete Button
		delete.setOnAction(e -> deleteFromList(list));

		//Aufbau Rezepte Eintragen
		VBox               rezepteFenster = new VBox(5);
		ArrayList<Zutat>   zutaten        = new ArrayList<Zutat>();
		ArrayList<Schritt> schritte       = new ArrayList<Schritt>();

		Label     rezeptName    = new Label("Rezept Name:");
		TextField rezeptEintrag = new TextField();

		Label     zutatLabel = new Label("Zutat");
		TextField zutat      = new TextField();
		TextField menge      = new TextField();
		Button    neueZutat  = new Button("Add");
		neueZutat.setOnAction(event -> zutaten.add(new Zutat(zutat.getText(), menge.getText())));
		HBox zutatInfos = new HBox(zutat, menge, neueZutat);
		VBox zutatNode  = new VBox(zutatLabel, zutatInfos);


		Label     zubereitung   = new Label("Zubereitung");
		Label     schrittNummer = new Label("1");
		TextField schritt       = new TextField();
		Button    neuerSchritt  = new Button("Add");
		neuerSchritt.setOnAction(event -> {
			schritte.add(new Schritt(Integer.parseInt(schrittNummer.getText()), schritt.getText()));
			schrittNummer.setText(String.valueOf(Integer.parseInt(schrittNummer.getText()) + 1));
		});
		HBox schrittInfo = new HBox(schrittNummer, schritt, neuerSchritt);
		VBox schrittNode = new VBox(zubereitung, schrittInfo);

		Button addRecipe = new Button("Rezept hinzufügen");
		addRecipe.setOnAction(event -> {});


		rezepteFenster.getChildren().addAll(rezeptName, rezeptEintrag, zutatNode, schrittNode);
		newRecipe.setOnAction(e -> mainMenu.setCenter(rezepteFenster));


		sceneShopping = prepareScene(mainMenu);
		mainMenu.setStyle("-fx-background-color: BEIGE");
		Scene scene = prepareScene(box);

		window = new Stage();
		window.initStyle(StageStyle.UNDECORATED);
		window.setTitle("Shopping Helper");
		window.setScene(scene);
		window.show();


	}

	private void deleteFromList(ListView<String> list) {
		int selectedItem = list.getSelectionModel().getSelectedIndex();
		if(selectedItem >= 0) {
			int newSelectedIdx = (selectedItem == list.getItems().size() - 1) ? selectedItem - 1 : selectedItem;

			list.getItems().remove(selectedItem);
			list.getSelectionModel().select(newSelectedIdx);
		}
	}

	private VBox getShoppingMain(BorderPane mainMenu) {
		//Fenster Aufbau Shopping
		String style = "-fx-font : normal 17px 'serif'; -fx-background-radius : 20;";

		newList = new Button("New List");
		newList.setStyle(style);
		yourList = new Button("Your List");
		yourList.setStyle(style);
		yourRecipes = new Button("Your Recipes");
		yourRecipes.setStyle(style);
		newRecipe = new Button("New Recipe");
		newRecipe.setStyle(style);

		//Main Menu Aufbau
		VBox layoutList = new VBox(20);
		mainMenu.setPadding(new Insets(110, 50, 50, 50));
		layoutList.getChildren().addAll(eingang, newList, yourList, yourRecipes, newRecipe);
		return layoutList;
	}

	//ComboBoxen für die New List
	private static ComboBox<String> buildItemComBox(String name, String[] items) {
		ComboBox<String> fruits = new ComboBox<String>();
		fruits.getItems().addAll(items);

		fruits.setPromptText(name);
		return fruits;
	}

	private static ComboBox bathroomBox() {
		ComboBox bathroom = new ComboBox();
		bathroom.getItems().addAll();

		bathroom.setPromptText("");
		return bathroom;
	}

	private static ComboBox cookingBox() {
		ComboBox cooking = new ComboBox();
		cooking.getItems().addAll();

		cooking.setPromptText("");
		return cooking;
	}

	private static ComboBox snackBox() {
		ComboBox snacks = new ComboBox();
		snacks.getItems().addAll(

								);

		snacks.setPromptText("");
		return snacks;
	}

	private static ComboBox cleaningBox() {
		ComboBox Cleaning = new ComboBox();
		Cleaning.getItems().addAll();

		Cleaning.setPromptText("");
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