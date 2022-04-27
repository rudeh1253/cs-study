import sm

class Feedback(sm.SM):

    def __init__(self, sm, delaySM):
        self.sm = sm
        self.delay = delaySM
        self.startState = (sm.startState, delaySM.startState)
    
    def getNextValue(self, state, inp):
        (s, ds) = state
        (newS, o) = self.sm.getNextValue(s, ds)
        (newDs, do) = self.delay.getNextValue(ds, o)

        return ((newS, newDs), o)
    
class Delay(sm.SM):

    def getNextValue(self, state, inp):

        return (inp, state)
    
