import ij.plugin.PlugIn;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import java.awt.AWTEvent;
import ij.IJ;
import ij.ImagePlus;

public class Converter_RGB_Cinza_Interface implements PlugIn, DialogListener {
	
	double wRed = 0;
	double wGreen = 0;
	double wBlue = 0;
	
	public void run(String arg) {
		apresentarInterfaceGrafica();
	}
	
	public void apresentarInterfaceGrafica() {
		GenericDialog interfaceGrafica = new GenericDialog("RGB para Cinza através de Interface");
		interfaceGrafica.addDialogListener(this);
		
		String[] estrategia = {"MediaAritmetica", "LuminanceAnalogico","LuminanceDigital"}; 
		interfaceGrafica.addMessage("Plugin para converter imagem RGB para Cinza através de Interface");
		interfaceGrafica.addRadioButtonGroup("Botões para escolher uma estratégia", estrategia, 1, 3, "Media Aritmetica");
		interfaceGrafica.addCheckbox("Gerar Nova Imagem?", true);
		interfaceGrafica.showDialog();
		
		if (interfaceGrafica.wasCanceled()) {
			IJ.showMessage("PlugIn cancelado!");
		}
		else {
			if (interfaceGrafica.wasOKed()) {
				
				Boolean novaImagem = interfaceGrafica.getNextBoolean();
				String radioSelecionado = interfaceGrafica.getNextRadioButton();
				
				IJ.log("_____________ultimas respostas obtidas_______________");				
				IJ.log("Resposta do radio button:" + radioSelecionado);				
		        IJ.log("Resposta do checkbox:" + novaImagem);	        
		        IJ.showMessage("Plugin encerrado com sucesso!");
		        
		        if(radioSelecionado.equals("MediaAritmetica")) {
		        	IJ.log("Media Aritmetica selecionada.");
		        	this.wRed = 1;
		        	this.wGreen = 1;
		        	this.wBlue = 1;
		        }		        

		        if(radioSelecionado.equals("LuminanceAnalogico")) {
		        	IJ.log("Luminance Analogico selecionado.");		        	
		        	this.wRed = 0.299;
		        	this.wGreen = 0.587;
		        	this.wBlue = 0.114;
		        }
		        
		        if(radioSelecionado.equals("LuminanceDigital")) {
		        	IJ.log("Luminance Digital selecionado.");
		        	this.wRed = 0.2125;
		        	this.wGreen = 0.7154;
		        	this.wBlue = 0.072;
		        }
		        
		        if(novaImagem) {
		        	gerarNovaImagem();
		        }else {
		        	TransformarImagemEmCinza();
		        } 
			}
		}
	}
	
	
	
	private void gerarNovaImagem() {
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador = imagem.getProcessor();
		int pixel[]  = new int[3];
		
		ImagePlus imagemCinza = IJ.createImage("Cinza", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor processadorCinza = imagemCinza.getProcessor();
		
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				pixel =   processador.getPixel(i, j, pixel );
				int result = (int) (((pixel[0] * this.wRed) + (pixel[1] * this.wGreen) + (pixel[2] * this.wBlue))/3);
				processadorCinza.putPixel(i, j, result);	
			}
		}		
		imagemCinza.show();		
	}
	
	
	
	private void TransformarImagemEmCinza() {
		ImagePlus imagem = IJ.getImage();
		ImageProcessor processador = imagem.getProcessor();
		ImageProcessor processadorCinza = new ByteProcessor(processador.getWidth(),processador.getHeight());
		int pixel[]  = new int[3];
		for(int i = 0; i < processador.getWidth(); i++){
			for(int j= 0; j < processador.getHeight(); j++){
				pixel =   processador.getPixel(i, j, pixel );
				int result = (int) (((pixel[0] * this.wRed) + (pixel[1] * this.wGreen) + (pixel[2] * this.wBlue))/3);
				processadorCinza.putPixel(i, j, result);	
			}
		}
		imagem.setProcessor(processadorCinza);
		imagem.updateAndDraw();
	}	
	
	
	
	@Override
	public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {
		if (interfaceGrafica.wasCanceled()) return false;
		
		IJ.log("Resposta do radio button:" + interfaceGrafica.getNextRadioButton());		
        IJ.log("Resposta do checkbox:" + interfaceGrafica.getNextBoolean());       
        IJ.log("\n");
        return true;
    }
}