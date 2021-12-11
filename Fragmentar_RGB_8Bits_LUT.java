import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.process.LUT;

public class Fragmentar_RGB_8Bits_LUT implements PlugIn {
	
	public void run(String arg) {
		ImagePlus imagem = IJ.getImage();
		fragmentar(imagem);		
	}

	public byte[] LUTZerado() {
		byte[] aux = new byte[256];
		for (int i = 0; i < 256; i++) {
			aux[i] = 0;
		}
		return aux;
	}

	public byte[] LUTComValores() {
		byte[] aux = new byte[256];
		for (int i = 0; i < 256; i++) {
			aux[i] = (byte) i;
		}
		return aux;
	}
	
	
	public void fragmentar (ImagePlus imagem) {
				
		
		ImageProcessor processador = imagem.getProcessor();
		int pixel[]  = new int[3];
		byte[] LUTValores = LUTComValores();
		byte[] LUTZero = LUTZerado();
		
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
		
		
		LUT LUTRed = new LUT(LUTValores, LUTZero, LUTZero);
		LUT LUTGreen = new LUT(LUTZero, LUTValores, LUTZero);
		LUT LUTBlue = new LUT(LUTZero, LUTZero, LUTValores);
		
		Red.setLut(LUTRed);
		Green.setLut(LUTGreen);
		Blue.setLut(LUTBlue);
		
		Red.show();
		Green.show();
		Blue.show();
		
			
		
	}	
	
	
	
	
}
