from unicodedata import name
from joint import *
import dist
import ssm

def PTestGivenDis(disease):
        if disease:

            return dist.DDist({ True : 0.99, False : 0.01 })
        else:

            return dist.DDist({ True : 0.001, False : 0.999 })


initialStateDistribution = dist.DDist({'good': 0.9, 'bad': 0.1})

def observationModel(state):
    if state == 'good':

        return dist.DDist({'perfect': 0.8, 'smudged': 0.1, 'black': 0.1})
    else:

        return dist.DDist({'perfect': 0.1, 'smudged': 0.7, 'black': 0.2})

def transitionModel(state, inp):
    if state == 'good':

        return dist.DDist({'good': 0.7, 'bad': 0.3})
    else:

        return dist.DDist({'good': 0.1, 'bad': 0.3})

if __name__ == '__main__':
    #joint distribution
    PAB = JDist(PA, pbgaM = PBgA)

    print(PAB)
    print(PAB.marginalizeOut(0))
    print(PAB.marginalizeOut(1))
    print(PAB.conditionOnVar(1, 'b1'))

    pDis = dist.DDist({ True : 0.001, False : 0.999 })

    print(dist.totalProbability(pDis, PTestGivenDis))

    # stochastic state machine
    copyMachine = ssm.StochasticSM(initialStateDistribution, transitionModel, observationModel)

    #print(copyMachine.transduce('copy' * 5))