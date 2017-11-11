/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class Edge implements Serializable{

    public Node a, b;
    double m;
    
    
    Edge(Node a, Node b) {
        this.a = new Node(a.x, a.y, a.z);
        this.b = new Node(b.x, b.y, b.z );
        this.calculateM();
    }

    public Node getNodeA() {
        return this.a;
    }

    public Node getNodeB() {
        return this.b;
    }
    
    public void setNodeA( Node newA ){
        this.a = new Node( newA.x, newA.y, newA.z );
    }
   
    public void setNodeB( Node newB ){
        this.b = new Node( newB.x, newB.y, newB.z );
    }
    
    public void calculateM(){
        if( b.x - a.x != 0 )
            this.m = ( (double)( b.y - a.y ) / ( b.x - a.x ) );
        else
            this.m = 0;
        
    }

}

