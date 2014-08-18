package jToolkit4FixedPipeline.image.texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.MipMap.gluBuild2DMipmaps;

public class TextureUploader {
	private static final int BYTES_PER_PIXEL = 4;// 3 for RGB, 4 for RGBA

	public static int loadTexture(BufferedImage image) {

		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0,
				image.getWidth());

		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth()
				* image.getHeight() * BYTES_PER_PIXEL); // 4 for RGBA, 3 for RGB

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF)); // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF)); // Green component
				buffer.put((byte) (pixel & 0xFF)); // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha component.
															// Only for RGBA
			}
		}

		buffer.flip(); // FOR THE LOVE OF GOD DO NOT FORGET THIS

		// You now have a ByteBuffer filled with the color data of each pixel.
		// Now just create a texture ID and bind it. Then you can load it using
		// whatever OpenGL method you want, for example:

		int textureID = glGenTextures(); // Generate texture ID
		glBindTexture(GL_TEXTURE_2D, textureID); // Bind texture ID

		// Setup wrap mode
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

		// Setup texture scaling filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		// Send texel data to OpenGL
		gluBuild2DMipmaps(GL_TEXTURE_2D,  GL_RGBA, image.getWidth(),
				image.getHeight(), GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		glBindTexture(GL_TEXTURE_2D, 0);
		// Return the texture ID so we can bind it later again
		return textureID;
	}

	public static BufferedImage loadImage(String loc) {
		try {
			return ImageIO.read(new File(loc));
		} catch (IOException e) {
			System.err.println("Failed to load Textures");
		}
		return null;
	}
	
	
	public static int loadMipTexture(BufferedImage image) {

		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0,
				image.getWidth());

		ByteBuffer buffer2 = BufferUtils.createByteBuffer(image.getWidth()
				* image.getHeight() * BYTES_PER_PIXEL); // 4 for RGBA, 3 for RGB

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[y * image.getWidth() + x];
				buffer2.put((byte) ((pixel >> 16) & 0xFF)); // Red component
				buffer2.put((byte) ((pixel >> 8) & 0xFF)); // Green component
				buffer2.put((byte) (pixel & 0xFF)); // Blue component
				buffer2.put((byte) ((pixel >> 24) & 0xFF)); // Alpha component.
															// Only for RGBA
			}
		}

		buffer2.flip(); // FOR THE LOVE OF GOD DO NOT FORGET THIS

		// You now have a ByteBuffer filled with the color data of each pixel.
		// Now just create a texture ID and bind it. Then you can load it using
		// whatever OpenGL method you want, for example:

		int textureID2 = glGenTextures(); // Generate texture ID
		glBindTexture(GL_TEXTURE_2D, textureID2); // Bind texture ID
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		// Setup texture scaling filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		// Setup wrap mode
		

		
		
		//glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
		// Send texel data to OpenGL
		gluBuild2DMipmaps(GL_TEXTURE_2D,  GL_RGBA8, image.getWidth(),
				image.getHeight(), GL_RGBA, GL_UNSIGNED_BYTE, buffer2);
		
		
		// Return the texture ID so we can bind it later again
		return textureID2;
	}
	
	public static int loadLightTexture(BufferedImage image2) {
		for(int i=0; i<image2.getWidth(); i++){
	        for(int j=0; j<image2.getHeight(); j++){
	            int color = image2.getRGB(i,j);
	 
	            int alpha = (color >> 24) & 255;
	            int red = (color >> 16) & 255;
	            int green = (color >> 8) & 255;
	            int blue = (color) & 255;
	 
	            final int lum = (int)(0.2126 * red + 0.7152 * green + 0.0722 * blue);
	 
	            alpha = (alpha << 24);
	            red = (lum << 16);
	            green = (lum << 8);
	            blue = lum;
	 
	            color = alpha + red + green + blue;
	 
	            image2.setRGB(i,j,color);
	        }
	    } 
		BufferedImage image = image2;
		
		
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0,
				image.getWidth());

		ByteBuffer buffer2 = BufferUtils.createByteBuffer(image.getWidth()
				* image.getHeight() * BYTES_PER_PIXEL); // 4 for RGBA, 3 for RGB

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[y * image.getWidth() + x];
				buffer2.put((byte) ((pixel >> 16) & 0xFF)); // Red component
				buffer2.put((byte) ((pixel >> 8) & 0xFF)); // Green component
				buffer2.put((byte) (pixel & 0xFF)); // Blue component
				buffer2.put((byte) ((pixel >> 24) & 0xFF)); // Alpha component.
															// Only for RGBA
				
			}
		}

		buffer2.flip(); // FOR THE LOVE OF GOD DO NOT FORGET THIS

		// You now have a ByteBuffer filled with the color data of each pixel.
		// Now just create a texture ID and bind it. Then you can load it using
		// whatever OpenGL method you want, for example:

		int textureID2 = glGenTextures(); // Generate texture ID
		glBindTexture(GL_TEXTURE_2D, textureID2); // Bind texture ID
		
		
		// Setup texture scaling filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		// Setup wrap mode


		//glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
		// Send texel data to OpenGL
		gluBuild2DMipmaps(GL_TEXTURE_2D,  GL_RGBA, image.getWidth(),
				image.getHeight(), GL_RGBA, GL_UNSIGNED_BYTE, buffer2);
		
		
		// Return the texture ID so we can bind it later again
		return textureID2;
	}
	

}