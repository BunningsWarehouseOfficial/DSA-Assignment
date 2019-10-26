import java.io.*;

//TODO rigorous file input testing (medium-low priority)

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
                            if (!name1.equals(name2))
                            {
                                network.addEdge(name1, name2);
                            }
                            else
                            {
                                throw new IllegalArgumentException("Error: '" +
                                    name1 + "' can't follow themselves");
                            }
                        }
                        else
                        {
                            throw new IllegalArgumentException("Error: Could " +
                                "not find '" + name1 + "'");
                        }
                    }
                    else
                    {
                        throw new IllegalArgumentException("Error: Could " +
                            "not find '" + name2 + "'");
                    }
                }
                else
                { //Adding a new person
                    if (network.hasVertex(line.trim()))
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

    public static void loadEvents(String filename, Network network)
    {
        FileInputStream fileStrm = null;
        InputStreamReader rdr;
        BufferedReader bufRdr;
        String line;
        String[] lineArray;
        Person newPerson;

        try
        {
            fileStrm = new FileInputStream(filename);
            rdr = new InputStreamReader(fileStrm);
            bufRdr = new BufferedReader(rdr);

            line = bufRdr.readLine(); //Read initial line
            while (line != null)
            { //Using trim to remove excess whitespace
                if (line.contains(":"))
                { //Adding a follower
                    String name1, name2;
                    lineArray = line.split(":");
                    Person newNode;

                    if (lineArray[0].equals("A") && lineArray.length == 2)
                    { //Add a new person/node
                        name1 = lineArray[1].trim();
                        if (!network.hasVertex(name1))
                        {
                            newNode = new Person(name1);
                            network.addEvent(newNode, 'A');
                        }
                        else
                        {
                            throw new IllegalArgumentException("Error: '" +
                                name1 + "' is already in the network");
                        }
                    }
                    else if (lineArray[0].equals("R") && lineArray.length == 2)
                    { //Remove a person/node
                        name1 = lineArray[1].trim();
                        if (network.hasVertex(name1))
                        {
                            network.addEvent(name1, 'R');
                        }
                        else
                        {
                            throw new IllegalArgumentException("Error: '" +
                                name1 + "' isn't in the network, it can't be " +
                                "removed");
                        }
                    }
                    else if (lineArray[0].equals("F") && lineArray.length == 3)
                    { //Add a new follow/edge
                        name1 = lineArray[1].trim();
                        name2 = lineArray[2].trim();
                        if (network.hasVertex(name1))
                        {
                            if (network.hasVertex(name2))
                            {
                                if (!name1.equals(name2))
                                {
                                    network.addEvent(name1, name2, 'F');
                                }
                                else
                                {
                                    throw new IllegalArgumentException("Error:"
                                        + " '" + name1 + "' can't follow " +
                                        "themselves");
                                }
                            }
                            else
                            {
                                throw new IllegalArgumentException("Error: " +
                                        "Could not find '" + name1 + "'");
                            }
                        }
                    }
                    else if (lineArray[0].equals("U") && lineArray.length == 3)
                    { //Remove a follow/edge
                        name1 = lineArray[1].trim();
                        name2 = lineArray[2].trim();
                        if (network.hasEdge(name1, name2))
                        {
                            network.addEvent(name1, name2, 'U');
                        }
                        else
                        {
                            throw new IllegalArgumentException("Error: '" +
                                name2 + "' doesn't follow '" + name1 + "', " +
                                "can't unfollow");
                        }
                    }
                    else if (lineArray[0].equals("P") && lineArray.length > 2)
                    { //Add a new post
                        Post newPost;
                        String poster, text;
                        poster = lineArray[1].trim();
                        text = lineArray[2].trim();

                        if (network.hasVertex(poster))
                        {
                            if (lineArray.length == 3)
                            { //No Clickbait
                                newPost = new Post(poster, text, 1.0);
                                network.addEvent(newPost, 'P');
                            }
                            else if (lineArray.length == 4)
                            { //Clickbait
                                int clickbait = Integer.parseInt(lineArray[3]);
                                newPost = new Post(poster, text, clickbait);
                                network.addEvent(newPost, 'P');
                            }
                        }
                        else
                        {
                            throw new IllegalArgumentException("Error: Could " +
                                "not find '" + poster + "'");
                        }
                    }
                    else
                    {
                        throw new IllegalArgumentException("Error: Invalid " +
                            "line format");
                    }
                }
                else
                { //Checking for a lack of colons
                    throw new IllegalArgumentException("Error: Invalid line " +
                        "format, no ':' found");
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
    }

    public static void saveNetwork(String filename, Network network)
    { //TODO saving
        throw new UnsupportedOperationException("Error: Not supported");
    }
}
