"""
Modeling distribution
"""

from dist import *

# Primitives
"""
Delta, which all of the probability mass on
"""
def deltaDist(v):

    return DDist(v)

"""
Uniform distribution.
"""
def uniformDist(elts):
    p = 1.0 / len(elts)

    return DDist(dict([(e, p) for e in elts]))