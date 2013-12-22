package sys;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.media.*;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;


public class CamaraWeb{
	public Component componente=null;
	public Player p=null;
	public Component video;
	public MediaLocator ml;
	CamaraWeb(){
		Manager.setHint( Manager.LIGHTWEIGHT_RENDERER, true );
		try{
			ml = new MediaLocator("vfw://0");
			p = Manager.createRealizedPlayer(ml);
			video = p.getVisualComponent();
			p.start();
	    	if ( video != null ){
	               componente=video;
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Error de Comunicacion con la WebCam " + e);
		}
	}
	
	public Component VerCamara(int x,int y,int alto,int ancho){
		this.componente.setBounds(x,y,alto,ancho);
		return componente;
	}
	
	public File Fotografiar(String Carpeta,Boolean NombreAutomatico,String NombreValoNull){
		javax.media.Buffer buf=null;
		Image img=null;
		File imagenArch;
		String nombre=null;
		String formato = null;
		Date HoraDate;
		String HoraString=null;
		File CarpetaFotografias;
		
		
		CarpetaFotografias=new File(Carpeta);
		if(CarpetaFotografias.exists()==false){
			CarpetaFotografias.mkdir();
		}
		FrameGrabbingControl fgc = (FrameGrabbingControl)
    	p.getControl("javax.media.control.FrameGrabbingControl");
    	buf = fgc.grabFrame();
    	BufferToImage btoi = new BufferToImage((VideoFormat)buf.getFormat());
    	img = btoi.createImage(buf);
    	if (img != null){
    		if(NombreAutomatico==true){
    			HoraDate=new Date();
    			HoraString=Integer.toString(HoraDate.getDate())+"-"+Integer.toString(HoraDate.getMonth())+"-"+Integer.toString(HoraDate.getYear())+
    				"-"+Integer.toString(HoraDate.getHours())+"-"+Integer.toString(HoraDate.getMinutes())+"-"+HoraDate.getSeconds();
    			nombre=Carpeta + "\\"+HoraString+".jpg";
    		}else{
    			nombre=Carpeta + "\\"+NombreValoNull+".jpg";
    		}
    			
     			imagenArch = new File(nombre);
         		formato = "JPEG";
				try{
					ImageIO.write((RenderedImage) img,formato,imagenArch);
					return imagenArch;
         		}catch (IOException ioe){
         			return null;
         		}
         }
         return null;
	}
}