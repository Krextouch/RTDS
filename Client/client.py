import random
import time

import requests


class Client:
    base_url = 'http://localhost:8443/api/v1/'

    def __init__(self, max_x, max_y, debug=False):
        response = requests.get(url=self.base_url + 'initClient')

        self.data = {
            'clientId': int(response.text),
            'curPos': [random.randint(0, max_x - 1),
                       random.randint(0, max_y - 1)],
            'destPos': [random.randint(0, max_x - 1),
                        random.randint(0, max_y - 1)]
        }

        if debug:
            print(f'curPos: {self.data["curPos"]}')
            print(f'destPos: {self.data["destPos"]}')

    def start(self, sleep_timer=0):
        while not (self.data['curPos'][0] == self.data['destPos'][0]
                   and self.data['curPos'][1] == self.data['destPos'][1]):
            response = requests.post(url=self.base_url + 'move', json=self.data, headers={})
            self.data['curPos'] = response.json()
            if sleep_timer:
                time.sleep(sleep_timer)

            #print(self.data['curPos'], self.data['destPos'])
