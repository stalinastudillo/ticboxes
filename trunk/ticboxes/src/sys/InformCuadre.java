package sys;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.Factura;
//import data.Resultado;

public class InformCuadre extends JInternalFrame implements ActionListener {
	JLabel lbfecha1 = new JLabel("Desde:");
	JLabel lbfecha2 = new JLabel("Hasta:");
//	JTextField tffecha = new JTextField(10);
	JButton btimprime = new JButton("Imprimir");
	JButton btpantall = new JButton("Pantalla");
	Date fecha;
	SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	MaskFormatter mffecha;
	JFormattedTextField tffecha,tffecha2;

	List<Factura> facturas = new ArrayList();
	
	public InformCuadre()
	{
		super("Cuadre Diario",true,true,true,true);
		setSize(150,150);
		setLayout(new FlowLayout());
		try {
		mffecha = new MaskFormatter("**/**/****");
		tffecha = new JFormattedTextField(mffecha);
		tffecha.setValue("  /  /    ");
		tffecha.setColumns(6);
		tffecha2 = new JFormattedTextField(mffecha);
		tffecha2.setValue("  /  /    ");
		tffecha2.setColumns(6);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		tffecha.setValue(formatoFecha.format(new Date().getTime()));
		tffecha2.setValue(formatoFecha.format(new Date().getTime()));
		
		add(lbfecha1);
		add(tffecha);
		add(lbfecha2);
		add(tffecha2);
		add(btimprime);
//		add(btpantall);
		
		System.out.println("En Informe Cuadre!!!");
		
		btimprime.addActionListener(this);
		btpantall.addActionListener(this);
		
	
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

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (ae.getSource()==btimprime)
		{
			System.out.println("Seleccionamos el btimprime!!!");
			facturas = new ArrayList();
			try {
				Session sesion = principal.fabrica.getCurrentSession();		
				sesion.beginTransaction();
				String tquery = "from Factura where Fecha between :A and :B";  //  and blockResult = false
				Query quer = sesion.createQuery(tquery);
				try {
					quer.setDate("A", formatoFecha.parse(tffecha.getText()));
					Calendar fech = Calendar.getInstance();
					fech.setTime( formatoFecha.parse(tffecha2.getText()));
					fech.add(Calendar.DATE, 1);
					quer.setDate("B", fech.getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				facturas = quer.list();				
				try 
				{
					Map param = new HashMap();
					param.put("Titulo","Cuadre Diario");
					JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(facturas);
					JasperReport rfac = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResource("sys/cuadrediario.jasper"));
					JasperPrint pfac = JasperFillManager.fillReport(rfac, param, data);
					try {
						JasperExportManager.exportReportToPdfFile(pfac,"c:/ticboxes/cuadrediario.pdf");
					}catch (JRException exp)
					{
						JOptionPane.showMessageDialog(new JFrame(), "Está abierto archivo de resultado\nDebe cerrarlo para poderlo generar nuevamente!\n","  "+exp,JOptionPane.ERROR_MESSAGE);
						exp.printStackTrace();
					}
					try {
						Runtime.getRuntime().exec("cmd /c start c:/ticboxes/CuadreDiario.pdf");
//						File file = new File("c:/ticlab2/resultado.pdf");
//						file.deleteOnExit();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}									
				} catch (JRException e1) 
				{
					e1.printStackTrace();
				}
			} catch (HibernateException e2) {
				JOptionPane.showMessageDialog(new JFrame(), "AQUI!!!Errores de Base de Datos!!!\n","Database Error!",JOptionPane.ERROR_MESSAGE);
				e2.printStackTrace();
			}						
		}
		if (ae.getSource()==btpantall)
		{
			
			
		}
	}

}
