package sys;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import sys.AdminFactura;
import data.Persona;


public class AdminPersona extends JInternalFrame {
	JPanel p1;
	static JTextField tfcedula = new JTextField(12);
	static JTextField tfnombre = new JTextField(25);
	static JTextField tfapelli = new JTextField(25);
	static JTextField tfdirecc = new JTextField(25);
	static JTextField tftelefo = new JTextField(12);
//	static JTextField tfedad = new JTextField(12);
	static Boolean carvacio = true;
	Boolean cierra = false;

	Ciudadcb ModCiudad;
	static JComboBox cbCiudad;
	
	String cedula;
	EventosManager eventosmanager;
	static BarraH barra;
	Date fecha;
	static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	JLabel lbcedula = new JLabel("Cédula");
	JLabel lbnombre = new JLabel("Nombre");
	JLabel lbapelli = new JLabel("Apellido");
	JLabel lbdirecc = new JLabel("Dirección");
	JLabel lbedad = new JLabel("Edad");
	JLabel lbtelefo = new JLabel("Teléfono");
	JLabel lbciudad = new JLabel("Ciudad");
	JLabel lbsexo = new JLabel("Sexo");
	Logger log = Logger.getLogger(this.getClass());
	static Persona actual, ultimo;
	static Boolean errorced = false;

	static Boolean buscando = false;
	static JLabel lbbuscando = new JLabel("");
	static List<Persona> busqueda = new ArrayList();
	static int puntero = 0;

	
	public AdminPersona() {		
		super("Datos de Personas", true, true, true, true);
		setSize(480, 335);
		setLayout(new FlowLayout());
		actual = new Persona();
//*		try {
//			mffecnac = new MaskFormatter("**/**/****");
//			tffecnac = new JFormattedTextField(mffecnac);
//			tffecnac.setValue("  /  /    ");
//			tffecnac.setColumns(6);
//		} catch (ParseException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//
		
		ModCiudad = MiCiudad();
		cbCiudad = new JComboBox(ModCiudad);
		
		tfcedula.setEditable(false);
		tfnombre.setEditable(false);
		tfapelli.setEditable(false);
		tfdirecc.setEditable(false);
		tftelefo.setEditable(false);
		cbCiudad.setEditable(false);
//		tfedad.setEditable(false);

		eventosmanager = new EventosManager();
		barra = new BarraH(1);

		barra.btnuevo.addActionListener(eventosmanager);
		barra.btmodifi.addActionListener(eventosmanager);
		barra.btgrabar.addActionListener(eventosmanager);
		barra.btcancel.addActionListener(eventosmanager);
		barra.btanteri.addActionListener(eventosmanager);
		barra.btbuscar.addActionListener(eventosmanager);
		barra.btsiguie.addActionListener(eventosmanager);
		barra.btultimo.addActionListener(eventosmanager);
		barra.btsalir.addActionListener(eventosmanager);

		p1 = new JPanel();
		GroupLayout layout = new GroupLayout(p1);
		p1.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup().addGroup(
								layout.createSequentialGroup()
										.addComponent(lbcedula)
										.addComponent(tfcedula,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup()
												.addComponent(lbnombre)
												.addComponent(lbapelli))
								.addGroup(
										layout.createParallelGroup()
												.addComponent(
														tfnombre,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														tfapelli,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)))
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup()
												.addComponent(lbciudad)
												.addComponent(lbdirecc)
												.addComponent(lbtelefo))
								.addGroup(
										layout.createParallelGroup()
												.addComponent(cbCiudad,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
												.addComponent(
														tfdirecc,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														tftelefo,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)))
				.addComponent(lbbuscando,GroupLayout.Alignment.CENTER)
				);
		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(lbcedula).addComponent(tfcedula))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(lbnombre).addComponent(tfnombre))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(lbapelli).addComponent(tfapelli))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(lbdirecc).addComponent(tfdirecc))
				.addGroup(
						layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lbciudad).addComponent(cbCiudad))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(lbtelefo).addComponent(tftelefo))
				.addComponent(lbbuscando)
				);

		tfcedula.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char car = e.getKeyChar();
				// Verificación de Teclas pulsadas...
				int largo = tfcedula.getText().length();
				if (largo == 0) {
					if ((car != 'V' && car != 'E') && car != '\b') {
						getToolkit().beep();
						e.consume();
					}
				} else if (largo < 5) {
					if ((car < '0' || car > '9') && car != '\b') {
						getToolkit().beep();
						e.consume();
					}
				} else if ((car < '0' || car > '9') && car != '\b'
						&& car != '.') {
					getToolkit().beep();
					e.consume();
				}
				if (largo > 11) {
					getToolkit().beep();
					e.consume();
				}
				if (tfcedula.getText().contains(".") && car == '.') {
					getToolkit().beep();
					e.consume();
				}

			}
		});

		tfcedula.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (!tfcedula.getText().trim().equals("")
						&& tfcedula.isEditable()) {
					Persona per = buscaPersona(tfcedula.getText());
					if (per != null) {
						JOptionPane.showMessageDialog(new JFrame(),
								"Nro. de Cédula ya Existe..., Llenar Datos!!!",
								"Database Error!", JOptionPane.ERROR_MESSAGE);
						tfcedula.setText(actual.getCedula());
						tfcedula.grabFocus();
					}
				}
			}

			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}

		});

		add(p1);
		add(barra);

		actual = obtenerUltimo();

		if (actual == null) {
			// JFrame frame = new JFrame();
			JOptionPane
					.showMessageDialog(new JFrame(),
							"No Existen Personas Registrados!!!!\nIngreso de Nuevo Persona");
			barra.btnuevo.doClick();
		} else {
			barra.Consulta();
			refreshPersona();
		}

		tfcedula.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char car = e.getKeyChar();
				int largo = tfcedula.getText().length();
				if (largo>0 && car=='\n' && buscando)
					barra.btanteri.doClick();
			}
		}
		
		);

		tfnombre.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char car = e.getKeyChar();
				int largo = tfnombre.getText().length();
				if (largo>0 && car=='\n' && buscando)
					barra.btanteri.doClick();
			}
		}
		
		);

		tfapelli.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char car = e.getKeyChar();
				int largo = tfapelli.getText().length();
				if (largo>0 && car=='\n' && buscando)
					barra.btanteri.doClick();
			}
		}
		
		);

	}

	public boolean isDate(String fechax) {
		try {
			formatoFecha.setLenient(false);
			fecha = formatoFecha.parse(fechax);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private Persona obtenerUltimo() {
		ultimo = new Persona();
		String q = "from Persona where CodOficina = "+principal.ccodoff+" order by codigo desc";
		try {
			Session sesion = principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			Query queryres = sesion
					.createQuery(q);
			queryres.setMaxResults(1);
			ultimo = (Persona) queryres.uniqueResult();
			sesion.getTransaction().commit();
			return ultimo;
		} catch (HibernateException e2) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Errores de Base de Datos!!!", "Database Error!",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	public static void refreshPersona() {
		if (buscando)
		{
			tfcedula.setText(actual.getCedula());
			tfnombre.setText(actual.getNombre());
			tfapelli.setText(actual.getApellido());
			tfdirecc.setText(actual.getDireccion());
			tftelefo.setText(actual.getTelefono());
			cbCiudad.setSelectedItem(actual.getPerCiudad().getDescripcion());
	    	lbbuscando.setText("Buscando "+(puntero+1)+"/"+busqueda.size());
		}else
		{
			if (actual != null) {
				tfcedula.setText(actual.getCedula());
				tfnombre.setText(actual.getNombre());
				tfapelli.setText(actual.getApellido());
				tfdirecc.setText(actual.getDireccion());
				tftelefo.setText(actual.getTelefono());
				cbCiudad.setSelectedItem(actual.getPerCiudad().getDescripcion());
			} else {
				tfcedula.setText("");
				tfnombre.setText("");
				tfapelli.setText("");
				tfdirecc.setText("");
				tftelefo.setText("");
				cbCiudad.setSelectedItem(-1);
			}
			if (barra.btnuevo.isEnabled()) {
				tfcedula.setEditable(false);
				tfnombre.setEditable(false);
				tfapelli.setEditable(false);
				tfdirecc.setEditable(false);
				tftelefo.setEditable(false);
				cbCiudad.setEditable(false);
//				tfedad.setEditable(false);
				barra.Consulta();
			}	
		}
	}

	private Persona buscaPersona(String text) {
		Persona per = new Persona();
		String q = "from Persona where cedula = :text and CodOficina = "+principal.ccodoff;
		try {
			Session sesion = principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			Query queryResult = sesion
					.createQuery(q);
			queryResult.setString("text", text);
			per = (Persona) queryResult.uniqueResult();
			sesion.getTransaction().commit();
			return per;
		} catch (HibernateException e2) {
			JOptionPane.showMessageDialog(new JFrame(),
					"buscaPersona!!!Errores de Base de Datos!!!\n" + e2 + " " + text,
					"Database Error!", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	public class EventosManager implements ActionListener {
		// TODO Auto-generated method stub
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == barra.btnuevo
					|| e.getSource() == barra.btmodifi) {
				if (e.getSource() == barra.btnuevo) {
					tfcedula.setText("");
					tfnombre.setText("");
					tfapelli.setText("");
					tfdirecc.setText("");
					// tfedad.setText("");
					tftelefo.setText("");
					ultimo = actual;
					actual = new Persona();
				}

				barra.Edicion();				
				tfcedula.setEditable(true);
				tfcedula.grabFocus();
				tfcedula.setEditable(true);
				tfnombre.setEditable(true);
				tfapelli.setEditable(true);
				tfdirecc.setEditable(true);
				tftelefo.setEditable(true);
				cbCiudad.setEditable(true);
			}
			if (e.getSource() == barra.btgrabar) {
				if (tfcedula.getText().trim().equals("")
						|| tfapelli.getText().trim().equals("")
						|| tfnombre.getText().trim().equals("")
						|| tfdirecc.getText().trim().equals("")) {
					JOptionPane
							.showMessageDialog(new JFrame(),
									"Debe completar todos los Datos del Persona\nIngreso de Nuevo Persona");
					tfcedula.grabFocus();
				} else {
					actual.setCedula(tfcedula.getText().trim());
					actual.setApellido(tfapelli.getText().trim());
					actual.setNombre(tfnombre.getText().trim());
					actual.setDireccion(tfdirecc.getText().trim());
					actual.setTelefono(tftelefo.getText());
					actual.setPerCiudad(ModCiudad.getElemento(cbCiudad.getSelectedIndex()));
					actual.setPerOficina(principal.ofic);
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
					/*
					 * if (pac!=null) { JOptionPane.showMessageDialog(new
					 * JFrame(),
					 * "Número de Cédula ya existe!!!","Database Error!"
					 * ,JOptionPane.ERROR_MESSAGE); tfcedula.grabFocus(); }
					 */
					barra.Consulta();
					if (cierra)
					{
						AdminFactura.setPersona(actual);
						cerrarventana();
					}
					
					// refreshPersona();
				}
			}
			if (e.getSource() == barra.btcancel) {
				if (cierra)
				{
					AdminFactura.setPersona(actual);
					cerrarventana();
				}
				if (buscando) {
			    	lbbuscando.setText("");
					buscando = false;
				}
				if (actual.equals(new Persona())) actual = ultimo;
				barra.Consulta();
				refreshPersona();
			}
			if (e.getSource() == barra.btbuscar) {
				barrabusca();
			}
			if (e.getSource() == barra.btanteri) {
				if (buscando){
					if (tfcedula.isEditable()){
						buscar();	
					}
						actual = busqueda.get(puntero>0?--puntero:puntero);
						System.out.println(""+puntero+"/"+busqueda.size());
						refreshPersona();
				}else
				{
					Persona per = null;
					int text = actual.getCodigo();
					String q = "from Persona where codigo < :text and CodOficina = "+principal.ccodoff+" order by codigo desc";
					try {
						Session sesion = principal.fabrica.getCurrentSession();
						sesion.beginTransaction();
						Query queryResult = sesion
								.createQuery(q);
						queryResult.setInteger("text", text);
						queryResult.setMaxResults(1);
						per = (Persona) queryResult.uniqueResult();
						sesion.getTransaction().commit();
					} catch (HibernateException e2) {
						JOptionPane.showMessageDialog(new JFrame(),
								"btAnterior!!!Errores de Base de Datos!!!\n" + e2 + " "
										+ text, "Database Error!",
								JOptionPane.ERROR_MESSAGE);
					}
					if (per != null) {
						actual = per;
						refreshPersona();
					}					
				}
			}
			if (e.getSource() == barra.btsiguie) {
				if (buscando){
					if (tfcedula.isEditable()){
						buscar();	
					}
						actual = busqueda.get(puntero<busqueda.size()-1?++puntero:puntero);
						System.out.println(""+puntero+"/"+busqueda.size());
						refreshPersona();
				}else
				{
					Persona per = null;
					int text = actual.getCodigo();
					String q = "from Persona where codigo > :text and CodOficina = "+principal.ccodoff+" order by codigo desc";
					try {
						Session sesion = principal.fabrica.getCurrentSession();
						sesion.beginTransaction();
						Query queryResult = sesion
								.createQuery(q);
						queryResult.setInteger("text", text);
						queryResult.setMaxResults(1);
						per = (Persona) queryResult.uniqueResult();
						sesion.getTransaction().commit();
					} catch (HibernateException e2) {
						JOptionPane.showMessageDialog(new JFrame(),
								"btsiguie!!!Errores de Base de Datos!!!\n" + e2 + " "
										+ text, "Database Error!",
								JOptionPane.ERROR_MESSAGE);
					}
					if (per != null) {
						actual = per;
						refreshPersona();
					}	
				}
			}

			if (e.getSource() == barra.btultimo) {
				if (buscando)
				{
					puntero = busqueda.size()-1;
					if (tfcedula.isEditable()){
						buscar();	
					}
						actual = busqueda.get(puntero);
						System.out.println(""+puntero+"/"+busqueda.size());
				}
				else
				{
					actual = obtenerUltimo();
					refreshPersona();
				}
			}
			if (e.getSource() == barra.btsalir) {
				dispose();
			}
		}

	}

	public void setCedula(String text) {
		// TODO Auto-generated method stub
		barra.btnuevo.doClick();
		tfcedula.setText(text);
		tfcedula.setEnabled(false);
		cierra = true;
	}

	public void cerrarventana()
	{
		this.dispose();
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


    public void buscar()
    {
		String q = "from Persona where codigo < :text and CodOficina = "+principal.ccodoff+" order by codigo desc";
    	puntero = 0;
		try {
			String quer = "from Persona ";
			if (!tfcedula.getText().trim().equals(""))
			{
				quer = quer+"where Cedula like '%"+tfcedula.getText().trim()+"%' ";
			}
			if (!tfnombre.getText().trim().equals(""))
			{
				quer = quer+(tfcedula.getText().trim().equals("")?"where ":"and ")+"Nombre like '%"+tfnombre.getText().trim()+"%' ";
			}
			if (!tfapelli.getText().trim().equals(""))
			{
				quer = quer+((tfcedula.getText().trim().equals("") && tfnombre.getText().trim().equals(""))?"where ":"and ")+"Apellido like '%"+tfapelli.getText().trim()+"%' ";
			}
			quer = quer + "and CodOficina = "+principal.ccodoff+" order by Cedula asc";
			Session sesion = principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery(quer);
            busqueda = (List<Persona>) queryResult.list();  
			sesion.getTransaction().commit();
		} catch (HibernateException e2) {
			JOptionPane.showMessageDialog(new JFrame(), "AQUI!!!Errores de Base de Datos!!!\n"+e2+" ","Database Error!",JOptionPane.ERROR_MESSAGE);
		}
    	this.tfcedula.setEditable(false);
    	this.tfnombre.setEditable(false);
    	this.tfapelli.setEditable(false);	
    	lbbuscando.setText("Buscando "+(puntero+1)+"/"+busqueda.size());
		if (busqueda.size()<1)
		{
			JOptionPane.showMessageDialog(new JFrame(), "La Busqueda no arrojó resultados!!!","Sin resultados!",JOptionPane.ERROR_MESSAGE);
			this.barra.btnuevo.setEnabled(true);
			actual = obtenerUltimo();
			refreshPersona();
		}
    }

    public void barrabusca()
    {
    	actual = new Persona();
    	this.barra.btnuevo.setEnabled(false);
    	this.barra.btmodifi.setEnabled(false);
    	this.barra.btgrabar.setEnabled(false);
    	this.barra.btcancel.setEnabled(true);
    	this.barra.btbuscar.setEnabled(true);
    	this.barra.btanteri.setEnabled(true);
    	this.barra.btsiguie.setEnabled(true);
    	this.barra.btultimo.setEnabled(true);
    	this.barra.btsalir.setEnabled(true);
    	buscando = true;
    	this.tfcedula.setEditable(true);
    	this.tfnombre.setEditable(true);
    	this.tfapelli.setEditable(true);
    	this.tfcedula.setText("");
    	this.tfnombre.setText("");
    	this.tfapelli.setText("");
    }

	
}
