import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class aMAZEing {
    
    //innerclass
    static class Coordinate
    {
        int x;
        int y;

        public Coordinate(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("(%d , %d)", x,y);
        }
    }
    
        //inner class
   static class verticeQ {
            Coordinate pt;
            int dist;

            public verticeQ(Coordinate pt, int dist)
            {
                this.pt = pt;
                this.dist = dist;
            }
   };

        static boolean accepted(int row, int col, int lineRow, int lineCol)
        {
            return (row >= 0) && (row < lineRow) &&
                    (col >= 0) && (col < lineCol);
        }

        static int rowNum[] = {-1, 0, 0, 1};
        static int colNum[] = {0, -1, 1, 0};

        static int BFS(int mat[][], Coordinate src,
                       Coordinate dest, int lineRow, int lineCol)
        {
            lineCol = lineCol +1;
            lineRow = lineRow +1;

            if (mat[src.x][src.y] != 1 ||
                    mat[dest.x][dest.y] != 1)
                return -1;

            boolean [][]visited = new boolean[lineRow][lineCol];
            visited[src.x][src.y] = true;
            Queue<verticeQ> q = new LinkedList<>();
            verticeQ s = new verticeQ(src, 0);
            q.add(s);
            while (!q.isEmpty())
            {
                verticeQ curr = q.peek();
                Coordinate thepoint = curr.pt;
                if (thepoint.x == dest.x && thepoint.y == dest.y)
                    return curr.dist;
                q.remove();
                //4 neighbor max
                for (int i = 0; i < 4; i++)
                {
                    int row = thepoint.x + rowNum[i];
                    int col = thepoint.y + colNum[i];
                    if (accepted(row, col, lineRow, lineCol) &&
                            mat[row][col] == 1 &&
                            !visited[row][col])
                    {
                        visited[row][col] = true;
                        verticeQ Adjcell = new verticeQ
                                (new Coordinate(row, col),
                                        curr.dist + 1 );
                        q.add(Adjcell);
                        System.out.print("("+row+","+col+") -> ");
                    }
                }
            }
            return -1;
        }

        // Driver Code
        public static void main(String[] args) throws IOException {
            Scanner sc = new Scanner(System.in);
            Scanner scanner;
            String fileName;
            FileReader fr;

            System.out.println("Name of file to read in (including file extension)");

            fileName = sc.nextLine();
            fr = new FileReader(fileName);
            scanner = new Scanner(fr);

            int[][] matrix;
            int lineCounter = 0;
            int arrCol = 0;

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] values = line.split("");
                arrCol = values.length;
                lineCounter++;
            }
            scanner.close();
            fr.close();

            FileReader fr1 = new FileReader(fileName);
            scanner = new Scanner(fr1);
            matrix = new int[lineCounter][arrCol];
            lineCounter=0;

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] values = line.split("");
                for(int i=0; i < values.length; i++){
                    matrix[lineCounter][i]=Integer.parseInt(values[i]);
                }
                lineCounter++;
            }

            scanner.close();

            for(int i=0;i< matrix.length; i++){
                for(int j=0;j<matrix[1].length; j++){
                    System.out.print(matrix[i][j]+" ");
                }
                System.out.println();
            }

            arrCol = arrCol - 1;
            lineCounter = lineCounter -1;

            Coordinate source = new Coordinate(0, 0);
            Coordinate dest = new Coordinate(lineCounter, arrCol);

            int distance = BFS(matrix, source, dest, lineCounter, arrCol);

            if (distance != -1)
                System.out.println("\nShortest Path in the maze(BFS): " + distance);
            else
                System.out.println("Shortest Path doesn't exist");
        }
    }

