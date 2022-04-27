"""
Feedback combinator, in which the output of a
machine is fed back to be the input of the same machine
at the next step.
It should consist of a cascade composition with a Delay state machine
"""

import sm

class Cascade(sm.SM):
    """
    Casecade combinator class
    """

    def __init__(self, sm1, sm2):
        self.m1 = sm1
        self.m2 = sm2
        self.startState = (sm1.startState, sm2.startState)

    def getNextValue(self, state, inp):
        (s1, s2) = state
        (newS1, o1) = self.m1.getNextValue(s1, inp)
        (newS2, o2) = self.m2.getNextValue(s2, o1)
        
        return ((newS1, newS2), o2)

class DelaySM(sm.SM):
    """
    Delay state machine.
    The output is the input in previous step.
    o[t] = i[t -1]
    """

    def getNextValue(self, state, inp):
        
        return (inp, state)

class Feedback(sm.SM):
    
    def __init__(self, sm):
        self.m = sm
        self.startState = self.m.startState
    
    def getNextValue(self, state, inp):
        (ignore, o) = self.m.getNextValue(state, 'undefined')
        (newS, ignore) = self.m.getNextValue(state, o)

        return (newS, o)