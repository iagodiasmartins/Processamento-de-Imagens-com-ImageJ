import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import java.awt.AWTEvent;
import java.util.Arrays;


import ij.IJ;
import ij.ImagePlus;

public class Filtro_Nao_Linear implements PlugIn, DialogListener  {

	ImagePlus imagemOriginal = IJ.getImage();
	ImageProcessor processador = imagemOriginal.getProcessor();	
	
	ImagePlus imagemFiltroNaoLinear = IJ.createImage("Imagem Processada", "8-bit", processador.getWidth(), processador.getHeight(), 1);
	ImageProcessor processadorNaoLinear = imagemFiltroNaoLinear.getProcessor();
	
	public void run(String arg) {
		interfaceGrafica();
	}

	private void interfaceGrafica() {
		GenericDialog interfaceGraphic = new GenericDialog("Filtro Não Linear");
		interfaceGraphic.addDialogListener(this);
		String[] filtros = {"Filtro - Mediana"}; 
		interfaceGraphic.addRadioButtonGroup("Escolha um tipo de filtro", filtros, 1, 1, "Filtro - Mediana");
		interfaceGraphic.showDialog();
		
		if (interfaceGraphic.wasCanceled()) {
			IJ.showMessage("Saindo do plugin!");
		}
		else {
			if (interfaceGraphic.wasOKed()) {
				
				String option = interfaceGraphic.getNextRadioButton();
				if (option.equalsIgnoreCase(filtros[0])) {
					filtroMediana(processadorNaoLinear);
				} 				
		        IJ.showMessage("Plugin encerrado com sucesso!");
			}
		}
	}

	private void filtroMediana(ImageProcessor processadorNaoLinear) {
		int[] vetor = new int[9];
		int mediana;
		
		for (int x = 1; x< processador.getWidth()-1; x++) {
            for (int y = 1; y < processador.getHeight()-1; y++) {
            	
            	vetor[0] = processador.getPixel(x-1,y+1);
	         	vetor[1] = processador.getPixel(x+1,y+1);
	         	vetor[2] = processador.getPixel(x, y+1);
	         	vetor[3] = processador.getPixel(x-1, y-1);
	         	
	         	vetor[4] = processador.getPixel(x+1,y-1);
	         	
	         	vetor[5] = processador.getPixel(x-1, y);
	         	vetor[6] = processador.getPixel(x+1, y);
	         	vetor[7] = processador.getPixel(x, y-1);
	         	vetor[8] = processador.getPixel(x, y);
	         	
	         	System.out.println("Vetor nao ordenado:");
	         	System.out.println(vetor[4]);
	         	
	         	Arrays.sort(vetor);
	         	
	         	System.out.println("Vetor ordenado:");
	         	System.out.println(vetor[4]);	 
	         	
	         	//nunca vai cair no caso de fazer media dos valores centrais pois o vetor possui tamanho ímpar
	         	//vai pegar somente o valor central do vetor ordenado
	         	
	         	mediana = vetor[4];         	
	         	
	         	processadorNaoLinear.putPixel(x, y, mediana);
              
            }
        }
		imagemFiltroNaoLinear.show();
	}
	
	

	@Override
	public boolean dialogItemChanged(GenericDialog interfaceGraphic, AWTEvent e) {
		if (interfaceGraphic.wasCanceled()) {
			return false;
		} else {
			return true;
		}
    } 

}