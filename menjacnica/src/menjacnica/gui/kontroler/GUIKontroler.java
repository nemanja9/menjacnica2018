package menjacnica.gui.kontroler;

import java.awt.EventQueue;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;
import menjacnica.gui.DodajKursGUI;
import menjacnica.gui.IzvrsiZamenuGUI;
import menjacnica.gui.MenjacnicaGUI;
import menjacnica.gui.ObrisiKursGUI;
import menjacnica.gui.models.MenjacnicaTableModel;


public class GUIKontroler {
	public static MenjacnicaInterface menjacnica = new Menjacnica();
	public static MenjacnicaGUI gp;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIKontroler.gp = new MenjacnicaGUI();
					GUIKontroler.gp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				menjacnica.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(gp, e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				menjacnica.ucitajIzFajla(file.getAbsolutePath());
				gp.prikaziSveValute();
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(gp, e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	

	public static void prikaziDodajKursGUI() {
		
			DodajKursGUI prozor = new DodajKursGUI();
			prozor.setLocationRelativeTo(gp);
			prozor.setVisible(true);
			
		
	}

	public static void prikaziObrisiKursGUI() {

		if (MenjacnicaGUI.table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel) (MenjacnicaGUI.table.getModel());
			ObrisiKursGUI prozor = new ObrisiKursGUI(gp, model.vratiValutu(MenjacnicaGUI.table.getSelectedRow()));
			prozor.setLocationRelativeTo(gp);
			prozor.setVisible(true);
		}
	}
	public static void unesiKurs(int sifra, String naziv, double prodajni, double
			kupovni, double srednji, String skraceni) {
		try {
			Valuta v = new Valuta();
			
			v.setKupovni(kupovni);
			v.setNaziv(naziv);
			v.setProdajni(prodajni);
			v.setSifra(sifra);
			v.setSkraceniNaziv(skraceni);
			v.setSrednji(srednji);
			
			menjacnica.dodajValutu(v);

			gp.prikaziSveValute();

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void obrisiValutu(Valuta valuta) {
		try{
			menjacnica.obrisiValutu(valuta);
			
			if (menjacnica.vratiKursnuListu() != null && !(menjacnica.vratiKursnuListu().isEmpty()))
			gp.prikaziSveValute(menjacnica.vratiKursnuListu());
			
			
		} catch (Exception e1) {
			if (!(e1.getMessage().isEmpty()))
			JOptionPane.showMessageDialog(gp, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
			
		}
	}

	
	public static void izvrsiZamenu(Valuta valuta, boolean prodaja, double iznos){
		try{
			double konacniIznos = 
					menjacnica.izvrsiTransakciju(valuta,prodaja, iznos);
		
			IzvrsiZamenuGUI.textFieldKonacniIznos.setText("" + konacniIznos);
		} catch (Exception e1) {
		JOptionPane.showMessageDialog(gp, e1.getMessage(),
				"Greska", JOptionPane.ERROR_MESSAGE);
	}
	}
	

}
