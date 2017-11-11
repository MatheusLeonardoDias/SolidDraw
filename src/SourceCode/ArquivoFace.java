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
public class ArquivoFace implements Serializable {

    ArrayList<Edge> Edges;
    ArrayList< ArrayList<Double>> intersectionPoints;
    int numArestas;
    String edgesColor, PolyColor;
    int minX, minY, maxX, maxY, minZ, maxZ;
    int centroX, centroY, centroZ;
    boolean complete, selected, visivel;
    double distance;

    ArquivoFace(ArrayList<Edge> EdgeBuffer, Color EdgeColor, Color FillColor) {
        this.Edges = new ArrayList<>();
        for (int i = 0; i < EdgeBuffer.size(); i++) {
            Edges.add(new Edge(EdgeBuffer.get(i).a, EdgeBuffer.get(i).b));
        }
        numArestas = this.Edges.size();
        edgesColor = EdgeColor.toString();
        PolyColor = FillColor.toString();
        complete = true;
        selected = false;
        visivel = true;
    }
    
    public Face convert(){
        return new Face( this.Edges, Color.valueOf(edgesColor), Color.valueOf(PolyColor) );
    }

}