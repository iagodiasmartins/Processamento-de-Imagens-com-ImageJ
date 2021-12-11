import ij.IJ;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.plugin.PlugIn;
import ij.plugin.frame.RoiManager;

public class ROI_Manager implements PlugIn {

	public void run(String arg) {
		
		ImagePlus imagemOriginal = IJ.getImage();		
		//ImagePlus imagemCopia = imagemOriginal.duplicate();
		
		IJ.run("8-bit");
		IJ.setThreshold(imagemOriginal, 0, 146, "Red");
		IJ.run("Make Binary");
		IJ.run("Fill Holes");
		IJ.run("Analyze Particles...", "add");

		RoiManager roiManager = RoiManager.getRoiManager();
		Roi[] rois = roiManager.getRoisAsArray();

		int numImagem = 0;
		
		String path = IJ.getDirectory("");

		for (Roi roi : rois) {
			
			ImagePlus imagemCopia = imagemOriginal.duplicate();  //Criando a copia ap�s as aplica��es separamos as regi�es de
																// de interesse da imagem j� binarizada
			numImagem++;
			imagemCopia.setRoi(roi); //.getBounds()
			ImagePlus imagemROI = imagemCopia.crop();
			imagemROI.setTitle("Sprite " + numImagem);
			IJ.save(imagemROI, path + numImagem + ".png");		
		}
		
	}

}