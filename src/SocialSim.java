/* Author: Kristian Rados (19764285)
   Created: 23/10/2019
   Last Modified: 28/10/2019                                                  */

import java.util.*;
public class SocialSim
{
    public static void main(String[] args)
    {
        try
        {
            if (args.length == 5)
            { //Checking for simulation mode
                if (args[0].equals("-s"))
                {
                    String networkFile, eventFile;
                    double pLike, pFollow;
                    networkFile = args[1];
                    eventFile = args[2];
                    pLike = Double.parseDouble(args[3]);
                    pFollow = Double.parseDouble(args[4]);
                    if (pLike > 1 || pFollow > 1 || pLike < 0 || pFollow < 0)
                    {
                        throw new IllegalArgumentException("Probabilities " +
                            "must by >= 0 and <= 1");
                    }
                    else
                    {
                        simulation(networkFile, eventFile, pLike, pFollow);
                    }
                }
                else
                { //Checking for an incorrect command line argument
                    throw new IllegalArgumentException("Command line argument" +
                        "must either be the -i flag for the interactive" +
                        "testing environment or the -s flag for simulation " +
                        "mode");
                }
            }
            else if (args.length == 1)
            { //Checking for interactive mode
                if (args[0].equals("-i"))
                {
                    menu();
                }
                else
                { //Checking for an incorrect command line argument
                    throw new IllegalArgumentException("Command line argument" +
                        "must either be the -i flag for the interactive" +
                        "testing environment or the -s flag for simulation " +
                        "mode");
                }
            }
            else if (args.length == 0)
            { //No command line flags
                usageInfo();
            }
            else
            { //Checking for > 1 command line arguments
                throw new IllegalArgumentException("Invalid number of command" +
                    "line arguments, run program with the -i flag, the -s " +
                    "flag or no flags");
            }
        }
        catch (NumberFormatException e1)
        {
            System.out.println(e1.getMessage());
        }
        catch (IllegalArgumentException e2)
        {
            System.out.println("Error: " + e2.getMessage());
        }
        catch (Exception e3)
        {
            System.out.println(e3.getMessage());
        }
    }

 /* Runs simulation mode, outputting statistics to a file after each timestep */
    private static void simulation(String networkFile, String eventFile,
                                   double probLike, double probFollow)
    {
        Network n;
        String filename;

        try
        {   //Load the network
            n = IO.loadNetwork(networkFile);
            n.setProbLike(probLike);
            n.setProbFollow(probFollow);
            //Load events file
            IO.loadEvents(eventFile, n);

            filename = IO.newLogfile(networkFile, eventFile, probLike,
                                     probFollow);
            if (filename != null)
            {
                long startTime = System.nanoTime();
                boolean status = true;
                String logText;
                DSAQueue checkStale = new DSAQueue();
                DSAQueue textQueue = new DSAQueue();
                int stepNum = 1; //Always runs first time
                while (status == true && (n.getEventsCount() != 0 ||
                       n.getNPosts() != n.getNPostsStale() || stepNum == 1))
                {
                    timeStep(n, checkStale);
                    displayStats(n, textQueue);
                    logText = "";
                    while (!textQueue.isEmpty())
                    {
                        logText += (String)textQueue.dequeue();
                    }
                    logText += n.emptyTimeStepText();
                    status = IO.appendLogFile(filename, logText, stepNum);
                    stepNum++;
                }

                if (status == true)
                {
                    //Displaying popularity 'leaderboards' at end of logfile
                    logText = "";
                    displayPopular(n, textQueue);
                    while (!textQueue.isEmpty())
                    {
                        logText += textQueue.dequeue();
                    }
                    IO.appendLogFile(filename, logText, 0);
                    System.out.println("Successfully ran simulation");
                    long endTime = System.nanoTime();
                    System.out.println("Time Taken: " + (int)((double)(endTime
                                       - startTime) / 1000000.0) + "ms");
                }
                else
                { //In case of some file IO failure
                    System.out.println("Simulation failed");
                }
            }
        }
        catch (IllegalArgumentException e1)
        {
            System.out.println(e1.getMessage());
        }
        catch (IndexOutOfBoundsException e2)
        {
            System.out.println("Error: Invalid colon placement in file");
        }

    }

    /* Main menu for the interactive testing environment */
    private static void menu()
    {
        Scanner sc = new Scanner(System.in);
        Network network = new Network();
        DSAQueue checkStale = new DSAQueue();
        String filename;
        int cmd = -1;

        do
        {
            try
            {
                System.out.println("\n[1] Load Network\n[2] Load Events File" +
                    "\n[3] Set Probabilities \n[4] Node Operations" +
                    "\n[5] Edge Operations\n[6] New Post\n[7] Display Network" +
                    "\n[8] Display Statistics \n[9] Update (Run Timestep)" +
                    "\n[10] Save Network\n[0] Exit\n");
                cmd = Integer.parseInt(sc.nextLine());
                switch (cmd)
                {
                    case 1: //Load Network
                        double pLike, pFollow;
                        //Carrying over probabilities to keep them consistent
                        pLike = network.getProbLike();
                        pFollow = network.getProbFollow();

                        System.out.print("Filename: ");
                        filename = sc.nextLine();
                        try
                        {
                            network = IO.loadNetwork(filename);
                            network.setProbLike(pLike);
                            network.setProbFollow(pFollow);
                        }
                        catch (IllegalArgumentException e1)
                        {
                            System.out.println(e1.getMessage());
                        }
                        catch (IndexOutOfBoundsException e2)
                        {
                            System.out.println("Error: Invalid colon " +
                                "placement in file");
                        }
                        break;
                    case 2: //Load Events
                        System.out.print("Filename: ");
                        filename = sc.nextLine();
                        try
                        {
                            IO.loadEvents(filename, network);
                        }
                        catch (IllegalArgumentException e1)
                        {
                            System.out.println(e1.getMessage());
                        }
                        catch (IndexOutOfBoundsException e2)
                        {
                            System.out.println("Error: Invalid colon " +
                                    "placement in file");
                        }
                        break;
                    case 3: //Probabilities
                        setProbabilities(network);
                        break;
                    case 4: //Node Ops
                        nodeOperations(network);
                        break;
                    case 5: //Edge Ops
                        edgeOperations(network);
                        break;
                    case 6: //New Post
                        newPost(network);
                        break;
                    case 7: //Display Network
                        network.displayAsList();
                        break;
                    case 8: //Display Stats
                        DSAQueue stats = new DSAQueue();
                        displayStats(network, stats);
                        displayPopular(network, stats);

                        System.out.println("\nProb. Like: " +
                                           network.getProbLike());
                        System.out.println("Prob. Follow: " +
                                           network.getProbFollow());
                        while (!stats.isEmpty())
                        {
                            System.out.print(stats.dequeue());
                        }
                        break;
                    case 9: //Run Timestep
                        timeStep(network, checkStale);
                        break;
                    case 10: //Save
                        System.out.print("Filename: ");
                        filename = sc.nextLine();
                        IO.saveNetwork(filename, network);
                        break;
                    default:
                        if (cmd != 0)
                        {
                            System.out.println("Error: Invalid selection");
                        }
                        break;
                }
            }
            catch (IllegalArgumentException | InputMismatchException e1)
            {
                System.out.println("Error: Invalid input");
            }
        } while (cmd != 0);
    }

 /* Setting the probabilities in interactive mode */
    private static void setProbabilities(Network network)
    {
        Scanner sc = new Scanner(System.in);
        double probLike, probFollow;

        System.out.print("Prob. Like: ");
        probLike = Double.parseDouble(sc.nextLine());
        network.setProbLike(probLike);

        System.out.print("Prob. Follow: ");
        probFollow = Double.parseDouble(sc.nextLine());
        network.setProbFollow(probFollow);
    }

 /* Sub-menu for node operations */
    private static void nodeOperations(Network network)
    {
        Scanner sc = new Scanner(System.in);
        String name = "";
        int cmd;

        System.out.println("[1] Find Node\n[2] Insert Node\n[3] Delete " +
                "Node\n[0] Back\n");
        cmd = Integer.parseInt(sc.nextLine());
        try
        {
            switch (cmd)
            { //Using trim to remove excess whitespace
                case 1:
                    System.out.print("Node/Person Name: ");
                    name = sc.nextLine().trim();
                    network.findNode(name);
                    break;
                case 2:
                    System.out.print("Node/Person Name: ");
                    name = sc.nextLine().trim();
                    Person newNode = new Person(name);
                    network.addVertex(name, newNode);
                    System.out.println("'" + name + "' was successfully added" +
                            " to the network");
                    break;
                case 3:
                    System.out.print("Node/Person Name: ");
                    name = sc.nextLine().trim();
                    network.removeVertex(name);
                    System.out.println("'" + name + "' was successfully" +
                            " removed from the network");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Error: Invalid selection");
                    break;
            }
        }
        catch (IllegalArgumentException e1)
        {
            System.out.println("Error: Node '" + name + "' already exists");
        }
        catch (NoSuchElementException e2)
        {
            System.out.println("Error: Could not find node '" + name + "'");
        }
    }

 /* Sub-menu for edge operations */
    private static void edgeOperations(Network network)
    {
        Scanner sc = new Scanner(System.in);
        String followee, follower;
        int cmd;

        System.out.println("[1] Add Follow\n[2] Remove Follow\n[0] Back\n");
        cmd = Integer.parseInt(sc.nextLine());
        try
        {
            switch (cmd)
            { //Using trim to remove excess whitespace
                case 1:
                    System.out.print("Followee Name: ");
                    followee = sc.nextLine().trim();
                    System.out.print("Follower Name: ");
                    follower = sc.nextLine().trim();

                    network.addEdge(followee, follower);
                    System.out.println("Link successfully added");
                    break;
                case 2:
                    System.out.print("Followee Name: ");
                    followee = sc.nextLine().trim();
                    System.out.print("Follower Name: ");
                    follower = sc.nextLine().trim();

                    network.removeEdge(followee, follower);
                    System.out.println("Link successfully removed");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Error: Invalid selection");
                    break;
            }
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }

 /* Manually adding a new post to the events queue */
    private static void newPost(Network network)
    {
        Scanner sc = new Scanner(System.in);
        String poster, text;
        double clickbait;

        System.out.println("The post will be added to events/timestep queue");
        System.out.print("Poster: ");
        poster = sc.nextLine();
        if (network.hasVertex(poster))
        {
            try
            {
                Post newPost;
                System.out.print("Post Text: ");
                text = sc.nextLine();
                System.out.print("Clickbait Factor: ");
                clickbait = Double.parseDouble(sc.nextLine());

                newPost = new Post(poster, text, clickbait);
                network.addEvent(newPost, 'P');
            }
            catch (IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }
        }
        else
        {
            System.out.println("Error: This person isn't in the network");
        }
    }

 /* Displaying network statistics */
    private static void displayStats(Network n, DSAQueue queue)
    {
        queue.enqueue("\nNodes: " + n.getVertexCount());
        queue.enqueue("\nEdges: " + n.getEdgeCount());
        queue.enqueue("\nPosts: " + n.getNPosts() + " (" +
                      n.getNPostsStale() + " stale)");
        queue.enqueue("\nEvents in Timestep Queue: " + n.getEventsCount() +
                      "\n");
    }

 /* Displaying people and posts in order of popularity */
    private static void displayPopular(Network n, DSAQueue queue)
    {
        Person[] people;
        Post[] posts;
        people = sortPeople(n);
        posts = sortPosts(n);

        queue.enqueue("\nPeople in Order of Total Followers:");
        queue.enqueue("\n===================================\n");
        for (int ii = people.length - 1; ii >= 0; ii--)
        {
            queue.enqueue(people[ii].getName() + ": " +
                    people[ii].getNFollowers() + "\n");
        }

        queue.enqueue("\nPosts in Order of Likes:");
        queue.enqueue("\n========================\n");
        for (int jj = posts.length - 1; jj >= 0; jj--)
        {
            queue.enqueue(posts[jj].getPoster() + ": " +
                    posts[jj].getLikes() + "\n'" +
                    posts[jj].getText() + "'\n");
        }
    }

    /* Sorting people in the network by number of followers */
    private static Person[] sortPeople(Network n)
    {
        int ii;
        DSALinkedList people = n.getAllVertexValues();
        Person[] array = new Person[people.getCount()];

        ii = 0;
        for (Object o : people)
        {
            array[ii] = (Person)o;
            ii++;
        }
        mergeSort(array);
        return array;
    }

    /* Sorting posts in the network by number of likes */
    private static Post[] sortPosts(Network n)
    {
        int ii;
        DSALinkedList posts = n.getPosts();
        Post[] array = new Post[posts.getCount()];

        ii = 0;
        for (Object o : posts)
        {
            array[ii] = (Post)o;
            ii++;
        }
        mergeSort(array);
        return array;
    }

 /* Steps through the simulation by one unit of 'time', pulling an event out
    of the queue and spreading any shared posts through the network */
    private static void timeStep(Network network, DSAQueue checkStale)
    {
        String name, followee, follower;
        Person person, poster, viewer;
        DSAQueue sharers, followers;
        Post post, sharedPost;
        int count;
        char tag;

        //Pulling next new event from events queue
        tag = network.peekQueueTag();
        switch (tag)
        {
            case 'A':
                person = (Person)network.dequeueEvent();
                if (network.hasVertex(person.getName()))
                {
                    System.out.println("Error: '" + person.getName() + "' is " +
                        "already in the network, can't add");
                }
                else
                {
                    network.addVertex(person.getName(), person);
                    network.enqueueTimeStepText("\n- A: Added '" +
                        person.getName() + "' to network");
                }
                break;
            case 'R':
                name = (String)network.dequeueEvent();
                if (!network.hasVertex(name))
                {
                    System.out.println("Error: '" + name + "' is " +
                        "not in the network, can't be removed");
                }
                else
                {
                    network.removeVertex(name);
                    network.enqueueTimeStepText("\n- R: Removed '" + name +
                        "' from network");
                }
                break;
            case 'F':
                followee = (String)network.dequeueEvent();
                follower = (String) network.dequeueEvent();
                if (!network.hasVertex(followee))
                {
                    System.out.println("Error: '" + followee + "' is not in " +
                        "the network, can't be a followee");
                }
                else if (!network.hasVertex(follower))
                {
                    System.out.println("Error: '" + follower + "' is not in " +
                        "the network, can't be a follower");
                }
                else if (network.hasEdge(followee, follower))
                {
                    System.out.println("Error: '" + follower + "' already " +
                        "follows '" + followee + "'");
                }
                else
                {
                    network.addEdge(followee, follower);
                    network.enqueueTimeStepText("\n- F: '" + follower + "' " +
                        "now follows '" + followee + "'");
                }
                break;
            case 'U':
                followee = (String)network.dequeueEvent();
                follower = (String)network.dequeueEvent();
                if (!network.hasVertex(followee))
                {
                    System.out.println("Error: '" + followee + "' is not in " +
                        "the network, can't be unfollowed");
                }
                else if (!network.hasVertex(follower))
                {
                    System.out.println("Error: '" + follower + "' is not in " +
                        "the network, can't remove follow");
                }
                else if (!network.hasEdge(followee, follower))
                {
                    System.out.println("Error: '" + follower + "' doesn't " +
                        "follow '" + followee + "', can't unfollow");
                }
                else
                {
                    network.removeEdge(followee, follower);
                    network.enqueueTimeStepText("\n- U: '" + follower + "' " +
                        "unfollowed '" + followee + "'");
                }
                break;
            case 'P':
                post = (Post)network.dequeueEvent();
                if (!network.hasVertex(post.getPoster()))
                {
                    System.out.println("Error: '" + post.getPoster() + "' is " +
                        "not in the network, can't post");
                }
                else
                {
                    poster = (Person)network.getVertexValue(post.getPoster());

                    poster.addJustShared(post);
                    poster.addPost();
                    network.addPost(post);
                    network.enqueueTimeStepText("\n- P: '" + poster.getName() +
                        "' created a post\n  > '" + post.getText() + "'");

                    checkStale.enqueue(post);
                }
                break;
        }

        //Search network, checking for posts just 'shared' to spread them
        sharers = network.findSharers();
        while (!sharers.isEmpty())
        {
            //Get the person who shared a post
            person = (Person)sharers.dequeue();
            //Retrieve the post being shared from person's sharing queue
            sharedPost = (Post)person.getJustShared().dequeue();
            //Get the original poster
            poster = (Person)network.getVertexValue(sharedPost.getPoster());
            followers = network.getAdjacentValues(person.getName());
            while (!followers.isEmpty())
            {
                viewer = (Person)followers.dequeue();
                viewer.viewPost(sharedPost, poster, network);
                    // ^ numSeen on post updated here
            }
        }

        //Check to see if any posts have become 'stale'
        count = checkStale.getCount();
        for (int ii = 0; ii < count; ii++)
        {
            int current, prev;
            Post checking = (Post)checkStale.dequeue();
            current = checking.getNumSeen();
            prev = checking.getNumSeenPrev();

            if (current != prev)
            { //Has changed: not stale
                checking.updateNumSeenPrev(); //Make prev = current
                checkStale.enqueue(checking); //Have it checked next timeStep
            }
            else
            { //Hasn't changed: stale
                checking.setStale(true);
                network.addPostStale();
                network.enqueueTimeStepText("\n- STALE: Post by '" +
                    checking.getPoster() + "'\n  > '" +
                    checking.getText() + "'");
            }
        }
    }

 /* Display general usage information to user about running program, including
    a description of the two modes and their flags */
    private static void usageInfo()
    {
        System.out.println("This is a program for analysing the spread of " +
            "information through a social network.\nEach person is a node in" +
             " a network and every time someone follows another person, it " +
            "creates a connection (edge) between them.");
        System.out.println("\nIn interactive mode, you can step through the " +
            "simulation, tweak the network and view the details of individual" +
            " people in the network mid-simulation.\nTo use interactive mode," +
            " run program as 'SocialSim -i'");
        System.out.println("\nIn simulation mode, the program runs the entire" +
            " simulation using the starting parameters provided on the " +
            "command line and keeps a log of all events that occur at each " +
            "time step. This log is saved to a dated log file.\nTo use " +
            "simulation mode, run program as 'SocialSim -s <netfile> " +
            "<eventfile> <prob_like> <prob_follow>'");
    }

    //SORTING
    //The Rest of This File is
    //Obtained from Kristian Rados DSA Practical 8 Work

    // mergeSort - front-end for kick-starting the recursive algorithm
    private static void mergeSort(Object[] A)
    {
        int leftIdx, rightIdx;
        leftIdx = 0;
        rightIdx = A.length - 1;
        mergeSortRecurse(A, leftIdx, rightIdx);
    }//mergeSort()
    private static Object[] mergeSortRecurse(Object[] A, int leftIdx,
                                             int rightIdx)
    {
        if (leftIdx < rightIdx)
        {
            int midIdx;
            midIdx = (leftIdx + rightIdx) / 2;

            mergeSortRecurse(A, leftIdx, midIdx); //Sort left half of subarray
            mergeSortRecurse(A, midIdx + 1, rightIdx); //Sort right half
            merge((Comparable[])A, leftIdx, midIdx, rightIdx);
        } //Base case: array is already sorted (only one element)
        return A;
    }//mergeSortRecurse()
    private static Object[] merge(Comparable[] A, int leftIdx, int midIdx,
                                  int rightIdx)
    {
        int ii, jj, kk;
        Object[] tempA = new Object[rightIdx - leftIdx + 1];
        ii = leftIdx; //Idx for the 'front' of left subarray
        jj = midIdx + 1; //Idx for the 'front' of right subarray
        kk = 0; //Idx for next free element in tempA

        //Merge subarrays into tempA
        while (ii <= midIdx && jj <= rightIdx)
        {
            if (A[ii].compareTo(A[jj]) <= 0) //Use <= for stable sort
            {
                tempA[kk] = A[ii]; //Take from left subarray
                ii++;
            }
            else
            {
                tempA[kk] = A[jj]; //Take from right subarray
                jj++;
            }
            kk++;
        }

        for (ii = ii; ii <= midIdx; ii++) //Flush remainder from left
        {
            tempA[kk] = A[ii];
            kk++;
        }
        for (jj = jj; jj <= rightIdx; jj++) //Flush remainder from right
        {
            tempA[kk] = A[jj];
            kk++;
        }

        for (kk = leftIdx; kk <= rightIdx; kk++) //Copy now sorted tempA to A
        {
            A[kk] = (Comparable)tempA[kk - leftIdx]; /*Use kk - leftIdx to
                                                       align tempA to 0*/
        }
        return A;
    }//merge()
}
