package SourceCode;

import SourceCode.ArquivoFace;
import SourceCode.Face;
import java.io.Serializable;
import java.util.ArrayList;

public class ArquivoSolido implements Serializable{

    ArrayList<ArquivoFace> Faces;
    int numFaces;
    int minX, minY, maxX, maxY, minZ, maxZ;
    int centroX, centroY, centroZ;
    boolean complete, selected;

    ArquivoSolido() {
        Faces = new ArrayList<ArquivoFace>();
        numFaces = 0;
        complete = true;
        selected = false;

    }
    
    ArquivoSolido( ArrayList<Face> FaceBuffer ){
        this.Faces = new ArrayList<>( );
        for (int i = 0; i < FaceBuffer.size(); i++) {
                Faces.add( new ArquivoFace(FaceBuffer.get(i).Edges, FaceBuffer.get(i).edgesColor, FaceBuffer.get(i).PolyColor) );
        }
        numFaces = this.Faces.size();
        complete = true;
        selected = false;
        
    }
    
    public void AddFace( ArquivoFace a ) {
        this.Faces.add(a);
        this.numFaces++;
    }
    
    public ArquivoFace faceAtIndex(int index) {
        return Faces.get(index);
    }
}