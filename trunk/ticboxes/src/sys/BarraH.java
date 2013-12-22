package sys;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class BarraH extends JToolBar {

	JButton btnuevo;
	JButton btmodifi;
	JButton btbuscar;
	JButton btgrabar;
	JButton btcancel;
	JButton btpagar;
	JButton btimprim;
	JButton btprimer;
	JButton btanteri;
	JButton btsiguie;
	JButton btultimo;
	JButton btsalir;
	static int sel=0;

	public BarraH(int i) {
		sel = i;
		btnuevo = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/New24.gif")));
		btmodifi = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/Edit24.gif")));
		btgrabar = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/Save24.gif")));
		btcancel = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/Cancel24.gif")));
		btpagar = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/Payment24.gif")));
		btimprim = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/Print24.gif")));
		btprimer = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/wfStart24.gif")));
		btanteri = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/wfBack24.gif")));
		btbuscar = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/Find24.gif")));
		btsiguie = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/wfNext24.gif")));
		btultimo = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/wfEnd24.gif")));
		btsalir = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/Exit24.gif")));

		setFloatable(false);
	
			add(btnuevo);
			add(btmodifi);
			add(btgrabar);
			add(btcancel);
			add(btpagar);
			add(btimprim);
			add(btbuscar);
			add(btanteri);
			add(btsiguie);
			add(btultimo);
			add(btsalir);						
		
	}
	
	public void Edicion() {
			btnuevo.setEnabled(false);
			btmodifi.setEnabled(false);
			btbuscar.setEnabled(false);
			btpagar.setEnabled(false);
			btimprim.setEnabled(false);
			btanteri.setEnabled(false);
			btsiguie.setEnabled(false);
			btgrabar.setEnabled(true);
			btcancel.setEnabled(true);
			btprimer.setEnabled(false);
			btultimo.setEnabled(false);
			btsalir.setEnabled(false);
	}

	public void Consulta() {
			btnuevo.setEnabled(true);
			btmodifi.setEnabled(true);
			btbuscar.setEnabled(true);
			btanteri.setEnabled(true);
			btsiguie.setEnabled(true);
			btimprim.setEnabled(true);
			btpagar.setEnabled(true);
			btgrabar.setEnabled(false);
			btcancel.setEnabled(false);
			btprimer.setEnabled(true);
			btultimo.setEnabled(true);
			btsalir.setEnabled(true);	
	}

}
