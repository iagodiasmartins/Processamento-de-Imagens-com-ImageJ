import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import java.awt.AWTEvent;
import ij.IJ;
import ij.ImagePlus;


public class Filtros_Lineares  implements PlugIn, DialogListener {
	
	ImagePlus imagemOriginal = IJ.getImage();
	ImageProcessor processador = imagemOriginal.getProcessor();	
	
	ImagePlus imagemFiltroLinear = IJ.createImage("Imagem Processada", "8-bit", processador.getWidth(), processador.getHeight(), 1);
	ImageProcessor processadorLinear = imagemFiltroLinear.getProcessor();
	
	public void run(String arg) {
		interfaceGrafica();
	}

	private void interfaceGrafica() {
		GenericDialog interfaceGraphic = new GenericDialog("Filtros Lineares");
		interfaceGraphic.addDialogListener(this);
		String[] filtros = {"Filtro - Passa Baixa Média", "Filtro Passa Alta", "Filtro de Borda Norte"}; 
		interfaceGraphic.addRadioButtonGroup("Escolha um tipo de filtro", filtros, 1, 3, "Filtro Passa Alta");
		interfaceGraphic.showDialog();
		
		if (interfaceGraphic.wasCanceled()) {
			IJ.showMessage("Saindo do plugin!");
		}
		else {
			if (interfaceGraphic.wasOKed()) {
				
				String option = interfaceGraphic.getNextRadioButton();
				if (option.equalsIgnoreCase(filtros[0])) {
					filtroPassaBaixaMedia(processadorLinear);
				} else if (option.equalsIgnoreCase(filtros[1])) {
					filtroPassaAlta(processadorLinear);
				} else {
					filtroBordaNorte(processadorLinear);
				}
				
		        IJ.showMessage("Plugin encerrado com sucesso!");
			}
		}
	}

	private void filtroPassaBaixaMedia(ImageProcessor processorLinear) {
		int pixelResultante = 0;
		int[] vetor = new int[9];
		
		
		for (int x = 0; x < processador.getWidth(); x++) {
            for (int y = 0; y < processador.getHeight(); y++) {
            	pixelResultante = 0;
            	
            	//Pegando todos os 9 pixels (8 vizinhos e o a trabalhar)
            	
            	vetor[0] = processador.getPixel(x-1,y+1);
	         	vetor[1] = processador.getPixel(x+1,y+1);
	         	vetor[2] = processador.getPixel(x, y+1);
	         	vetor[3] = processador.getPixel(x-1, y-1);
	         	vetor[4] = processador.getPixel(x+1,y-1);
	         	vetor[5] = processador.getPixel(x-1, y);
	         	vetor[6] = processador.getPixel(x+1, y);
	         	vetor[7] = processador.getPixel(x, y-1);
	         	vetor[8] = processador.getPixel(x, y);
	         	
	         	//Utilizado estratégia de media aritmetica 
	         	
	         	for (int i = 0; i < 9; i++) {
	         		
	         			pixelResultante = pixelResultante + (vetor[i] / 9);
	         		
	         	}
            	
         	   	processadorLinear.putPixel(x, y, pixelResultante);
              
            }
        }
        imagemFiltroLinear.show();
	}
	
	private void filtroPassaAlta(ImageProcessor processor) {
		int pixelResultante = 0;
		int[] vetor = new int[9];
		
		
		for (int x = 0; x < processador.getWidth(); x++) {
            for (int y = 0; y < processador.getHeight(); y++) {
            	pixelResultante = 0;
            	
            	//Pegando todos os 9 pixels (8 vizinhos e o a trabalhar)
            	
            	vetor[0] = processador.getPixel(x-1,y+1);
	         	vetor[1] = processador.getPixel(x+1,y+1);
	         	vetor[2] = processador.getPixel(x, y+1);
	         	vetor[3] = processador.getPixel(x-1, y-1);
	         	vetor[4] = processador.getPixel(x+1,y-1);
	         	vetor[5] = processador.getPixel(x-1, y);
	         	vetor[6] = processador.getPixel(x+1, y);
	         	vetor[7] = processador.getPixel(x, y-1);
	         	vetor[8] = processador.getPixel(x, y);
	         	
	         	//Utilizado estratégia do slide 13/20 de filtros 
	         	for (int i = 0; i < 9; i++) {
	         		if (i >= 0 && i < 8) {
	         			pixelResultante = pixelResultante + (vetor[i] * -1);
	         		} 
	         		 else {
	         			pixelResultante = pixelResultante + (vetor[i] * 8);
	         		}
	         	}
	                     	
         	   	processadorLinear.putPixel(x, y, pixelResultante);
              
            }
        }
		
        imagemFiltroLinear.show();
	}
	
	private void filtroBordaNorte(ImageProcessor processor) {
		int pixelResultante;
		int[] vetor = new int[9];
      
        for (int x = 0; x< processador.getWidth(); x++) {
            for (int y = 0; y < processador.getHeight(); y++) {
            	pixelResultante = 0;
            	
            	//Pixels superiores ao central
	         	vetor[0] = processador.getPixel(x-1,y+1);    
	         	vetor[1] = processador.getPixel(x, y+1);
	         	vetor[2] = processador.getPixel(x+1,y+1);
	         	//Pixels laterais ao central
	         	vetor[3] = processador.getPixel(x-1, y);
	         	vetor[4] = processador.getPixel(x+1,y);
	         	//Pixels inferiores ao central
	         	vetor[5] = processador.getPixel(x-1, y-1);
	         	vetor[6] = processador.getPixel(x, y-1);
	         	vetor[7] = processador.getPixel(x+1, y-1);
	         	//Pixel central
	         	vetor[8] = processador.getPixel(x, y); 
         	   
	         	//Utilizado o filtro Borda Norte
	         	for (int i = 0; i < 9; i++) {
	         		if (i >=0 && i<5) {
	         			pixelResultante = pixelResultante + (vetor[i] * 1);  
	         		} else if (i >= 5 && i < 8) {
	         			pixelResultante = pixelResultante + (vetor[i] * -1);
	         		} else {
	         			pixelResultante = pixelResultante + (vetor[i] * -2);
	         		}
	         	}
	         	
	         	processadorLinear.putPixel(x, y, pixelResultante);
            }
        }
        
        imagemFiltroLinear.show();
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



