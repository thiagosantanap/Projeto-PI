# -*- coding: utf-8 -*-
"""
Created on Fri May  3 17:18:28 2019

@author: lpmatos
"""

# Bubble Implementation

def bubbleSort(lista):
    ordenado = False
    while not ordenado:
        ordenado = True
        for indice in range(len(lista) - 1):
            if(lista[indice] > lista[indice + 1]):
                lista[indice], lista[indice + 1] = lista[indice + 1], lista[indice]
                ordenado = False
    return lista

def bubbleSortOther(lista):
	len_lista = len(lista)
	for i in range(len_lista - 1, 0, -1):
		swapped = False
		for j in range(i):
			if lista[j] > lista[j + 1]:
				lista[j], lista[j + 1] = lista[j + 1], lista[j]
				swapped = True
		if not swapped:
			break
