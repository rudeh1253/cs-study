"""
Search tree module
"""

class SearchNode:

    """
    Represented by
    the state of the node
    the action that was taken to arrive at the node
    the search node from which this node can be reached(parent node)
    """
    def __init__(self, action, state, parent):
        self.state = state
        self.action = action
        self.parent = parent
    
    """
    return a list of pairs (a, s) corresponding to the path from root of the tree to this node.
    """
    def path(self):
        if self.parent == None:

            return [(self.action), (self.state)]
        else:

            return self.parent.path() + [(self.action, self.state)]
    
    """
    Takes a state and returns True if the state occurs anywhere
    in the path from the root to the node.
    """
    def inPath(self, s):
        if s == self.state:

            return True
        elif self.parent == None:

            return False
        else:

            return self.parent.inPath(s)