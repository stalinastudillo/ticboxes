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
import data.Facturacion;
import data.Persona;


public class AdminFacturacion extends JInternalFrame {
	JPanel p1;
	static JTextField tfrif = new JTextField(12);
	static JTextField tfrazon = new JTextField(25);
	static JTextField tfdireccion = new JTextField(25);
	static JTextField tftelefono = new JTextField(15);
	static JTextField tfcorreo = new JTextField(15);
	Boolean cierra = false;

	Ciudadcb ModCiudad;
	static JComboBox cbCiudad;
	
	String cedula;
	EventosManager eventosmanager;
	static BarraH barra;
	MaskFormatter mffecnac;
	Date fecha;
	static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	JLabel lbrif = new JLabel("RIF");
	JLabel lbrazon = new JLabel("Razón");
	JLabel lbdireccion = new JLabel("Dirección");
	JLabel lbtelefono = new JLabel("Teléfono");
	JLabel lbcorreo = new JLabel("Correo");
	JLabel lbciudad = new JLabel("Ciudad");
//	JLabel lbfecnac = new JLabel("Fecha de Nac.");
//	JLabel lbedad = new JLabel("Edad");
//	JLabel lbtelefo = new JLabel("Teléfono");
//	JLabel lbemail = new JLabel("e-mail");
//	JLabel lbsexo = new JLabel("Sexo");
	Logger log = Logger.getLogger(this.getClass());
	static Facturacion actual, ultimo;
	static Boolean errorced = false;

	static Boolean buscando = false;
	static JLabel lbbuscando = new JLabel("");
	static List<Facturacion> busqueda = new ArrayList();
	static int puntero = 0;
	
	public AdminFacturacion() {		
		super("Datos de Facturación", true, true, true, true);
		setSize(480, 335);
		setLayout(new FlowLayout());
		actual = new Facturacion();
//		try {
//			mffecnac = new MaskFormatter("**/**/****");
//			tffecnac = new JFormattedTextField(mffecnac);
//			tffecnac.setValue("  /  /    ");
//			tffecnac.setColumns(6);
//		} catch (ParseException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}

		tfrif.setEditable(false);
		tfrazon.setEditable(false);
		tfdireccion.setEditable(false);
		tftelefono.setEditable(false);
		tfcorreo.setEditable(false);


		eventosmanager = new EventosManager();
		barra = new BarraH(1);

		ModCiudad = MiCiudad();
		cbCiudad = new JComboBox(ModCiudad);

		cbCiudad.setEditable(false);
		
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
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup()
												.addComponent(lbrif)
												.addComponent(lbrazon)
												.addComponent(lbdireccion)
												.addComponent(lbciudad)
												.addComponent(lbtelefono)
												.addComponent(lbcorreo))
								.addGroup(
										layout.createParallelGroup()
												.addComponent(tfrif,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(tfrazon,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(tfdireccion,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(cbCiudad,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(tftelefono,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														tfcorreo,
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
								.addComponent(lbrif).addComponent(tfrif))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(lbrazon).addComponent(tfrazon))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(lbdireccion).addComponent(tfdireccion))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(lbciudad).addComponent(cbCiudad))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(lbtelefono).addComponent(tftelefono))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(lbcorreo).addComponent(tfcorreo))
				.addComponent(lbbuscando)
);

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
			refreshFacturacion();
		}

		tfrazon.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char car = e.getKeyChar();
				int largo = tfrazon.getText().length();
				if (largo>0 && car=='\n' && buscando)
					barra.btanteri.doClick();
			}
		}
		
		);

		tfrif.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char car = e.getKeyChar();
				int largo = tfrif.getText().length();
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
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private Facturacion obtenerUltimo() {
		ultimo = new Facturacion();
		String q = "from Facturacion where CodOficina = "+principal.ccodoff+" order by codigo desc";
		try {
			Session sesion = principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			Query queryres = sesion
					.createQuery(q);
			queryres.setMaxResults(1);
			ultimo = (Facturacion) queryres.uniqueResult();
			sesion.getTransaction().commit();
			return ultimo;
		} catch (HibernateException e2) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Errores de Base de Datos!!!", "Database Error!",
					JOptionPane.ERROR_MESSAGE);
			e2.printStackTrace();
			return null;
		}
	}

	public static void refreshFacturacion() {
		if (buscando)
		{
			tfrif.setText(actual.getRif());
			tfrazon.setText(actual.getRazon());
			tfdireccion.setText(actual.getDireccion());
			tfcorreo.setText("" + actual.getCorreo());
			cbCiudad.setSelectedItem(actual.getFacCiudad().getDescripcion());
	    	lbbuscando.setText("Buscando "+(puntero+1)+"/"+busqueda.size());
		}else
		{
			if (actual != null) {
				tfrif.setText(actual.getRif());
				tfrazon.setText(actual.getRazon());
				tfdireccion.setText(actual.getDireccion());
				tfcorreo.setText("" + actual.getCorreo());
				cbCiudad.setSelectedItem(actual.getFacCiudad().getDescripcion());
			} else {
				tfrif.setText("");
				tfrazon.setText("");
				tfdireccion.setText("");
				tfcorreo.setText("");
				cbCiudad.setSelectedItem(-1);
			}
			if (barra.btnuevo.isEnabled()) {
				tfrif.setEditable(false);
				tfrazon.setEditable(false);
				tfdireccion.setEditable(false);
				tftelefono.setEditable(false);
				tfcorreo.setEditable(false);
				cbCiudad.setEditable(false);
				barra.Consulta();
			}	
		}
	}

	private Facturacion buscaFacturacion(String text) {
		Facturacion per = new Facturacion();
		String q = "from Facturacion where CodOficina = "+principal.ccodoff+" and rif = :text";
		try {
			Session sesion = principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			Query queryResult = sesion
					.createQuery(q);
			queryResult.setString("text", text);
			per = (Facturacion) queryResult.uniqueResult();
			sesion.getTransaction().commit();
			return per;
		} catch (HibernateException e2) {
			JOptionPane.showMessageDialog(new JFrame(),
					"buscaPersona!!!Errores de Base de Datos!!!\n" + e2 + " " + text,
					"Database Error!", JOptionPane.ERROR_MESSAGE);
			e2.printStackTrace();
			return null;
		}
	}

	public class EventosManager implements ActionListener {
		// TODO Auto-generated method stub
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == barra.btnuevo
					|| e.getSource() == barra.btmodifi) {
				if (e.getSource() == barra.btnuevo) {
					tfrif.setText("");
					tfrazon.setText("");
					tfdireccion.setText("");
					tftelefono.setText("");
					tfcorreo.setText("");
					cbCiudad.setSelectedItem(-1);
					ultimo = actual;
					actual = new Facturacion();
				}
				barra.Edicion();
				tfrif.grabFocus();

				tfrif.setEditable(true);
				tfrazon.setEditable(true);
				tfdireccion.setEditable(true);
				tftelefono.setEditable(true);
				tfcorreo.setEditable(true);
				cbCiudad.setEditable(true);
			}
			if (e.getSource() == barra.btgrabar) {
				if (tfrif.getText().trim().equals("")
						|| tfrazon.getText().trim().equals("")
						|| tfdireccion.getText().trim().equals("")) {
					JOptionPane
							.showMessageDialog(new JFrame(),
									"Debe completar todos los Datos del Persona\nIngreso de Nuevo Persona");
					tfrif.grabFocus();
				} else {
					actual.setRif(tfrif.getText().trim());
					actual.setRazon(tfrazon.getText().trim());
					actual.setDireccion(tfdireccion.getText().trim());
					actual.setTelefono(tftelefono.getText().trim());
					actual.setFacCiudad(ModCiudad.getElemento(cbCiudad.getSelectedIndex()));
					actual.setFacOficina(principal.ofic);
					if (cbCiudad.getSelectedIndex()>-1)
						actual.setFacCiudad(ModCiudad.getElemento(cbCiudad.getSelectedIndex()));
					actual.setCorreo(tfcorreo.getText().trim());
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
						e2.printStackTrace();
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
						AdminFactura.setFacturacion(actual);
						cerrarventana();
					}
					
					// refreshPersona();
				}
			}
			if (e.getSource() == barra.btcancel) {
				if (cierra)
				{
//					AdminFactura.setFacturacion(actual);  corregido el 12/01/2012 estaba dando error, ya que si cancela la ventana no debe grabar nada en AdminFactura.setFacturacion()
					cerrarventana();
				}
				if (buscando) {
			    	lbbuscando.setText("");
					buscando = false;
				}
				if (actual.equals(new Persona())) actual = ultimo;
				barra.Consulta();
				refreshFacturacion();
			}
			if (e.getSource() == barra.btbuscar) {
				barrabusca();
			}
			if (e.getSource() == barra.btanteri) {
				if (buscando){
					if (tfrif.isEditable()){
						buscar();	
					}
						actual = busqueda.get(puntero>0?--puntero:puntero);
						System.out.println(""+puntero+"/"+busqueda.size());
						refreshFacturacion();
				}else
				{
					Facturacion per = null;
					int text = actual.getCodigo();
					String q = "from Facturacion where CodOficina = "+principal.ccodoff+" and codigo < :text order by codigo desc";
					try {
						Session sesion = principal.fabrica.getCurrentSession();
						sesion.beginTransaction();
						Query queryResult = sesion
								.createQuery(q);
						queryResult.setInteger("text", text);
						queryResult.setMaxResults(1);
						per = (Facturacion) queryResult.uniqueResult();
						sesion.getTransaction().commit();
					} catch (HibernateException e2) {
						JOptionPane.showMessageDialog(new JFrame(),
								"btAnterior!!!Errores de Base de Datos!!!\n" + e2 + " "
										+ text, "Database Error!",
								JOptionPane.ERROR_MESSAGE);
						e2.printStackTrace();
					}
					if (per != null) {
						actual = per;
						refreshFacturacion();
					}					
				}
			}
			if (e.getSource() == barra.btsiguie) {
				if (buscando){
					if (tfrif.isEditable()){
						buscar();	
					}
						actual = busqueda.get(puntero<busqueda.size()-1?++puntero:puntero);
						System.out.println(""+puntero+"/"+busqueda.size());
						refreshFacturacion();
				}else
				{
					Facturacion per = null;
					int text = actual.getCodigo();
					String q = "from Facturacion where CodOficina = "+principal.ccodoff+" and codigo > :text order by codigo asc";
					try {
						Session sesion = principal.fabrica.getCurrentSession();
						sesion.beginTransaction();
						Query queryResult = sesion
								.createQuery(q);
						queryResult.setInteger("text", text);
						queryResult.setMaxResults(1);
						per = (Facturacion) queryResult.uniqueResult();
						sesion.getTransaction().commit();
					} catch (HibernateException e2) {
						JOptionPane.showMessageDialog(new JFrame(),
								"btsiguie!!!Errores de Base de Datos!!!\n" + e2 + " "
										+ text, "Database Error!",
								JOptionPane.ERROR_MESSAGE);
						e2.printStackTrace();
					}
					if (per != null) {
						actual = per;
						refreshFacturacion();
					}					
				}
			}

			if (e.getSource() == barra.btultimo) {
				if (buscando)
				{
					puntero = busqueda.size()-1;
					if (tfrif.isEditable()){
						buscar();	
					}
						actual = busqueda.get(puntero);
						System.out.println(""+puntero+"/"+busqueda.size());
				}
				else
				{
					actual = obtenerUltimo();
					refreshFacturacion();
				}
			}
			if (e.getSource() == barra.btsalir) {
				dispose();
			}
		}

	}

	public void setRif(String text) {
		// TODO Auto-generated method stub
		barra.btnuevo.doClick();
		tfrif.setText(text);
		tfrif.setEnabled(false);
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
			e2.printStackTrace();
			return null;
		}
	}



    public void buscar()
    {
    	puntero = 0;
		try {
			String quer = "from Facturacion ";
			if (!tfrif.getText().trim().equals(""))
			{
				quer = quer+"where Rif like '%"+tfrif.getText().trim()+"%' ";
			}
			if (!tfrazon.getText().trim().equals(""))
			{
				quer = quer+(tfrif.getText().trim().equals("")?"where ":"and ")+"Razon like '%"+tfrazon.getText().trim()+"%' ";
			}
/*			if (!tfapelli.getText().trim().equals(""))
			{
				quer = quer+((tfcedula.getText().trim().equals("") && tfnombre.getText().trim().equals(""))?"where ":"")+"Apellido like '%"+tfapelli.getText().trim()+"%' ";
			}
*/			quer = quer + " and CodOficina = "+principal.ccodoff+" order by Rif asc";



			Session sesion = principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			Query queryResult = sesion.createQuery(quer);
            busqueda = (List<Facturacion>) queryResult.list();  
			sesion.getTransaction().commit();
		} catch (HibernateException e2) {
			JOptionPane.showMessageDialog(new JFrame(), "AQUI!!!Errores de Base de Datos!!!\n"+e2+" ","Database Error!",JOptionPane.ERROR_MESSAGE);
			e2.printStackTrace();
		}
    	this.tfrif.setEditable(false);
    	this.tfrazon.setEditable(false);
//    	this.tfapelli.setEditable(false);	
    	lbbuscando.setText("Buscando "+(puntero+1)+"/"+busqueda.size());
		if (busqueda.size()<1)
		{
			JOptionPane.showMessageDialog(new JFrame(), "La Busqueda no arrojó resultados!!!","Sin resultados!",JOptionPane.ERROR_MESSAGE);
			this.barra.btnuevo.setEnabled(true);
			actual = obtenerUltimo();
			refreshFacturacion();
		}
    }

    public void barrabusca()
    {
    	actual = new Facturacion();
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
    	this.tfrif.setEditable(true);
    	this.tfrazon.setEditable(true);
//    	this.tfapelli.setEditable(true);
    	this.tfrif.setText("");
    	this.tfrazon.setText("");
//    	this.tfapelli.setText("");
    }


	
}
