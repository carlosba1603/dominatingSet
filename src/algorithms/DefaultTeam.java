package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DefaultTeam {
	
	private final double OPTIMAL = 1.5;
	
	
  public ArrayList<Point> calculDominatingSet(ArrayList<Point> points, int edgeThreshold) {

    
    ArrayList<Point> solution = new ArrayList<>();
    ArrayList<Point> rest = (ArrayList<Point>) points.clone();
    
    /*while( !rest.isEmpty() ) {
    	
    	Point highest = rest.get(0);
    	
    	for( Point p : rest ) {
    		if( getDegree( p, rest, edgeThreshold ) > getDegree( highest, rest, edgeThreshold ) ) {
    			highest = p;
    		}
    	}
    	
    	solution.add( highest );
    	rest.remove( highest );
    	rest.removeAll( getNeighbours( highest, rest, edgeThreshold ) );
    	
    }*/
    
    
    solution = this.readFromFile("output_K3_0.points");
    
    int solutionSize = 0;
    
    do {
        
    	solutionSize = solution.size();
    	
    	solution = improveK2( solution, points,  edgeThreshold );
    	
    	System.out.println("Loop: "+solution.size() );
    	
    	this.saveToFile("output_K1_", solution);
    	
    }while( solutionSize > solution.size() );
    
    return solution;
  }
  
  private ArrayList<Point> improveK1( ArrayList<Point> solution, ArrayList<Point> points, int edgeThreshold ){
	  
	  for( int i = 0; i < solution.size() ; i++ ) {
		  
		  Point a = solution.get(i);
				  
		  ArrayList<Point> attempt = (ArrayList<Point>) solution.clone();
		  attempt.remove(a);
		  
			  
		  if( isValidSolution( attempt, points, edgeThreshold ) ) {
			  return attempt;
		  }  
				 
	  }	
	  
	  return solution;				
	  
  }
  
  
  private ArrayList<Point> improveK2( ArrayList<Point> solution, ArrayList<Point> points, int edgeThreshold ){
	  
	
	  for( int i = 0; i < solution.size() ; i++ ) {
		  
		  Point a = solution.get(i);
				  
		  for( int j = i+1; j < solution.size() ; j++ ) {
			  
			  Point b = solution.get(j);

					  
			  if( a.distance(b) >= OPTIMAL * edgeThreshold  ) {
				  continue;
			  }
			  
			  Point median = new Point( (a.x+b.x)/2, (a.y+b.y)/2 );
			  
			  ArrayList<Point> rest = (ArrayList<Point>) points.clone();//this.getNeighbours(median, points, edgeThreshold);
			  
			  rest.add( a );
			  rest.add( b );

			  for( Point x : rest ){
				  
				  if( x.distance( median ) <= edgeThreshold ) {
					  ArrayList<Point> attempt = (ArrayList<Point>) solution.clone();
					  
					  attempt.remove(a);
					  attempt.remove(b);
					  
					  attempt.add(x);
					  
					  if( isValidSolution( attempt, points, edgeThreshold ) ) {
						  return attempt;
					  }				 
				  }
			  }
				 
		  }	 
				 
	  }	
	  
	  return solution;				
	  
  }
  
  private ArrayList<Point> improveK3( ArrayList<Point> solution, ArrayList<Point> points, int edgeThreshold ){
	  
		
	  for( int i = 0; i < solution.size() ; i++ ) {
		  
		  Point a = solution.get(i);
				  
		  for( int j = i+1; j < solution.size() ; j++ ) {
			  
			  Point b = solution.get(j);

			  for( int k = j+1; k < solution.size() ; k++ ) {
					
				  Point c = solution.get(k);
				  
				  if( a.distance(a) > OPTIMAL * edgeThreshold &&
				      a.distance(b) > OPTIMAL * edgeThreshold ) {
					  continue;
				  }
				  
				  Point median = getMedian( a, b, c );
				  
				  ArrayList<Point> rest = this.getNeighbours(median, points, edgeThreshold);
				  
				  rest.add( a );
				  rest.add( b );
				  rest.add( c );
	
				  for( int ii = 0; ii < rest.size(); ii++ ) {
					  
					  Point x = rest.get(ii);
					  
					  for( int jj = ii+1; jj < rest.size(); jj++ ) {
						  
						  Point y = rest.get(jj);
					  
						  if( i % 5 == 0 && j % 10 == 0 && k % 15 == 0 && ii % 20 == 0 ) {
							  System.out.println(" i: "+i+" j: "+j+" k: "+k+" ii: "+ii+" jj: "+jj);
						  }
						  
						  if( x.distance( median ) <= edgeThreshold && y.distance( median ) <= edgeThreshold ) {
							  ArrayList<Point> attempt = (ArrayList<Point>) solution.clone();
							  
							  attempt.remove(a);
							  attempt.remove(b);
							  attempt.remove(c);
							  
							  attempt.add(x);
							  attempt.add(y);
							  
							  if( isValidSolution( attempt, points, edgeThreshold ) ) {
								  return attempt;
							  }				 
						  }
					  }
				  }
			  }
				 
		  }	 
				 
	  }	
	  
	  return solution;				
	  
  }
  
  private Point getMedian( Point a, Point b ) {
	  return new Point( (a.x+b.x)/2, (a.y+b.y)/2 );
  }
  
  private Point getMedian( Point a, Point b, Point c ) {
	  return new Point( (a.x+b.x+c.x)/3, (a.y+b.y+c.y)/3 );
  }
  
/*private ArrayList<Point> improveK3( ArrayList<Point> solution, ArrayList<Point> points, int edgeThreshold ){
	  
		
			for( int i = 0; i < solution.size() ; i++ ) {
			  
			  Point a = solution.get(i);
					  
			  for( int j = i+1; j < solution.size() ; j++ ) {
				  
				  Point b = solution.get(j);
	
				  int randomIndex = (int)(Math.random()*HIGH_OPTIMAL);
	
				  for( int l = j+1; l < solution.size() ; l++ ) {
						
						Point c = solution.get(l);
						
						  
				  if( a.distance(b) > OPTIMAL * edgeThreshold &&
				      a.distance(c) > OPTIMAL * edgeThreshold &&
				      a.distance(c) > 10 * edgeThreshold  ) {
					  continue;
				  }
				  
				  points.add( a );
				  points.add( b );
				  points.add( c );
				  
				  Point centroid = new Point( (a.x+b.x)/2, (a.y+b.y)/2 );
	
				  for( int k = 0; k < points.size(); k++ ) {
				  
					  Point x = points.get(k);
					  
					  int randomIndex2 = (int)(Math.random()*LOW_OPTIMAL);
					  
					  if( x.distance( median ) < 3 * edgeThreshold ) {
						  ArrayList<Point> attempt = (ArrayList<Point>) solution.clone();
						  
						  attempt.remove(a);
						  attempt.remove(b);
						  
						  attempt.add(x);
						  
						  if( isValidSolution( attempt, points, edgeThreshold ) ) {
							  return attempt;
						  }				 
					  }
				  }
					 
			  }
			}
				 
	  }	
	  
	  return solution;				
	  
  }*/
  
  private boolean isValidSolution( ArrayList<Point> solution, ArrayList<Point> points, int edgeThreshold ) {
	  
	  ArrayList<Point> rest = (ArrayList<Point>) points.clone();
	  
	  for( Point p : solution ) {
		  
		  rest.remove( p );
		  rest.removeAll( getNeighbours( p, rest, edgeThreshold ) );
		  
	  }
	  
	  if( !rest.isEmpty() )
		  return false;
	  
	  return true;
	  
  }
  
  private ArrayList<Point> getNeighbours( Point origin, ArrayList<Point> points, int edgeThreshold ) {
	  
	//ArrayList<Point> neighbours = new ArrayList<Points>();
	  
	ArrayList<Point> neighbours = (ArrayList<Point>) points.stream()
														   .filter( p -> !origin.equals(p) && origin.distance(p) <= edgeThreshold )
														   .collect( Collectors.toList() );
	
	/*  for( Point p : points ) {
		  if( !origin.equals(p) && origin.distance(p) < edgeThreshold ) {
			  neighbours.add(p);
		  }
	  }*/
		  
	  return neighbours;
	  
  }
  
  private int getDegree( Point origin, ArrayList<Point> points, int edgeThreshold ) {
	  
	  /*points.stream()
	  		.map( p -> ( !origin.equals(p) && origin.distance(p) < threshold ) ? 1 : 0 )
	  		.reduce( (x,r) -> x+r )
	  		.orElse(-1);*/
	  
	  //Better for big data
	  int degree = points.stream()
		.reduce( 0, (x,p) -> x + ( ( !origin.equals(p) && origin.distance(p) <= edgeThreshold ) ? 1 : 0  ) , Integer::sum  );
	  
	  /*for( Point p : points ) {
		  if( !origin.equals(p) && origin.distance(p) < threshold ) {
			  degree++;
		  }
	  }*/
		  
	  return degree;
	  
  }
  
  //FILE PRINTER
  private void saveToFile(String filename,ArrayList<Point> result){
    int index=0;
    try {
      while(true){
        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(filename+Integer.toString(index)+".points")));
        try {
          input.close();
        } catch (IOException e) {
          System.err.println("I/O exception: unable to close "+filename+Integer.toString(index)+".points");
        }
        index++;
      }
    } catch (FileNotFoundException e) {
      printToFile(filename+Integer.toString(index)+".points",result);
    }
  }
  private void printToFile(String filename,ArrayList<Point> points){
    try {
      PrintStream output = new PrintStream(new FileOutputStream(filename));
      int x,y;
      for (Point p:points) output.println(Integer.toString((int)p.getX())+" "+Integer.toString((int)p.getY()));
      output.close();
    } catch (FileNotFoundException e) {
      System.err.println("I/O exception: unable to create "+filename);
    }
  }

  //FILE LOADER
  private ArrayList<Point> readFromFile(String filename) {
    String line;
    String[] coordinates;
    ArrayList<Point> points=new ArrayList<Point>();
    try {
      BufferedReader input = new BufferedReader(
          new InputStreamReader(new FileInputStream(filename))
          );
      try {
        while ((line=input.readLine())!=null) {
          coordinates=line.split("\\s+");
          points.add(new Point(Integer.parseInt(coordinates[0]),
                Integer.parseInt(coordinates[1])));
        }
      } catch (IOException e) {
        System.err.println("Exception: interrupted I/O.");
      } finally {
        try {
          input.close();
        } catch (IOException e) {
          System.err.println("I/O exception: unable to close "+filename);
        }
      }
    } catch (FileNotFoundException e) {
      System.err.println("Input file not found.");
    }
    return points;
  }
}
