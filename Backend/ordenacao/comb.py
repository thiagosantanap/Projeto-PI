# -*- coding: utf-8 -*-
"""
Created on Sat May  4 16:17:50 2019

@author: CASA
"""
def getNextGap(separacao): 
    separacao = (separacao * 10)/13
    if separacao < 1: 
        return 1
    return int(separacao)

def combSort(lista): 
    tamanho = len(lista) 
    separacao = tamanho 
    swapped = True
    while separacao !=1 or swapped == 1: 
        separacao = getNextGap(separacao) 
        swapped = False
        for indice in range(0, tamanho - separacao): 
            if lista[indice] > lista[indice + separacao]: 
                lista[indice], lista[indice + separacao] = lista[indice + separacao], lista[indice] 
                swapped = True

