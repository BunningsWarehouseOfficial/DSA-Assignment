import java.util.*;

//TODO unit testing (low priority)

public class SocialSim
{
    public static void main(String[] args)
    {
//        try
//        {
            if (args.length == 5)
            { //Checking for simulation mode
                //TODO check for correct -s CLI format
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
//        }
//        catch (Exception e)
//        {
//            System.out.println(e.getMessage());
//        }//TODO
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
//            try
//            {
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
                        catch (IllegalArgumentException e)
                        {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2: //Load Events
                        System.out.print("Filename: ");
                        filename = sc.nextLine();
//                        try
//                        {
                            IO.loadEvents(filename, network);
//                        }
//                        catch (IllegalArgumentException e)
//                        {//TODO
//                            System.out.println(e.getMessage());
//                        }
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
                        newPost(network); //TODO test with timestep
                        break;
                    case 7: //Display Network
                        network.displayAsList();
                        break;
                    case 8: //Display Stats
                        displayStats(network);
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
//            } //TODO
//            catch (IllegalArgumentException e1)
//            {
//                System.out.println("Error: Invalid input");
//            }
//            catch (NumberFormatException | InputMismatchException e2)
//            {
//                System.out.println("Error: Invalid input");
//            }
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
    private static void displayStats(Network n)
    {
        System.out.println("\nProb. Like: " + n.getProbLike());
        System.out.println("Prob. Follow: " + n.getProbFollow() + "\n");

        System.out.println("Nodes: " + n.getVertexCount());
        System.out.println("Edges: " + n.getEdgeCount());
        System.out.println("Posts: " + n.getNPosts() + " (" + n.getNPostsStale()
                           + " stale)");
        System.out.println("Events in Timestep Queue: " + n.getEventsCount());
    }

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
                network.addVertex(person.getName(), person);
                break;
            case 'R':
                name = (String)network.dequeueEvent();
                network.removeVertex(name);
                break;
            case 'F':
                followee = (String)network.dequeueEvent();
                follower = (String)network.dequeueEvent();
                network.addEdge(followee, follower);
                break;
            case 'U':
                followee = (String)network.dequeueEvent();
                follower = (String)network.dequeueEvent();
                network.removeEdge(followee, follower);
                break;
            case 'P':
                post = (Post)network.dequeueEvent();
                poster = (Person)network.getVertexValue(post.getPoster());

                poster.addJustShared(post);
                poster.addPost();
                network.addPost(post);

                checkStale.enqueue(post);
                break;
        }

        //Search network, checking for nodes just 'shared' to spread them
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
            }
        }
    }

 /* Display general usage information to user about running program, including
    a description of the two modes and their flags */
    private static void usageInfo()
    {
        //TODO user information print statements
        System.out.println("\n   \n");
    }
}
