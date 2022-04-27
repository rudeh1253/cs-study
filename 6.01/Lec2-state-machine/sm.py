"""
Base class of state machine.
This is an abstract class, it may not be instantiated,
every kind of state machine must inherit this class.
"""
class SM:
    startState = 0

    def __init__(self, startState):
        self.startState = startState

    def start(self):
        """
        Set start state
        """
        self.state = self.startState
    
    def step(self, inp):
        """
        transition.
        Setting next state and returning output of the step.
        """
        (s, o) = self.getNextValue(self.state, inp)
        self.state = s

        return o
    
    def transduce(self, inputs):
        """
        Generates outputs of several steps at once.
        """
        self.start()

        return [self.step(inp) for inp in inputs]