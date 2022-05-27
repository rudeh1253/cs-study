class DoublyLinkedListSeq:
    
    def __init__(self):
        self.head = None
        self.tail = None
    
    def insert_first(self, val):
        new_node = Node(val)
        if (self.head == None):
            self.head = new_node
            self.tail = new_node
        else:
            self.head.set_prev(new_node)
            new_node.set_next(self.head)
            self.head = new_node

    def insert_last(self, val):
        new_node = Node(val)
        if (self.tail == None):
            self.head = new_node
            self.tail = new_node
        else:
            self.tail.set_next(new_node)
            new_node.set_prev(self.tail)
            self.tail = new_node

    def delete_first(self):
        at_first = self.head
        self.head.get_next().set_prev(None)
        self.head = self.head.get_next()
        at_first.set_next(None)
        return_value = at_first.get_value()
        del at_first
        return return_value

    def delete_last(self):
        at_last = self.tail
        self.tail.get_prev().set_next(None)
        self.tail = self.tail.get_prev()
        at_last.set_prev(None)
        return_value = at_last.get_value()
        del at_last
        return return_value

    def __str__(self):
        if self.head == None:
            return ""
        l = []
        cur = self.head
        while cur != self.tail:
            l.append(cur.get_value())
            cur = cur.get_next()
        l.append(self.tail.get_value())
        return l.__str__()

class Node:
    
    def __init__(self, val):
        self._val = val
        self._next = None
        self._prev = None

    def set_next(self, next_node):
        self._next = next_node

    def get_next(self):
        return self._next

    def set_prev(self, prev_node):
        self._prev = prev_node

    def get_prev(self):
        return self._prev

    def get_value(self):
        return self._val
