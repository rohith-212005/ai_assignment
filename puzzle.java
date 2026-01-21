import java.util.*;


class node
{
    int state[][];
    int g;
    int h;
    int f;
    node parent;

    node(int s[][] , int g , node p)
    {
        state = s;
        this.g = g;
        parent = p;
        h = misplacedtiles(s);
        f = g + h;
    }

    
    static int misplacedtiles(int s[][])
    {
        int goal[][] = {
            {1,2,3},{4,5,6},{7,8,0}
        };

        int count = 0;

        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(s[i][j] != 0 && s[i][j] != goal[i][j])
                {
                    count++;
                }
            }
        }
        return count;
    }
}

public class puzzle
{
    
    static int start[][] = {
        {1,2,3},{4,0,6},{7,5,8}
    };

    
    static int goal[][] = {
        {1,2,3},{4,5,6},{7,8,0}
    };

    static boolean isgoal(int s[][])
    {
        return Arrays.deepEquals(s , goal);
    }

    static int[][] copy(int s[][])
    {
        int c[][] = new int[3][3];
        for(int i=0;i<3;i++)
        {
            c[i] = s[i].clone();
        }
        return c;
    }

    
    static ArrayList<int[][]> nextstates(int s[][])
    {
        ArrayList<int[][]> list = new ArrayList<>();

        int x = 0 , y = 0;

        
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(s[i][j] == 0)
                {
                    x = i;
                    y = j;
                }
            }
        }

        int moves[][] = {
            {-1,0},{1,0},{0,-1},{0,1}
        };

        for(int i=0;i<4;i++)
        {
            int a = x + moves[i][0];
            int b = y + moves[i][1];

            if(a >= 0 && b >= 0 && a < 3 && b < 3)
            {
                int next[][] = copy(s);
                next[x][y] = next[a][b];
                next[a][b] = 0;
                list.add(next);
            }
        }
        return list;
    }

    static void printstate(int s[][])
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                System.out.print(s[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void printpath(node n)
    {
        Stack<node> st = new Stack<>();

        while(n != null)
        {
            st.push(n);
            n = n.parent;
        }

        int step = 0;

        while(!st.empty())
        {
            node cur = st.pop();
            System.out.println("\nstep " + step++);
            printstate(cur.state);
            System.out.println("g = " + cur.g + "  h = " + cur.h + "  f = " + cur.f);
        }
    }

    public static void main(String args[])
    {
        PriorityQueue<node> open = new PriorityQueue<>(
            (a , b) -> a.f - b.f
        );

        ArrayList<String> closed = new ArrayList<>();

        open.add(new node(start , 0 , null));

        while(!open.isEmpty())
        {
            node cur = open.poll();

            if(isgoal(cur.state))
            {
                System.out.println("goal reached");
                printpath(cur);
                return;
            }

            closed.add(Arrays.deepToString(cur.state));

            ArrayList<int[][]> children = nextstates(cur.state);

            for(int i=0;i<children.size();i++)
            {
                int child[][] = children.get(i);

                if(!closed.contains(Arrays.deepToString(child)))
                {
                    open.add(new node(child , cur.g + 1 , cur));
                }
            }
        }

        System.out.println("no solution");
    }
}
