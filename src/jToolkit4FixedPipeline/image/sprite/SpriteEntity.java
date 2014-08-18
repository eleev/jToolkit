package jToolkit4FixedPipeline.image.sprite;

/**
 * Sprite for entity
 * @author Astemir Yeleev
 */
public class SpriteEntity {
    private final String name;
    private final int x;
    private final int y;
    private final int w;
    private final int h;

    public SpriteEntity(String name, int x, int y, int w, int h) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpriteEntity sprite = (SpriteEntity) o;

        if (h != sprite.h) return false;
        if (w != sprite.w) return false;
        if (x != sprite.x) return false;
        if (y != sprite.y) return false;
        if (name != null ? !name.equals(sprite.name) : sprite.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + w;
        result = 31 * result + h;
        return result;
    }

    @Override
    public String toString() {
        return "Sprite{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", w=" + w +
                ", h=" + h +
                '}';
    }

    public String getName() {
        return new String(name);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }
}
