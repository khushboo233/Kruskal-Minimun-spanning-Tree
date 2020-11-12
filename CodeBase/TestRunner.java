import java.util.ArrayList;
import java.util.List;

/**
 * INPUT:
 *
 *  14	10	10	17	 2
 *   6	 3	 8	15	 7
 *   5	18	 9	14	19
 *
 * OUTPUT:
 *  135, 51
 *
 * ORIGINAL GRAPH:
 *
 *   * - 4-  * -0-  * -7-  * -15-  *
 *   |       |      |      |       |
 *   8       7      2      2       5
 *   |       |      |      |       |
 *   * - 3-  * -5-  * -7-  * - 8-  *
 *   |       |      |      |       |
 *   1      15      1      1      12
 *   |       |      |      |       |
 *   * -13-  * -9-  * -5-  * - 5-  *
 *
 * PRUNED GRAPH:
 *
 *   * - 4-  * -0-  *      *       *
 *                  |      |       |
 *                  2      2       5
 *                  |      |       |
 *   * - 3-  * -5-  *      * - 8-  *
 *   |              |      |       
 *   1              1      1      
 *   |              |      |       
 *   *       * -9-  * -5-  * - 5-  *
 */


public class TestRunner {
    public static void main(String[] args){

        Program2 prog2 = new Program2();

        // Sample image using the example in the assignment handout

        // Initialize the image
        final int [][] image0 = new int[][] {
                    { 5,  4,  3,  2},
                    { 3, 10, 11,  1},
                    { 1, 12, 10,  0},
		    { 0,  3,  2,  1}
                    };
        // Set the intensity graph weight
        final int intensityWeight0 = 91;
        // Set the pruned graph weight
        final int prunedWeight0 = 23;
		
	//Sample image using the example in the comments above
		
	// Initialize the image
        final int [][] image1 = new int[][] {
                    { 14, 10, 10, 17,  2},
                    {  6,  3,  8, 15,  7},
                    {  5, 18,  9, 14, 19},
                    };
        // Set the intensity graph weight
        final int intensityWeight1 = 135;
        // Set the pruned graph weight
        final int prunedWeight1 = 51;
		



	// NEW TEST CASES BELOW

	// Initialize the image2 - All unique edge weights
        final int [][] image2 = new int[][] {
		{  2,  2,  3,  5},
		{ 12, 15,  8, 14},
		{  8, 23, 34, 46},
		{ 54, 37, 57, 73},
		};

	// Set the intensity graph weight
        final int intensityWeight2 = 327;
        // Set the pruned graph weight
        final int prunedWeight2 = 126;



	// Initialize the image3 - Large integer edge weight
        final int [][] image3 = new int[][] {
		{  100, 200,  400, 1000},
		{    0, 900,  800,  100},
		{ 1100, 100,  500,  700},
		{  400, 200, 1000,  100},
		};

	// Set the intensity graph weight
        final int intensityWeight3 = 12900;
        // Set the pruned graph weight
        final int prunedWeight3 = 5100;



	// Initialize the image4 - All edge having same weight 
        final int [][] image4 = new int[][] {
		{1, 2, 1, 2, 3},
		{2, 1, 2, 1, 2},
		{3, 2, 3, 2, 1},
		{2, 3, 2, 1, 0},
		{1, 2, 1, 0, 1},
		};

	// Set the intensity graph weight
        final int intensityWeight4 = 40;
        // Set the pruned graph weight
        final int prunedWeight4 = 24;



	// Initialize the image5 - Large matrix of pixels
        final int [][] image5 = new int[][] {
		{  0, 5, 10,  8, 6,   1},
		{  4, 1,  0,  9, 7,   6},
		{  0, 6, 10,  6, 0,   9},
		{ 11, 7,  2,  4, 3,   2},
		{  0, 3,  1,  7, 0,   4},
		{  1, 5,  9, 10, 16, 15},
		};

	// Set the intensity graph weight
        final int intensityWeight5 = 271;
        // Set the pruned graph weight
        final int prunedWeight5 = 91;



		
        System.out.println("----- Running Tests ------");

        List<TestCase> tests = new ArrayList<TestCase>();

        // Create and add test case based on specified image and intensity and pruned graph weights
        tests.add(new TestCase(image0, intensityWeight0, prunedWeight0));
        tests.add(new TestCase(image1, intensityWeight1, prunedWeight1));

	//NEW TEST CASE OBJECTS BELOW

	tests.add(new TestCase(image2, intensityWeight2, prunedWeight2));
        tests.add(new TestCase(image3, intensityWeight3, prunedWeight3));
	tests.add(new TestCase(image4, intensityWeight4, prunedWeight4));
        tests.add(new TestCase(image5, intensityWeight5, prunedWeight5));
	

		
        // Compare your Program2 graphs against the solutions specified above

        int totalPoints = 0;
        for(TestCase test : tests){
            long start = System.currentTimeMillis();

            try{
                int intensityResult = prog2.constructIntensityGraph(test.getImage());
                int prunedResult = prog2.constructPrunedGraph(test.getImage());
                //int prunedResult = 0; // uncomment and comment above to hack and only run intensity creation
                long end = System.currentTimeMillis();
                long duration = end-start;
                totalPoints += test.check(intensityResult, prunedResult);
                System.out.println("Duration in ms: " +  duration + "\n");
            } catch(Exception e){
                System.out.println("Test failed due to Exception");
                e.printStackTrace();
            } catch(OutOfMemoryError me){
                System.out.println("Test failed due to running out of memory");
            }
        }
        System.out.println("Total Points Earned: " + totalPoints + "/32");
    }
}
