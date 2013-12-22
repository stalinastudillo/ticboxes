package sys;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.Embalaje;
import data.Envio;
import data.Factura;
import data.Facturacion;
import data.Persona;
import data.Tarifa;
import data.TarifaIpostel;
import data.Unidad;


import sys.CamaraWeb;

public class AdminFactura extends JInternalFrame implements FocusListener, ActionListener {
	JPanel p1 = new JPanel();
	JPanel p11 = new JPanel();
	JPanel p22 = new JPanel();
	JLabel lbremite = new JLabel("Remitente");
	JLabel lbrcedul = new JLabel("Cédula");
	JLabel lbrapell = new JLabel("Apellidos");
	JLabel lbrnombr = new JLabel("Nombres");
	JLabel lbrdirec = new JLabel("Dirección");
	JLabel lbrtelef = new JLabel("Teléfono");
	JLabel lbrciuda = new JLabel("Ciudad");
	JLabel lbrfoto  = new JLabel("Foto");
	JLabel lbrrif   = new JLabel("RIF");
	JLabel lbrrazon = new JLabel("Razón Social");
	JLabel lbrdirfi = new JLabel("Direccion Fiscal");
	JLabel lbrtelfi = new JLabel("Telefono");
	JLabel lbrciufi = new JLabel("Ciudad");
	static JTextField tfrcedul = new JTextField(7);
	static JTextField tfrapell = new JTextField(15);
	static JTextField tfrnombr = new JTextField(15);
	static JTextField tfrdirec = new JTextField(25);
	static JTextField tfrtelef = new JTextField(6);
	JTextField tfrciuda = new JTextField(8);
	static JTextField tfrrif   = new JTextField(7);
	static JTextField tfrrazon = new JTextField(25);
	static JTextField tfrdirfi = new JTextField(25);
	JTextField tfrtelfi = new JTextField(6);
	JTextField tfrciufi = new JTextField(8);
	JButton captura = new JButton("Captura");
	ImageIcon pix;
	ImageIcon spix;
	JLabel imag;

	Logger log = Logger.getLogger(this.getClass());
	
    static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	static NumberFormat formateador = new DecimalFormat("###,##0.00");
    
	JPanel p2 = new JPanel();
	JLabel lbdestin = new JLabel("Destinatario");
	JLabel lbdrif = new JLabel("Rif");
	JLabel lbdrazon = new JLabel("Razón");
	JLabel lbdesced = new JLabel("Cédula");
	JLabel lbddirec = new JLabel("Dirección");
	JLabel lbdtelef = new JLabel("Teléfono");
	JLabel lbdciuda = new JLabel("Ciudad Destino");
	static JTextField tfdestin = new JTextField(15);
	static JTextField tfdrif = new JTextField(7);
	static JTextField tfdrazon = new JTextField(25);
	static JTextField tfddirec = new JTextField(25);
	static JTextField tfdesced = new JTextField(10);
//	JTextField tfdtelef = new JTextField(6);
	JTextField tfdciuda = new JTextField(8);
	
	String nombarch = new String();
	
	JPanel p3 = new JPanel();
	JLabel lbeenvio = new JLabel("Envío");
	JLabel lbetipo  = new JLabel("Tipo");
	JLabel lbetpago = new JLabel("Forma de Pago");
	JLabel lbefecha = new JLabel("Fecha");
	JLabel lbfactur = new JLabel("Factura");
	JLabel lbetemba = new JLabel("Embalaje");
	JLabel lbepieza = new JLabel("Cantidad/Piezas");
	JLabel lbepeso  = new JLabel("Peso");
	JLabel lbemonto = new JLabel("Monto");
	JLabel lbedescr = new JLabel("Descripción");
	JLabel lbeipost = new JLabel("Ipostel");
	JLabel lbeiva   = new JLabel("I.V.A.");
	JLabel lbetotal = new JLabel("Total a Pagar");
	static JTextField tfetipo  = new JTextField(6);
	static JTextField tfetpago = new JTextField(5);
	static JTextField tfetemba = new JTextField(8);
	static JTextField tfepieza = new JTextField(5);
	static JTextField tfepeso  = new JTextField(5);
	static JTextField tfemonto = new JTextField(7);
	static JLabel tffactur = new JLabel();
	static JLabel tfefecha = new JLabel();
	static JTextField tfeipost = new JTextField(7);
	static JTextField tfeiva   = new JTextField(7);
	static JTextField tfedescr = new JTextField(15);
	static JTextField tfetotal = new JTextField(8);

	JRadioButton fldest = new JRadioButton("Flete Destino");
//	JRadioButton especial = new JRadioButton("Especial");
	
	static Factura actual,ultimo;

	Embalajecb ModEmbalaje;
	JComboBox cbEmbalaje;
	
	Ciudadcb ModCiudad;
	JComboBox cbCiudad;

	Unidadcb ModUnidad;
	JComboBox cbUnidad;

	Embalaje embal = new Embalaje();
	Unidad unid = new Unidad();
	Tarifa tar = new Tarifa();
	TarifaIpostel tar2 = new TarifaIpostel();
	Persona remit = new Persona();
	static Facturacion fact1 = new Facturacion();
	static Facturacion fact2 = new Facturacion();

	Eventos eventos = new Eventos();
	
	BarraH barra;
	
	GridBagConstraints gbp1, gbp11, gbp22, gbp2, gbp3, gbbarra;
	
	CamaraWeb ver;
	static Boolean remitente,destinatario;
	
	int numero = 0;
	int control = 0;
	
	public AdminFactura()
	{
		super("Facturación",true,true,true,true);
		setLayout(new GridBagLayout());
		setSize(850,600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		log.info("Entró en AdminFactura... al menos!!!");

		actual = new Factura();
		ultimo = new Factura();
		remitente = false;
		destinatario = false;

//		tfefecha.setEnabled(false);
//		tffactur.setEnabled(false);

		
		pix = new ImageIcon(getClass().getClassLoader().getResource("images/Exit24.gif"));
//		pix = new ImageIcon("c:/imagenes/12-9-112-10-35-27.jpg");
		if (principal.cam) ver = new CamaraWeb();
		imag = new JLabel(pix);
		imag.setPreferredSize(new Dimension(80,60));
		
		barra = new BarraH(1);
		
		ModEmbalaje = MiEmbalaje();
		cbEmbalaje = new JComboBox(ModEmbalaje);
		
		ModUnidad = MiUnidad();
		cbUnidad = new JComboBox(ModUnidad);
		
		ModCiudad = MiCiudad();
		cbCiudad = new JComboBox(ModCiudad);
		
		GroupLayout layer1 = new GroupLayout(p11);
		layer1.setAutoCreateGaps(true);
		layer1.setAutoCreateContainerGaps(true);
		layer1.setHorizontalGroup(
			layer1.createParallelGroup()
//				.addComponent(lbremite,GroupLayout.Alignment.CENTER)
				.addGroup(layer1.createSequentialGroup()
					.addGroup(layer1.createParallelGroup()
						.addComponent(lbrcedul)
						.addComponent(lbrapell)
						.addComponent(lbrnombr)
						.addComponent(lbrdirec)						
						.addComponent(lbrtelef)
						.addComponent(lbrrif)
						.addComponent(lbrrazon)
						.addComponent(lbrdirfi)
					)
					.addGroup(layer1.createParallelGroup()
						.addComponent(tfrcedul,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)							
						.addComponent(tfrapell,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
						.addComponent(tfrnombr,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
						.addComponent(tfrdirec,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
						.addComponent(tfrtelef,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
						.addComponent(tfrrif,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
						.addComponent(tfrrazon,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
						.addComponent(tfrdirfi,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
					)
				)
		);
		layer1.setVerticalGroup(
			layer1.createSequentialGroup()
//				.addComponent(lbremite)
				.addGroup(layer1.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lbrcedul)
					.addComponent(tfrcedul,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layer1.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lbrapell)
					.addComponent(tfrapell,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layer1.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lbrnombr)
					.addComponent(tfrnombr,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layer1.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lbrdirec)
					.addComponent(tfrdirec,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layer1.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lbrtelef)
					.addComponent(tfrtelef,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layer1.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lbrrif)
						.addComponent(tfrrif,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layer1.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lbrrazon)
						.addComponent(tfrrazon,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layer1.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lbrdirfi)
						.addComponent(tfrdirfi,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)	
		);
		p11.setLayout(layer1);
		Border linea = BorderFactory.createLineBorder(Color.black);
		TitledBorder trem = BorderFactory.createTitledBorder(linea, "Remitente");
		trem.setTitleJustification(TitledBorder.CENTER);
		p11.setBorder(trem);
		gbp1 = new GridBagConstraints();
		gbp1.gridx = 0;
		gbp1.gridy = 0;
		gbp1.gridheight = 1;
		gbp1.gridwidth = 1;
		gbp1.weighty = 0;
		gbp1.weightx = 0;
		gbp1.fill = GridBagConstraints.BOTH;

//		tfrcedul.setEditable(false);
		tfrapell.setEditable(false);
		tfrnombr.setEditable(false);
		tfrdirec.setEditable(false);
		tfrtelef.setEditable(false);

		tfrrazon.setEditable(false);
		tfrdirfi.setEditable(false);

		tfdrazon.setEditable(false);
		tfddirec.setEditable(false);
		
		tfemonto.setEditable(false);
		tfeipost.setEditable(false);
		tfeiva.setEditable(false);
		tfetotal.setEditable(false);
		
		TitledBorder tfot = BorderFactory.createTitledBorder(linea, "Foto");
		trem.setTitleJustification(TitledBorder.CENTER);
		
		p22.setLayout(new BorderLayout());
		p22.add(imag,BorderLayout.CENTER);
		p22.add(captura,BorderLayout.SOUTH);
		p22.setBorder(tfot);

		log.info("Declaraciones varias de AdminFactura...");
		
		captura.addActionListener(eventos);
		captura.setEnabled(false);
		
//		p22.setSize(50,50);
//		p22.setLayout(null);
//		p22.add(ver.VerCamara(0, 0, 50, 50));
		
/*		JFrame foto = new JFrame();
		foto.add(ver.VerCamara(0, 0, 416, 312));
		foto.setSize(416,312);
		foto.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		foto.setVisible(true);
*/		
		p1.setLayout(new GridBagLayout());

		gbp11 = new GridBagConstraints();
		gbp11.gridx = 0;
		gbp11.gridy = 0;
		gbp11.gridheight = 1;
		gbp11.gridwidth = 1;
		gbp11.weighty = 1;
		gbp11.weightx = 1;
		gbp11.fill = GridBagConstraints.BOTH;
		
		gbp22 = new GridBagConstraints();
		gbp22.gridx = 1;
		gbp22.gridy = 0;
		gbp22.gridheight = 1;
		gbp22.gridwidth = 1;
		gbp22.weighty = 1;
		gbp22.weightx = 1;
		gbp22.fill = GridBagConstraints.BOTH;

		p1.add(p11,gbp11);
		p1.add(p22,gbp22);
		

		GroupLayout layer2 = new GroupLayout(p2);
		layer2.setAutoCreateGaps(true);
		layer2.setAutoCreateContainerGaps(true);
		layer2.setHorizontalGroup(
			layer2.createParallelGroup()
//				.addComponent(lbremite,GroupLayout.Alignment.CENTER)
				.addGroup(layer2.createSequentialGroup()
					.addGroup(layer2.createParallelGroup()
						.addComponent(lbdestin)
						.addComponent(lbdesced)
						.addComponent(lbdrif)
						.addComponent(lbdrazon)
						.addComponent(lbddirec)
						.addComponent(lbdciuda)
//						.addComponent(lbdciuda)
					)
					.addGroup(layer2.createParallelGroup()
						.addComponent(tfdestin,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
						.addComponent(tfdesced,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
						.addComponent(tfdrif,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
						.addComponent(tfdrazon,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
						.addComponent(tfddirec,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
						.addComponent(cbCiudad,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
//						.addComponent(tfdciuda,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
					)
				)
		);
		layer2.setVerticalGroup(
			layer2.createSequentialGroup()
//				.addComponent(lbremite)
				.addGroup(layer2.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lbdestin)
					.addComponent(tfdestin,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layer2.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lbdesced)
					.addComponent(tfdesced,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layer2.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lbdrif)
					.addComponent(tfdrif,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layer2.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lbdrazon)
					.addComponent(tfdrazon,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layer2.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lbddirec)
					.addComponent(tfddirec,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layer2.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lbdciuda)
					.addComponent(cbCiudad,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)	
		);
		p2.setLayout(layer2);
		TitledBorder tdes = BorderFactory.createTitledBorder(linea, "Destinatario");
		tdes.setTitleJustification(TitledBorder.CENTER);
		p2.setBorder(tdes);
		gbp2 = new GridBagConstraints();
		gbp2.gridx = 0;
		gbp2.gridy = 1;
		gbp2.gridheight = 1;
		gbp2.gridwidth = 1;
		gbp2.weighty = 2;
		gbp2.weightx = 2;
		gbp2.fill = GridBagConstraints.BOTH;
		

		GroupLayout layer3 = new GroupLayout(p3);
		layer3.setAutoCreateGaps(true);
		layer3.setAutoCreateContainerGaps(true);
		layer3.setHorizontalGroup(
			layer3.createParallelGroup()
				.addGroup(layer3.createSequentialGroup()
					.addGroup(layer3.createParallelGroup()
						.addComponent(lbetemba)
						.addComponent(lbedescr)
						.addComponent(lbepieza)
						.addComponent(lbepeso)
						.addComponent(lbemonto)
						.addComponent(lbeipost)
						.addComponent(lbeiva)
						.addComponent(lbetotal)
					)
					.addGroup(layer3.createParallelGroup()
						.addComponent(cbEmbalaje,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)							
						.addComponent(tfedescr,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
						.addComponent(tfepieza,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
						.addGroup(layer3.createSequentialGroup()
								.addComponent(tfepeso,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
								.addComponent(cbUnidad,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
								)
						.addComponent(tfemonto,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
						.addComponent(tfeipost,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
						.addComponent(tfeiva,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
						.addComponent(tfetotal,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
					)
				)
				.addComponent(fldest,GroupLayout.Alignment.CENTER)
//				.addComponent(especial,GroupLayout.Alignment.CENTER)
				.addComponent(tfefecha,GroupLayout.Alignment.CENTER)
				.addComponent(tffactur,GroupLayout.Alignment.CENTER)
		);
		layer3.setVerticalGroup(
			layer3.createSequentialGroup()
				.addGroup(layer3.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lbetemba)
					.addComponent(cbEmbalaje,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layer3.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lbedescr)
					.addComponent(tfedescr,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layer3.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lbepieza)
					.addComponent(tfepieza,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)	
				.addGroup(layer3.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lbepeso)
					.addComponent(tfepeso,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
					.addComponent(cbUnidad,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layer3.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lbemonto)
					.addComponent(tfemonto,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layer3.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lbeipost)
					.addComponent(tfeipost,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layer3.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lbeiva)
					.addComponent(tfeiva,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layer3.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lbetotal)
					.addComponent(tfetotal,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
				)
				.addComponent(fldest)
				.addComponent(tfefecha)
				.addComponent(tffactur)
//				.addComponent(especial)
		);
		p3.setLayout(layer3);
		TitledBorder tenv = BorderFactory.createTitledBorder(linea, "Envío");
		tenv.setTitleJustification(TitledBorder.CENTER);
		p3.setBorder(tenv);
		gbp3 = new GridBagConstraints();
		gbp3.gridx = 1;
		gbp3.gridy = 0;
		gbp3.gridheight = 2;
		gbp3.gridwidth = 1;
		gbp3.weighty = 0.2;
		gbp3.weightx = 0.2;
		gbp3.fill = GridBagConstraints.BOTH;

		log.info("Todos los Layouts... AdminFactura...");

		gbbarra = new GridBagConstraints();
		gbbarra.gridx = 0;
		gbbarra.gridy = 3;
		gbbarra.gridheight = 1;
		gbbarra.gridwidth = 2;
//		gbbarra.weighty = 1;
//		gbbarra.weightx = 1;
		gbbarra.fill = GridBagConstraints.CENTER;
		
		add(p1,gbp1);
		add(p2,gbp2);
		add(p3,gbp3);
		add(barra,gbbarra);

		barra.btnuevo.addActionListener(eventos);
		barra.btanteri.addActionListener(eventos);
		barra.btbuscar.addActionListener(this);
		barra.btcancel.addActionListener(eventos);
		barra.btimprim.addActionListener(eventos);
		barra.btmodifi.addActionListener(eventos);
		barra.btgrabar.addActionListener(eventos);
		barra.btpagar.addActionListener(eventos);
		barra.btprimer.addActionListener(eventos);
		barra.btsalir.addActionListener(eventos);
		barra.btsiguie.addActionListener(eventos);
		barra.btultimo.addActionListener(eventos);
		captura.addActionListener(eventos);
		
		barra.Consulta();
		captura.setEnabled(false);
		
		ultimo = obtenerUltimo();

		log.info("Todas las barras de herramientas... AdminFactura...");
		
		if (ultimo==null)
		{	
			JOptionPane.showMessageDialog(new JFrame(), "No hay Facturas Registradas\nAgregando la Primera!!!","Database Error!",JOptionPane.ERROR_MESSAGE);
			barra.btnuevo.doClick();
		}else{
			actual = ultimo;
			refreshFactura();
			barra.Consulta();
			captura.setEnabled(false);
		}

			
		tfrcedul.addKeyListener(new KeyAdapter() 
		{
			public void keyTyped(KeyEvent e)
			{
				char car = e.getKeyChar();
				// Verificación de Teclas pulsadas...
				int largo = tfrcedul.getText().length();
				if (largo==0) {
					if((car!='V' && car!='E') && car!='\b') 
					{
						getToolkit().beep();
						e.consume();
					}
				}else if (largo<5) 
					{
					if((car < '0' || car > '9') && car!='\b' && car!='\n') {
						if (car=='\n') tfrrif.grabFocus();
						getToolkit().beep();
						e.consume();
					}
					}else if((car < '0' || car > '9') && car!='\b' && car!='.') 
					{
					if (car=='\n') tfrrif.grabFocus();
					getToolkit().beep();
					e.consume();
					}				
				if (largo>11) {
					getToolkit().beep();
					e.consume();
				}
				if (tfrcedul.getText().contains(".") && car=='.'){
					getToolkit().beep();
					e.consume();
				}
			}
		});
		
		
		tfrcedul.addFocusListener(new FocusListener() 
		{
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void focusLost(FocusEvent arg0) 
			{
				Persona per = new Persona();
				if (!tfrcedul.getText().trim().equals("") && tfrcedul.isEditable())
				{
					per = buscaPersona(tfrcedul.getText());
					try
					{
						tfrnombr.setText(per.getNombre());
						setPersona(per);
					}catch (NullPointerException e)
					{
						AdminPersona a = new AdminPersona();
						a.setCedula(tfrcedul.getText());
						delPersona();
						principal.mostrarInternalFrame(a, false);
					}
				}else
				{
					
				}
			}
			
		});

		tfrrif.addKeyListener(new KeyAdapter() 
		{
			public void keyTyped(KeyEvent e)
			{
				char car = e.getKeyChar();
				// Verificación de Teclas pulsadas...
				int largo = tfrrif.getText().length();
				if (largo==0) {
					if((car!='J' && car!='G' && car!='V' && car!='E') && car!='\b') 
					{
						getToolkit().beep();
						e.consume();
					}
				}else if (largo<5) 
					{
					if((car < '0' || car > '9') && car!='\b' && car!='\n') {
						if (car=='\n') tfetipo.grabFocus();
						getToolkit().beep();
						e.consume();
					}
					}else if((car < '0' || car > '9') && car!='\b' && car!='.') 
					{
					if (car=='\n') tfetipo.grabFocus();
					getToolkit().beep();
					e.consume();
					}				
				if (largo>11) {
					getToolkit().beep();
					e.consume();
				}
				if (tfrrif.getText().contains(".") && car=='.'){
					getToolkit().beep();
					e.consume();
				}
			}
		});
		
		
		tfrrif.addFocusListener(new FocusListener() 
		{
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void focusLost(FocusEvent arg0) 
			{
				Facturacion per = null;
				remitente = true;
				if (!tfrrif.getText().trim().equals("") && tfrrif.isEditable())
				{
					per = buscaFacturacion(tfrrif.getText());
					try
					{
						tfrrazon.setText(per.getRazon());
						setFacturacion(per);
					}catch (NullPointerException e)
					{
						AdminFacturacion a = new AdminFacturacion();
						a.setRif(tfrrif.getText());
						delFacturacion();
						remitente = true;
						principal.mostrarInternalFrame(a, false);
					}
				}else
				{
					remitente = false;
				}
			}
			
		});

		tfdrif.addKeyListener(new KeyAdapter() 
		{
			public void keyTyped(KeyEvent e)
			{
				char car = e.getKeyChar();
				// Verificación de Teclas pulsadas...
				int largo = tfdrif.getText().length();
				if (largo==0) {
					if((car!='J' && car!='G' && car!='V' && car!='E') && car!='\b') 
					{
						getToolkit().beep();
						e.consume();
					}
				}else if (largo<5)
					{
					if((car < '0' || car > '9') && car!='\b' && car!='\n') {
						if (car=='\n') tfetipo.grabFocus();
						getToolkit().beep();
						e.consume();
					}
					}else if((car < '0' || car > '9') && car!='\b' && car!='.') 
					{
					if (car=='\n') tfetipo.grabFocus();
					getToolkit().beep();
					e.consume();
					}				
				if (largo>11) {
					getToolkit().beep();
					e.consume();
				}
				if (tfdrif.getText().contains(".") && car=='.'){
					getToolkit().beep();
					e.consume();
				}
			}
		});
		
		
		tfdrif.addFocusListener(new FocusListener() 
		{
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void focusLost(FocusEvent arg0) 
			{
				Facturacion per = null;
				destinatario = true;
				if (!tfdrif.getText().trim().equals("") && tfdrif.isEditable())
				{
					per = buscaFacturacion(tfdrif.getText());
					try
					{
						tfdrazon.setText(per.getRazon());
						setFacturacion(per);
					}catch (NullPointerException e)
					{
						AdminFacturacion a = new AdminFacturacion();
						a.setRif(tfdrif.getText());
						delFacturacion();
						destinatario = true;
						principal.mostrarInternalFrame(a, false);
					}
				}else
				{
					destinatario = false;
				}
			}
			
		});

//		tfepieza.addActionListener(l);
		
		tfepeso.addFocusListener(this);
		
		cbEmbalaje.addFocusListener(this);
				
		cbEmbalaje.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED && !barra.btnuevo.isEnabled())
				{
//					cbsocio.setEnabled(true);
				}else
				{
//					cbsocio.setEnabled(false);
				}
			}
			
		});

		
//		cbEmbalaje.addActionListener(arg0);
		
		cbUnidad.addFocusListener(this);

	}

	
	protected String calculamonto1(Embalaje embal2, int peso, Unidad unid2) 
	{	
		int peso1 = (int) (peso*unid2.getFactor());
		try {
			Session sesion = principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from Tarifa where desde <= :pes and hasta > :pes");
			queryResult.setInteger("pes", peso1);
			System.out.println(""+peso1);
//			queryResult.setBoolean("ipost", embal2.getIpostel());
            tar = (Tarifa)queryResult.uniqueResult();
			sesion.getTransaction().commit();
			System.out.println("Código de la Tarifa: "+peso1+"   "+embal2.getIpostel());
			String monto = ""+tar.getMonto();
			return monto;
		} catch (HibernateException e2) {
			JOptionPane.showMessageDialog(new JFrame(), "Calcula Monto!!!Errores de Base de Datos!!!\n"+e2,"Database Error!",JOptionPane.ERROR_MESSAGE);
			return "0.00";
		}
		catch (NullPointerException e3)
		{
			JOptionPane.showMessageDialog(new JFrame(), "El peso o tarifa no existe!!!\n","Database Error!",JOptionPane.ERROR_MESSAGE);
			return "0.00";
		}
	}
	protected String calculamonto2(Embalaje embal2, int peso, Unidad unid2) 
	{	
//		if (embal2.getIpostel().equals(true))
//		{
		int peso1 = (int) (peso*unid2.getFactor());
		try {
			Session sesion = principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from TarifaIpostel where desde <= :pes and hasta > :pes");
			queryResult.setDouble("pes", peso1);
            tar2 = (TarifaIpostel)queryResult.uniqueResult();
			sesion.getTransaction().commit();
			String monto = ""+tar2.getMonto();
			return monto;
		} catch (HibernateException e2) {
//			JOptionPane.showMessageDialog(new JFrame(), "buscaPersona!!!Errores de Base de Datos!!!\n"+e2,"Database Error!",JOptionPane.ERROR_MESSAGE);
			return "0.00";
		}
		catch (NullPointerException e3)
		{
//			JOptionPane.showMessageDialog(new JFrame(), "El peso o tarifa no existe!!!\n","Database Error!",JOptionPane.ERROR_MESSAGE);
			return "0.00";
		}
//		}else return "0.00";
		
	}
	
	public void refreshFactura() {
		setPersona(actual.getRemitente());
		remitente = true;
		setFacturacion(actual.getFacturacion1());
		destinatario = true;
		setFacturacion(actual.getFacturacion2());
		tfdestin.setText(actual.getDestinatario());
		tfdesced.setText(actual.getDesCedula());
		
		cbUnidad.setSelectedItem(actual.getUndMedida().getDescripcion());
		cbEmbalaje.setSelectedItem(actual.getFacEmbalaje().getDescripcion());
		
//		especial.setSelected(actual.getEspecial());
		fldest.setSelected(actual.getFleteDestino());
		
		tfepieza.setText(""+actual.getCantidad());
		tfepeso.setText(""+actual.getPeso());
		tfemonto.setText(""+actual.getMonto());
		tfeipost.setText(""+actual.getMontoIpostel());
		tfeiva.setText(""+actual.getIva());
		tfedescr.setText(""+actual.getDescripcion());
		tfetotal.setText(""+actual.getTotal());
		tfefecha.setText("Fecha: "+formatoFecha.format(actual.getFecha()));
		tffactur.setText("Factura Nro.:"+ actual.getNumero());
		cbCiudad.setSelectedItem(actual.getFacCiudad().getDescripcion());

		if (principal.cam)
		try{
			pix = new ImageIcon(actual.getArchivo());
			spix = new ImageIcon(pix.getImage().getScaledInstance(200, 120, Image.SCALE_DEFAULT));
			imag.setIcon(spix);
		}catch (NullPointerException e5)
		{
			JOptionPane.showMessageDialog(new JFrame(), "Archivo Fotográfico no se encuentra!!!\n","Database Error!",JOptionPane.ERROR_MESSAGE);			
		}
		else
		{
			pix = new ImageIcon(getClass().getClassLoader().getResource("images/Exit24.gif"));
			spix = new ImageIcon(pix.getImage().getScaledInstance(200, 120, Image.SCALE_DEFAULT));
			imag.setIcon(spix);
		}
		tfrrif.setEditable(false);
		tfrcedul.setEditable(false);
		tfdestin.setEditable(false);
		tfdesced.setEditable(false);

		tfedescr.setEditable(false);
		tfepieza.setEditable(false);
		tfepeso.setEditable(false);
		cbCiudad.setEnabled(false);
		
		tfdrif.setEditable(false);
		tfrcedul.grabFocus();
	
	}
	
	
	private Factura obtenerUltimo() {
		Factura facc = null;
		try {
			Session sesion = principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from Factura where facOficina = :ofi order by numero desc");
			queryResult.setEntity("ofi",principal.ofic);
			queryResult.setMaxResults(1);
            facc = (Factura)queryResult.uniqueResult();
			sesion.getTransaction().commit();
			return facc;
		} catch (HibernateException e2) {
			JOptionPane.showMessageDialog(new JFrame(), "buscaPersona!!!Errores de Base de Datos!!!\n","Database Error!",JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	protected Persona buscaPersona(String text) {
		Persona pp = new Persona();
		try {
			Session sesion = principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from Persona where cedula = :text");
			queryResult.setString("text", text);
            pp = (Persona)queryResult.uniqueResult();
			sesion.getTransaction().commit();
			return pp;
		} catch (HibernateException e2) {
			JOptionPane.showMessageDialog(new JFrame(), "buscaPersona!!!Errores de Base de Datos!!!\n"+e2+" "+text,"Database Error!",JOptionPane.ERROR_MESSAGE);
			return null;
		}

	}
	
	
	public class Eventos implements ActionListener {		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String Carpeta = "c:\\imagenes";
			Boolean NombreAutomatico = true;
			String NombreValoNull = "foto_actual";
			File archivo;

			// TODO Auto-generated method stub
			if (arg0.getSource()==captura && principal.cam)
			{
				archivo = ver.Fotografiar(Carpeta, NombreAutomatico, NombreValoNull);
				nombarch = archivo.toString();
				pix = new ImageIcon(archivo.toString());
				spix = new ImageIcon(pix.getImage().getScaledInstance(200, 120, Image.SCALE_DEFAULT));
				imag.setIcon(spix);
				System.out.println("Pasó por aquí!!!! PIX");
				System.out.println("Nombre del Archivo: "+archivo.toString());
			}
			if (arg0.getSource()==barra.btanteri)
			{
				Factura per = null;
				int text = actual.getCodigo();
				try {
					Session sesion = principal.fabrica.getCurrentSession();
					sesion.beginTransaction();
					Query queryResult = sesion
							.createQuery("from Factura where codigo < :text and facOficina = :ofi order by numero desc");
					queryResult.setEntity("ofi",principal.ofic);
					queryResult.setInteger("text", text);
					queryResult.setMaxResults(1);
					per = (Factura) queryResult.uniqueResult();
					sesion.getTransaction().commit();
				} catch (HibernateException e2) {
					JOptionPane.showMessageDialog(new JFrame(),
							"btAnterior!!!Errores de Base de Datos!!!\n" + e2 + " "
									+ text, "Database Error!",
							JOptionPane.ERROR_MESSAGE);
				}
				if (per != null) {
					actual = per;
					refreshFactura();
				}				
			}
			if (arg0.getSource()==barra.btcancel)
			{
				actual = ultimo;
				if (actual.equals(new Factura()))
				{
					dispose();
				}
				actual = ultimo;
				barra.Consulta();
				captura.setEnabled(false);
				refreshFactura();
			}
			if (arg0.getSource()==barra.btgrabar)
			{
				if ((tfemonto.getText().isEmpty()
						|| Double.parseDouble(tfemonto.getText())==0)
						|| (tfdestin.getText().trim().equals("") && tfdrazon.getText().trim().equals("")) 
						|| tfrcedul.getText().trim().equals("")
						|| principal.cam?nombarch.trim().equals(""):false
						|| cbCiudad.getSelectedIndex()<0
						) 
				{
					System.out.println(""+tfemonto.getText().isEmpty()+
							" || "+(Double.parseDouble(tfemonto.getText())==0)+
							" || "+(tfdestin.getText().trim().equals("") && tfdrazon.getText().trim().equals(""))+
							" || "+tfrcedul.getText().trim().equals("")+
							" || "+(principal.cam?nombarch.trim().equals(""):false)+
							" || "+(cbCiudad.getSelectedIndex()<0));
				
					JOptionPane
							.showMessageDialog(new JFrame(),
									"Debe completar la factura \nPara poder Grabar!!!");
					tfrcedul.grabFocus();
				} else {
					actual.setCantidad(Integer.parseInt(tfepieza.getText().trim()));
					actual.setControl(""+control);
					actual.setDesCedula(tfdesced.getText().trim());
					actual.setDescripcion(tfedescr.getText().trim());
					actual.setDestinatario(tfdestin.getText().trim());
					actual.setEstado("A");
					actual.setArchivo(nombarch);
					actual.setFecha(new Date());
					actual.setFleteDestino(fldest.isSelected());
					actual.setIva(Double.parseDouble(tfeiva.getText()));
					actual.setMonto(Double.parseDouble(tfemonto.getText()));
					actual.setFacCiudad(ModCiudad.getElemento(cbCiudad.getSelectedIndex()));
					actual.setMontoIpostel(Double.parseDouble(tfeipost.getText()));
					actual.setPeso(Integer.parseInt(tfepeso.getText()));
					actual.setTotal(Double.parseDouble(tfetotal.getText()));
					actual.setFacEmbalaje(embal);
					actual.setFacOficina(principal.ofic);
/*					if (fact1.equals(null))
						actual.setFacturacion1(null);
					else
*/					actual.setFacturacion1(fact1);
//					actual.setRemitente(remit);
					actual.setEstado(nombarch);
/*					if (fact2.equals(null))
						actual.setFacturacion2(null);
					else
*/					actual.setFacturacion2(fact2);
					actual.setUndMedida(unid);
//					actual.setEspecial(especial.isSelected());

					
//					actual.setfacEmbalaje();
//					actual.setApellido(tfapelli.getText().trim());
//					actual.setNombre(tfnombre.getText().trim());
//					actual.setDireccion(tfdirecc.getText().trim());
//					actual.setSexo(tfsexo.getText().charAt(0));
//					actual.setTelefono(tftelefo.getText());
					try {
						Session sesion = principal.fabrica.getCurrentSession();
						sesion.beginTransaction();
						sesion.saveOrUpdate(actual);
						sesion.getTransaction().commit();
					} catch (HibernateException e2) {
						JOptionPane.showMessageDialog(new JFrame(),
								"btGrabar!!!Errores de Base de Datos!!!\n" + e2
										+ " ", "Database Error!",
								JOptionPane.ERROR_MESSAGE);
					}
					barra.Consulta();
					captura.setEnabled(false);
					refreshFactura();
					grabarenvio();
				}

			}
			if (arg0.getSource()==barra.btimprim)
			{
				int mm=JOptionPane.showOptionDialog(new JFrame(),"Está Seguro que desea Imprimir la Factura?", "Impresión de Factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null,null,null);
				if (mm==JOptionPane.YES_OPTION)
				{
					this.imprimir();
//					actual.setImpresFactura(true);
					try {		
						Session sesion = principal.fabrica.getCurrentSession();
						sesion.beginTransaction();
						sesion.update(actual);
						sesion.getTransaction().commit();
					} catch (HibernateException e2) {
						JOptionPane.showMessageDialog(new JFrame(), "Actualizó la impresión","Database Error!",JOptionPane.ERROR_MESSAGE);
					}			
//					btimprim.setEnabled(false);
				}				
			}
			if (arg0.getSource() == barra.btnuevo
					|| arg0.getSource() == barra.btmodifi) {
				if (arg0.getSource() == barra.btnuevo) {
					delPersona();
					remitente = true;
					destinatario = true;
					delFacturacion();
					
					tfdestin.setText("");
					tfdesced.setText("");
		
					cbUnidad.setSelectedIndex(-1);
					cbEmbalaje.setSelectedIndex(-1);
					cbCiudad.setSelectedIndex(-1);
					
					nombarch = "";
					
					fldest.setSelected(false);
//					especial.setSelected(false);
					
					pix = new ImageIcon(getClass().getClassLoader().getResource("images/Exit24.gif"));
					spix = new ImageIcon(pix.getImage().getScaledInstance(200, 120, Image.SCALE_DEFAULT));
					imag.setIcon(spix);
					
					tfepieza.setText("");
					tfepeso.setText("");
					tfemonto.setText("");
					tfeipost.setText("");
					tfeiva.setText("");
					tfedescr.setText("");
					tfetotal.setText("");

					tfefecha.setText("");
					tffactur.setText("");
					
					ultimo = actual;
					actual = new Factura();
				}

				barra.Edicion();

				captura.setEnabled(true);

				tfrrif.setEditable(true);
				tfrcedul.setEditable(true);
				tfdestin.setEditable(true);
				tfdesced.setEditable(true);
				tfdrif.setEditable(true);
				tfrcedul.grabFocus();

				tfedescr.setEditable(true);
				tfepieza.setEditable(true);
				tfepeso.setEditable(true);
				cbCiudad.setEnabled(true);

			}			
			if (arg0.getSource()==barra.btpagar)
			{
				JInternalFrame ap = new FormadePago(actual);
				principal.mostrarVentana(ap);
			}
			if (arg0.getSource()==barra.btsalir)
			{
				dispose();				
			}
			if (arg0.getSource()==barra.btsiguie)
			{
				Factura per = null;
				int text = actual.getCodigo();
				try {
					Session sesion = principal.fabrica.getCurrentSession();
					sesion.beginTransaction();
					Query queryResult = sesion
							.createQuery("from Factura where codigo > :text and facOficina = :ofi order by codigo asc");
					queryResult.setEntity("ofi",principal.ofic);
					queryResult.setInteger("text", text);
					queryResult.setMaxResults(1);
					per = (Factura) queryResult.uniqueResult();
					sesion.getTransaction().commit();
				} catch (HibernateException e2) {
					JOptionPane.showMessageDialog(new JFrame(),
							"btsiguie!!!Errores de Base de Datos!!!\n" + e2 + " "
									+ text, "Database Error!",
							JOptionPane.ERROR_MESSAGE);
				}
				if (per != null) {
					actual = per;
					refreshFactura();
				}
			}
			if (arg0.getSource()==barra.btultimo)
			{
				actual = obtenerUltimo();
				refreshFactura();				
			}			
		}

		private void imprimir() 
		{
			if (actual.getNumero()<1)
				{
				
				Envio env = new Envio();
				env.setOrigen(actual.getFacOficina().getDescripcion());
				env.setDestino(actual.getFacCiudad().getDescripcion());
				env.setEmbalaje(actual.getFacEmbalaje().getDescripcion());
				env.setPeso(actual.getPeso());
				env.setCodOficina(principal.ofic.getCodigo());
				env.setUnidad(actual.getUndMedida().getDescripcion());
				env.setMonto(actual.getMonto());
				env.setFleteDestino(actual.getFleteDestino());
				env.setRemCedula(actual.getRemitente().getCedula());
				env.setRemNombre(actual.getRemitente().getNombre().trim()+" "+actual.getRemitente().getApellido());

				try
				{
				env.setRemRif(actual.getFacturacion1().getRif());
				env.setRemRazon(actual.getFacturacion1().getRazon());
				}
				catch (NullPointerException nu)
				{
					env.setRemRif("");
					env.setRemRazon("");					
				}
				
				try
				{
					env.setDesCedula(actual.getDesCedula());					
				}
				catch (NullPointerException nu)
				{
					env.setDesCedula("");
				}
				env.setDesNombre(actual.getDestinatario());
				
				try
				{
					env.setDesRif(actual.getFacturacion2().getRif());
					env.setDesRazon(actual.getFacturacion2().getRazon());					
				}
				catch (NullPointerException nu)
				{
					env.setDesRif("");
					env.setDesRazon("");
				}				
				env.setFecha(actual.getFecha());
				env.setId_0(principal.ofic.getCodigo()==0?actual.getCodigo():-1);
				env.setId_1(principal.ofic.getCodigo()==1?actual.getCodigo():-1);
				env.setId_2(principal.ofic.getCodigo()==2?actual.getCodigo():-1);
				env.setEstado('P');
//				env.setId_0(id_0);
//				env.setId_1(id_1);
//				env.setId_2(id_2);

				Factura nrofac = new Factura();
				
				try 
				{
					Session sesiox = principal.fabrica.getCurrentSession();
					sesiox.beginTransaction();
					Query queryResulx = sesiox.createQuery("from Factura where facOficina = :ofi order by numero desc");
					queryResulx.setEntity("ofi",principal.ofic);
					queryResulx.setMaxResults(1);
					nrofac = (Factura) queryResulx.uniqueResult();
					sesiox.getTransaction().commit();
					Session sesiox1 = principal.fabrica.getCurrentSession();
					sesiox1.beginTransaction();
//					actual.setFechaControl(new Date());
					actual.setNumero(nrofac.getNumero()+1);
//					actual.setDateFiFactur(new Date());
					sesiox1.update(actual);
					env.setFactura(actual.getNumero());
					sesiox1.save(env);
					sesiox1.getTransaction().commit();			
	
				}
				catch (HibernateException ee)
				{
					System.out.println("No se grabó el número de factura!!! EERRROORRR");
					log.error("No se grabó el número de factura!!! EERRROORRR");
				}
				
				}

//				System.out.println(resul.toString());

				log.info("Debería estar imprimiendo!!!");
				Map param = new HashMap();

				String string = new String();
				
				if (fldest.isSelected())
				{
					if (!tfdrazon.getText().trim().equals(""))
					{
						param.put("rif",""+actual.getFacturacion2().getRif());
						param.put("razon",""+actual.getFacturacion2().getRazon());
						param.put("direccion",""+actual.getFacturacion2().getDireccion());
						param.put("ciudad",""+actual.getFacturacion2().getFacCiudad().getDescripcion());						
					}else
					{
						param.put("rif",""+actual.getDesCedula());
						param.put("razon",""+actual.getDestinatario());
						param.put("direccion","");
						param.put("ciudad","");												
					}
				}else
				{
					if (!tfrrazon.getText().trim().equals(""))
					{
						param.put("rif",""+actual.getFacturacion1().getRif());
						param.put("razon",""+actual.getFacturacion1().getRazon());
						param.put("direccion",""+actual.getFacturacion1().getDireccion());
						param.put("ciudad",""+actual.getFacturacion1().getFacCiudad().getDescripcion());
					}else
					{
						param.put("rif",""+actual.getRemitente().getCedula());
						param.put("razon",""+actual.getRemitente().getApellido().trim()+", "+actual.getRemitente().getNombre().trim());
						param.put("direccion",""+actual.getRemitente().getDireccion());
						param.put("ciudad",""+actual.getRemitente().getPerCiudad().getDescripcion());						
					}
				}
				DecimalFormat num = new DecimalFormat("000000");
				param.put("total",""+formateador.format(Double.parseDouble(tfetotal.getText())).toString());
				param.put("numero","SERIE "+principal.ofic.getSerieFiscal()+" "+num.format(actual.getNumero()).toString());
				param.put("fecha",""+formatoFecha.format(actual.getFecha()).toString());
				param.put("remitente",""+actual.getRemitente().getApellido().trim()+", "+actual.getRemitente().getNombre().trim());

				if (tfrrif.getText().trim().equals(""))
				{
					param.put("rifrem", "");
					param.put("razrem", "");		
				}
				else
				{
					param.put("rifrem", ""+actual.getFacturacion1().getRif());
					param.put("razrem", ""+actual.getFacturacion1().getRazon());
				}
					
					
				param.put("cedrem", ""+actual.getRemitente().getCedula().trim());
				param.put("ceddes", ""+actual.getDesCedula());
				param.put("destinatario",""+actual.getDestinatario());
				param.put("bultos", ""+actual.getCantidad()+" "+actual.getFacEmbalaje().getDescripcion().trim()+" / PESO: "+new String(""+actual.getPeso())+" "+actual.getUndMedida().getDescripcion());
				param.put("subtotal",""+formateador.format(actual.getMonto()+actual.getMontoIpostel()).toString());
				param.put("monto", ""+formateador.format(actual.getMonto()).toString());
				param.put("monipostel",""+formateador.format(actual.getMontoIpostel()).toString());
				param.put("moniva",""+formateador.format(actual.getIva()).toString());
				param.put("fechah",""+actual.getFecha().toString());

				log.info("Debería estar imprimiendo!!! 2");
				for (int i=0;i<2;i++)
				{
					string+="\n\n\n\n\n";
					string+=String.format("%1$" + 64 + "s", "FACTURA: "+param.get("numero"))
					+"         ";
					string+=String.format("%1$" + 64 + "s", "FACTURA: "+param.get("numero"))
					+"\n";
					log.info("Debería estar imprimiendo!!! lin 1");
					string+=String.format("%1$" + 64 + "s", "FECHA: "+param.get("fecha"))
					+"         ";
					string+=String.format("%1$" + 64 + "s", "FECHA: "+param.get("fecha"))
					+"\n";
					log.info("Debería estar imprimiendo!!! lin 2");
					string+=String.format("%1$-" + 64 + "s", "RIF/CEDULA: "+param.get("rif"))
					+"         ";
					string+=String.format("%1$-" + 64 + "s", "RIF/CEDULA: "+param.get("rif"))
					+"\n"; 
					log.info("Debería estar imprimiendo!!! lin 3");
					string+=String.format("%1$-" + 64 + "s", "RAZON SOCIAL / NOMBRES Y APELLIDOS: ")
					+"         ";
					string+=String.format("%1$-" + 64 + "s", "RAZON SOCIAL / NOMBRES Y APELLIDOS: ")
					+"\n";
					log.info("Debería estar imprimiendo!!! lin 4");
					string+=String.format("%1$-" + 64 + "s", param.get("razon"))
					+"         ";
					string+=String.format("%1$-" + 64 + "s", param.get("razon"))
					+"\n";
					log.info("Debería estar imprimiendo!!! lin 5");
					string+=String.format("%1$"+64+"s",param.get("fechah"))
					+"         ";
					string+=String.format("%1$"+64+"s",param.get("fechah"))
					+"\n";
					log.info("Debería estar imprimiendo!!! lin 5");
					string+=String.format("%1$-"+64+"s","REMITENTE: "+"C.I.:"+param.get("cedrem"))
					+"         ";
					string+=String.format("%1$-"+64+"s","REMITENTE: "+"C.I.:"+param.get("cedrem"))
					+"\n";
					log.info("Debería estar imprimiendo!!! lin 7");
					string+=String.format("%1$-"+64+"s",""+param.get("remitente"))
					+"         ";
					string+=String.format("%1$-"+64+"s",""+param.get("remitente"))
					+"\n";
					log.info("Debería estar imprimiendo!!! lin 8");
					string+=String.format("%1$-"+64+"s",""+param.get("rifrem")+"/"+param.get("razrem"))
					+"         ";
					string+=String.format("%1$-"+64+"s",""+param.get("rifrem")+"/"+param.get("razrem"))
					+"\n";
					log.info("Debería estar imprimiendo!!! lin 9");
					string+=String.format("%1$-"+64+"s","DESTINATARIO: ")
					+"         ";
					string+=String.format("%1$-"+64+"s","DESTINATARIO: ")
					+"\n";
					log.info("Debería estar imprimiendo!!! lin 10");
					string+=String.format("%1$-"+64+"s","C.I.: "+param.get("ceddes"))
					+"         ";
					string+=String.format("%1$-"+64+"s","C.I.: "+param.get("ceddes"))
					+"\n";
					log.info("Debería estar imprimiendo!!! lin 11");
					string+=String.format("%1$-"+64+"s",param.get("destinatario"))
					+"         ";
					string+=String.format("%1$-"+64+"s",param.get("destinatario"))
					+"\n";
					log.info("Debería estar imprimiendo!!! lin 12");
					string+=String.format("%1$-"+24+"s", actual.getDescripcion());
					string+=String.format("%1$"+28+"s",param.get("bultos"));
					string+=String.format("%1$"+12+"s",param.get("monto"))
					+"         ";
					string+=String.format("%1$-"+24+"s", actual.getDescripcion());
					string+=String.format("%1$"+28+"s",param.get("bultos"));
					string+=String.format("%1$"+12+"s",param.get("monto"))
					+"\n";
					log.info("Debería estar imprimiendo!!! lin 13");
					string+=String.format("%1$-"+44+"s", "DESTINO: "+actual.getFacCiudad().getDescripcion());
					string+=String.format("%1$"+8+"s","IPOSTEL");
					string+=String.format("%1$"+12+"s",param.get("monipostel"))
					+"         ";
					string+=String.format("%1$-"+44+"s", "DESTINO: "+actual.getFacCiudad().getDescripcion());
					string+=String.format("%1$"+8+"s","IPOSTEL");
					string+=String.format("%1$"+12+"s",param.get("monipostel"))
					+"\n";
					log.info("Debería estar imprimiendo!!! lin 14");
					string+=String.format("%1$-"+40+"s", ""+(actual.getFacturacion2()==null?"    ":actual.getFacturacion2().getDireccion()));
					string+=String.format("%1$"+12+"s","SUB-TOTAL");
					string+=String.format("%1$"+12+"s",param.get("subtotal"))
					+"         ";
					string+=String.format("%1$-"+40+"s", ""+(actual.getFacturacion2()==null?"    ":actual.getFacturacion2().getDireccion()));
					string+=String.format("%1$"+12+"s","SUB-TOTAL");
					string+=String.format("%1$"+12+"s",param.get("subtotal"))
					+"\n";
					log.info("Debería estar imprimiendo!!! lin 15");
					string+=String.format("%1$"+52+"s","IVA 12%");
					string+=String.format("%1$"+12+"s",param.get("moniva"))
					+"         ";
					string+=String.format("%1$"+52+"s","IVA 12%");
					string+=String.format("%1$"+12+"s",param.get("moniva"))
					+"\n";
					log.info("Debería estar imprimiendo!!! lin 16");
					string+=String.format("%1$"+52+"s","TOTAL A PAGAR");
					string+=String.format("%1$"+12+"s",param.get("total"))
					+"         ";
					string+=String.format("%1$"+52+"s","TOTAL A PAGAR");
					string+=String.format("%1$"+12+"s",param.get("total"))
					+"\n";
					log.info("Debería estar imprimiendo!!! lin 17");
					string+=String.format("%1$-"+64+"s","Nota: ")
					+"         ";
					string+=String.format("%1$-"+64+"s","Nota: ")
					+"\n";
					log.info("Debería estar imprimiendo!!! lin 18");
					string+=String.format("%1$-"+64+"s","Los envios de fácil descomposición viajan por CUENTA Y RIESGO")
					+"         ";
					string+=String.format("%1$-"+64+"s","Los envios de fácil descomposición viajan por CUENTA Y RIESGO")
					+"\n";
					log.info("Debería estar imprimiendo!!! lin 19");
					string+=String.format("%1$-"+64+"s","DEL REMITENTE. Después de 30 días, este recibo no tiene validez.")
					+"         ";				
					string+=String.format("%1$-"+64+"s","DEL REMITENTE. Después de 30 días, este recibo no tiene validez.")
					+"\n";
					log.info("Debería estar imprimiendo!!! lin 20");
					string+=String.format("%1$-"+64+"s","NO RESPONDEMOS POR VALORES NO DECLARADOS EN ESTE COMPROBANTE")
					+"         ";
					string+=String.format("%1$-"+64+"s","NO RESPONDEMOS POR VALORES NO DECLARADOS EN ESTE COMPROBANTE\n\n");
					log.info("Debería estar imprimiendo!!! lin 21");
					string+=String.format("%1$"+62+"s","HABILITACION POSTAL NRO. 50-08")+"         ";
					string+=String.format("%1$"+64+"s","HABILITACION POSTAL NRO. 50-08\n")+
					(i==0?"\n\n\n\n\n":"\n");			
					
					log.info("Debería estar imprimiendo!!! linea final");
					
					log.info("imprime la segunda factura!!!");
				}
				string+="\f";

				System.out.println(string);
				
				PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
				DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
				DocPrintJob docPrintJob = printService.createPrintJob(); 
				Doc doc=new SimpleDoc(string.getBytes(),flavor,null);
				log.info("Debería estar imprimiendo!!! 4");
				try {
				docPrintJob.print(doc, null);
				}
				catch (PrintException e) {
				System.out.println("Error al imprimir: "+e.getMessage());
				log.info("hubo error en la impresion!!!");
				} 
				System.out.println("FIN DE IMPRESION");
				log.info("Debería estar imprimiendo!!! final");
				log.info("imprimió normal");
				}
		}
	
	
	public static void setPersona(Persona p)
	{
		tfrcedul.setText(p.getCedula());
		tfrapell.setText(p.getApellido());
		tfrnombr.setText(p.getNombre());
		tfrdirec.setText(p.getDireccion());
		tfrtelef.setText(p.getTelefono());
		actual.setRemitente(p);
	}
	
	public void grabarenvio() {
		// TODO Auto-generated method stub
		
	}

	public static void delPersona()
	{
		tfrcedul.setText("");
		tfrapell.setText("");
		tfrnombr.setText("");
		tfrdirec.setText("");
		tfrtelef.setText("");
		actual.setRemitente(new Persona());
	}
	
	public static void delDestinatario()
	{
		tfdestin.setText("");
		tfdesced.setText("");
	}
	
	public static void delEnvio()
	{
		tfetipo.setText("");
		tfetpago.setText("");
		tfetemba.setText("");
		tfepieza.setText("");
		tfepeso.setText("");
		tfemonto.setText("");
		tfeipost.setText("");
		tfedescr.setText("");
		tfetotal.setText("");
	}
	
	public static void setFacturacion(Facturacion actual2) {
		if (remitente)
		{
			try
			{
				tfrrif.setText(actual2.getRif());
				tfrrazon.setText(actual2.getRazon());
				tfrdirfi.setText(actual2.getDireccion());				
			}catch (NullPointerException e2)
			{
				tfrrif.setText("");
				tfrrazon.setText("");
				tfrdirfi.setText("");
			}
//			tfrcorreo.setText("" + actual2.getCorreo());
			fact1 = actual2;
			remitente = false;
		}
		if (destinatario)
		{
			try
			{
				tfdrif.setText(actual2.getRif());
				tfdrazon.setText(actual2.getRazon());
				tfddirec.setText(actual2.getDireccion());				
			}catch (NullPointerException e3)
			{
				tfdrif.setText("");
				tfdrazon.setText("");
				tfddirec.setText("");
			}
//			tfrcorreo.setText("" + actual2.getCorreo());
			fact2 = actual2;
			destinatario = false;
		}
		
	}
	
	public static void delFacturacion()
	{
		if (remitente)
		{
			tfrrif.setText("");
			tfrrazon.setText("");
			tfrdirfi.setText("");
			actual.setFacturacion1(new Facturacion());
			fact1 = null;
			remitente = false;
		}
		if (destinatario)
		{
			tfdrif.setText("");
			tfdrazon.setText("");
			tfddirec.setText("");
			actual.setFacturacion2(new Facturacion());
			fact2 = null;
			destinatario = false;
		}

	}
	
	protected Facturacion buscaFacturacion(String text) {
		Facturacion pp = new Facturacion();
		try {
			Session sesion = principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery("from Facturacion where rif = :text");
			queryResult.setString("text", text);
            pp = (Facturacion)queryResult.uniqueResult();
			sesion.getTransaction().commit();
			return pp;
		} catch (HibernateException e2) {
			JOptionPane.showMessageDialog(new JFrame(), "buscaFacturacion!!!Errores de Base de Datos!!!\n"+e2+" "+text,"Database Error!",JOptionPane.ERROR_MESSAGE);
			return null;
		}

	}
	
	public static Embalajecb MiEmbalaje() {
		try {
			Session sesion = principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			List Embalajes = sesion.createQuery("from Embalaje").list();
			System.out.println("OJO!!! MiModelo()"+Embalajes.size()+" Elementos");
//			muevacio = (Embalajes.size()==0);			
			sesion.getTransaction().commit();
			Embalajecb modelo = new Embalajecb(Embalajes);		
			return modelo;
		} catch (HibernateException e2) {
			JOptionPane.showMessageDialog(new JFrame(), "Errores de Base de Datos!!!","Database Error!",JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	public static Unidadcb MiUnidad() {
		try {
			Session sesion = principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			List Unidades = sesion.createQuery("from Unidad").list();
			System.out.println("OJO!!! MiModelo()"+Unidades.size()+" Elementos");
//			muevacio = (Embalajes.size()==0);			
			sesion.getTransaction().commit();
			Unidadcb modelo = new Unidadcb(Unidades);		
			return modelo;
		} catch (HibernateException e2) {
			JOptionPane.showMessageDialog(new JFrame(), "Errores de Base de Datos!!!","Database Error!",JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	public static Ciudadcb MiCiudad() {
		try {
			Session sesion = principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			List Ciudades = sesion.createQuery("from Ciudad").list();
			System.out.println("OJO!!! MiModelo()"+Ciudades.size()+" Elementos");
//			muevacio = (Embalajes.size()==0);			
			sesion.getTransaction().commit();
			Ciudadcb modelo = new Ciudadcb(Ciudades);		
			return modelo;
		} catch (HibernateException e2) {
			JOptionPane.showMessageDialog(new JFrame(), "Errores de Base de Datos!!!","Database Error!",JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void focusLost(FocusEvent arg0) {
		if ((tfepeso.getText().isEmpty() || Double.parseDouble(tfepeso.getText().trim())>0) && (cbUnidad.getSelectedIndex()>-1) && (cbEmbalaje.getSelectedIndex()>-1))
		{
			embal = ModEmbalaje.getElemento(cbEmbalaje.getSelectedIndex());
			unid = ModUnidad.getElemento(cbUnidad.getSelectedIndex());
			tfemonto.setText(""+calculamonto1(embal,Integer.parseInt(tfepeso.getText().trim()),unid));
			tfeipost.setText(""+calculamonto2(embal,Integer.parseInt(tfepeso.getText().trim()),unid));
			Double total = (Double.parseDouble(tfemonto.getText())+Double.parseDouble(tfeipost.getText()));
			Double iva = Math.rint((total*0.12)*100)/100;
			Double gtotal = total+iva;
			tfeiva.setText(""+iva);
			tfetotal.setText(""+gtotal);
		}else
			tfemonto.setText("");
		
	}	
	
	
	
	public void getPrinter(File file)
	throws PrintException, FileNotFoundException {

	javax.print.DocFlavor flavor = javax.print.DocFlavor.INPUT_STREAM.AUTOSENSE;

	javax.print.attribute.PrintRequestAttributeSet pras =
	new javax.print.attribute.HashPrintRequestAttributeSet();

	PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
	javax.print.DocPrintJob job = printService.createPrintJob();

	java.io.FileInputStream fis = new java.io.FileInputStream(file);
	javax.print.attribute.DocAttributeSet das = new javax.print.attribute.HashDocAttributeSet();
	javax.print.Doc doc = new javax.print.SimpleDoc(fis, flavor, das);
	job.print(doc, pras);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==barra.btbuscar)
		{
			principal.mostrarInternalFrame(new BuscarFacturaFrame(this),false);
		}
		
	}

}
