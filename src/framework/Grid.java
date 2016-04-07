/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

import java.util.ArrayList;
import java.util.TreeMap;
import Entity.Player;
public class Grid {
    float xmin, xmax, zmin, zmax, S;
    TreeMap<Integer, TreeMap<Integer, ArrayList<Player>>> G = new TreeMap<>();
    public Grid(float xmin, float xmax, float zmin, float zmax, float S)
    {
        this.xmin = xmin;
        this.xmax = xmax;
        this.zmin = zmax;
        this.zmax = zmax;
        this.S = S; // size of each cell
    }
    
    void put(float x, float z, Player obj, int[] oldcell)
    {
        int i = (int)((x-this.xmin)/this.S);
        int j = (int)((x-this.xmin)/this.S); //???
        if(oldcell != null && i == oldcell[0] && j == oldcell[1])
            return; //not returning anything
        if (oldcell != null)
        {
            //remove obj from G[oldcell[0]][oldcell[1]
        }
        if(!G.containsKey(i))
            this.G.put(i, new TreeMap<>());
        if(!this.G.get(i).containsKey(j))
            //this.G.get(i).put(j, new TreeMap<>());    //???
        this.G.get(i).get(j).add(obj);
        //return new int[]{i,j};                        //???
    }
    
    ArrayList<int[]> getProximate(int[] cell)
    {
        ArrayList<int[]> L = new ArrayList<>();
        for (int i = cell[0]-1; i <= cell[0]+1; ++i)
        {
            for(int j=cell[1]-1; j <= cell[1]+1; j++)
            {
                if(this.G.containsKey(i) && this.G.get(i).containsKey(j))
                    L.add(new int[]{i,j});
            }
        }
        return L;
    }
}
