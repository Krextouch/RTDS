import random


class Coordinate:

    def __init__(self, max_x, max_y):
        self.x = random.randint(0, max_x - 1)
        self.y = random.randint(0, max_y - 1)
