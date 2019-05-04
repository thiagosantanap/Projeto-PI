# -*- coding: utf-8 -*-
"""
Created on Sat May  4 13:44:40 2019

@author: CASA
"""

def gnomeSort(lista):
  pivot = 0
  lista_length = len(lista) 
  while pivot < lista_length - 1:
    if lista[pivot] > lista[pivot + 1]:
      lista[pivot + 1], lista[pivot] = lista[pivot], lista[pivot + 1]
      if pivot > 0:
        pivot -= 2
    pivot += 1
    