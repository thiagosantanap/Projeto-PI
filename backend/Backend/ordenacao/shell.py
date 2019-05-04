# -*- coding: utf-8 -*-
"""
Created on Sat May  4 16:18:46 2019

@author: CASA
"""

def shellSort(lista): 
    tamanho = len(lista) 
    tamanho = int(tamanho)
    corte = tamanho/2
    corte = int(corte)
    while corte > 0: 
        for indice in range(corte,tamanho): 
            temp = lista[indice] 
            j = indice 
            while  j >= corte and lista[j-corte] >temp: 
                lista[j] = lista[j-corte] 
                j -= corte 
            lista[j] = temp 
        corte //= 2
        