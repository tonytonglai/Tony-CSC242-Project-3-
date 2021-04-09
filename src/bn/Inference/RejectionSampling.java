package bn.Inference;



import bn.base.Domain;
import bn.core.*;
import bn.base.*;
import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.core.Distribution;
import bn.core.Value;
import bn.parser.XMLBIFParser;

import java.util.List;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import bn.parser.XMLBIFParser;

public class RejectionSampling {

    public static Distribution query(RandomVariable X, Assignment e, BayesianNetwork network, int N) {
        Distribution C = new bn.base.Distribution(X);
        for(Value v: X.getDomain()){
            C.set(v,1);
        }
        List<RandomVariable> vars = network.getVariablesSortedTopologically();
        for(int j=0;j<N;j++){
            Assignment x = new bn.base.Assignment();
            for(RandomVariable rv: vars){
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
            if(x.containsAll(e)){
                Value result_val = x.get(X);
                double updated_prob = C.get(result_val)+1;

                C.set(result_val,updated_prob);
            }
        }
        C.normalize();
        return C;
    }

    // @Override
    // public Distribution query(RandomVariable X, Assignment e, BayesianNetwork network) {
    //     return null;
    // }

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
