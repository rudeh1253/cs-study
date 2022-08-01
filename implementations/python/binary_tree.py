class BinaryTree:
    
    def __init__(self, val):
        self.parent = None
        self.left = None
        self.right = None
        self.val = val

    def get_left(self):
        return self.left

    def get_right(self):
        return self.right

    def get_parent(self):
        return self.parent

    def get_val(self):
        return self.val

    def set_left(self, val):
        self.left = BinaryTree(val, self, None, None)

    def set_right(self, val):
        self.right = BinaryTree(val, self, None, None)

    def set_parent(self, val):
        pass 
