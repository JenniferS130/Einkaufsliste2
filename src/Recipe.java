import java.util.ArrayList;

public class Recipe {
	private final String             rezeptTitel;
	private final ArrayList<Zutat>   zutaten;
	private final ArrayList<Schritt> schritte;

	public Recipe(String rezeptTitel, ArrayList<Zutat> zutaten, ArrayList<Schritt> schritte) {
		this.rezeptTitel = rezeptTitel;
		this.zutaten     = zutaten;
		this.schritte    = schritte;
	}

	public String getRezeptTitel() {
		return rezeptTitel;
	}

	public ArrayList<Zutat> getZutaten() {
		return zutaten;
	}

	public ArrayList<Schritt> getSchritte() {
		return schritte;
	}
}
