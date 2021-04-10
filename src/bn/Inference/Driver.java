package bn.Inference;

import java.io.Console;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Driver {
    
    // aima-alarm.xml, query variable B, J true M true
    // java -cp "./bin" MYBNInferencer aima-alarm.xml B J true M true

    //for wet grass AIMA example...
    // java -cp "./bin" MyBNInferencer aima-wet-grass.xml R S true
    public static void main(String args[]) throws IOException, ParserConfigurationException {
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

        try {
            String inferencer = args[0];
            String filename = args[1];
    
            // specify beyond args[1]... args[0] and args[1] are always fixed...
            for (int i = 2; i < args.length; i++) {
                System.out.println(args[i]);
            }

        } catch (Exception e) {
            System.out.print("Invalid input: ");
            if (args.length <= 3) {
                System.out.println("Didn't supply enough arguments.");
            }
        }

        
        
    }
}
