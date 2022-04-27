"""
Stochastic state machine
For simplicity, no input.

Initial state distribution: Pr(S_0)
State transition model: Pr(S_n+1 | S_n)
Observation model: Pr(O_n | S_n)
"""

import dist
import sm

class StochasticSM(sm.SM):

    def __init__(self, startDistribution: dist.DDist, transitionModel, observationModel):
        self.startDistribution = startDistribution
        self.transitionModel = transitionModel
        self.observationModel = observationModel

    def startState(self):

        return self.startDistribution.draw()
    
    def getNextValue(self, state, inp):
        
        return (self.transitionModel(state, inp).draw(), self.observationModel(state).draw())