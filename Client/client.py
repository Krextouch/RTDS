import requests
import json
import random
from Coordinate import *


class Client:
    base_url = 'http://localhost:8443/api/v1/'

    def __init__(self, max_x, max_y):
        response = requests.get(url=self.base_url + 'initClient')
        print(response.text)

        self.cur_pos = [random.randint(0, max_x - 1),
                random.randint(0, max_y - 1)]
        self.dest_pos = [random.randint(0, max_x - 1),
                random.randint(0, max_y - 1)]

        self.data = {
            'clientId': int(response.text),
            'curPos': self.cur_pos,
            'destPos':  self.dest_pos
        }



    def start(self):


        #while self.cur_pos[0] is not self.dest_pos[0] and self.cur_pos[1] is not self.dest_pos[1]:
        response = requests.post(url=self.base_url + 'move', json=self.data, headers={})
        print(response.text)



test = Client(1000, 1000)

test.start()
