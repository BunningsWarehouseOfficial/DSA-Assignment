import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

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
                        newNode = new Person(name1);
                        network.addEvent(newNode, 'A');
                    }
                    else if (lineArray[0].equals("R") && lineArray.length == 2)
                    { //Remove a person/node
                        name1 = lineArray[1].trim();
                        network.addEvent(name1, 'R');
                    }
                    else if (lineArray[0].equals("F") && lineArray.length == 3)
                    { //Add a new follow/edge
                        name1 = lineArray[1].trim();
                        name2 = lineArray[2].trim();
                        network.addEvent(name1, name2, 'F');
                    }
                    else if (lineArray[0].equals("U") && lineArray.length == 3)
                    { //Remove a follow/edge
                        name1 = lineArray[1].trim();
                        name2 = lineArray[2].trim();
                        network.addEvent(name1, name2, 'F');
                    }
                    else if (lineArray[0].equals("P") && lineArray.length > 2)
                    { //Add a new post
                        Post newPost;
                        String poster, text;
                        poster = lineArray[1].trim();
                        text = lineArray[2].trim();

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

 /* Save the network in same format as when loading a network */
    public static void saveNetwork(String filename, Network network)
    {
        FileOutputStream fileStrm = null;
        PrintWriter pw;
        DSALinkedList names;
        DSAQueue followers;
        String name, follower;

        try
        {
            fileStrm = new FileOutputStream(filename);
            pw = new PrintWriter(fileStrm);

            //Writing the names of the people in network
            names = network.getLabels();
            for (Object o : names)
            {
                name = (String)o;
                pw.println(name);
            }

            //Writing the follower relationships between people in network
            for (Object o : names)
            {
                name = (String)o;
                followers = network.getAdjacentValues(name);
                while (!followers.isEmpty())
                {
                    follower = ((Person)followers.dequeue()).getName();
                    pw.println(name + ":" + follower);
                }
            }
            pw.close();
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
            System.out.println("Error: Couldn't save," + e1.getMessage());
        }
    }

 /* Create a new logfile to save information about the simulation */
    public static String newLogfile(String networkFile, String eventFile,
                                    double probLike, double probFollow)
    {
        FileOutputStream fileStrm = null;
        PrintWriter pw;
        String filename;
        DateTimeFormatter logtime =
                          DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        LocalDateTime time = LocalDateTime.now();
        filename = "log_" + logtime.format(time) + "_UTC";

        try
        {
            fileStrm = new FileOutputStream(filename);
            pw = new PrintWriter(fileStrm);

            pw.println("\n\nNetwork File: " + networkFile);
            pw.println("Event File: " + eventFile);
            pw.println("Prob. Like: " + probLike);
            pw.println("Prob. Follow: " + probFollow + "\n");
            pw.close();
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
            System.out.println("Error: Couldn't create log," + e1.getMessage());
            filename = null; //Used to alert rest of the program of failure
        }
        return filename;
    }

 /* Add new information from timestep to end of logfile */
    public static boolean appendLogFile(String filename, String text,
                                        int stepNum)
    {
        FileWriter fWriter = null;
        PrintWriter pw;
        boolean status = true;

        try
        {
            fWriter = new FileWriter(filename, true);
            pw = new PrintWriter(fWriter);

            if (stepNum != 0)
            { //Don't write this for final input to log
                pw.print("\nTime Step " + stepNum + "\n===============");
            }
            if (text.equals(""))
            {
                pw.print("\nNothing happened");
            }
            pw.println(text);
            pw.close();
        }
        catch (IOException e1)
        {
            if (fWriter != null)
            {
                try
                {
                    fWriter.close();
                }
                catch (IOException e2) {}
            }
            System.out.println("Error: Couldn't create log," + e1.getMessage());
            status = false;
        }
        return status;
    }
}
