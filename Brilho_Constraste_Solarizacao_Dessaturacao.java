import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import java.awt.AWTEvent;
import ij.IJ;
import ij.ImagePlus;

//ALUNO: IAGO DIAS MARTINS RANGEL
public class Brilho_Constraste_Solarizacao_Dessaturacao implements PlugIn, DialogListener {
	
	private ImagePlus imagem;
	private ImageProcessor processador;
	private ImageProcessor imagemCopia;
	
	public void run(String arg) {
		
		apresentarInterfaceGrafica();
		
	}
	
	public void apresentarInterfaceGrafica() {
		GenericDialog interfaceGrafica = new GenericDialog("Operações Ponto a Ponto");
		interfaceGrafica.addDialogListener(this);
		
		this.imagem = IJ.getImage();
		this.processador = this.imagem.getProcessor();
		
		interfaceGrafica.addMessage("Plugin com algumas Operações Ponto a Ponto");
		interfaceGrafica.addSlider("Brilho", -255, 255, 0, 1);
		interfaceGrafica.addSlider("Contraste", -255, 255, 0, 1);
		interfaceGrafica.addSlider("Solarizacao Minima", 0, 255, 128, 1);
		interfaceGrafica.addSlider("Solarizacao Maxima", 0, 255, 128, 1);
		interfaceGrafica.addSlider("Dessaturacao", 0, 1, 1, 0.1);
		interfaceGrafica.showDialog();
		
		if (interfaceGrafica.wasCanceled()) {
			IJ.showMessage("PlugIn cancelado!");
			resetarImagem();
		}
		else {
			if (interfaceGrafica.wasOKed()) {
				
				int sliderBrilho = (int) interfaceGrafica.getNextNumber();
				int sliderContraste = (int) interfaceGrafica.getNextNumber();
				int sliderSolarizacaoMinima = (int) interfaceGrafica.getNextNumber();
				int sliderSolarizacaoMaxima = (int) interfaceGrafica.getNextNumber();
				float sliderDessaturacao = (float) interfaceGrafica.getNextNumber();
				IJ.log("_____________Ãšltimas respostas obtidas_______________");			
		        IJ.log("Resposta do slider Brilho:" + sliderBrilho);
		        IJ.log("Resposta do slider Contraste:" + sliderContraste);
		        IJ.log("Resposta do slider Solarizacao Minima:" + sliderSolarizacaoMinima);
		        IJ.log("Resposta do slider Solarizacao Maxima:" + sliderSolarizacaoMaxima);
		        IJ.log("Resposta do slider Dessaturacao:" + sliderDessaturacao);
		        
		        IJ.showMessage("Plugin encerrado com sucesso!");
			}
		}
	}
	
	private void resetarImagem() {
		this.imagem.setProcessor(this.processador);
		this.imagem.updateAndDraw();
		
	}
	
	
	@Override
	public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {
		
		if (interfaceGrafica.wasCanceled()) return false;
		
		int sliderBrilho = (int) interfaceGrafica.getNextNumber();
		int sliderContraste = (int) interfaceGrafica.getNextNumber();
		int sliderSolarizacaoMinima = (int) interfaceGrafica.getNextNumber();
		int sliderSolarizacaoMaxima = (int) interfaceGrafica.getNextNumber();
		float sliderDessaturacao = (float) interfaceGrafica.getNextNumber();
		
		alterarImagem(sliderBrilho, sliderContraste, sliderSolarizacaoMinima, sliderSolarizacaoMaxima, sliderDessaturacao);
	    
		IJ.log("Resposta do slider Brilho:" + sliderBrilho);
		IJ.log("Resposta do slider Contraste:" + sliderContraste);
		IJ.log("Resposta do slider Solarizacao Minima" + sliderSolarizacaoMinima);
		IJ.log("Resposta do slider Solarizacao Maxima" + sliderSolarizacaoMaxima);
		IJ.log("Resposta do slider Dessaturacao:" + sliderDessaturacao);
		
        return true;
		
	}
	
		
	private void alterarImagem(int sliderBrilho, int sliderContraste, int sliderSolarizacaoMinima, int sliderSolarizacaoMaxima, float sliderDessaturacao) {
		
		this.imagem.setProcessor(this.processador);
		int pixel[]  = new int[3];
		imagemCopia = this.processador.duplicate();
		int contraste = sliderContraste;
		float fator = (259 * ((float) contraste + 255))/(255*(259-contraste));
		
		
		//__________Calculo para operação de BRILHO_______________
		
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				pixel =   imagemCopia.getPixel(i, j, pixel );
				for(int rgb = 0 ; rgb < 3 ; rgb++ ){
					 pixel[rgb] += sliderBrilho;  
					 	if(pixel[rgb] < 0){
					 		pixel[rgb] = 0;
					 	}else 
					 		if(pixel[rgb] > 255){
							 pixel[rgb] = 255;
						 }					
				 }	 
				imagemCopia.putPixel(i, j, pixel);
			}
		}
		
		//__________Calculo para operação de Contraste_______________
		
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				pixel =   imagemCopia.getPixel(i, j, pixel );
				for(int rgb = 0 ; rgb < 3 ; rgb++ ){
					pixel[rgb] = (int) ((fator * (pixel[rgb] - 128)) + 128);  
					 	if(pixel[rgb] < 0){
					 		pixel[rgb] = 0;
					 	}else if(pixel[rgb] > 255){
							 pixel[rgb] = 255;
						 }					
				 }	 
				 imagemCopia.putPixel(i, j, pixel);
			}
		}
		
		//__________Calculo para operação de Solarizacao_______________
		
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				pixel =   imagemCopia.getPixel(i, j, pixel );
				for(int rgb = 0 ; rgb < 3 ; rgb++ ){
					if((pixel[rgb] < sliderSolarizacaoMaxima) && (pixel[rgb] > sliderSolarizacaoMinima)	) {
						pixel[rgb]= 255 - pixel[rgb];
					}
				}	 
			    imagemCopia.putPixel(i, j, pixel);
			}
		}
		

		//__________Calculo para operação de Dessaturacao_______________
		
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				
				pixel =   imagemCopia.getPixel(i, j, pixel );
				float estrategiaMediaAritmetica = (pixel[0] + pixel[1] + pixel[2]) / 3;
				
				for(int rgb = 0 ; rgb < pixel.length ; rgb++ ){					
					pixel[rgb] = (int) estrategiaMediaAritmetica + (int) (sliderDessaturacao * (pixel[rgb] - (int)estrategiaMediaAritmetica ) ) ;
				 }	 
				 imagemCopia.putPixel(i, j, pixel);
			}
		}
		
				
		
		
		this.imagem.setProcessor(imagemCopia);
		this.imagem.updateAndDraw();
	}

	
	
	
}