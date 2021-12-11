import java.awt.AWTEvent;
import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;



// Expansao ou normalizaçao de histograma


public class Expansao_Equalizacao_Histograma implements PlugIn, DialogListener {
	
	private ImagePlus imagem;
	private ImageProcessor processador;
	private ImagePlus imagemCinza;
	private ImageProcessor processadorCinza;	
	private ImageProcessor CopiaProcessadorCinza;
	
	//Valores possiveis para imagem em escala de cinza
	int aMin = 0;
	int aMax = 255;
	
	//Valores da imagem
	int aLow = 255;
	int aHigh = 0;
	

	
	@Override
	public void run(String arg) {
		
		MostrarInterfaceGrafica();
		
	}
	
	
	public void MostrarInterfaceGrafica() {
		
		GenericDialog interfaceGrafica = new GenericDialog("Expansão e Equalização de Histograma");

		this.imagem = IJ.getImage();
		this.processador = this.imagem.getProcessor();
		
		CriarProcessadorCinza();

		interfaceGrafica.addDialogListener(this);

		interfaceGrafica.addMessage("Expansão e Equalização de Histograma");		
		interfaceGrafica.addCheckbox("Expansão", true);
		interfaceGrafica.addCheckbox("Equalização", false);
		interfaceGrafica.showDialog();

		if (interfaceGrafica.wasCanceled()) {
			IJ.showMessage("PlugIn cancelado!");
			
			
		} else {
			if (interfaceGrafica.wasOKed()) {		
				Boolean ExpSelecionado = interfaceGrafica.getNextBoolean();
				Boolean EquaSelecionado = interfaceGrafica.getNextBoolean();
				this.imagemCinza = WindowManager.getImage("Cinza");
				//IJ.run("Histogram");
				
				if (ExpSelecionado) {
					AplicarExpansao();
					} else if (EquaSelecionado) {
						//AplicarEqualização();
					} else {
					  
					}
				
			}			
		}
		
	}
	
	

	private void CriarProcessadorCinza() {
		int mediaPixels;
		int pixel[] = new int[3];
		imagemCinza = IJ.createImage("Cinza", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		this.processadorCinza = imagemCinza.getProcessor();
		for (int i = 0; i < processador.getWidth(); i++) {
			for (int j = 0; j < processador.getHeight(); j++) {
				pixel = processador.getPixel(i, j, pixel);
				mediaPixels = (pixel[0] + pixel[1] + pixel[2]) / 3;
				processadorCinza.putPixel(i, j, mediaPixels);
			}
		}
		
		imagemCinza.show();
	}
	
// ____________________________________________________________________	
	
	private void AplicarExpansao() {
		
		aLow_aHigh_da_Imagem();		
		
		this.imagemCinza.setProcessor(processadorCinza);
		CopiaProcessadorCinza = this.processadorCinza.duplicate();
		
		int pixel[] = { 0 };
		int a = 0;


		for (int i = 0; i < processador.getWidth(); i++) {
			for (int j = 0; j < processador.getHeight(); j++) {
				pixel = processadorCinza.getPixel(i, j, pixel);
				a = aMin + (pixel[0] - aLow) * (aMax - aMin) / (aHigh - aLow);				
				CopiaProcessadorCinza.putPixel(i, j, a);
			}
			System.out.println(a);
		}
		
		this.imagemCinza.setProcessor(CopiaProcessadorCinza);
		this.imagemCinza.updateAndDraw();
		IJ.run("Histogram");
	}	

// ____________________________________________________________________

	
	private void aLow_aHigh_da_Imagem() {
		int pixel[]= {0};
		for (int i = 0; i < processador.getWidth(); i++) {
			for (int j = 0; j < processador.getHeight(); j++) {
				pixel = processadorCinza.getPixel(i, j, pixel);

				if (pixel[0] < aLow) {
					aLow = pixel[0];
				}

				if (pixel[0] > aHigh) {
					aHigh = pixel[0];
				}
			}
		}
	}
	
// ____________________________________________________________________
		
	
	@Override
	public boolean dialogItemChanged(GenericDialog interfaceGrafica, AWTEvent e) {
		if (interfaceGrafica.wasCanceled()) return false;
		
		IJ.log("Resposta do checkbox:" + interfaceGrafica.getNextBoolean()); 
		IJ.log("\n");		

		return true;
	}

	
}
