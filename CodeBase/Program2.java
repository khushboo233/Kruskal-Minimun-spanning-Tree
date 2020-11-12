import java.util.*;

public class Program2 {

    public int constructIntensityGraph(int[][] image)
	{
		
		int sum = 0;
		int height = image.length;
		int breadth = image[0].length;
		
		// Aim of Intensity Graph is to calculate the edge weight of all edges in graph
		
		for (int i = 0 ; i < height ; i++ )						// Adds edge weight of all horizontal edges
		{
			for(int j = 0; j < breadth-1 ; j++)
			{
				sum += Math.abs(image[i][j] - image[i][j+1]);
			}
		}
		
		for(int i = 0 ; i < breadth ; i++)						// Adds edge weight of all vertical edges
		{
			for(int j = 0 ; j < height-1 ; j++)
			{
				sum += Math.abs(image[j][i] - image[j+1][i]);
			}
		}
	return sum;
    }

    public int constructPrunedGraph(int[][] image)
	{
		int pruned_weight = 0;
		int height = image.length;
		int breadth = image[0].length;
		
		ArrayList<Dist> list = new ArrayList<Dist>(); 		// Stores edge weight, and its corresponding 2 vertice coordinates
		
		int count1 = 0;
		for (int i = 0 ; i < height ; i++ )					// Row wise adding elements to ArrayList
		{
			for(int j = 0; j < breadth-1 ; j++)
			{
				list.add(new Dist(count1, count1+1, Math.abs(image[i][j] - image[i][j+1])));
				count1 += 1;
			}
			count1 += 1 ;
		}
		
		count1 = 0;
		int count2 = 0;
		for(int i = 0 ; i < breadth ; i++)					// Column wise adding elements to ArrayList
		{
			for(int j = 0 ; j < height-1 ; j++)
			{
				list.add(new Dist(count1, count1+breadth, Math.abs(image[j][i] - image[j+1][i])));
				count1 += breadth;
			}
			count1 = count2 + 1;
			count2 += 1;
		}
		
		Collections.sort(list);								// Sorts the edge weights
		
		HashMap<Integer, ArrayList<Integer>> hm = new HashMap<>();		// Creating hash map for Make_Set. 
		
		int k=0;
		for(int i = 0 ; i < height ; i++ )								// Make_set has started implementing
		{
			for(int j = 0 ; j < breadth ; j++ )
			{
				ArrayList<Integer> values = new ArrayList<>();
				values.add(k);
				values.add(k);
				hm.put(k, values);
				k++;
			}
		}
		
		int p;
		// Find_set started implementing
		for(p = 0 ; p < list.size() ; p++ )								// Takes edge from sorted list
		{
			int l,r,w;													// Variables
			int left_hash = 0, right_hash = 0;							// Stores unique number of each separated tree in big forest
			int len_l = 0 , len_r = 0 ;									
			l = list.get(p).get_L();									// Stores left vertice coordinate of choosen edge
			r = list.get(p).get_R();									// Stores right vertice coordinate of choosen edge
			
			int i = 0;
			for(i = 0 ; i < hm.size() ; i++ )							// Loops over whole hash map
			{
				
				int j = 0;
				for(j = 1 ; j < hm.get(i).size() ; j++ )				// Loop to find left vertice coordinate in hash map
				{
					if(hm.get(i).get(0)== -1)							// If index is -1 then break
					{
						break;
					}
					if(hm.get(i).get(j) == l)
					{
						len_l = hm.get(i).size();
						left_hash = hm.get(i).get(0);
						break;
					}
				}
			}
		
			for(i = 0 ; i < hm.size() ; i++ )							// Loops over whole hash map
			{
				int j = 0;
				for(j = 1 ; j < hm.get(i).size() ; j++ )				// Loop to find right vertice coordinate in hash map
				{
					if(hm.get(i).get(0)== -1)							// If index is -1 then break
					{
						break;
					}
					if((hm.get(i).get(j) == r))
					{			
						len_r = hm.get(i).size();
						right_hash= hm.get(i).get(0);
						break;
					}
				}
			}

			if( left_hash != right_hash )								// Check if each vertice belong to separate trees in big forest
			{
				pruned_weight += list.get(p).get_W();					// Adding edge weight to total pruned weight 
				
				// union of 2 disconnected graphs
				if (left_hash < right_hash)					// To avoid randomly merging trees - always larger index hash values are removed and added to smaller index hash values
				{
					int element[] = new int[len_r-1];
					
					for(i= 1 ; i < len_r ; i++ )
					{
						element[i-1] = (hm.get(right_hash).get(i));		// Storing values in temporary array
					}
					
					ArrayList<Integer> values = new ArrayList<>();
					values.add(-1);										// Updating hash index of that tree because all elements are removed
					hm.put(right_hash, values);
					
					for(i = 0 ; i < element.length ; i++ )
					{
						hm.get(left_hash).add(element[i]);				// Storing values to smaller hash index ArrayList
					}
					
				}
				else
				{
					int element[] = new int[len_l-1];
					
					for(i = 1 ; i < len_l ; i++ )
					{
						element[i-1] = (hm.get(left_hash).get(i));		// Storing values in temporary array
					}
					ArrayList<Integer> values = new ArrayList<>();
					values.add(-1);										// Updating hash index of that tree because all elements are removed
					hm.put(left_hash, values);
					
					for(i = 0 ; i < element.length ; i++ )
					{
						hm.get(right_hash).add(element[i]);				// Storing values to smaller hash index ArrayList
					}
				}
			}
		}
	
	return pruned_weight;												// Returning total edge weight of edges in Pruned graph 
    }
}

class Dist implements Comparable<Dist> 									// Comparator used to sort the ArrayList using weight values
{ 
    private int left; 
    private int right; 
    private int weight; 
  
    public Dist(int l, int r, int wt) 										// Constructor 
    { 
		this.left = l;
		this.right = r;
		this.weight = wt;
    } 
	
    public int compareTo(Dist d) 											// Method to sort distance by weight
    { 
        return this.weight - d.weight; 
    } 
  
    // Getter methods for accessing private data 
    public int get_L() 	{ return left; } 
    public int get_R()  {  return right; } 
    public int get_W()  {  return weight;  } 
} 