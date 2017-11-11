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
public class Face implements Serializable {

    ArrayList<Edge> Edges;
    ArrayList< ArrayList<Double>> intersectionPoints;
    int numArestas;
    Color edgesColor, PolyColor;
    int minX, minY, maxX, maxY, minZ, maxZ;
    int centroX, centroY, centroZ;
    boolean complete, selected, visivel;
    double distance;

    Face(ArrayList<Edge> EdgeBuffer, Color EdgeColor, Color FillColor) {
        this.Edges = new ArrayList<>();
        for (int i = 0; i < EdgeBuffer.size(); i++) {
            Edges.add(new Edge(EdgeBuffer.get(i).a, EdgeBuffer.get(i).b));
        }
        numArestas = this.Edges.size();
        edgesColor = EdgeColor;
        PolyColor = FillColor;
        complete = true;
        selected = false;
        visivel = true;
    }

    Face(Color EdgeColor, Color FillColor) {
        Edges = new ArrayList<Edge>();
        numArestas = 0;
        edgesColor = EdgeColor;
        PolyColor = FillColor;
        complete = true;
        selected = false;
        visivel = true;

    }

    Face(ArrayList<Node> pontos, Node origem, boolean comp, Color EdgeColor, Color FillColor) {
        Edges = new ArrayList<Edge>();
        numArestas = 0;
        edgesColor = EdgeColor;
        PolyColor = FillColor;

        AddEdge(origem, pontos.get(0));
        for (int i = 1; i < pontos.size(); i++) {
            AddEdge(pontos.get(i), pontos.get(i - 1));
        }

        if (comp) {
            AddEdge(pontos.get(pontos.size() - 1), origem);
            this.complete = true;
        } else {
            this.complete = false;
        }

        selected = false;
        visivel = true;

    }

    public void construirRectangulo() {
        this.minX = this.minY = this.minZ = Integer.MAX_VALUE;
        this.maxX = this.maxY = this.maxZ = Integer.MIN_VALUE;

        for (int i = 0; i < this.Edges.size(); i++) {
            this.minY = (int) Math.ceil(Math.min(Edges.get(i).getNodeA().y, this.minY));
            this.minY = (int) Math.ceil(Math.min(Edges.get(i).getNodeB().y, this.minY));
            this.minX = (int) Math.ceil(Math.min(Edges.get(i).getNodeA().x, this.minX));
            this.minX = (int) Math.ceil(Math.min(Edges.get(i).getNodeB().x, this.minX));
            this.minZ = (int) Math.ceil(Math.min(Edges.get(i).getNodeB().z, this.minZ));
            this.minZ = (int) Math.ceil(Math.min(Edges.get(i).getNodeB().z, this.minZ));

            this.maxX = (int) Math.max(Edges.get(i).getNodeA().x, this.maxX);
            this.maxX = (int) Math.max(Edges.get(i).getNodeB().x, this.maxX);
            this.maxY = (int) Math.max(Edges.get(i).getNodeA().y, this.maxY);
            this.maxY = (int) Math.max(Edges.get(i).getNodeB().y, this.maxY);
            this.maxZ = (int) Math.max(Edges.get(i).getNodeA().z, this.maxZ);
            this.maxZ = (int) Math.max(Edges.get(i).getNodeB().z, this.maxZ);
        }

        this.centroX = ((maxX - minX) / 2);// + minX;
        this.centroY = ((maxY - minY) / 2);// + minY;
        this.centroZ = ((maxZ - minZ) / 2);// + minZ;
    }

    public void AddEdge(Node a, Node b) {
        Edge temp = new Edge(a, b);
        this.Edges.add(temp);
        this.numArestas++;

        this.construirRectangulo();
    }

    public Node nodeCoordinatesA(int index) {
        return Edges.get(index).a;
    }

    public Node nodeCoordinatesB(int index) {
        return Edges.get(index).b;
    }

    public Edge edgeAtIndex(int index) {
        return Edges.get(index);
    }

    public void inverteOrientacao() {
        for (int i = 0; i < this.Edges.size(); i++) {
            Node temp = new Node(this.Edges.get(i).getNodeA());
            this.Edges.get(i).setNodeA(new Node(this.Edges.get(i).getNodeB()));
            this.Edges.get(i).setNodeB(new Node(temp));
        }
        Collections.reverse(this.Edges);
    }

    public void setEdgeColor(Color c) {
        this.edgesColor = c;
    }

    public void setFillColor(Color c) {
        this.PolyColor = c;
    }

    public void calcularIntersec() {
        this.construirRectangulo();
        this.intersectionPoints = new ArrayList<>();
        for (int l = 0; l <= (maxY - minY + 1); l++) {
            intersectionPoints.add(new ArrayList<>());
        }

        for (int k = 0; k < this.Edges.size(); k++) {
            this.Edges.get(k).calculateM();
            int inicio = (int) Math.ceil(Math.min(this.Edges.get(k).getNodeA().y, this.Edges.get(k).getNodeB().y));
            int fim = (int) Math.ceil(Math.max(this.Edges.get(k).getNodeA().y, this.Edges.get(k).getNodeB().y));

            double intersec = (this.Edges.get(k).getNodeA().y <= this.Edges.get(k).getNodeB().y)
                    ? this.Edges.get(k).getNodeA().x : this.Edges.get(k).getNodeB().x;
            for (int desloc = inicio; desloc < fim; desloc++) {
                intersectionPoints.get(desloc - minY).add(intersec);
                intersec += (this.Edges.get(k).m != 0 ? 1.0 / this.Edges.get(k).m : 0);
            }
//            intersectionPoints.get( (int) Math.ceil(this.Edges.get(k).getNodeA().y) - minY).add( this.Edges.get(k).getNodeA().x );
//            intersectionPoints.get( (int) Math.ceil(this.Edges.get(k).getNodeB().y) - minY).add( this.Edges.get(k).getNodeB().x );
        }

        for (int l = 0; l < intersectionPoints.size(); l++) {
            Collections.sort(this.intersectionPoints.get(l));
        }

    }

    public void calculaAlgoritmoPintor(Node VRP) {
        this.construirRectangulo();
        this.distance = Math.sqrt(Math.pow((VRP.x - this.centroX), 2.0)
                + Math.pow((VRP.y - this.centroY), 2.0)
                + Math.pow((VRP.z - this.centroZ), 2.0));
    }

    public void calculaVisibilidadeNormal(Node localVRP, Node localP ) {
        if (this.Edges.size() >= 3) {
            Node primeiro_vetor = this.Edges.get(0).getNodeA().subtrairPonto(this.Edges.get(1).getNodeA());
            Node segundo_vetor = this.Edges.get(1).getNodeB().subtrairPonto(this.Edges.get(1).getNodeA());
            Node normal = primeiro_vetor.produtoVetorial(segundo_vetor);
            
            Node n = new Node(localVRP.x - localP.x, localVRP.y - localP.y, localVRP.z - localP.z);
            n.dividirPorNorma();
            if ( n.produtoEscalar(normal) > 0) {
                this.visivel = true;
            } else {
                this.visivel = false;
            }
        } else {
            this.visivel = false;
        }
    }
    
}