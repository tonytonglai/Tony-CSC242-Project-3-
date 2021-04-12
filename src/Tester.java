
import bn.core.*;
import bn.Inference.Enumeration;
import bn.Inference.Gibbs;
import bn.Inference.RejectionSampling;
import bn.Inference.Weighted;
import bn.base.*;
import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.core.Distribution;
import bn.core.Value;
import bn.parser.XMLBIFParser;

import java.io.Console;
import java.util.Arrays;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import org.xml.sax.*;

// TODO: REFACTOR ALL INSTANCES OF input[i] to args[i]
public class Tester {
    
    // aima-alarm.xml, query variable B, J true M true
    // java -cp "./bin" MYBNInferencer aima-alarm.xml B J true M true

    //for wet grass AIMA example...
    // java -cp "./bin" MyBNInferencer aima-wet-grass.xml R S true
    public static void main(String input []) throws IOException, ParserConfigurationException, SAXException {

            //java Tester.java gibbs  bn/examples/aima-alarm.xml 1000000 B J true M true
       //String[] input = {"enumeration", "bn/examples/aima-alarm.xml", "100", "B", "J", "true", "M", "true"}; // TODO: MODIFY AS NECESSARY
        // enumeration src/bn/examples/aima-alarm.xml 100 B J true M true
        //System.out.println("I am here in  aboove try");
        try {
        //System.out.println("I am here in try"+ input[1]);
        
        String inferencer = input[0];
        String filename = input[1];
        String queryVarLetter;
        int fixedLength;
        int trials = 1000;

        try {
           trials = Integer.parseInt(input[2]); // to see if there is a value supplied. This means that it is an approximate inferencer, in which case...
            queryVarLetter = input[3]; // query variable is the 4th input (input[3])
            fixedLength = 4;
        } catch (Exception e) {
            queryVarLetter = input[2]; // otherwise, it's the 3rd input...
            fixedLength = 3;
        }

        XMLBIFParser parser = new XMLBIFParser();
        BayesianNetwork network = parser.readNetworkFromFile(filename);
        //System.out.println("I am here eeee"+queryVarLetter);

        RandomVariable queryVar = network.getVariableByName(queryVarLetter);

        Assignment ass = new bn.base.Assignment();
        // get length between args.length - 3...
            // it should be an even number

        int argLengthDiff = input.length - fixedLength;  
   
        //System.out.println(argLengthDiff);
        //System.out.println("I am here eeee");


        if (argLengthDiff % 2 == 1) { // if it's odd...
            System.out.println("Not valid");
        }

        for (int i = fixedLength; i < input.length; i += 2) {
            RandomVariable rv = network.getVariableByName(input[i]);
            Value v = new bn.base.Value(input[i + 1]);
            ass.put(rv, v);
        }

        Distribution dist;

        switch (inferencer.toLowerCase()){
            case "gibbs":
                dist = Gibbs.query(queryVar, ass.copy(), network, trials);
                System.out.println("Gibbs Result: "+dist);
                break;
            case "enumeration":
                dist = Enumeration.query(queryVar, ass.copy(), network);
                System.out.println("Enumeration Result: "+dist);
                break;
            case "rejection":
                dist = RejectionSampling.query(queryVar, ass.copy(), network, trials);
                System.out.println("Rejection Sampling Result: "+dist);
            break;
            case "weighted":
                dist = Weighted.query(queryVar, ass.copy(), network, trials);
                System.out.println("Weighted Sampling Result: "+dist);
            break;
            default:
            System.out.println("No such inferencer exists");
        }
        } catch (Exception e) {
            System.out.println("Error occurred");
        }
        
    }
}
