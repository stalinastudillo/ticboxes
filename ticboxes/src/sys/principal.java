package sys;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.Border;

import javax.swing.UIManager.*;

//import javax.mail.*;
 

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import data.*;

public class principal extends JFrame{
	
	static JDesktopPane desktop;
	/**
	 * @param args
	 */
	static final String con = "terminal";
	static final Boolean cam = false;
	static Oficina ofic;

	static final int ccodoff = 2;
	
	Logger log = Logger.getLogger(this.getClass());
	static AnnotationConfiguration config;
	static SessionFactory fabrica; 	
	static Session sesion; 
	
	JMenu mppEnvios;
	JMenu mppClientes;
	JMenu mppInform;
	JMenu mppUtilid;

	JMenuItem envFactura;
	
	JMenuItem cliClientes;
	JMenuItem cliFacturacion;
		
	JMenuItem infClientes;
	JMenuItem infCuadre;
	JMenuItem infFacturacion;

	JMenuItem utiConfig;

	JToolBar mppBarra;
	JButton btnSalir;
	JButton btnAcerca;
	
	JPanel panell;
	
	Dimension separador;
	JTextArea txaBarra;
	
	Calendar fecha;
	
	final String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};

	public void buildGUI()
	{		
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		super.setTitle("TicBoxes V.2.0");
		super.setSize(1000,720);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setResizable(true);

/*		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getClassLoader().getResource("images/frame.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		super.setIconImage(image);
*/		
		
		mppEnvios = new JMenu("Envios");
		mppEnvios.setMnemonic('E');

		desktop = new JDesktopPane();
		
/*		BufferedImage image1 = null;
		try {
			image1 = ImageIO.read(getClass().getClassLoader().getResource("images/fondo.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		desktop.setBorder(new ImagenFondoCentrada(image1));
*/
		
		envFactura = new JMenuItem("Facturar");
		envFactura.setMnemonic('F');

		mppEnvios.add(envFactura);
		mppEnvios.add(envFactura);

		mppClientes = new JMenu("Clientes");
		mppClientes.setMnemonic('C');
		
		cliClientes = new JMenuItem("Clientes");
		cliClientes.setMnemonic('l');
		cliFacturacion = new JMenuItem("Facturacion");
		cliFacturacion.setMnemonic('u');

		mppClientes.add(cliClientes);
		mppClientes.add(cliFacturacion);
			
		mppInform = new JMenu("Informes");
		mppInform.setMnemonic('I');
		
		infClientes = new JMenuItem("Clientes");
		infClientes.setMnemonic('i');
		infFacturacion = new JMenuItem("Facturacion");
		infFacturacion.setMnemonic('r');
		infCuadre = new JMenuItem("Cuadre Diario");
		infCuadre.setMnemonic('e');
		
		mppInform.add(infClientes);
		mppInform.add(infFacturacion);
		mppInform.add(infCuadre);
		
		mppUtilid = new JMenu("Utilidades");
		mppUtilid.setMnemonic('U');
		
		utiConfig = new JMenuItem("Configuración");
		utiConfig.setMnemonic('F');
		
		mppUtilid.add(utiConfig);
		
		JMenuBar menu = new JMenuBar();

		menu.add(mppEnvios);
		menu.add(mppClientes);
		menu.add(mppInform);
		menu.add(mppUtilid);

		mppBarra = new JToolBar();
		btnSalir = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/Exit24.gif")));
		btnAcerca = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/About24.gif")));

		txaBarra = new JTextArea(2,20);

		fecha = Calendar.getInstance();  //Carga la fecha del Sistema para mostrarla!!!!
		
		txaBarra.setText("Asociación Civil Línea San Antonio\nRIF: J-09006948-8\n"+fecha.get(Calendar.DATE)+" de "+meses[fecha.get(Calendar.MONTH)]+" de "+fecha.get(Calendar.YEAR));
		txaBarra.setBackground(new Color(200,240,240));
		txaBarra.setForeground(new Color(255,0,0));

		mppBarra.add(btnAcerca);
		mppBarra.add(btnSalir);
		separador = new Dimension();
		separador.setSize(480, 10);
		mppBarra.addSeparator(separador);
		txaBarra.setAlignmentX(RIGHT_ALIGNMENT);
		txaBarra.setAlignmentY(CENTER_ALIGNMENT);
		txaBarra.setEditable(false);
		txaBarra.setSize(20, 20);
		mppBarra.add(txaBarra);
		
		envFactura.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				envFacturaActionPerformed(ev);
			}
		});

		cliClientes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				cliClientesActionPerformed(ev);
			}
		});

		cliFacturacion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				cliFacturacionActionPerformed(ev);
			}
		});
		
		infClientes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				infClientesActionPerformed(ev);
			}
		});

		infFacturacion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				infFacturacionActionPerformed(ev);
			}
		});

		infCuadre.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				infCuadreActionPerformed(ev);
			}
		});
		

		utiConfig.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				utiConfigActionPerformed(ev);
			}
		});

/*		proProces.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				proProcesActionPerformed(ev);
			}
		});
*/		
	
		setJMenuBar(menu);
		add(mppBarra, BorderLayout.NORTH);
		add(desktop, BorderLayout.CENTER);
		setVisible(true);
		
	}
	
	protected void envFacturaActionPerformed(ActionEvent ev) {
		log.info("Seleccioné Admin Factura !!!!");
		principal.mostrarInternalFrame(new AdminFactura(), false);
	}

	private void cliClientesActionPerformed(ActionEvent ev) {
		// TODO Auto-generated method stub
		log.info("Seleccioné Admin Clientes!!!!");
		principal.mostrarInternalFrame(new AdminPersona(), false);		
	}

	private void cliFacturacionActionPerformed(ActionEvent ev) {
		// TODO Auto-generated method stub
		log.info("Seleccioné Admin Clientes!!!!");
		principal.mostrarInternalFrame(new AdminFacturacion(), false);		
	}

	private void infClientesActionPerformed(ActionEvent ev) {
		log.info("Seleccioné Informe Clientes!!!!");
		principal.mostrarInternalFrame(new InforClientes(),false);

	}

	private void infFacturacionActionPerformed(ActionEvent ev) {
		log.info("Seleccioné Informe Facturación!!!!");
		principal.mostrarInternalFrame(new InforFacturacion(), true);
	}

	private void infCuadreActionPerformed(ActionEvent ev) {
		// TODO Auto-generated method stub
		log.info("Seleccioné Informe Cuadre!!!!");
		principal.mostrarInternalFrame(new InformCuadre(), false);

	}

	private void utiConfigActionPerformed(ActionEvent ev) {
		log.info("Seleccioné Configuración");
		principal.mostrarInternalFrame(new AdminConfig(),true);
	}

	public void initdata() throws HibernateException {
//		JOptionPane.showMessageDialog(new JFrame(),"Entramos en initdata!!!!");
		log.info("Entramos en initdata///////////////////////////////////////////");
		try 
		{
			config = new AnnotationConfiguration();
			log.info("Cargando las Clases Persistentes//////////////////////////////////");
			config = new AnnotationConfiguration();
			config.addAnnotatedClass(Banco.class);
			config.addAnnotatedClass(Ciudad.class);
			config.addAnnotatedClass(Direccion.class);
			config.addAnnotatedClass(Estado.class);
			config.addAnnotatedClass(Factura.class);
			config.addAnnotatedClass(Facturacion.class);
			config.addAnnotatedClass(Grupo.class);
			config.addAnnotatedClass(Unidad.class);
			config.addAnnotatedClass(Embalaje.class);
			config.addAnnotatedClass(Imagen.class);
			config.addAnnotatedClass(Municipio.class);
			config.addAnnotatedClass(Pago.class);
			config.addAnnotatedClass(Persona.class);
			config.addAnnotatedClass(Seccion.class);
			config.addAnnotatedClass(Usuario.class);
			config.addAnnotatedClass(Tarifa.class);
			config.addAnnotatedClass(TarifaIpostel.class);
			config.addAnnotatedClass(Oficina.class);
			config.addAnnotatedClass(Envio.class);
			
//			IGUAL QUE = config.configure();
			Properties propiedades = new Properties();
			propiedades.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");

			if (con=="terminal")
			{
				propiedades.setProperty("hibernate.connection.url","jdbc:mysql://localhost/ticboxes");
				propiedades.setProperty("hibernate.connection.username", "prueba");
				propiedades.setProperty("hibernate.connection.password", "12345"); 				
			}

			if (con=="sanantonio")
			{
				propiedades.setProperty("hibernate.connection.url","jdbc:mysql://localhost/ticboxes");
				propiedades.setProperty("hibernate.connection.username", "prueba");
				propiedades.setProperty("hibernate.connection.password", "12345"); 				
			}

			
			if (con=="local")
			{
				propiedades.setProperty("hibernate.connection.url","jdbc:mysql://localhost/ticboxes");
				propiedades.setProperty("hibernate.connection.username", "prueba");
				propiedades.setProperty("hibernate.connection.password", ""); 				
			}

			propiedades.setProperty("hibernate.connection.pool_size","2");
			propiedades.setProperty("hibernate.current_session_context_class","thread");
	        propiedades.setProperty("hibernate.cache.provider_class","org.hibernate.cache.NoCacheProvider");
			propiedades.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
			propiedades.setProperty("hibernate.show_sql","true");
			config.addProperties(propiedades);
// 			FIN DE IGUAL QUE!!!
			
			log.info("Entramos en configure()///////////////////////////////////////////");
			fabrica = config.buildSessionFactory();
			log.info("Entramos en config.buildSessionFactory()///////////////////////////////////////////");
//			new SchemaExport(config).create(true,true);
		}catch (HibernateException e) {
			log.info("Entramos en Hibernate Exception!: "+e);			
		}
		log.info("Pasó el try/catch!!!///////////////////////////////////////////");


	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new principal().buildGUI();
		new principal().initdata();
		List<Oficina> Oficinas = new ArrayList();
		try {
			Session sesion = principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			Oficinas = sesion.createQuery("from Oficina").list();
			System.out.println("OJO!!! CANTIDAD DE OFICINAS "+Oficinas.size()+" Elementos");
			
			sesion.getTransaction().commit();

			for (Oficina iterxx : Oficinas)
			{
				System.out.println("OFICINA!!!!!"+iterxx.getCodigo()+", "+iterxx.getDescripcion()+",  "+iterxx.getActiva());

//				if (iterxx.getActiva())
				if (iterxx.getCodigo()==ccodoff)
				{
					ofic = iterxx;
					System.out.println("OFICINA!!!!!"+iterxx.getCodigo()+", "+iterxx.getDescripcion()+" ACTIVA");
				}
			}
			System.out.println("OFICINA!!!!!"+ofic.getCodigo()+", "+ofic.getDescripcion()+" ACTIVA");
		} catch (HibernateException e2) {
			JOptionPane.showMessageDialog(new JFrame(), "Errores de Base de Datos!!!","Database Error!",JOptionPane.ERROR_MESSAGE);

		}
//		Enlace h_enlace = new Enlace();
//		h_enlace.start();
		
	}


	public static void mostrarInternalFrame(JInternalFrame frame, Boolean max){
		frame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		principal.desktop.add(frame);
		frame.setLocation(10, 10);
//		frame.setSize(700,450);
		if (max)
			try {
				frame.setMaximum(true);
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		frame.setVisible(true);
	}

	public static void mostrarVentana(JInternalFrame frame) {
		frame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
//		frame.setSize(450,250);
		principal.desktop.add(frame);
		frame.setLocation(10,10);
		frame.setVisible(true);
	}
	
	public class ImagenFondoCentrada implements Border {
		
		private final Image image;
		
		public ImagenFondoCentrada(Image image) {
			this.image = image;
		}

		@Override
		public Insets getBorderInsets(Component arg0) {
			// TODO Auto-generated method stub
			return new Insets(0,0,0,0);
		}

		@Override
		public boolean isBorderOpaque() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x,
				int y, int width, int height) {
			// TODO Auto-generated method stub
			int x0 = x + ((width-image.getWidth(c))/2);
			int y0 = y + ((height-image.getHeight(c))/2);
			g.drawImage(image, x0, y0, null);
		}
		
	}

	public boolean executeDBScripts(String aSQLScriptFilePath, Statement stmt)
	{
		boolean isScriptExecuted = false;
		try 
		{
			BufferedReader in = new BufferedReader(new FileReader(aSQLScriptFilePath));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = in.readLine()) != null) 
			{
				sb.append(str + "\n ");
			}
			in.close();
			stmt.executeUpdate(sb.toString());
			isScriptExecuted = true;
		} catch (Exception e) {
				System.err.println("Failed to Execute" + aSQLScriptFilePath +". The error is"+ e.getMessage());
		} 
		return isScriptExecuted;
	}
	
	public void writetextfile(String archivo, String contenido)
	{
		Writer output = null;
		String text = contenido;
		File file = new File(archivo);
		try {
			output = new BufferedWriter(new FileWriter(file));
			output.write(text);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Your file has been written");  
	}
	
	public void Leerarchivos()
	{
		String path = "recetas/";   /// DIRECTORIO DE EJEMPLO EL REAL DEBE DECIR ALGO ASÍ C:\\Users\\Titon\\Dropbox\\SANANTONIO
		File directorio = new File(path);
		String [] ficheros = directorio.list();
		String line;
		for (int i = 0; i < ficheros.length; i++) {
		    try {
		        BufferedReader br = new BufferedReader(new FileReader(path + ficheros[i]));
		        System.out.println("Contenido del fichero " + ficheros[i]);
		        while ((line = br.readLine()) != null) {
		            System.out.println(line);
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    File fichero = new File(path + ficheros[i]);
		    fichero.delete();   /// DESPUÉS DEL PROCESO SE ELIMINA EL ARCHIVO!!!
		}

	}
	
}
