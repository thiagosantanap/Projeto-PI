# -*- coding: utf-8 -*-
"""
Created on Sat May  4 19:36:44 2019

@author: CASA
"""

import random

def particao(a, ini, fim):
    pivo = a[fim-1]
    start = ini
    end = ini
    for i in range(ini,fim):
        if a[i] > pivo:
            end += 1
        else:
            end += 1
            start += 1
            aux = a[start-1]
            a[start-1] = a[i]
            a[i] = aux
    return start-1

def quick(a, ini, fim):
    if ini < fim:
        pp = randparticao(a, ini, fim)
        quick(a, ini, pp)
        quick(a, pp+1,fim)
    

def randparticao(a,ini,fim):
    rand = random.randrange(ini,fim)
    aux = a[fim-1]
    a[fim-1] = a[rand]
    a[rand] = aux
    return particao(a,ini,fim)
