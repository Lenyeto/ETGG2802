package framework;

import framework.Program.SamplerCubeSetter;
public class SkyBox extends Mesh 
{
    public SkyBox(String[] filenames, Program skyprog){
        super("assets/cube.obj.mesh");
        Texture c = new ImageCubeTexture(filenames);
        this.texture = c;
        skyprog.setUniform("basetexture",c);
    }
}
