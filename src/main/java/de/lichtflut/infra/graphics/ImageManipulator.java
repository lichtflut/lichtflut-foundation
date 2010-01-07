/*
 * Copyright (C) 2009 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.infra.graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Helper for manipulating of images using Java2D features.
 * 
 * Created: 28.10.2008
 *
 * @author Oliver Tigges
 */
public class ImageManipulator {
	
	public BufferedImage cutCentered(ImageIcon icon, int chop){
		BufferedImage img = new BufferedImage(
				icon.getIconWidth() - 2*chop, 
				icon.getIconHeight() - 2*chop, 
				BufferedImage.TYPE_INT_ARGB);
		
		icon.paintIcon(null, img.getGraphics(), - chop, - chop);
		return img;
	}
	
	public BufferedImage scale(Image img, int width, int height){
		Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = result.getGraphics();
		g.drawImage(scaled, 0, 0, null);
		g.dispose();
		
		return result;
	}
	
	public BufferedImage narrow(Image image, Dimension maxImgDimension){
		double scale = 1.0d;
		float oldWidth = image.getWidth(null);
		float oldHeight = image.getHeight(null);
		
		if ( oldWidth > maxImgDimension.width){
			scale = oldWidth / maxImgDimension.width;
		}
		if (oldHeight > maxImgDimension.height){
			scale = Math.max(scale, oldHeight/maxImgDimension.height);
		}
		if (scale > 1.0d){
			int newWidth = Math.min(maxImgDimension.width, (int) (oldWidth/scale));
			int newHeight = Math.min(maxImgDimension.height, (int) (oldHeight/scale));
			return scale(image, newWidth, newHeight);
		} else {
			return toBufferedImage(image);
		}
	}
	
	public BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage){
			return (BufferedImage) image;
		}
		BufferedImage result = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics g = result.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return result;
	}
	
	//-----------------------------------------------------

	public static void main(String[] args) {
		ImageManipulator im = new ImageManipulator();
		
		File baseDir = new File("/Users/oliver/projects/lichtflut/glasnost/res/red_icons");
		File targetDir = new File("/Users/oliver/projects/lichtflut/glasnost/res/icons");
		
		if (!targetDir.exists()){
			targetDir.mkdir();
		}
		
		File[] files = baseDir.listFiles(new FilenameFilter(){
			public boolean accept(File dir, String name) {
				return name.endsWith(".png");
			}
		});
		
		for (File current : files) {
			ImageIcon icon = new ImageIcon(current.getAbsolutePath());
			
			BufferedImage img = im.cutCentered(icon, 7);
			img = im. scale(img, 24,24);
			
			try {
				ImageIO.write(img, "png", new File(targetDir, current.getName()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	

}
