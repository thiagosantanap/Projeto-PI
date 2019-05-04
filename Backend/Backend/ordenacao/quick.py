# -*- coding: utf-8 -*-
"""
Created on Fri May  3 17:43:37 2019

@author: lpmatos
"""

def particiona(lista, inicio, fim):
	pivo = inicio
	for indice in range(inicio + 1, fim + 1):
		if lista[indice] <= lista[inicio]:
			pivo += 1
			lista[indice], lista[pivo] = lista[pivo], lista[indice]
	lista[pivo], lista[inicio] = lista[inicio], lista[pivo]
	return pivo

def quickSort(lista, inicio, fim):
	if fim > inicio:
		pivo = particiona(lista, inicio, fim)
		quickSort(lista, inicio, pivo - 1)
		quickSort(lista, pivo + 1, fim)
