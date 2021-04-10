package bn.base;

import bn.core.RandomVariable;



import bn.base.Domain;
import bn.core.*;
import bn.base.*;
import bn.Inference.*;
import bn.Inference.Enumeration;
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

public class Tester {
    public static void main(String args[]) throws IOException, ParserConfigurationException, SAXException {
        
		/*if (args.length > 0) {
			filename = args[0];
		}
        */
        test_alarm();

    }
    private static void test_alarm() throws IOException, ParserConfigurationException, SAXException{
        String filename = "src/bn/examples/aima-alarm.xml";
        XMLBIFParser parser = new XMLBIFParser();
		BayesianNetwork network = parser.readNetworkFromFile(filename);

        RandomVariable rv1 = network.getVariableByName("E");
        Assignment ass = new bn.base.Assignment(rv1, rv1.getDomain().);
        RandomVariable rv2 = network.getVariableByName("A");
        Distribution dist = Enumeration.query(rv2, ass, network);
        System.out.println("Enumeration Result: "+dist);

        dist = Gibbs.query(rv2, ass, network, 1000000);
        System.out.println("Gibbs Result: "+dist);

        dist = RejectionSampling.query(rv2, ass, network, 1000000);
        System.out.println("Rejection Result: "+dist);

        dist = Weighted.query(rv2, ass, network, 1000000);
        System.out.println("Weighted Result: %dist "+dist);




    }
    
}
