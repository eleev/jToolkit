package jToolkit4FixedPipeline.physics;

import jToolkit4FixedPipeline.vector.Vector2f;
import jToolkit4FixedPipeline.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

/**
 * Axis-Aligned Bounding Box for 2D box
 *
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 22.02.13
 * Time: 13:01
 * To change this template use File | Settings | File Templates.
 */
public class AABB2d implements BoundingAABB2d <AABB2d>{
    private Vector2f position;
    private float height;
    private float width;

    public AABB2d() {
        // do nothing
    }

    public AABB2d(Vector2f position, float height, float width) {
        this.position = position;
        this.height = height;
        this.width = width;
    }

    @Override
    public boolean intersects (final AABB2d that) {
        if (this.getPosition().getX() - this.getHeight() <= that.getPosition().getX() + that.getHeight() &&
                this.getPosition().getX() + this.getHeight() >= that.getPosition().getX() - that.getHeight() &&
                this.getPosition().getY() + this.getWidth() >= that.getPosition().getY() - that.getWidth() &&
                this.getPosition().getY() - this.getWidth() <= that.getPosition().getY() + that.getWidth()) {
            return true;
        }
        return false;
    }

    public boolean intersects (final Circle that) {
        float[] temp = position.getFloatArray();
        Vector2f tpos = new Vector2f();
        tpos.setFloatArray(temp[0], temp[1]);

        Vector2f center = tpos.substract(that.getPosition());
        Vector2f closestPointOnTheAABB = new Vector2f();

        if (center.getX() < -this.height) {
            closestPointOnTheAABB.setX(-this.height);
        } else if (center.getX() > this.height) {
            closestPointOnTheAABB.setX(this.height);
        } else {
            closestPointOnTheAABB.setX(center.getX());
        }

        if (center.getY() < -this.width) {
            closestPointOnTheAABB.setY(-this.width);
        } else if (center.getY() > this.width) {
            closestPointOnTheAABB.setY(this.width);
        } else {
            closestPointOnTheAABB.setY(center.getY());
        }

        Vector2f distance = center.substract(closestPointOnTheAABB);
        if (((distance.getX() * distance.getX()) + (distance.getY() * distance.getY())) < (that.getRadius()* that.getRadius())) {
            return true;
        }

        return false;
    }

    public boolean intersects (final Sphere that) {
        float[] temp = position.getFloatArray();
        Vector3f tpos = new Vector3f();
        tpos.setFloatArray(temp[0], temp[1], .0f);

        Vector3f center = tpos.substract(that.getPosition());
        Vector3f closestPointOnTheAABB = new Vector3f();

        if (center.getX() < -this.height) {
            closestPointOnTheAABB.setX(-this.height);
        } else if (center.getX() > this.height) {
            closestPointOnTheAABB.setX(this.height);
        } else {
            closestPointOnTheAABB.setX(center.getX());
        }

        if (center.getY() < -this.width) {
            closestPointOnTheAABB.setY(-this.width);
        } else if (center.getY() > this.width) {
            closestPointOnTheAABB.setY(this.width);
        } else {
            closestPointOnTheAABB.setY(center.getY());
        }

        Vector3f distance = center.substract(closestPointOnTheAABB);
        if (((distance.getX() * distance.getX()) + (distance.getY() * distance.getY()) + (distance.getZ() * distance.getZ())) < (that.getRadius()* that.getRadius())) {
            return true;
        }

        return false;
    }

    public boolean intersects (final AABB3d that) {
        if ((Math.abs(this.position.getX() - that.getPosition().getX()) < Math.abs((this.height + that.getHeight()))) &&
                (Math.abs(this.position.getY() - that.getPosition().getY()) < Math.abs((this.width + that.getWidth()))) &&
                    (Math.abs(that.getPosition().getZ()) < Math.abs(that.getDepth()))) {
            return true;
        }
        return false;
    }

    public boolean collidesWith (final AABB2d that) {
        if ((Math.abs(this.position.getX() - that.getPosition().getX()) < Math.abs((this.height + that.getHeight()))) &&
                (Math.abs(this.position.getY() - that.getPosition().getY()) < Math.abs((this.width + that.getWidth())))) {
            return true;
        }
        return false;
    }

    @Override
    public void drawSolidBV () {
        glDisable(GL_CULL_FACE);
        glPushMatrix(); {
            glTranslatef(position.getX(), position.getY(), .0f);
            glColor3f(2.0f, 2.0f, 2.0f);

            glBegin(GL_QUADS); {
                glVertex2f(-height, width);
                glVertex2f(-height, -width);
                glVertex2f(height, -width);
                glVertex2f(height, width);
            }
            glEnd();
        }
        glPopMatrix();
        glEnable(GL_CULL_FACE);
    }

    @Override
    public void drawWireBV () {
        glPushMatrix(); {
            glTranslatef(position.getX(), position.getY(), .0f);
            glColor3f(2.0f, 2.0f, 2.0f);

            glBegin(GL_LINE_STRIP); {
                glVertex2f(-height, width);
                glVertex2f(-height, -width);
                glVertex2f(height, -width);
                glVertex2f(height, width);
                glVertex2f(-height, width);
            }
            glEnd();
        }
        glPopMatrix();
    }

    @Override
    public Vector2f getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector2f position) {
        this.position = position;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
    }
}


