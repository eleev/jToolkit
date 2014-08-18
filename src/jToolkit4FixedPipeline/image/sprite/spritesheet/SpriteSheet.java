package jToolkit4FixedPipeline.image.sprite.spritesheet;

import jToolkit4FixedPipeline.image.reader.TWLLoader;
import jToolkit4FixedPipeline.image.sprite.SpriteEntity;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.glDeleteTextures;

/**
 *
 * @author Astemir Yeleev
 */
public class SpriteSheet {

    private int spritesheet;
    private Map<String, SpriteEntity> spriteMap;
    private SpriteEntity currentSprite;
    private final String SPRITESHEET_IMAGE_LOCATION;
    private final String SPRITESHEET_XML_LOCATION;

    public SpriteSheet(String SPRITESHEET_IMAGE_LOCATION, String SPRITESHEET_XML_LOCATION) {
        this.SPRITESHEET_IMAGE_LOCATION = SPRITESHEET_IMAGE_LOCATION;
        this.SPRITESHEET_XML_LOCATION = SPRITESHEET_XML_LOCATION;
        spriteMap = new HashMap<String, SpriteEntity>();
        setUpSpriteSheetMap();
    }

    public Map<String, SpriteEntity> getSpriteSheetMap() {
        return Collections.unmodifiableMap(spriteMap);
    }
    
    public int getSpriteSheetIndex () {
        return spritesheet;
    }
    
    private void setUpSpriteSheetMap() {
        spritesheet = new TWLLoader().glLoadLinearPNG(SPRITESHEET_IMAGE_LOCATION);
        SAXBuilder builder = new SAXBuilder();

        try {
            Document document = builder.build(new File(SPRITESHEET_XML_LOCATION));
            Element root = document.getRootElement();
            for (Object spriteObject : root.getChildren()) {
                Element spriteElement = (Element) spriteObject;
                String name = spriteElement.getAttributeValue("n");
                name = name.substring(0, name.indexOf("."));
                int x = spriteElement.getAttribute("x").getIntValue();
                int y = spriteElement.getAttribute("y").getIntValue();
                int w = spriteElement.getAttribute("w").getIntValue();
                int h = spriteElement.getAttribute("h").getIntValue();
                SpriteEntity sprite = new SpriteEntity(name, x, y, w, h);
                spriteMap.put(sprite.getName(), sprite);
            }
        } catch (JDOMException e) {
            e.printStackTrace();
            deleteSpriteSheetFromMemory();
        } catch (IOException e) {
            e.printStackTrace();
            deleteSpriteSheetFromMemory();
        }
    }

    private void deleteSpriteSheetFromMemory() {
        glDeleteTextures(spritesheet);
    }
}
