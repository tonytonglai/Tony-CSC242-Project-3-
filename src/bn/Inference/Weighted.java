package bn.Inference;



import bn.base.Domain;
import bn.core.*;
import bn.base.*;
import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.core.Distribution;
import bn.core.Value;
import bn.parser.XMLBIFParser;
// import jdk.internal.event.Event;

import java.util.List;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import bn.parser.XMLBIFParser;


public class Weighted {
    public static class Tuple2 {

        Assignment first;
        Double second;
      
        public Tuple2( Assignment first, Double second){
            this.first = first;
            this.second = second;
        }
    }

    public static Distribution query (RandomVariable X, Assignment e, BayesianNetwork network, int N) {
    
        Distribution W = new bn.base.Distribution(X); //Get the distribution of X vector of weighted counts for each valye of X  
        for (Value v: X.getDomain()){  //Assign 0.0 to all the values in W
           W.put(v, 0.0);
        }
        
        for (int j = 0; j < N; j ++){
           Tuple2 tuple = Weightedsample(network, e);
           Assignment x = tuple.first; 
           Double w = tuple.second;
           W.set(x.get(X), W.get(x.get(X))+w);
        }
        W.normalize(); 
        return W; 
    }

    public static Tuple2 Weightedsample (BayesianNetwork network, Assignment e){
        //parents of X => x

        Double w = 1.0; 
        Assignment x = e.copy();
        List<RandomVariable> var = network.getVariablesSortedTopologically();
        for (RandomVariable rv: var){
            if (e.containsKey(rv)){
               double prob = 0;
               prob = network.getProbability(rv, x); //get probability of v given x
               w = w * prob;
            }
            else{ //GEt the prior sampling
                bn.core.Domain dom = rv.getDomain();
                double outcome = Math.random();
                double prob_sum = 0;
                for(bn.core.Value v: dom){
                    x.put(rv,v);
                    prob_sum += network.getProbability(rv,x);
                    if(outcome <= prob_sum){
                        break;
                    }
                }
            }
        }

        Tuple2 tuple = new Tuple2 (x, w);
        return tuple;

    }
    public static void main(String args[]) throws IOException, ParserConfigurationException, SAXException {
        String filename = "src/bn/examples/aima-alarm.xml";
		if (args.length > 0) {
			filename = args[0];
		}
		XMLBIFParser parser = new XMLBIFParser();
		BayesianNetwork network = parser.readNetworkFromFile(filename);
        RandomVariable rv1 = network.getVariableByName("E");
        // System.out.println("rv1 " +  rv1);
        Assignment ass = new bn.base.Assignment(rv1, rv1.getDomain().iterator().next());
        // System.out.println("ass " + ass);
        RandomVariable rv2 = network.getVariableByName("A");
        // System.out.println("rv2 " + rv2);
        Distribution dist = query(rv2, ass, network, 1000000);
        System.out.println(dist);
    }

}
