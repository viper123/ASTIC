package ro.info.astic;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JImageView extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private String imagePath;

    public JImageView(String imagePath) {
       try {                
          image = ImageIO.read(new File(imagePath));
       } catch (IOException ex) {
            ex.printStackTrace();
       }
    }
    
    public void setImagePath(String path){
    	imagePath = path;
    	try {                
            image = ImageIO.read(new File(imagePath));
         } catch (IOException ex) {
              ex.printStackTrace();
         }
    	repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        try {
			image = getScaledImage(image, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
        g.drawImage(image, 0, 0, null);            
    }
    
    public static BufferedImage getScaledImage(BufferedImage image, int width, int height) throws IOException {
    	if(image == null){
    		return null ;
    	}
        int imageWidth  = image.getWidth();
        int imageHeight = image.getHeight();

        double scaleX = (double)width/imageWidth;
        double scaleY = (double)height/imageHeight;
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

        return bilinearScaleOp.filter(
            image,
            new BufferedImage(width, height, image.getType()));
    }
}
