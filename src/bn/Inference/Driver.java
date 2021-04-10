package bn.Inference;


import bn.core.*;
import bn.base.*;
import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.core.Distribution;
import bn.core.Value;
import bn.parser.XMLBIFParser;

import java.io.Console;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import org.xml.sax.*;

public class Driver {
    
    // aima-alarm.xml, query variable B, J true M true
    // java -cp "./bin" MYBNInferencer aima-alarm.xml B J true M true

    //for wet grass AIMA example...
    // java -cp "./bin" MyBNInferencer aima-wet-grass.xml R S true
    public static void main(String args[]) throws IOException, ParserConfigurationException, SAXException {

        // Console c = System.console();
        // String answer = c.readLine("Enter your answer: ");
        // System.out.print(answer);

        // should catch in the event that no input is supplied

        // so let's build out the rest of the algorithm...

        // i ASSUME that if i were to do file i/o, I want to use the classpath to get to the relevant file (this is so I can access the method as specified)
            // question of how to handle additional parameters if I am invoking a method? Should I just have an array of random variables, so I can just
            // iterate through the array until no more variables are left?

        // then, what do I do about true? Beyond 3rd argument provided (args[2]), it seems like a boolean value always follows the args. So, should it be that 
        // if something like $... R S true B will flag false, since there isn't a boolean value specified for instantiation?
        // also, does instantiation mean that it will ALWAYS be true? In that case, what gives?

        /*
        try {
            String inferencer = args[0];
            String filename = args[1];
            String queryVar = args[2];
            XMLBIFParser parser = new XMLBIFParser();
            BayesianNetwork network = parser.readNetworkFromFile(filename);

            // for inferencer, we can actually just import ALL package statements, and have an ugly if-else/case switch conditional to just call the relevant query, but
            // I feel like it's kind of overkill...
                // but it might be easier?
            // the alternative would be to try and pull the relevant query once we reach that class, and I have _no idea_ how I'd go about doing that.

            // specify beyond args[2]... args[0], args[1], args[2] are always fixed...
            // this gets us the relevant assignment variables that we want to assign. Rest are unobserved variables.

            for (int i = 3; i < args.length; i += 2) {
                System.out.println(args[i]);
            }

        } catch (Exception e) {
            System.out.print("Invalid input: ");
            if (args.length <= 3) {
                System.out.println("Didn't supply enough arguments.");
            }
        }
        */

        

        // try it without command line first, so I can circumvent having to compile first...

        // assume arguments are still passed in as an array...
        String[] input = {}; // TODO: MODIFY AS NECESSARY
        String inferencer = input[0];
        String filename = input[1];
        String queryVarLetter = input[2];
        XMLBIFParser parser = new XMLBIFParser();
        BayesianNetwork network = parser.readNetworkFromFile(filename);
        RandomVariable queryVar = network.getVariableByName(queryVarLetter);
        
    }
}
