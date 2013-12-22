package sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import data.Envio;

public class Enlace extends Thread {

	static AnnotationConfiguration config, confige;
	static SessionFactory fabrica, fabrique; 	
	static Session sesion, sesione; 

	Logger log = Logger.getLogger(this.getClass());

	List<Envio> envios = new ArrayList() , envios_e = new ArrayList();
	
	static Boolean xx = true;

	public void run()
	{
		do 
		{
//			if (!sesion.isConnected())
			try 
			{
				config = new AnnotationConfiguration();
				log.info("Cargando las Clases Persistentes//////////////////////////////////");
				config = new AnnotationConfiguration();
				config.addAnnotatedClass(Envio.class);
				
//				IGUAL QUE = config.configure();
				Properties propiedadesE = new Properties();
				propiedadesE.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");

				propiedadesE.setProperty("hibernate.connection.url","jdbc:mysql://localhost/ticboxes");
				propiedadesE.setProperty("hibernate.connection.username", "prueba");
				propiedadesE.setProperty("hibernate.connection.password", "");    // "12345" y ""

				propiedadesE.setProperty("hibernate.connection.pool_size","2");
				propiedadesE.setProperty("hibernate.current_session_context_class","thread");
		        propiedadesE.setProperty("hibernate.cache.provider_class","org.hibernate.cache.NoCacheProvider");
				propiedadesE.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
				propiedadesE.setProperty("hibernate.show_sql","true");
				config.addProperties(propiedadesE);
//	 			FIN DE IGUAL QUE!!!
				
				log.info("Entramos en configure()///////////////////////////////////////////");
				fabrica = config.buildSessionFactory();
				log.info("Entramos en config.buildSessionFactory()///////////////////////////////////////////");
//				new SchemaExport(config).create(true,true);
			}catch (HibernateException e) {
				log.info("Thread ---->  Exception al declarar propiedades y configurar conexión hibernate local "+e);
			}	

//			if (!sesione.isConnected())
			try 
			{
				confige = new AnnotationConfiguration();
				log.info("Cargando las Clases Persistentes//////////////////////////////////");
				confige = new AnnotationConfiguration();
				confige.addAnnotatedClass(Envio.class);
				
//				IGUAL QUE = config.configure();
				Properties propiedadese = new Properties();
				propiedadese.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");

				propiedadese.setProperty("hibernate.connection.url","jdbc:mysql://instance39305.db.xeround.com:7065/ticboxes_e");
				propiedadese.setProperty("hibernate.connection.username", "prueba");
				propiedadese.setProperty("hibernate.connection.password", "supertiton");    // "12345" y ""

				propiedadese.setProperty("hibernate.connection.pool_size","2");
				propiedadese.setProperty("hibernate.current_session_context_class","thread");
		        propiedadese.setProperty("hibernate.cache.provider_class","org.hibernate.cache.NoCacheProvider");
				propiedadese.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
				propiedadese.setProperty("hibernate.show_sql","true");
				confige.addProperties(propiedadese);
//	 			FIN DE IGUAL QUE!!!
				
				log.info("Entramos en configure()///////////////////////////////////////////");
				fabrique = confige.buildSessionFactory();
				log.info("Entramos en config.buildSessionFactory()///////////////////////////////////////////");
//				new SchemaExport(confige).create(true,true);
			}catch (HibernateException e) {
//				log.info("Entramos en Hibernate Exception!: "+e);
				log.info("Thread ---->  Exception al declarar propiedades y configurar conexión hibernate externa!!! "+e);				
			}	

			try
			{
				try {

			sesion = fabrica.getCurrentSession();
			sesion.beginTransaction();

			Query queryResult = null;

			queryResult = sesion.createQuery("from Envio where codOficina = :text and estado = 'P' ");
			queryResult.setInteger("text", principal.ofic.getCodigo());
            envios = (List<Envio>) queryResult.list();
            
			sesion.getTransaction().commit();

			System.out.println("Consiguió "+envios.size()+" Envios por procesar!!!!");

			if (envios.size()>0){

				sesione = fabrique.getCurrentSession();
				sesione.beginTransaction();
				
				sesion = fabrica.getCurrentSession();
				sesion.beginTransaction();
				
				for (Envio iter : envios)
				{
					sesione.save(iter);
					iter.setEstado('A');
					sesion.update(iter);
				}
				sesione.getTransaction().commit();
				sesion.getTransaction().commit();
				
				System.out.println("Fueron Subidos a xeround con éxito!!!!!");
				
			}else
				System.out.println("No hay nada por procesar!!!!");
			
/*			if (envios.size()>0){

				sesion = fabrica.getCurrentSession();
				sesion.beginTransaction();

				
				for (Envio inter : envios)
				{
					inter.setEstado('A');
					sesion.saveOrUpdate(inter);
				}
				
				sesion.getTransaction().commit();

				System.out.println("Fueron Actualizados en localhost con éxito!!!!!");
				
			}else
				System.out.println("No hay nada por procesar!!!!");
*/
			sesione = fabrique.getCurrentSession();
			sesione.beginTransaction();

			Query queryResult2 = null;

			switch ( principal.ofic.getCodigo() )
			{
			case 0 :
				queryResult2 = sesione.createQuery("from Envio where codOficina != 0 and Id_0 < 0");
//				queryResult2.setInteger("text", principal.ofic.getCodigo());
	            envios_e = (List<Envio>) queryResult2.list();
				break;
			case 1 :
				queryResult2 = sesione.createQuery("from Envio where codOficina != 1 and Id_1 < 0 ");
//				queryResult2.setInteger("text", principal.ofic.getCodigo());
	            envios_e = (List<Envio>) queryResult2.list();
				break;						
			case 2 :
				queryResult2 = sesione.createQuery("from Envio where codOficina != 2 and Id_2 < 0 ");
//				queryResult2.setInteger("text", principal.ofic.getCodigo());
	            envios_e = (List<Envio>) queryResult2.list();
				break;
			}
			           
			sesione.getTransaction().commit();

			System.out.println("Consiguió "+envios.size()+" Envios por procesar!!!! desde la web!!!");

			if (envios_e.size()>0)
			{
				sesion = fabrica.getCurrentSession();
				sesion.beginTransaction();	
				
				Envio nueEnvio = null;
				
				for (Envio iter : envios_e)
				{
					nueEnvio = new Envio();
					nueEnvio = iter.getNewEnvio(iter);
					nueEnvio.setEstado('A');
					switch ( principal.ofic.getCodigo() )
					{
					case 0 :
						nueEnvio.setId_0(0);
						break;
					case 1 :
						nueEnvio.setId_1(0);
						break;						
					case 2 :
						nueEnvio.setId_2(0);
						break;
					}
					sesion.save(nueEnvio);
				}
				sesion.getTransaction().commit();
			}else
				System.out.println("No hay nada por procesar envios_e!!!!");

			
			if (envios_e.size()>0)
			{
				sesione = fabrique.getCurrentSession();
				sesione.beginTransaction();

				for (Envio itt : envios_e)
				{
					itt.setEstado('A');
					switch ( principal.ofic.getCodigo() )
					{
					case 0 :
						itt.setId_0(0);
						break;
					case 1 :
						itt.setId_1(0);
						break;						
					case 2 :
						itt.setId_2(0);
						break;
					}
					sesione.update(itt);
				}
				sesione.getTransaction().commit();				
			}else
				System.out.println("No hay nada por procesar envios_e!!!!");
			
			
	/*					switch ( principal.ofic.getCodigo() )
						{
						case 0 :
							queryResult = sesion.createQuery("from Envio where codOficina = 0 and Id_0 > 0 and estado = 'P' ");
							queryResult.setInteger("text", principal.ofic.getCodigo());
				            envios = (List<Envio>) queryResult.list();
							sesion.getTransaction().commit();

							System.out.println("OJO!!! MiModelo()"+envios.size()+" Elementos");

							break;
						case 1 :
							queryResult = sesion.createQuery("from Envio where codOficina = 1 and Id_0 > 1 and estado = 'P' ");
							queryResult.setInteger("text", principal.ofic.getCodigo());
				            envios = (List<Envio>) queryResult.list();
							sesion.getTransaction().commit();

							System.out.println("OJO!!! MiModelo()"+envios.size()+" Elementos");

							break;						
						case 2 :
							queryResult = sesion.createQuery("from Envio where codOficina = 2 and Id_0 > 2 and estado = 'P' ");
							queryResult.setInteger("text", principal.ofic.getCodigo());
				            envios = (List<Envio>) queryResult.list();
							sesion.getTransaction().commit();

							System.out.println("OJO!!! MiModelo()"+envios.size()+" Elementos");

							break;
						}
						
						System.out.println("Envíos pendientes!!!"+envios.size());			
*/					
				} catch (HibernateException e2) {
//					JOptionPane.showMessageDialog(new JFrame(), "Errores de Base de Datos!!! e2","Database Error!",JOptionPane.ERROR_MESSAGE);
					System.out.println("No fue posible la conexion con la bd en la web!!!!");
				}

				
			}catch (HibernateException ee)
			{
//				JOptionPane.showMessageDialog(new JFrame(), "Errores de Base de Datos!!! ee","Database Error!",JOptionPane.ERROR_MESSAGE);
				System.out.println("No fue posible la conexion con la bd en la web!!!!");
			}

			fabrica.close();
			fabrique.close();
			
						
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		} while (xx); 
		
	}
	
}
