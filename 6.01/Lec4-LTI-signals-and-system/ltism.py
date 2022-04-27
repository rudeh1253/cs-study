from ctypes import util
import sm

class LITSM(sm.SM):
    """
    Representaion of general form of LTI systems
    y[n] = c_0 * y[n - 1] + c_1 * y[y - 2] + ... + c_k-1 * y[n - k]
         + d_0 * x[n] + d_1 * x[n - 1] + ... + d_j * x[n - j]
    The state of this system consists of the k previous output values and j previous input values
    """

    def __init__(self, dCoeffs, cCoeffs):
        j = len(dCoeffs) - 1
        k = len(cCoeffs)

        self.cCoeffs = cCoeffs
        self.dCoeffs = dCoeffs
        self.startState = ([0.0] * j, [0.0] * k)
    
    def getNextValue(self, state, input):
        (inputs, outputs) = state
        inputs = [input] + inputs

        currentOutput = util.dotProd(outputs, self.cCoeffs) + \
            util.dotProd(inputs, self.dCoeffs)
        
        return ((inputs[ : -1], ([currentOutput] + outputs)[: -1]), currentOutput)

