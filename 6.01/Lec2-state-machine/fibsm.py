import cascade
import sm

class FibSM(cascade.Cascade):

    def __init__(self, sm1, sm2):
        super().__init__(sm1, sm2)
    
    def getNextValue(self, state, inp):

        return super().getNextValue(state, inp)

class Accumulator(sm.SM):

    def getNextValue(self, state, inp):

        return (state + inp, state + inp)

class Delay(sm.SM):

    def getNextValue(self, state, inp):

        return (inp, state)

def test(inp):
    