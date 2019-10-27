import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation{
    private boolean[] grid;
    private int n,top,bottom;
    private int openCount;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF full;
    private int index(int row,int col)
    {
        if(row<1 || col<1 || row>n || col>n) throw new IllegalArgumentException();
        return ((row-1)*n)+(col-1);
    }
    public Percolation(int n) {
        if(n<=0) throw new IllegalArgumentException();
        this.n=n;
        grid = new boolean[n*n];
        uf = new WeightedQuickUnionUF((n*n)+2);
        full = new WeightedQuickUnionUF((n*n)+1);
        top=n*n;
        bottom=(n*n)+1;
    }
    public void open(int row,int col){
        if(grid[index(row,col)]) return;
        grid[index(row,col)]=true;
        openCount++;
        if(row==1){
            uf.union(top,index(row,col));
            full.union(top,index(row,col));
        }
        if(row==n) uf.union(bottom,index(row,col));
        if(row>1 && isOpen(row-1,col)){
            uf.union(index(row,col),index(row-1,col));
            full.union(index(row,col),index(row-1,col));
        }
        if(row<n && isOpen(row+1,col)){
            uf.union(index(row,col),index(row+1,col));
            full.union(index(row,col),index(row+1,col));
        }
        if(col>1 && isOpen(row,col-1)){
            uf.union(index(row,col),index(row,col-1));
            full.union(index(row,col),index(row,col-1));
        }
        if(col<n && isOpen(row,col+1)){
            uf.union(index(row,col),index(row,col+1));
            full.union(index(row,col),index(row,col+1));
        }
    }
    public boolean isOpen(int row,int col){
        return grid[index(row,col)];
    }
    public boolean isFull(int row,int col){
        return full.connected(top,index(row,col));
    }
    public int numberOfOpenSites(){
        return openCount;
    }
    public boolean percolates(){
        return uf.connected(top,bottom);
    }
}