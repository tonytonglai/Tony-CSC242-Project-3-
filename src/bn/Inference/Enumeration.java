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
import org.w3c.dom.*;
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
                ev.putIfAbsent(V, v);
                sum_porb += network.getProbability(V,e) * enumerate_all(new ArrayList<>(vars), ev, network);
            }
            return sum_porb;
        }
    }
    public static void main(String args[]) throws IOException, ParserConfigurationException, SAXException {
        String filename = "src/bn/examples/aima-alarm.xml";
		if (args.length > 0) {
			filename = args[0];
		}
		XMLBIFParser parser = new XMLBIFParser();
		BayesianNetwork network = parser.readNetworkFromFile(filename);
        RandomVariable rv1 = network.getVariableByName("E");
        Assignment ass = new bn.base.Assignment(rv1, rv1.getDomain().iterator().next());
        RandomVariable rv2 = network.getVariableByName("A");
        Distribution dist = query(rv2, ass, network);
        System.out.println(dist);
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