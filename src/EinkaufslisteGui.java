import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EinkaufslisteGui extends Application {

	Stage  window;
	Scene  sceneShopping;
	Button eingang;
	private double            xOffset;
	private double            yOffset;
	private Button            newList;
	private Button            yourList;
	private Button            yourRecipes;
	private Button            newRecipe;
	private ArrayList<Recipe> customRezepte = new ArrayList<>();
	private BorderPane        mainMenu;
	private ListView<String>  list;


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
		box.setPadding(new Insets(50, 50, 50, 50));
		box.getChildren().addAll(buttonBar);
		box.setStyle("-fx-background-color: BEIGE");


		mainMenu = new BorderPane();
		mainMenu.setPrefSize(800, 400);
		mainMenu.setLeft(getShoppingMain(mainMenu));


		//Speichern der Methoden in neue Comboboxen für den Aufruf der Items
		list = new ListView<>();

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
		shoppingItems.setPadding(new Insets(0, 50, 50, 50));
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
		delete.setOnAction(e -> deleteFromList(list));

		yourRecipes.setOnAction(actionEvent -> {
			mainMenu.setCenter(buildCustomRecipeList());
		});

		VBox rezepteFenster = buildNewRecipe();
		newRecipe.setOnAction(e -> mainMenu.setCenter(rezepteFenster));

		loadCustomRecipes();

		sceneShopping = prepareScene(mainMenu);
		mainMenu.setStyle("-fx-background-color: BEIGE");
		Scene scene = prepareScene(box);

		window = new Stage();
		window.initStyle(StageStyle.UNDECORATED);
		window.setTitle("Shopping Helper");
		window.setScene(scene);
		window.show();


	}

	private void loadCustomRecipes() {
		try {
			File    file    = new File("rezepte.txt");
			Scanner scanner = new Scanner(file);

			while(scanner.hasNextLine()) {
				String line;

				try {
					line = scanner.nextLine();
				} catch(NoSuchElementException e) {
					break;
				}

				String[] strings = line.split(";");

				String rezeptName = strings[0];

				ArrayList<Zutat> zutaten = new ArrayList<>();
				for(String tmpString : strings[1].split("\\|")) {
					String[] tmpSplitString = tmpString.split(",");
					zutaten.add(new Zutat(tmpSplitString[0], tmpSplitString[1]));
				}

				ArrayList<Schritt> schritt = new ArrayList<>();
				int                i       = 1;
				for(String tmpString : strings[2].split("\\|")) {
					schritt.add(new Schritt(i, tmpString));
					i++;
				}
				customRezepte.add(new Recipe(rezeptName, zutaten, schritt));
			}

		} catch(IOException e) {
			System.out.println(e);
		}
	}

	private VBox buildCustomRecipeList() {
		if(customRezepte.size() == 0)
			return null;

		VBox vBox = new VBox();
		vBox.setMaxWidth(290);
		for(Recipe recipe : customRezepte) {
			Label name = new Label(recipe.getRezeptTitel());
			name.setPrefWidth(150);
			Label zutaten = new Label(String.valueOf(recipe.getZutaten().size()));
			zutaten.setPrefWidth(50);
			Label schritte = new Label(String.valueOf(recipe.getSchritte().size()));
			schritte.setPrefWidth(50);
			HBox hBox = new HBox(name, zutaten, schritte);
			hBox.setOnMouseClicked(mouseEvent -> mainMenu.setCenter(buildRecipeScene(recipe)));
			hBox.setStyle("-fx-background-color:#FFFFFF;-fx-border-width:0.5;-fx-border-style:solid");

			Button addIngredients = new Button("Add");
			addIngredients.setOnAction(actionEvent -> addListIngredient(recipe));
			addIngredients.setAlignment(Pos.CENTER_RIGHT);
			hBox.getChildren().add(addIngredients);

			vBox.getChildren().add(hBox);
		}

		vBox.setSpacing(5);
		return vBox;
	}

	private void addListIngredient(Recipe recipe) {
		for(Zutat zutat : recipe.getZutaten()) {
			list.getItems().add(zutat.zutat + " " + zutat.menge);
		}
	}

	private VBox buildRecipeScene(Recipe recipe) {
		Label rezeptTitel = new Label(recipe.getRezeptTitel());
		rezeptTitel.setStyle("-fx-font-size:14;-fx-font-weight:bold");


		Label zutaten = new Label("Zutaten:");
		zutaten.setStyle("-fx-font-weight:bold");
		VBox vBox = new VBox(rezeptTitel, zutaten);
		for(Zutat zutat : recipe.getZutaten()) {
			vBox.getChildren().add(new Label(zutat.zutat + " " + zutat.menge));
		}

		Label schritte = new Label("Schritte:");
		schritte.setStyle("-fx-font-weight:bold");
		vBox.getChildren().add(schritte);
		for(Schritt schritt : recipe.getSchritte()) {
			vBox.getChildren().add(new Label(schritt.nummer + ". " + schritt.beschreibung));
		}

		Button addIngredient = new Button("Add");
		addIngredient.setOnAction(actionEvent -> addListIngredient(recipe));
		vBox.getChildren().add(addIngredient);
		vBox.setPadding(new Insets(0, 0, 0, 50));
		vBox.setSpacing(5);

		return vBox;
	}

	private VBox buildNewRecipe() {
		//Aufbau Rezepte Eintragen
		VBox               rezepteFenster = new VBox(5);
		ArrayList<Zutat>   zutaten        = new ArrayList<Zutat>();
		ArrayList<Schritt> schritte       = new ArrayList<Schritt>();

		Label     rezeptName    = new Label("Rezept Name:");
		TextField rezeptEintrag = new TextField();

		Label     zutatLabel = new Label("Zutat / Menge");
		TextField zutat      = new TextField();

		TextField menge     = new TextField();
		Button    neueZutat = new Button("Add");
		neueZutat.setOnAction(event -> {
			zutaten.add(new Zutat(zutat.getText(), menge.getText()));
			zutat.setText("");
			menge.setText("");
		});
		HBox zutatInfos = new HBox(zutat, menge, neueZutat);
		VBox zutatNode  = new VBox(zutatLabel, zutatInfos);

		Label zubereitung = new Label("Zubereitung");

		Label     schrittNummer = new Label("1");
		TextField schritt       = new TextField();
		Button    neuerSchritt  = new Button("Add");
		neuerSchritt.setOnAction(event -> {
			schritte.add(new Schritt(Integer.parseInt(schrittNummer.getText()), schritt.getText()));
			schrittNummer.setText(String.valueOf(Integer.parseInt(schrittNummer.getText()) + 1));
			schritt.setText("");
		});
		HBox schrittInfo = new HBox(schrittNummer, schritt, neuerSchritt);
		VBox schrittNode = new VBox(zubereitung, schrittInfo);

		Button addRecipe = new Button("Rezept hinzufügen");
		addRecipe.setOnAction(event -> {
			Recipe recipe = new Recipe(rezeptEintrag.getText(), (ArrayList<Zutat>)zutaten.clone(),
									   (ArrayList<Schritt>)schritte.clone());
			customRezepte.add(recipe);
			writeNewRecipe(recipe);

			rezeptEintrag.setText("");
			zutat.setText("");
			menge.setText("");
			schritt.setText("");
			schrittNummer.setText("1");

			zutaten.clear();
			schritte.clear();
		});


		rezepteFenster.getChildren().addAll(rezeptName, rezeptEintrag, zutatNode, schrittNode, addRecipe);
		rezepteFenster.setPadding(new Insets(0, 0, 0, 50));
		return rezepteFenster;
	}

	private void writeNewRecipe(Recipe recipe) {

		String line = "";

		line += recipe.getRezeptTitel() + ";";

		for(Zutat zutat : recipe.getZutaten()) {
			line += zutat.zutat + "," + zutat.menge + "|";
		}
		line += ";";

		for(Schritt schritt : recipe.getSchritte()) {
			line += schritt.beschreibung + "|";
		}

		try {
			FileWriter file = new FileWriter("rezepte.txt", true);
			file.write(line + "\n");
			file.close();
		} catch(IOException e) {
			System.out.println(e);
		}


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
		toolPane.setRight(buildControls());
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

	private Node buildControls() {
		return new HBox(buildMinimizeButton(),buildCloseButton());
	}

	private Node buildMinimizeButton() {
		Button    minimize = new Button();
		ImageView graphic  = new ImageView(new Image("file:Pictures/-.png", 30d, 30d, false, false));
		minimize.setGraphic(graphic);
		minimize.setOnAction(event -> window.setIconified(true));
		minimize.setBackground(Background.EMPTY);
		return minimize;
	}

	private Button buildCloseButton() {
		Button close = new Button();
		ImageView value = new ImageView(new Image("file:Pictures/black-circle-close-button-png-5.png",30d,30d,false,false));
		close.setGraphic(value);
		close.setOnAction(event -> window.close());
		close.setBackground(Background.EMPTY);
		return close;
	}
}