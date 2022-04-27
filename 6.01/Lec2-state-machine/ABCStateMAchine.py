class SM:
    """
    Abstract class for various state machine.
    All state machine model inherit this class.
    It doesn't make sense to instantiate this class.
    """
    startState = 0

    def start(self):
        self.state = self.startState
    
    def step(self, input):
        (s, o) = self.getNextValue(input)
        self.state = s

        return o
    
    def transduce(self, inputs):
        self.start()
        
        return [self.step(inp) for inp in inputs]

class ABC(SM):
    """
    Language acceptor.
    """

    def getNextValue(self, input):
        if self.state == 0 and input == 'a':

            return (1, True)
        elif self.state == 1 and input == 'b':

            return (2, True)
        elif self.state == 2 and input == 'c':

            return (0, True)
        else:

            return (3, False)

print(ABC().transduce(['a', 'b', 'c']))