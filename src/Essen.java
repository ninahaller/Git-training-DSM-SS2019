import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class Essen extends JFrame implements ActionListener {

	// Essen und Preise
	private String essen = "";
	private String menge = "";
	private Double gp = 0d;
	private String p = "" + gp;

	// Grafische Elemente
	private JLabel artikel = new JLabel("Artikel");
	private Choice artikelEingabe = new Choice();

	private JLabel anzahl = new JLabel("Anzahl");
	private JTextField anzahlEingabe = new JTextField(this.menge, 10);

	private JButton kaufen = new JButton("Kaufen");
	private JLabel bisher = new JLabel("Bisher gekauft");
	private List einkaufsliste = new List();
	private JLabel gesamtPreis1 = new JLabel("Gesamtpreis: ");
	private JTextField gesamtPreis2 = new JTextField(p, 10);


	Essen() {
		init();
	}

	private void init() {

		this.setSize(400, 400);
		this.setTitle("Wie viel kostet mein Essen?");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);

		mainPanel.add(this.artikel);
		this.artikel.setBounds(10, 20, 50, 20);

		mainPanel.add(this.artikelEingabe);
		this.artikelEingabe.setBounds(60, 20, 80, 20);
		for (final Item artikel : Item.values()) {
			// Alle Artikel als String aus enum hinzufuegen
			this.artikelEingabe.addItem(artikel.name());
		}

		mainPanel.add(this.anzahl);
		this.anzahl.setBounds(10, 50, 50, 20);

		mainPanel.add(this.anzahlEingabe);
		this.anzahlEingabe.setBounds(60, 50, 80, 20);
		this.anzahlEingabe.setText("1");

		mainPanel.add(this.kaufen);
		this.kaufen.setBounds(10, 100, 100, 20);

		mainPanel.add(this.bisher);
		this.bisher.setBounds(200, 10, 150, 20);

		mainPanel.add(this.einkaufsliste);
		this.einkaufsliste.setBounds(200, 50, 150, 200); // Einkaufslisten Anzeige

		mainPanel.add(this.gesamtPreis1);
		this.gesamtPreis1.setBounds(200, 190, 300, 200);

		mainPanel.add(this.gesamtPreis2);
		this.gesamtPreis2.setBounds(200, 320, 150, 20); // Gesamtpreis Ausgabe und verrechnet

		this.setContentPane(mainPanel);

		this.kaufen.addActionListener(this);

	}

	public void actionPerformed(final ActionEvent evt) {
		final Object src = evt.getSource();
		if (src == this.kaufen) {
			// Artikel-String wieder in enum wandeln
			final Item item = Item.fromString(this.artikelEingabe.getSelectedItem());
			if (item == null) {
				// kein passender Eintrag gefunden -> nichts weiter tun
				return;
			}

			final Integer anzahl = parseInteger(this.anzahlEingabe.getText());
			final Double gesamtPreis = item.getPreis() * anzahl;
			this.gp = this.gp + gesamtPreis;
			DecimalFormat df = new DecimalFormat("0.00");
			this.gesamtPreis2.setText(df.format(this.gp) + " €");
			this.einkaufsliste.add(item.alsStringMitPreis(anzahl));
		}
	}

	private static Integer parseInteger(final String value) {
		try {
			final Integer result = Integer.parseInt(value);
			return result > 0 ? result : 1; // keine negativen Werte
		}
		catch (final NumberFormatException ex) {
			return 1; // default
		}
	}

	// Artikel mit Preis
	enum Item {
		Snickers(0.70),
		Mars(0.70),
		Semmel(1.10),
		Wasser(1.20),
		Cola(1.50);

		private final Double preis;

		Item(final Double preis) {
			this.preis = preis;
		}

		Double getPreis() {
			return this.preis;
		}

		String alsStringMitPreis(final Integer anzahl) {
			return String.format("%s %dx %.2f €", this.name(), anzahl, this.preis * anzahl);
		}

		static Item fromString(final String value) {
			if (value == null) {
				return null;
			}

			for (final Item a : Item.values()) {
				if (a.name().equalsIgnoreCase(value)) {
					return a;
				}
			}

			return null;
		}
	}
}