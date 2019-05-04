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

def bubbleSortImplementation(lista):
    tamanho = len(lista)
    for indice in range(tamanho):
        for prox in range(0, tamanho - indice - 1):
            if lista[prox] > lista[prox + 1] :
                lista[prox], lista[prox + 1] = lista[prox + 1], lista[prox]
                
def bubbleSortRecursivo(lista): 
    for indice, num in enumerate(lista): 
        try: 
            if lista[indice + 1] < num: 
                lista[indice] = lista[indice + 1] 
                lista[indice + 1] = num 
                bubbleSortRecursivo(lista) 
        except IndexError: 
            pass
    return lista

lista = [202,1020,9999,192,92,3928,10000,32,3930,29390,3299,9,0,2,3,34,4,1,3232,9999,3]
print(bubbleSortRecursivo(lista))