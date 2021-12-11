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
			
			ImagePlus imagemCopia = imagemOriginal.duplicate();  //Criando a copia após as aplicações separamos as regiões de
																// de interesse da imagem já binarizada
			numImagem++;
			imagemCopia.setRoi(roi); //.getBounds()
			ImagePlus imagemROI = imagemCopia.crop();
			imagemROI.setTitle("Sprite " + numImagem);
			IJ.save(imagemROI, path + numImagem + ".png");		
		}
		
	}

}