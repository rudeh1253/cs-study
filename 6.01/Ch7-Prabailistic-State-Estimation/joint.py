"""
Constructing a joint distribution
"""

import dist

# Pr(A)
PA = dist.DDist({'a1' : 0.9, 'a2' : 0.1})

"""
PBgA creates Pr(B | A)
"""
def PBgA(a):
    if a == 'a1':

        return dist.DDist({'b1' : 0.7, 'b2' : 0.3}) # Pr(B | A = a1)
    else:

        return dist.DDist({'b1' : 0.2, 'b2' : 0.8}) # Pr(B | A = a2)

"""
Joint distribution
"""
class JDist(dist.DDist):
    
    def __init__(self, pa, pbgaM) -> None:
        self.d = dict()
        
        for i in pa.support():
            pbga = pbgaM(i)
            for j in pbga.support():
                self.d[(i, j)] = pa.prob(i) * pbga.prob(j)
    
    def __str__(self) -> str:
        s = 'DDist('
        for i in self.d.keys():
            e = str(i) + ' : ' + str(round(self.d[i], 6)) + ', '
            s += e
        s = s[:-2] + ')'

        return s
    
    def marginalizeOut(self, idx):
        m = dict()
        for k in self.d.keys():
            a = removeElt(k, idx)
            incrDictEntry(m, a, self.d[k])
        
        return dist.DDist(m)
    
    def conditionOnVar(self, idx, val):
        m = dict()
        mpr = 0.0
        for k in self.d.keys():
            if val in k:
                mpr += self.d[k]
        for k in self.d.keys():
            if val in k:
                a = removeElt(k, idx)
                m[a] = self.d[k] / mpr
        
        return dist.DDist(m)


"""
utilitiy procedure for marginalization
"""
def removeElt(items, i):
    result = items[:i] + items[i + 1:]
    if len(result) == 1:

        return result[0]
    else:

        return result

"""
utilitiy procedure for marginalization
"""
def incrDictEntry(d, k, v):
    if k in d:
        d[k] += v
    else:
        d[k] = v