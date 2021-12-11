import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.PlugIn;
import ij.IJ;
import ij.plugin.ImageCalculator;

public class Operacoes_Ari_e_Log implements PlugIn{	
	
	public void run(String arg) {
		//ImagePlus imagem1, imagem2;
		
		ImagePlus imagem1 = WindowManager.getImage("imagem1.png"); 
		ImagePlus imagem2 = WindowManager.getImage("imagem2.png"); 
		//imagem1 = IJ.openImage("C:\\Users\\Iago Dias\\Desktop\\Imagens Processadas\\Imagens Sendo Utilizadas\\Binary\\imagem1.png");
		//imagem2 = IJ.openImage("C:\\Users\\Iago Dias\\Desktop\\Imagens Processadas\\Imagens Sendo Utilizadas\\Binary\\imagem2.png");	
		ImagePlus imagem3;		
	
		String[] operadores = {"add","subtract", "multiply","divide", "and", "or", "xor", "min", "max", "average", "difference" , "copy"}; 	
		
		String path = IJ.getDirectory("");
        System.out.println(path);
        
        for(int i = 0; i < operadores.length ; i++) {   
            imagem3 = ImageCalculator.run(imagem1, imagem2,  operadores[i] + " " + "create");
            System.out.println(operadores[i] + " " + "create");
            IJ.save(imagem3, path + operadores[i]+ ".png");
		
        }	
	}
}
