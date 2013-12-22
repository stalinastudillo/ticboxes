package sys;

import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import data.*;

public class crear 
{

	static AnnotationConfiguration config;
	static SessionFactory fabrica; 	
	static Session sesion;

	/**
	 * @param args
	 */
	public crear()
	{
		
		// TODO Auto-generated method stub
		config = new AnnotationConfiguration();
		config.addAnnotatedClass(Banco.class);
		config.addAnnotatedClass(Ciudad.class);
//		config.addAnnotatedClass(Destinatario.class);
		config.addAnnotatedClass(Direccion.class);
		config.addAnnotatedClass(Estado.class);
		config.addAnnotatedClass(Factura.class);
		config.addAnnotatedClass(Facturacion.class);
		config.addAnnotatedClass(Grupo.class);
		config.addAnnotatedClass(Imagen.class);
		config.addAnnotatedClass(Municipio.class);
		config.addAnnotatedClass(Pago.class);
		config.addAnnotatedClass(Persona.class);
		config.addAnnotatedClass(Seccion.class);
		config.addAnnotatedClass(Usuario.class);


//		IGUAL QUE = config.configure();

		Properties propiedades = new Properties();
		propiedades.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");		
		propiedades.setProperty("hibernate.connection.url","jdbc:mysql://localhost/ticboxes"); // 100% Y LOCAL				
		propiedades.setProperty("hibernate.connection.username", "prueba"); //local
		propiedades.setProperty("hibernate.connection.password", "");  //local				
		propiedades.setProperty("hibernate.connection.pool_size","2");
		propiedades.setProperty("hibernate.current_session_context_class","thread");
        propiedades.setProperty("hibernate.cache.provider_class","org.hibernate.cache.NoCacheProvider");
		propiedades.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
		propiedades.setProperty("hibernate.show_sql","true");
		config.addProperties(propiedades);
//			FIN DE IGUAL QUE!!!			


		
		fabrica = config.buildSessionFactory();
		
		int n = JOptionPane.showConfirmDialog(new JFrame(),"¿Está seguro de Crear Toda la Base de Datos?\nSe borrarán todos los datos actuales!!!","Seleccione una Opción",JOptionPane.YES_NO_OPTION);
		if (n==JOptionPane.YES_OPTION)
		{
			int n1 = JOptionPane.showConfirmDialog(new JFrame(),"Se Perderá toda la Información?\nConfirma la Creación de la Base de Datos?","Seleccione una Opción",JOptionPane.YES_NO_OPTION);
			if (n1==JOptionPane.YES_OPTION){
			try 
			{
				new SchemaExport(config).create(true,true);
			}catch (HibernateException e2) 
			{
				JOptionPane.showMessageDialog(new JFrame(),"ERROR EN LA BASE DE DATOS!!!");
			}finally
			{
				JOptionPane.showMessageDialog(new JFrame(),"BASE DE DATOS CREADA CON ÉXITO!!!");
			}
		}
	}				
}

}

