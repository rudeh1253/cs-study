import sm

class Diff(sm.SM):
    """
    Representaion of
    Y = X - RX
    """

    def __init__(self, previousInput):
        self.startState = previousInput
    
    def getNextValue(self, state, inp):

        return (inp, inp - state)

def test(inp):
    print(Diff(0).transduce(inp))

test([1, 0, 0, 0])