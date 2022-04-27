"""
Discrete distribution class
stores its entries in a dictionary, where the elements of the
sample space are the keys and their probabilities are the values.
"""

import random


class DDist:

    def __init__(self, dictionary) -> None:
        self.d = dictionary
    
    """
    takes as an argument an element of the domain of this distribution
    and returns the probability associated with it.
    If the element is not present in the dictionary, returns 0.
    """
    def prob(self, elt):
        if elt in self.d:

            return self.d[elt]
        else:

            return 0
    
    """
    the support of the distribution, which is a list of elements that have
    non-zero probability. Just in case there are some zero-probability
    elements stored explicitly in the dictionary, we filter to be sure they
    do not get returned
    """
    def support(self):

        return [k for k in self.d.keys() if self.prob(k) > 0]

    """
    returns an element from the sample space of the
    distribution, selectd at random according to the
    specified distribution
    """
    def draw(self):
        r = random.random()
        sum = 0.0
        for val in self.support():
            sum += self.prob(val)
            if r < sum:

                return val
    
    def __str__(self) -> str:
        s = 'DDist('
        for i in self.d.keys():
            e = str(i) + ': ' + str(self.d[i]) + ', '
            s += e
        s = s[: -2] + ')'

        return s

d = DDist({(0, 0) : 0.5, (0, 1) : 0.2, (1, 0) : 0.1, (1, 1) : 0.2}) # representation of joint distribution

"""
Representation of conditional distribution by DDist
Representation of efficacy
Pr(T|D) = {positive : 0.99, negative : 0.01} if D = disease
          {positive : 0.001, negative : 0.999} if D = no disease
"""
def TgivenD(D):
    if D == 'disease':

        return DDist({'positive' : 0.99, 'negative' : 0.01})
    elif D == 'nodisease':

        return DDist({'positive' : 0.001, 'negative' : 0.999})
    else:
        raise Exception('invalid value for D')

# To find a value for Pr(T = negative | D = disease)
# >>> TgivenD('disease').prob('negative')

def totalProbability(dis, genCondition):
    m = dict()
    for k1 in dis.support():
        for k2 in dis.support():
            if k1 in m:
                m[k1] += dis.prob(k2) * genCondition(k2).prob(k1)
            else:
                m[k1] = dis.prob(k2) * genCondition(k2).prob(k1)
    
    return DDist(m)