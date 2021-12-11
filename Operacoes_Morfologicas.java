import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import java.awt.AWTEvent;
import ij.IJ;
import ij.ImagePlus;


public class Operacoes_Morfologicas  implements PlugIn, DialogListener {
	
	
	
	
	public void run(String arg) {
		interfaceGrafica();
	}

	private void interfaceGrafica() {
		
		ImagePlus imagemOriginal = IJ.getImage();
		ImageProcessor processador = imagemOriginal.getProcessor();	
		
		ImagePlus imagemOperacaoMorfologica = IJ.createImage("Imagem Processada", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor processadorMorfologico = imagemOperacaoMorfologica.getProcessor();	
		
		GenericDialog interfaceGraphic = new GenericDialog("Operações Morfológicas");
		interfaceGraphic.addDialogListener(this);
		String[] operacoes = {"Dilatação", "Erosão", "Fechamento", "Abertura", "Borda"}; 
		interfaceGraphic.addRadioButtonGroup("Escolha uma operação", operacoes, 1, 5, "Dilatação");
		interfaceGraphic.showDialog();
		
		if (interfaceGraphic.wasCanceled()) {
			IJ.showMessage("Saindo do plugin!");
		}
		else {
			if (interfaceGraphic.wasOKed()) {
				
				String option = interfaceGraphic.getNextRadioButton();
				if (option.equalsIgnoreCase(operacoes[0])) {
					operacaoDilatacao(processadorMorfologico);
				} else if (option.equalsIgnoreCase(operacoes[1])) {
					operacaoErosao(processadorMorfologico);
				} else if (option.equalsIgnoreCase(operacoes[2])) {
					operacaoFechamento(processadorMorfologico);
				}else if (option.equalsIgnoreCase(operacoes[3])) {
					operacaoAbertura(processadorMorfologico);
				}else {
					operacaoBorda(processadorMorfologico);
				}
				
		        IJ.showMessage("Plugin encerrado com sucesso!");
			}
		}
	}

	
	//_____________________________________________________________________
	
	private void operacaoDilatacao(ImageProcessor process){
		
		ImagePlus imagemOriginal = IJ.getImage();
		ImageProcessor processador = imagemOriginal.getProcessor();	
		
		ImagePlus imagemOperacaoMorfologica = IJ.createImage("Imagem Processada", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor processadorMorfologico = imagemOperacaoMorfologica.getProcessor();	
		
		int valorCentral;
      
        for (int x = 0; x< processador.getWidth(); x++) {
            for (int y = 0; y < processador.getHeight(); y++) {
            	
            	//Pixel central
            	valorCentral = processador.getPixel(x, y); 
            	if(valorCentral == 0) {
            		processadorMorfologico.putPixel(x,y,0); 
            		//Pixels superiores ao central
            		processadorMorfologico.putPixel(x-1,y+1,0);    
            		processadorMorfologico.putPixel(x, y+1, 0);
            		processadorMorfologico.putPixel(x+1,y+1, 0);
    	         	//Pixels laterais ao central
            		processadorMorfologico.putPixel(x-1, y, 0);
            		processadorMorfologico.putPixel(x+1,y, 0);
    	         	//Pixels inferiores ao central
            		processadorMorfologico.putPixel(x-1, y-1, 0);
            		processadorMorfologico.putPixel(x, y-1, 0);
            		processadorMorfologico.putPixel(x+1, y-1, 0);
            	}     	
	         	
            }
        }
        
       
        imagemOperacaoMorfologica.show();
       
		
	}
	
//_____________________________________________________________________
	
	private void operacaoErosao(ImageProcessor process){
		
		ImagePlus imagemOriginal = IJ.getImage();
		ImageProcessor processador = imagemOriginal.getProcessor();	
		
		ImagePlus imagemOperacaoMorfologica = IJ.createImage("Imagem Erosão", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor processadorMorfologico = imagemOperacaoMorfologica.getProcessor();	
		
		int[] vetor = new int[9];
      
        for (int x = 0; x< processador.getWidth(); x++) {
            for (int y = 0; y < processador.getHeight(); y++) {
            	
            	int contador = 0;
            	
            	//Pixels superiores ao central
	         	vetor[0] = processador.getPixel(x-1,y+1);
	         	//System.out.println("Valor:");
	         	//System.out.println(vetor[0]);	 
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
         	   
	         	
	         	for (int i = 0; i < 9; i++) {	         	
	         		if (vetor[i]==0) {
	         			contador++; 
	         		} if(contador==9) { //Checando se toda a região de interesse está contida na imagem
	         			processadorMorfologico.putPixel(x,y,0);
	         		}
	         	}	         	
            }
        }
        
        
        imagemOperacaoMorfologica.show();		
	}
	
//_____________________________________________________________________
	
	private void operacaoFechamento(ImageProcessor process){
		
		ImagePlus imagemOriginal = IJ.getImage();
		ImageProcessor processador = imagemOriginal.getProcessor();	
		
		ImagePlus imagemOperacaoMorfologica = IJ.createImage("Imagem Processada", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor processadorMorfologico = imagemOperacaoMorfologica.getProcessor();
		
		operacaoDilatacao(processador);
		operacaoErosao(processadorMorfologico);
			
		
	}
	
	
//_____________________________________________________________________
	
	
	private void operacaoAbertura(ImageProcessor process){
		
		ImagePlus imagemOriginal = IJ.getImage();
		ImageProcessor processador = imagemOriginal.getProcessor();	
		
		ImagePlus imagemOperacaoMorfologica = IJ.createImage("Imagem Processada", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor processadorMorfologico = imagemOperacaoMorfologica.getProcessor();
		
		operacaoErosao(processador);
		operacaoDilatacao(processadorMorfologico);		
		
	}
	

//_____________________________________________________________________
		
	
	private void operacaoBorda(ImageProcessor process){
		
		ImagePlus imagemOriginal = IJ.getImage();
		ImageProcessor processador = imagemOriginal.getProcessor();	
		
		operacaoErosao(processador);
		ImagePlus imagemOperacaoMorfologica = IJ.getImage();
		ImageProcessor processadorMorfologico = imagemOperacaoMorfologica.getProcessor();
		
		
		ImagePlus imagemSubtracao = IJ.createImage("Subtração", "8-bit", processador.getWidth(), processador.getHeight(), 1);
		ImageProcessor processadorSubtracao = imagemSubtracao.getProcessor();
		
		
		for(int x = 0; x < processador.getWidth(); x++){
			for(int y= 0; y < processador.getHeight(); y++){
				int pixelOriginal =   processador.getPixel(x, y );
				int pixelErosao = 	processadorMorfologico.getPixel(x, y );
				if(pixelErosao != pixelOriginal) {
				 	System.out.println("Entrou no SE:");
				 	System.out.println("Pixel Erosao:"+pixelErosao);
				 	System.out.println("Pixel Original:"+pixelOriginal);
					processadorSubtracao.putPixel(x, y, 0);
				}
				
			}
		}
		imagemSubtracao.show();
		
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

