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
import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;

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
        test_grass();

    }
    private static void test_alarm() throws IOException, ParserConfigurationException, SAXException{
        String filename = "src/bn/examples/aima-alarm.xml";
        XMLBIFParser parser = new XMLBIFParser();
		BayesianNetwork network = parser.readNetworkFromFile(filename);

        RandomVariable rv1 = network.getVariableByName("J");
        RandomVariable rv2 = network.getVariableByName("M");
        RandomVariable queryRV = network.getVariableByName("B");


        String inputVal1 = "true";
        String inputVal2 = "true";
        Value v1 = new bn.base.Value(inputVal1);
        Value v2 = new bn.base.Value(inputVal2);
        Assignment ass = new bn.base.Assignment(rv1, v1,rv2,v2);
        Distribution dist = Gibbs.query(queryRV, ass.copy(), network, 1000000);
        System.out.println("Gibbs Result: "+dist);

        dist = RejectionSampling.query(queryRV, ass.copy(), network, 1000000);
        System.out.println("Rejection Result: "+dist);

        dist = Weighted.query(queryRV, ass.copy(), network, 1000000);
        System.out.println("Weighted Result: "+dist);

        dist = Enumeration.query(queryRV, ass.copy(), network);
        System.out.println("Enumeration Result: "+dist);

    }
    private static void test_grass() throws IOException, ParserConfigurationException, SAXException{
        String filename = "src/bn/examples/aima-wet-grass.xml";
        XMLBIFParser parser = new XMLBIFParser();
		BayesianNetwork network = parser.readNetworkFromFile(filename);

        RandomVariable rv1 = network.getVariableByName("S");
        //RandomVariable rv2 = network.getVariableByName("M");
        RandomVariable queryRV = network.getVariableByName("R");


        String inputVal1 = "true";
        //String inputVal2 = "true";
        Value v1 = new bn.base.Value(inputVal1);
        //Value v2 = new bn.base.Value(inputVal2);
        Assignment ass = new bn.base.Assignment(rv1, v1);
        Distribution dist = Gibbs.query(queryRV, ass.copy(), network, 10);
        System.out.println("Gibbs Result: "+dist);

        dist = RejectionSampling.query(queryRV, ass.copy(), network, 1000000);
        System.out.println("Rejection Result: "+dist);

        dist = Weighted.query(queryRV, ass.copy(), network, 1000000);
        System.out.println("Weighted Result: "+dist);

        dist = Enumeration.query(queryRV, ass.copy(), network);
        System.out.println("Enumeration Result: "+dist);

    }
    
}
