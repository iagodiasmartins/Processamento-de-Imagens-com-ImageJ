import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;


public class RGB_HSV implements PlugIn {

	public void run(String arg) {
		
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador = imagem.getProcessor();
		
		ImagePlus ImagemHue = IJ.createImage("HUE (Matiz)", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor processadorHue = ImagemHue.getProcessor();
		
		ImagePlus ImagemSaturation = IJ.createImage("Saturation (Saturação)", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor processadorSaturation = ImagemSaturation.getProcessor();
		
		ImagePlus ImagemValue = IJ.createImage("Value (Valor ou Brilho)", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor processadorValue = ImagemValue.getProcessor();
		
//___Percorre os processadores das 3 imagens chamando as funções que convertem cada pixel e colocando os novos valores após a conversão
		
		for(int x = 0 ; x < processador.getWidth() ; x++){
			for(int y = 0 ; y < processador.getHeight() ; y++){
				processadorHue.putPixel(x, y, hue(processador.getPixel(x, y, null))); 
				processadorSaturation.putPixel(x, y, saturation(processador.getPixel(x, y, null)));
				processadorValue.putPixel(x, y, value(processador.getPixel(x, y, null)));
			}
		}
		
		ImagemHue.show();
		ImagemSaturation.show();
		ImagemValue.show();
		
	}
	

	//_____Value_____ retorna o valor Maximo RGB de cada pixel
	
	public int value(int[] pixel) {		
		
		int maior = 0;
		for(int i = 0 ; i < 3 ; i++) {
			if( pixel[i] > maior) {
				maior = pixel[i];
			}
		}
		return maior;
	}
	
	
	//_____HUE_____ 	
	
	public int hue(int[] pixel) {
		
		float result = 0;
		float auxPixel[] = new float[3];
		
		for(int i = 0 ; i < 3 ; i++) {
			auxPixel[i] = normalizacao(pixel[i]);
		}
		
		float maior = auxPixel[0], menor = auxPixel[0];
		
		for(int i = 0 ; i < 3 ; i++) {
			if( maior < auxPixel[i]) {
				maior = auxPixel[i];
			}
			if(menor > auxPixel[i]) {
				menor = auxPixel[i];
			}
		}
				
		
		if( maior == auxPixel[0] && auxPixel[1] >= auxPixel[2]) {
			result = 60 * ((auxPixel[1] - auxPixel[2])/(maior - menor)) + 0;
	
		} else if (maior == auxPixel[0] && auxPixel[1] < auxPixel[2]) {
			result = 60 * ((auxPixel[1] - auxPixel[2])/(maior - menor)) + 360;
		
		} else if (maior == auxPixel[1]) {
			result = 60 * ((auxPixel[2] - auxPixel[0])/(maior - menor)) + 120;
			
		} else if (maior == auxPixel[2]) {
			result = 60 * ((auxPixel[0] - auxPixel[1])/(maior - menor)) + 240;

		}
		
		return (int) result;
	}
	
	
	public int saturation(int[] pixel) {
		float maior = 0, menor = 1;
		float auxPixel[] = new float[3];

		
		for(int i = 0 ; i < 3 ; i++) {
			auxPixel[i] = normalizacao(pixel[i]);
		}
		
		for(int i = 0 ; i < 3 ; i++) {  //Para calcular a saturação, eu preciso do maior e menor valor dos pixel 
			if( auxPixel[i] > maior) {		
				maior = auxPixel[i];
			}
			if(auxPixel[i] < menor) {
				menor = auxPixel[i];
			}
		}
		               //e também a diferença entre eles
		float result =  ((maior - menor)/maior); 
		result = normalizacao2(result);
		return (int) result;
		
		
	}
	
	

//______________Normalizar_____________________

	public float normalizacao(float result) {  
		float valorNormalizado = result/255 ;
		return valorNormalizado;	
	}
	
	public float normalizacao2(float result) {
		float valorNormalizado = result*255 ;
		return valorNormalizado;	
	}
	
}