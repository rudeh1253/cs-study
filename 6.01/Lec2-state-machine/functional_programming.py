def selfComposition(someFunction):
    def returnFunction(*args):

        return someFunction(someFunction(*args))

    return returnFunction
    
f = selfComposition(lambda x: x * x)
print(f(2))

demoList = [1, 2, 3, 4, 5]
map(lambda x: x * 2, demoList)

listComprehension = [(x, y) for x in demoList for y in demoList]