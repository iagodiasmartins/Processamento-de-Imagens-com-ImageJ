import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class Fragmentar_RGB_8Bits implements PlugIn {
	public void run(String arg) {
		ImagePlus imagem = IJ.getImage();
		fragmentar(imagem);
	}
	
	
	
	public void fragmentar (ImagePlus imagem) {
		ImageProcessor processador = imagem.getProcessor();
		int pixel[]  = new int[3];
		
		ImagePlus Red = IJ.createImage("RED", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor processadorRed = Red.getProcessor();
		ImagePlus Green = IJ.createImage("GREEN", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor processadorGreen = Green.getProcessor();
		ImagePlus Blue = IJ.createImage("BLUE", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor processadorBlue = Blue.getProcessor();
		
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				pixel =   processador.getPixel(i, j, pixel );
				processadorRed.putPixel(i, j, pixel[0]);
				processadorGreen.putPixel(i, j, pixel[1]);
				processadorBlue.putPixel(i, j, pixel[2]);
			}
		}
		
		Red.show();
		Green.show();
		Blue.show();		
		
	}		
	
}
