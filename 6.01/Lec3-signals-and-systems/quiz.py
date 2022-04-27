import sm

class VendingMachine(sm.SM):

    def __init__(self):
        self.startState = 0
        self.condition = lambda x: x >= 75

    def getNextValue(self, state, inp):
        if inp == 'dispense':
            if self.condition(state):
                
                return (0, (state - 75, True))
            else:

                return (state, (0, False))
        elif inp == 'quarter':

            return (state + 25, (0, False))
        else:

            return (0, (state, False))

def test(inp):

    return VendingMachine().transduce(inp)

print(test(['dispense', 'quarter', 'quarter', 'quarter', 'quarter',
'dispense', 'quarter', 'cancel', 'dispense']))