import multiprocessing

from client import *
from threading import Thread
from multiprocessing import Process

def main():

    pool = multiprocessing.Pool()
    pool.map(client_thread(), range(0, 3))

    #for x in range(100):
    #    print(x)
    #    process = Process(target=client_thread())
    #    process.start()


def client_thread():
    client = Client(1000, 1000)
    client.start(0)

if __name__ == '__main__':
    main()
