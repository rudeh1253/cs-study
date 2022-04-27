class Hammock:

    def __init__(self) -> None:
        # start state
        self.count = 0
        self.lastRequset = ''

    def sitDown(self, person):
        if self.count == 0 or person == self.lastRequset:
            print("Welcome!")
            self.count += 1
        else:
            print('Sorry, no room.')
        self.lastRequset = person


    def leave(self):
        self.count -= 1
        print(self.count)