package bn.Inference;



import bn.base.Domain;
import bn.core.*;
import bn.base.*;
import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.core.Distribution;
import bn.core.Value;

import java.util.List;

public class RejectionSampling implements Inferencer {

    public Distribution query(RandomVariable X, Assignment e, BayesianNetwork network, int N) {
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

    @Override
    public Distribution query(RandomVariable X, Assignment e, BayesianNetwork network) {
        return null;
    }
}
