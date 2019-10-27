import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;
    public PercolationStats(int n,int trials){
        if(n<=0 || trials<=0) throw new IllegalArgumentException();
        double[] percolationThreshold = new double[trials];
        for(int i=0;i<trials;i++)
        {
            Percolation grid = new Percolation(n);
            while(!grid.percolates()) {
                int col;
                int row;
                do{
                    row =StdRandom.uniform(1,n+1);
                    col =StdRandom.uniform(1,n+1);
                }while(grid.isOpen(row, col));
                grid.open(row, col);
            }
            percolationThreshold[i]=(double) grid.numberOfOpenSites()/(double)(n*n);
        }
        mean=StdStats.mean(percolationThreshold);
        stddev=StdStats.stddev(percolationThreshold);
        double temp = (1.96 * stddev) / Math.sqrt(trials);
        confidenceHi=mean+ temp;
        confidenceLo=mean- temp;
    }
    public double mean(){
        return mean;
    }
    public double stddev(){
        return stddev;
    }
    public double confidenceLo(){
        return confidenceLo;
    }
    public double confidenceHi(){
        return confidenceHi;
    }
    public static void main(String[] args){
        //PercolationStats obj = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        PercolationStats obj = new PercolationStats(200,100);
        System.out.println("mean                    = "+obj.mean());
        System.out.println("stddev                  = "+obj.stddev());
        System.out.println("95% confidence interval = ["+obj.confidenceLo+","+obj.confidenceHi+"]");
    }
}
