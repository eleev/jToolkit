package jToolkit4FixedPipeline.physics;

import jToolkit4FixedPipeline.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

/**
 * Axis-Aligned Bounding Box for 3D box
 *
 * Created with IntelliJ IDEA.
 * User: Astemir Eleev
 * Date: 22.02.13
 * Time: 13:01
 * To change this template use File | Settings | File Templates.
 */
public class AABB3d implements BoundingAABB3d <AABB3d>{
    private Vector3f position;
    private float height;
    private float width;
    private float depth;

    public AABB3d() {
        // do nothing
    }

    public AABB3d(Vector3f position, float height, float width, float depth) {
        this.position = position;
        this.height = height;
        this.width = width;
        this.depth = depth;
    }

    @Override
    public boolean intersects (final AABB3d that) {
        if (this.getPosition().getX() - this.getHeight() <= that.getPosition().getX() + that.getHeight() &&
            this.getPosition().getX() + this.getHeight() >= that.getPosition().getX() - that.getHeight() &&
            this.getPosition().getY() + this.getWidth() >= that.getPosition().getY() - that.getWidth() &&
            this.getPosition().getY() - this.getWidth() <= that.getPosition().getY() + that.getWidth() &&
            this.getPosition().getZ() - this.getDepth() <= that.getPosition().getZ() + that.getDepth() &&
            this.getPosition().getZ() + this.getDepth() >= that.getPosition().getZ() - that.getDepth()) {
            return true;
        }
        return false;
    }

    public boolean intersects (final Sphere that) {
        float[] temp = position.getFloatArray();
        Vector3f tpos = new Vector3f();
        tpos.setFloatArray(temp[0], temp[1], temp[2]);

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

        if (center.getZ() < -this.depth) {
            closestPointOnTheAABB.setZ(-this.depth);
        } else if (center.getZ() > this.depth) {
            closestPointOnTheAABB.setZ(this.depth);
        } else {
            closestPointOnTheAABB.setZ(center.getZ());
        }

        Vector3f distance = center.substract(closestPointOnTheAABB);
        if (((distance.getX() * distance.getX()) + (distance.getY() * distance.getY()) + (distance.getZ() * distance.getZ())) < (that.getRadius()* that.getRadius())) {
            return true;
        }
        return false;
    }

    public boolean intersects (final AABB2d that) {
        if ((Math.abs(this.position.getX() - that.getPosition().getX()) < Math.abs((this.height + that.getHeight()))) &&
                (Math.abs(this.position.getY() - that.getPosition().getY()) < Math.abs((this.width + that.getWidth()))) &&
                (Math.abs(this.getPosition().getZ()) < Math.abs(this.getDepth()))) {
            return true;
        }
        return false;
    }

    public boolean collidesWith (final AABB3d that) {
        if ((Math.abs(this.position.getX() - that.getPosition().getX()) < Math.abs((this.height + that.height))) &&
                (Math.abs(this.position.getY() - that.getPosition().getY()) < Math.abs((this.width + that.width))) &&
                (Math.abs(this.position.getZ() - that.getPosition().getZ()) < Math.abs((this.depth + that.depth)))) {
            return true;
        }
        return false;
    }

    public boolean intersects (final Circle that) {
        float[] temp = this.getPosition().getFloatArray();
        Vector3f tpos = new Vector3f();
        tpos.setFloatArray(temp[0], temp[1], temp[2]);

        Vector3f center = tpos.substract(new Vector3f(that.getPosition().getX(), that.getPosition().getY(), .0f));
        Vector3f closestPointOnTheAABB = new Vector3f();

        if (center.getX() < -this.getHeight()) {
            closestPointOnTheAABB.setX(-this.getHeight());
        } else if (center.getX() > this.getHeight()) {
            closestPointOnTheAABB.setX(this.getHeight());
        } else {
            closestPointOnTheAABB.setX(center.getX());
        }

        if (center.getY() < -this.getWidth()) {
            closestPointOnTheAABB.setY(-this.getWidth());
        } else if (center.getY() > this.getWidth()) {
            closestPointOnTheAABB.setY(this.getWidth());
        } else {
            closestPointOnTheAABB.setY(center.getY());
        }

        Vector3f distance = center.substract(closestPointOnTheAABB);
        if (((distance.getX() * distance.getX()) + (distance.getY() * distance.getY()) + (distance.getZ() * distance.getZ())) < (that.getRadius() * that.getRadius())) {
            return true;
        }
        return false;
    }

    @Override
    public void drawSolidBV () {
        glPushMatrix(); {
            glTranslatef(position.getX(), position.getY(), position.getZ());
            glColor3f(2.0f, 2.0f, 2.0f);

            glBegin(GL_QUADS); {
                glVertex3f(-height, width, depth);
                glVertex3f(-height, -width, depth);
                glVertex3f(height, -width, depth);
                glVertex3f(height, width, depth);

                glVertex3f(height, width, depth);
                glVertex3f(height, -width, depth);
                glVertex3f(height, -width, -depth);
                glVertex3f(height, width, -depth);

                glVertex3f(height, width, -depth);
                glVertex3f(height, -width, -depth);
                glVertex3f(-height, -width, -depth);
                glVertex3f(-height, width, -depth);

                glVertex3f(-height, width, -depth);
                glVertex3f(-height, -width, -depth);
                glVertex3f(-height, -width, depth);
                glVertex3f(-height, width, depth);

                glVertex3f(-height, width, depth);
                glVertex3f(height, width, depth);
                glVertex3f(height, width, -depth);
                glVertex3f(-height, width, -depth);

                glVertex3f(-height, -width, depth);
                glVertex3f(-height, -width, -depth);
                glVertex3f(height, -width, -depth);
                glVertex3f(height, -width, depth);

            }
            glEnd();
        }
        glPopMatrix();
    }

    @Override
    public void drawWireBV () {
        glPushMatrix(); {
            glTranslatef(position.getX(), position.getY(), position.getZ());
            glColor3f(2.0f, 2.0f, 2.0f);

            glBegin(GL_LINE_STRIP); {
                glVertex3f(-height, width, depth);
                glVertex3f(-height, -width, depth);
                glVertex3f(height, -width, depth);
                glVertex3f(height, width, depth);

                glVertex3f(-height, width, depth);
                glVertex3f(-height, width, -depth);
                glVertex3f(-height, -width, -depth);
                glVertex3f(-height, -width, depth);

                glVertex3f(height, -width, depth);
                glVertex3f(height, -width, -depth);
                glVertex3f(-height, -width, -depth);
                glVertex3f(-height, width, -depth);

                glVertex3f(height, width, -depth);
                glVertex3f(height, -width, -depth);
                glVertex3f(height, width, -depth);
                glVertex3f(height, width, depth);
            }
            glEnd();
        }
        glPopMatrix();
    }

    @Override
    public Vector3f getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector3f position) {
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

    @Override
    public float getDepth() {
        return depth;
    }

    @Override
    public void setDepth(float depth) {
        this.depth = depth;
    }
}
