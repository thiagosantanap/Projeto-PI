# -*- coding: utf-8 -*-
"""
Created on Fri May  3 17:34:46 2019

@author: lpmatos
"""

# Selection Implementation

def selectionSort(lista):
    for indice in range(0, len(lista), 1):
        indiceMenor = indice
        for prox_indice in range(indice + 1, len(lista), 1):
            if(lista[prox_indice] < lista[indiceMenor]):
                indiceMenor = prox_indice
        lista[indice], lista[indiceMenor] = lista[indiceMenor], lista[indice]
        
def selectionSortOther(lista):
	len_lista = len(lista)
	for i in range(len_lista):
		menor = i
		for j in range(i + 1, len_lista):
			if lista[j] < lista[menor]:
				menor = j
		lista[menor], lista[i] = lista[i], lista[menor]
