import java.io.*;
import java.util.*;

public class IO
{
    public static Network loadNetwork(String filename)
    {
        FileInputStream fileStrm = null;
        InputStreamReader rdr;
        BufferedReader bufRdr;
        String line;
        String[] lineArray;
        Person newPerson;
        Network network = new Network();

        try
        {
            fileStrm = new FileInputStream(filename);
            rdr = new InputStreamReader(fileStrm);
            bufRdr = new BufferedReader(rdr);

            line = bufRdr.readLine(); //Read initial line
            while (line != null)
            {
                if (line.contains(":"))
                { //Adding a follower
                    String name1, name2;
                    lineArray = line.split(":");
                    //Accounting for any stray whitespace in input file
                    name1 = lineArray[0].trim();
                    name2 = lineArray[1].trim();

                    if (network.hasVertex(name1))
                    {
                        if (network.hasVertex(name2))
                        {
                            network.addEdge(name1, name2);
                        }
                        else
                        {
                            throw new IllegalArgumentException("Error: Could " +
                                "not find " + name1);
                        }
                    }
                    else
                    {
                        throw new IllegalArgumentException("Error: Could " +
                                "not find " + name2);
                    }
                }
                else
                { //Adding a new person
                    if (network.hasVertex(line))
                    {
                        throw new IllegalArgumentException("Error: " + line +
                                " is already in the network");
                    }
                    else
                    {
                        String name = line.trim();
                        newPerson = new Person(name);
                        network.addVertex(name, newPerson);
                    }
                }
                line = bufRdr.readLine(); //Next line
            }
            fileStrm.close();
        }
        catch (IOException e1)
        {
            if (fileStrm != null)
            {
                try
                {
                    fileStrm.close();
                }
                catch (IOException e2) {}
            }
            System.out.println("Error: Couldn't load " + e1.getMessage());
        }
        return network;
    }

    public static void saveNetwork(String filename, Network network)
    {
        throw new UnsupportedOperationException("Error: Not supported");
    }
}
