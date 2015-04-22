package group;

/**
 *
 * @author richard_sayad
 */
public class Group {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1)
        {
            displayUsage();
        }
        else
        {
            Cartesian c = new Cartesian(args[0]);
            System.out.println(c.getValue());
        }
    }
    
    private static void displayUsage()
    {
        System.out.println("Usage : java -jar Group.jar <string>\neg. java -jar Group.jar ab{a,c}de\n");
    }
}
