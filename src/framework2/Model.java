
package framework2;

import framework.math3d.vec3;
import framework.math3d.vec4;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {
    private List<ModelPart> parts;
    private List<vec4> normals;
    private int num_indices = 0;
    
    public Model() {
        
    }

    public Model(String filename) {
        OBJReader o = new OBJReader();
    }
    
    public int getNumParts() {
        return parts.size();
    }
    
    public List<Float> getVertexList() {
        List<Float> tmpList = null;
        
        for (ModelPart p : parts) {
            for (Integer i : p.indices) {
                tmpList.add(p.vertices.get(i*3));
                tmpList.add(p.vertices.get(i*3 + 1));
                tmpList.add(p.vertices.get(i*3 + 2));
            }
        }
        
        return tmpList;
    }
    
    public List<Float> getUVList() {
        List<Float> tmpList = null;
        
        for (ModelPart p : parts) {
            
        }
        
        return tmpList;
    }
    
    void addPart(ModelPart currentPart) {
        parts.add(currentPart);
    }

    void generateNormals() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

class ModelPart {

    public String name = null;
    public List<Float> vertices;
    public List<Integer> indices;
    public List<Float> uvs;
    public List<Integer> uvIndices;
    
    public ModelPart() {
        
    }
    
    public void addUV(float parseFloat) {
        uvs.add(parseFloat);
    }

    public void setName(String split) {
        name = split;
    }

    public void addUVIndex(int i) {
        uvIndices.add(i);
    }

    public void addIndex(int i) {
        indices.add(i);
    }

    public void addVertex(float parseFloat) {
        vertices.add(parseFloat);
    }
    
}

class OBJReader {
    
    public OBJReader() {
        
    }
    
    //Using the readline method from the Mesh.java.
    private String readLine(DataInputStream din) {
        String x="";
        
        while(true){
            try{
                byte b = din.readByte();
                if( b == '\n' )
                    return x;
                x += (char)b;
            }
            catch(EOFException e){
                if( x.length() == 0)
                    return null;
                else
                    return x;
            }
            catch(IOException e){
                throw new RuntimeException("IO error");
            }
        }
    }
    
    private String readLine2(DataInputStream din) {
        String x = "";
        
        while(true) {
            try{
                int b = din.read();
                if (b == -1) {
                    x += (char)255;
                    return x;
                }
                if (b == '\n')
                    return x;
                x += (char)b;
                
            } catch (EOFException e) {
                if (x.length() == 0)
                    return null;
                else
                    return x;
            } catch (IOException e) {
                throw new RuntimeException("IO Error");
            }
        }
        
    }
        
    
    public Model readFile( String filename ) {
        Model model = new Model();
        ModelPart currentPart = null;
        
        FileInputStream fin;
        try{
            fin = new FileInputStream(filename);
        }
        catch(FileNotFoundException ex) {
            throw new RuntimeException("File not found: "+filename);
        }
        
        DataInputStream din = new DataInputStream(fin);
        
        String line = null;
        
        while(!line.endsWith((char)255+"")) {
            line = readLine2(din);
            
            
            if (line.startsWith("v ")) {
                String[] verts = line.split(" ");
                for (int i = 1; i < verts.length; i++) {
                    currentPart.addVertex(Float.parseFloat(verts[i]));
                }
            } else if (line.startsWith("vt")) {
                String[] uvs = line.split(" ");
                for (int i = 1; i < uvs.length; i++) {
                    currentPart.addUV(Float.parseFloat(uvs[i]));
                }
            } else if (line.startsWith("f")) {
                String[] indices = line.split(" ");
                for (int i = 1; i < indices.length; i++) {
                    String[] tmpIndex = indices[i].split("/");
                    currentPart.addIndex(Integer.parseInt(tmpIndex[i]) - 1);
                    if (tmpIndex.length > 1) {
                        currentPart.addUVIndex(Integer.parseInt(tmpIndex[1]) - 1);
                    }
                }
            } else if (line.startsWith("o")) {
                if (currentPart == null) {
                    currentPart = new ModelPart();
                } else {
                    model.addPart(currentPart);
                    currentPart = new ModelPart();
                }
                
                currentPart.setName(line.split(" ")[1]);
            }
            
            
            
        }
        
        try {
            din.close();
        } catch (IOException ex) {
            Logger.getLogger(OBJReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (currentPart != null) {
            model.addPart(currentPart);
        }
        
        model.generateNormals();
        
        return model;
    }
    
}

/* Code that is being used from andrews stuff





# FILENAME: model.py
# BY: Andrew Holbrook
# DATE: 9/24/2015

import ctypes
from OpenGL import GL
from . import GLWindow, Vector4, Matrix4
from sdl2 import sdlimage

class Model(object):
    """Class for representing a Wavefront OBJ object.
    """
    def __init__(self):
        self.parts = []
        self.normals = []
        self.num_indices = 0
    
    def __str__(self):
        return str(self.num_indices)
    
    def getNumParts(self):
        return len(self.parts)
    
    def getNumIndices(self):
        return self.num_indices
    
    def getVertexList(self):
        tempList = []
        for part in self.parts:
            for i in part.indices:
                tempList += part.vertices[i*3:i*3+3]
        return tempList

    def getUVList(self):
        tempList = []
        for part in self.parts:
            for i in part.uvIndices:
                tempList += part.uvs[i*2:i*2+2]
        return tempList
    
    def getIndexList(self):
        tmpList = []
        for p in self.parts:
            tmpList += p.indices
        
        return tmpList
    
    def getUVIndexList(self):
        tmpList = []
        for p in self.parts:
            tmpList += p.uvIndices
        
        return tmpList
    
    def getNormalList(self):
        return self.normals
    
    def addPart(self, p):
        self.parts.append(p)
        self.num_indices += p.getNumIndices()
    
    def generateNormals(self):
        tmpList = [Vector4(),] * self.getNumIndices()
        indexList = self.getIndexList()
        vertexList = self.getVertexList()
        for i in range(0, self.getNumIndices(), 3):
            idx0, idx1, idx2 = indexList[i:i + 3]
            v0 = Vector4(vertexList[idx0 * 3:idx0 * 3 + 3])
            v1 = Vector4(vertexList[idx1 * 3:idx1 * 3 + 3])
            v2 = Vector4(vertexList[idx2 * 3:idx2 * 3 + 3])
            
            v01 = v1 - v0
            v12 = v2 - v1
            normal = v01.cross(v12)
            
            tmpList[idx0] += normal
            tmpList[idx1] += normal
            tmpList[idx2] += normal
        
        for n in tmpList:
            n.normalize()
            self.normals += n.getXYZ()
    
    def cleanup(self):
        GL.glDeleteBuffers(1, self.positionBuffer)
        GL.glDeleteBuffers(1, self.colorBuffer)
        GL.glDeleteBuffers(1, self.normalBuffer)
        GL.glDeleteBuffers(1, self.indexBuffer)
        GL.glDeleteVertexArrays(1, self.vertexArrayObject)
    
    def loadToVRAM(self):

        GL.glActiveTexture(GL.GL_TEXTURE0)
        self.textureObject = GL.glGenTextures(1)
        GL.glBindTexture(GL.GL_TEXTURE_2D, self.textureObject)
        img = sdlimage.IMG_Load(b'diffuse.png')
        pixels = ctypes.cast(img.contents.pixels, ctypes.c_void_p)
        GL.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, 1024, 1024, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, pixels)
        GL.glTexParameter(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST)
        GL.glTexParameter(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST)
        self.vertexArrayObject = GL.glGenVertexArrays(1)
        GL.glBindVertexArray(self.vertexArrayObject)


        self.positionBuffer = GL.glGenBuffers(1)
        GL.glBindBuffer(GL.GL_ARRAY_BUFFER, self.positionBuffer)
        c_vertexArray = ctypes.c_float * len(self.getVertexList())
        c_vertexArray = c_vertexArray(*self.getVertexList())
        GL.glBufferData(GL.GL_ARRAY_BUFFER, c_vertexArray, GL.GL_STATIC_DRAW)
        del c_vertexArray

        GL.glVertexAttribPointer(0, 3, GL.GL_FLOAT, False, 0, None)

        self.uvBuffer = GL.glGenBuffers(1)
        GL.glBindBuffer(GL.GL_ARRAY_BUFFER, self.uvBuffer)
        c_uvArray = ctypes.c_float * len(self.getUVList())
        c_uvArray = c_uvArray(*self.getUVList())
        GL.glBufferData(GL.GL_ARRAY_BUFFER, c_uvArray, GL.GL_STATIC_DRAW)
        del c_uvArray

        GL.glVertexAttribPointer(1, 2, GL.GL_FLOAT, False, 0, None)

        self.normalBuffer = GL.glGenBuffers(1)
        GL.glBindBuffer(GL.GL_ARRAY_BUFFER, self.normalBuffer)
        c_normalArray = ctypes.c_float * (3 * self.getNumIndices())
        c_normalArray = c_normalArray(*self.getNormalList())
        GL.glBufferData(GL.GL_ARRAY_BUFFER, c_normalArray, GL.GL_STATIC_DRAW)
        del c_normalArray

        GL.glVertexAttribPointer(2, 3, GL.GL_FLOAT, False, 0, None)

        GL.glEnableVertexAttribArray(0)
        GL.glEnableVertexAttribArray(1)
        GL.glEnableVertexAttribArray(2)

        GL.glBindVertexArray(0)
    
    def renderPartByIndex(self, index):
        GL.glBindVertexArray(self.vertexArrayObject)
        
        offset = 0
        for i in range(0, index):
            offset += self.parts[i].getNumIndices()
        
        c_offset = ctypes.c_void_p(offset * ctypes.sizeof(ctypes.c_uint))
        GL.glDrawElements(GL.GL_TRIANGLES, self.parts[index].getNumIndices(), GL.GL_UNSIGNED_INT, c_offset)
        
        GL.glBindVertexArray(0)
        
    def renderPartByName(self, name):
        GL.glBindVertexArray(self.vertexArrayObject)
        
        offset = 0
        for p in self.parts:
            if p.name == name:
                c_offset = ctypes.c_void_p(offset * ctypes.sizeof(ctypes.c_uint))
                GL.glDrawElements(GL.GL_TRIANGLES, p.getNumIndices(), GL.GL_UNSIGNED_INT, c_offset)
                break
            
            offset += p.getNumIndices()
        
        GL.glBindVertexArray(0)
    
    def renderAllParts(self):
        GL.glBindVertexArray(self.vertexArrayObject)
        GL.glDrawArrays(GL.GL_TRIANGLES, 0, self.getNumIndices())
        #GL.glDrawElements(GL.GL_TRIANGLES, self.getNumIndices(), GL.GL_UNSIGNED_INT, None)
        
        GL.glBindVertexArray(0)

class ModelPart(object):
    """Represents a part (object) from the obj file.
    """
    def __init__(self):
        self.name = None
        self.vertices = []
        self.indices = []
        self.uvs = []
        self.uvIndices = []
    
    def getNumIndices(self):
        return len(self.indices)
    
    def getNumUVIndices(self):
        return len(self.uvIndices)
    
    def setName(self, name):
        self.name = name
    
    def addVertex(self, v):
        self.vertices.append(v)
    
    def addIndex(self, i):
        self.indices.append(i)
    
    def addUV(self, uv):
        self.uvs.append(uv)
    
    def addUVIndex(self, uvIndex):
        self.uvIndices.append(uvIndex)

class OBJReader(object):
    
    @staticmethod
    def readFile(file):
        """Reads an .obj file and returns the data as a Model object.
        """
        model = Model()
        currentPart = None
        
        fp = open(file)
        
        for line in fp:
            if line[0:2] == 'v ':
                verts = line.split()
                for i in range(1, len(verts)):
                    currentPart.addVertex(float(verts[i]))
            elif line[0:2] == 'vt':
                uvs = line.split()
                for i in range(1, len(uvs)):
                    currentPart.addUV(float(uvs[i]))
            elif line[0] == 'f':
                indices = line.split()
                for i in range(1, len(indices)):
                    tmpIndex = indices[i].split('/')
                    currentPart.addIndex(int(tmpIndex[0]) - 1)
                    if len(tmpIndex) > 1:
                        currentPart.addUVIndex(int(tmpIndex[1]) - 1)
            elif line[0] == 'o':
                if currentPart == None:
                    currentPart = ModelPart()
                else:
                    model.addPart(currentPart)
                    currentPart = ModelPart()
                
                currentPart.setName(line.split()[1])
                
        fp.close()
        
        if currentPart != None:
            model.addPart(currentPart)
        
        model.generateNormals()
        
        return model







*/