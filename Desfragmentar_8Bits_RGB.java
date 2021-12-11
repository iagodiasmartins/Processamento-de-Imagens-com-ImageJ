import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Desfragmentar_8Bits_RGB implements PlugIn {
	public void run(String arg) {
		ImagePlus imagem = IJ.getImage();
		desfragmentar(imagem);
	}
	
		
	public void desfragmentar (ImagePlus imagem)  {		
		
		int pixel[]  = new int[3];
		
		ImagePlus Red = WindowManager.getImage("RED");
		ImageProcessor processadorRed = Red.getProcessor();
		ImagePlus Green = WindowManager.getImage("GREEN");
		ImageProcessor processadorGreen = Green.getProcessor();
		ImagePlus Blue = WindowManager.getImage("BLUE");
		ImageProcessor processadorBlue = Blue.getProcessor();
		
		ImagePlus RGB = IJ.createImage("RGB", "RGB", processadorRed.getWidth(), processadorRed.getHeight(), 1);
		ImageProcessor processador = RGB.getProcessor();
		
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				pixel[0] = processadorRed.getPixel(i, j);
				pixel[1] = processadorGreen.getPixel(i, j);
				pixel[2]= processadorBlue.getPixel(i,j);
				processador.putPixel(i, j, pixel);
				
			}
			
		   
		}
		
		RGB.show();				
			
		
	}			
		
}		
	

