package Entity;
import framework.Mesh;

public class PlanetEntity extends MeshEntity{
    public int mHealth;
    public float size = 1.0f;
    public String mName;
    public PlanetEntity(float x, float y, float z, int health, float scale, Mesh mesh, String name)
    {
        super(x,y,z,mesh);
        setScale(scale);
        mName = name;
        size *= scale;
        mHealth = health;
    }
}
