import sm

class Swtich(sm.SM):

    def __init__(self, condition, sm1, sm2):
        self.m1 = sm1
        self.m2 = sm2
        self.condition = condition
        self.startState = (self.m1.startState, self.m2.startState)
    
    def getNextValue(self, state, inp):
        (s1, s2) = state
        if self.condition(inp):
            (ns1, o) = self.m1.getNextValue(s1, inp)

            return ((ns1, s2), o)
        else:
            (ns2, o) = self.m2.getNextValue(s2, inp)

            return ((s1, ns2), o)

class Multiplex(Swtich):
    
    def getNextValue(self, state, inp):
        (s1, s2) = state
        (ns1, o1) = self.m1.getNextValue(s1, inp)
        (ns2, o2) = self.m2.getNextValue(s2, inp)

        if self.condition(inp):

            return ((ns1, ns2), o1)
        else:

            return ((ns1, ns2), o2)

class Accumulator(sm.SM):

    def getNextValue(self, state, inp):
        
        return (state + inp, state + inp)

def testByAccumulator(inp):
    m1 = Swtich(lambda inp: inp > 100, Accumulator(0), Accumulator(0))
    m2 = Multiplex(lambda inp: inp > 100, Accumulator(0), Accumulator(0))

    print(m1.transduce(inp))
    print(m2.transduce(inp))

testByAccumulator([2, 3, 4, 200, 300, 400, 1, 2, 3])