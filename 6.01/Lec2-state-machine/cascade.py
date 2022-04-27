import sm

class Cascade(sm.SM):

    def __init__(self, sm1, sm2):
        self.sm1 = sm1
        self.sm2 = sm2
        self.startState = (sm1.startState, sm2.startState)

    def getNextValue(self, state, inp):
        (s1, s2) = state
        (newS1, o1) = self.sm1.getNextValue(s1, inp)
        (newS2, o2) = self.sm2.getNextValue(s2, o1)

        return ((newS1, newS2), o2)
