"""
Parallel composition class
"""
import sm

class Parallel(sm.SM):
    """
    Parallel class which built-in machines takes same input.
    """
    
    def __init__(self, sm1, sm2) -> None:
        self.m1 = sm1
        self.m2 = sm2
        self.startState = (sm1.startState, sm2.startState)
    
    """
    Two state machine in parallel composition takes the same input.
    """
    def getNextValue(self, state, inp):
        (s1, s2) = state
        (newS1, o1) = self.m1.getNextValue(s1, inp)
        (newS2, o2) = self.m2.getNextValue(s2, inp)

        return ((newS1, newS2), (o1, o2))

class Parallel2(sm.SM):
    """
    Parallel composition class which built-in machines takes different input respectively
    """

    def __init__(self, sm1, sm2):
        self.m1 = sm1
        self.m2 = sm2
        self.startState(sm1.startState, sm2.startState)

    def getNextValue(self, state, inp):
        (s1, s2) = state
        (i1, i2) = self.splitInp(inp)
        (newS1, o1) = self.m1.getNextValue(s1, i1)
        (newS2, o2) = self.m2.getNextValue(s2, i2)

        return ((newS1, newS2), (o1, o2))

    def splitInp(self, inp):
        if inp == 'undefined':

            return ('undefined', 'undefined')
        else:

            return inp

class ParallelAdd(Parallel):
    """
    The ParallelAdd state machine combination is just like Parallel, except that it has a
    single output whose value is the sum of the outputs of the constituent machine.
    """

    def getNextValue(self, state, inp):
        (s1, s2) = state
        (newS1, o1) = self.m1.getNextValue(s1, inp)
        (newS2, o2) = self.m2.getNextValue(s2, inp)

        return ((newS1, newS2), o1 + o2)