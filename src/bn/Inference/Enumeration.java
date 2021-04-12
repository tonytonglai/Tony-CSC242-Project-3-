package bn.Inference;
import bn.base.Distribution;
import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.core.Inferencer;
import bn.core.RandomVariable;
import bn.core.Value;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import bn.parser.XMLBIFParser;

public class Enumeration{
    public static Distribution query(RandomVariable X, Assignment e, BayesianNetwork network) {
        List<RandomVariable> vars = network.getVariablesSortedTopologically();
        Distribution Qx = new Distribution(X);
        for (Value x : X.getDomain()) {
            Assignment ex = e.copy();
            ex.putIfAbsent(X, x);
            Qx.set(x, enumerate_all(new ArrayList<>(vars), ex, network)); // null pointer error here.
        }
        Qx.normalize();
        return Qx;


    }
    private  static double enumerate_all(List<RandomVariable> vars, Assignment e, BayesianNetwork network){
        if(vars.isEmpty()){
            return 1.0;
        }

        RandomVariable V = vars.remove(0); //.iterator().next();
        if(e.containsKey(V)){
            return network.getProbability(V,e) * enumerate_all(vars,e, network);
        }else{
            double sum_porb = 0;
            for (Value v : V.getDomain()) {
                Assignment ev = e.copy();
                ev.putIfAbsent(V, v); // ?
                // System.out.println("V is " + V);
                // System.out.println("e is " + e);
                // System.out.println(V.cpt.get(value, e));
                double ya = network.getProbability(V, ev);
                sum_porb += ya * enumerate_all(new ArrayList<>(vars), ev, network);
            }
            return sum_porb;
        }
    }
    public static void main(String input[]) throws IOException, ParserConfigurationException, SAXException {

        //String[] input = {"enumeration", "src/bn/examples/aima-alarm.xml", "100", "B", "J", "true", "M", "true"}; // TODO: MODIFY AS NECESSARY
        // enumeration src/bn/examples/aima-alarm.xml 100 B J true M true
        try {
            System.out.println("I am here");
            String inferencer = input[0];
            String filename = input[1];
            String queryVarLetter;
            int fixedLength;
            int trials = 10;
            try {
                Integer.parseInt(input[2]); // to see if there is a value supplied. This means that it is an approximate inferencer, in which case...
                queryVarLetter = input[3]; // query variable is the 4th input (input[3])
                fixedLength = 4;
            } catch (Exception e) {
                queryVarLetter = input[2]; // otherwise, it's the 3rd input...
                fixedLength = 3;
            }
            
            XMLBIFParser parser = new XMLBIFParser();
            BayesianNetwork network = parser.readNetworkFromFile(filename);
            RandomVariable queryVar = network.getVariableByName(queryVarLetter);
            Assignment ass = new bn.base.Assignment();
            // get length between args.length - 3...
                // it should be an even number
            
            int argLengthDiff = input.length - fixedLength;     
            System.out.println(argLengthDiff);

            if (argLengthDiff % 2 == 1) { // if it's odd...
                System.out.println("Not valid");
            }

            for (int i = fixedLength; i < input.length; i += 2) {
                RandomVariable rv = network.getVariableByName(input[i]);
                Value v = new bn.base.Value(input[i + 1]);
                ass.put(rv, v);
            }

            Distribution dist;

            dist = Enumeration.query(queryVar, ass.copy(), network);
            System.out.println("Enumeration Result: "+dist);

        } catch (Exception e) {
            System.out.println("Error occurred");
        }
    }
}

/*
public class Inference {
    public static double[] enumeration(BayesNode X, HashMap<Integer,Integer> e, BayesNetwork bn){
        ArrayList<BayesNode> vars = new ArrayList<>(bn.get_nodes());
        double[] Qx = new double[X.possible_vals.length];
        for(int i =0; i<X.get_possible_vals().length;i++){
            //ArrayList<double[]> ex = new ArrayList<>(e);
            //ex.add(new double[]{ X.id, X.get_possible_vals()[i]});
            HashMap<Integer,Integer> ex = new HashMap<>(e);
            ex.put(X.id,X.get_possible_vals()[i])
            Qx[i] = enumerate_all(vars,ex);
        }

    return Qx;
    }
    private static double enumerate_all(ArrayList<BayesNode> vars,HashMap<Integer,Integer> e){
        if(vars.isEmpty()){
            return 1;
        }
        BayesNode V = vars.remove(0);
        if(e.containsKey(V.id)){
            return V.get_outcome()*enumerate_all(vars,e);
        }else{

        }

        return 1.5;
    }
}
*/