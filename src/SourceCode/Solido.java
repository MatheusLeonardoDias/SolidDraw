/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode;

import javafx.scene.paint.Color;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author root
 */
public class Solido implements Serializable{

    ArrayList<Face> Faces;
    int numFaces;
    int minX, minY, maxX, maxY, minZ, maxZ;
    int centroX, centroY, centroZ;
    boolean complete, selected;

    Solido() {
        Faces = new ArrayList<Face>();
        numFaces = 0;
        complete = true;
        selected = false;

        construirRectangulo();
    }
    
    Solido( ArrayList<Face> FaceBuffer ){
        this.Faces = new ArrayList<>( );
        for (int i = 0; i < FaceBuffer.size(); i++) {
                Faces.add( new Face(FaceBuffer.get(i).Edges, FaceBuffer.get(i).edgesColor, FaceBuffer.get(i).PolyColor) );
        }
        numFaces = this.Faces.size();
        complete = true;
        selected = false;
        
        construirRectangulo();
    }

    public void construirRectangulo() {
        this.minX = this.minY = this.minZ = Integer.MAX_VALUE;
        this.maxX = this.maxY = this.maxZ = Integer.MIN_VALUE;

        for (int i = 0; i < this.Faces.size(); i++) {
            for (int j = 0; j < this.Faces.get(i).numArestas; j++) {
                this.minY = (int) Math.min(Faces.get(i).edgeAtIndex(j).getNodeA().y, this.minY);
                this.minY = (int) Math.min(Faces.get(i).edgeAtIndex(j).getNodeB().y, this.minY);
                this.minX = (int) Math.min(Faces.get(i).edgeAtIndex(j).getNodeA().x, this.minX);
                this.minX = (int) Math.min(Faces.get(i).edgeAtIndex(j).getNodeB().x, this.minX);
                this.minZ = (int) Math.min(Faces.get(i).edgeAtIndex(j).getNodeB().z, this.minZ);
                this.minZ = (int) Math.min(Faces.get(i).edgeAtIndex(j).getNodeB().z, this.minZ);

                this.maxX = (int) Math.max(Faces.get(i).edgeAtIndex(j).getNodeA().x, this.maxX);
                this.maxX = (int) Math.max(Faces.get(i).edgeAtIndex(j).getNodeB().x, this.maxX);
                this.maxY = (int) Math.max(Faces.get(i).edgeAtIndex(j).getNodeA().y, this.maxY);
                this.maxY = (int) Math.max(Faces.get(i).edgeAtIndex(j).getNodeB().y, this.maxY);
                this.maxZ = (int) Math.max(Faces.get(i).edgeAtIndex(j).getNodeA().z, this.maxZ);
                this.maxZ = (int) Math.max(Faces.get(i).edgeAtIndex(j).getNodeB().z, this.maxZ);
            }
        }

        this.centroX = ((maxX - minX) / 2) + minX;
        this.centroY = ((maxY - minY) / 2) + minY;
        this.centroZ = ((maxZ - minZ) / 2) + minZ;
    }

    public void AddFace( Face a ) {
        this.Faces.add(a);
        this.numFaces++;

        this.construirRectangulo();
    }

    public Face faceAtIndex(int index) {
        return Faces.get(index);
    }
}
