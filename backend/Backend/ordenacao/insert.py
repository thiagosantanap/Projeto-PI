# -*- coding: utf-8 -*-
"""
Created on Fri May  3 17:33:59 2019

@author: lpmatos
"""

# Insertion Implementation

def insertionSort(lista):
    for indice in range(1, len(lista)):
        elemento = lista[indice]
        indice_elemento = indice
        while indice_elemento > 0 and elemento < lista[indice_elemento - 1]:
            lista[indice_elemento] = lista[indice_elemento - 1]
            indice_elemento -= 1
        lista[indice_elemento] = elemento
        
def insertionSortOther(lista):
	len_lista = len(lista)
	for i in range(1, len_lista):
		chave = lista[i]
		j = i - 1
		while j >= 0 and lista[j] > chave:
			lista[j + 1] = lista[j]
			j -= 1
		lista[j + 1] = chave

