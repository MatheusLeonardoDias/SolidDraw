/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.Serializable;
import javafx.event.ActionEvent;
import javafx.stage.Screen;

/**
 *
 * @author root
 */
public class Node implements Serializable{

    public double x, y, z;

    Node(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    Node( Node n ){
        this.x = new Double( n.x );
        this.y = new Double( n.y );
        this.z = new Double( n.z );
    }
    
    public void setX( double nx ){
        this.x = nx;
    }

    public void setY( double ny ){
        this.y = ny;
    }
    
    public void setZ( double nz ){
        this.z = nz;
    }
    
    public void dividirPorNorma(){
        double norma = Math.sqrt( this.x*this.x + this.y*this.y + this.z*this.z );
        this.setX(this.x/norma);
        this.setY(this.y/norma);
        this.setZ(this.z/norma);
    }
    
    public Node produtoVetorial( Node t ){
        Node ans = new Node( ( this.y * t.z - this.z * t.y ), (this.z * t.x - this.x * t.z), ( this.x * t.y - this.y * t.x ) );
        return ans;
    }
    
    public double produtoEscalar( Node t ){
        return (this.x*t.x)+(this.y*t.y)+(this.z*t.z);
    }
    
    public Node inverterPonto( ){
        return new Node( -this.x, -this.y, -this.z );
    }
    
    public Node subtrairPonto( Node sub ){
        return new Node( this.x - sub.x, this.y - sub.y, this.z - sub.z );
    }
    
    public Node calculaSRUSRC( Node u, Node v, Node n, double VRPu, double VRPv, double VRPn ){
        Node ans = new Node( u.x * this.x + u.y * this.y + u.z * this.z + VRPu,
                             v.x * this.x + v.y * this.y + v.z * this.z + VRPv,
                             n.x * this.x + n.y * this.y + n.z * this.z + VRPn  );
        return ans;
    }
    
    public Node calculaSRCPers( double zvp, double zprp, double dp ){
        Node ans = new Node( this.x,
                             this.y,
                             (-zvp/dp)*this.z+zvp*(zprp/dp));
        double h = (-1/dp) * this.z + (zprp/dp);
        ans.setX( ans.x / h );
        ans.setY( ans.y / h );
        ans.setZ( ans.z / h );
        return ans;
    }
    
    public Node ConversaoSRUSRC_Ponto( Node localVRP, Node localP ){
        Node n = new Node(localVRP.x - localP.x, localVRP.y - localP.y, localVRP.z - localP.z);
        n.dividirPorNorma();
        Node v = new Node(0 - (n.y * n.x), 1 - (n.y * n.y), 0 - (n.y * n.z));
        v.dividirPorNorma();
        Node u = v.produtoVetorial(n);

        double localVRPn = n.produtoEscalar(localVRP.inverterPonto());
        double localVRPv = v.produtoEscalar(localVRP.inverterPonto());
        double localVRPu = u.produtoEscalar(localVRP.inverterPonto());
        
        return new Node( this.calculaSRUSRC(u, v, n, localVRPu, localVRPv, localVRPn) );
    }
    
//    public 
    
}
