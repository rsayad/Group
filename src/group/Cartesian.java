package group;

/**
 * Cartesian: create a cartesian product of strings
 * e.g. a{b,c}c{d,e} = abcd abce accd acce
 * 
 * @author richard_sayad
 */
public class Cartesian {
    // this is the string we want to parse
    private final String group;
    // the resuld of the cartesian join
    private String value = null;
    
    /**
     * Constructor for performing a Cartesian product on a string
     * 
     * @param s : The string we want to parse
     */
    public Cartesian(String s)
    {
        if (s != null)
        {
            // clean up the string first
            this.group = s.replace(" ", "").trim();
        }
        else
        {
            this.group = s;
        }
    }
    
    /**
     * Lazy processing of results.
     * 
     * @return 
     */
    public String getValue()
    {
        if (value == null && group != null)
        {
            value = processGroups().trim();
        }
        
        return value;
    }
    
    /**
     * processGroups : Process cartesian groups
     * @return the cartesian product
     */
    private String processGroups()
    {
        StringBuilder sbresult = new StringBuilder();
        StringBuilder sbprefix = new StringBuilder();
        StringBuilder sbgroup = new StringBuilder(this.group);
        processNextGroup(sbprefix, sbgroup, sbresult);
        return sbresult.toString().replace("  ", " ").trim();
    }
    
    /**
     * processNextGroup : recursive function to calculate the cartesian products
     * 
     * @param prefix the prefix for the current iteration
     * @param remaining remaining string to be processed
     * @param result the final result of the product
     */
    private void processNextGroup(StringBuilder prefix, StringBuilder remaining, StringBuilder result)
    {
        // get the next element
        String popped = pop(remaining);
        
        while (!popped.isEmpty())
        {
            if (!popped.startsWith("{"))
            {
                prefix.append(popped);
            }
            else
            {
                StringBuilder poppedGroup = new StringBuilder(popped.substring(1, popped.length() - 1));
                String groupResult = pop(poppedGroup);

                // we are in a group, so we need to do a cartesian product
                while (groupResult != null && !groupResult.isEmpty())
                {
                    StringBuilder newPrefix = new StringBuilder(prefix);
                    newPrefix.append(groupResult);
                    if (poppedGroup.length() > 0 && poppedGroup.charAt(0) == '{')
                    { 
                        poppedGroup.append(remaining);
                        processNextGroup(newPrefix, poppedGroup, result);
                    }
                    else
                    {
                        processNextGroup(newPrefix, new StringBuilder(remaining), result);
                    }

                    // we finished processing the line for one element in the group so
                    // place a separator
                    result.append(" ");
                    groupResult = pop(poppedGroup);
                } 
                // nothing left in remaining so clear it out
                remaining.delete(0, remaining.length());
                // end of loop so prefix should also be empty
                prefix.delete(0, prefix.length());
            }
            popped = pop(remaining);
        }
        result.append(prefix);
    }
        
    /**
     * pop : removes the first element of the string keeping groups together 
     * for parsing.
     * 
     * @param current is the string we want to parse
     * @return is the first element
     */
    public static String pop(StringBuilder current)
    {
        int groupCount = 0; // groups can be nested, we want the parent
        StringBuilder sbPop = new StringBuilder();
        
        for (int i = 0; i < current.length(); i++)
        {
            // breaker used within groups
            if (current.charAt(i) == ',' && groupCount == 0)
            {
                current.delete(0, i + 1);
                return sbPop.toString();
            }
            // breaker used to define start of a group
            else if (current.charAt(i) == '{')
            {
                if (i > 0 && groupCount == 0)
                {
                    current.delete(0, i);
                    return sbPop.toString();
                }
                else
                {
                    groupCount++;
                }
            }
            // breaker used to define the end of a group
            else if (current.charAt(i) == '}')
            {
                groupCount--;
                // end of the parent group
                if (groupCount == 0)
                {
                    sbPop.append(current.charAt(i));
                    current.delete(0, i + 1);
                    return sbPop.toString();
                }
            }

            sbPop.append(current.charAt(i));
        }

        // last element to pop from the string
        current.delete(0, current.length());
        return sbPop.toString();        
    }
}
