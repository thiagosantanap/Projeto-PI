# -*- coding: utf-8 -*-
"""
Created on Sat May  4 15:20:43 2019

@author: CASA
"""
def heapify(lista, tamanho, indice): 
    largest = indice
    left  = 2 * indice + 1
    right  = 2 * indice + 2
    
    if(left < tamanho and lista[indice] < lista[left]): 
        largest = left 
  
    if(right < tamanho and lista[largest] < lista[right]): 
        largest = right 
        
    if(largest != indice): 
        lista[indice], lista[largest] = lista[largest], lista[indice] 
        heapify(lista, tamanho, largest) 
        
def heapSort(lista): 
    tamanho = len(lista) 
  
    for indice in range(tamanho, -1, -1): 
        heapify(lista, tamanho, indice) 
  
    for indice in range(tamanho - 1, 0, -1): 
        lista[indice], lista[0] = lista[0], lista[indice]   
        heapify(lista, indice, 0) 
        
