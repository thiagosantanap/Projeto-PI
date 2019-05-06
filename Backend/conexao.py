# -*- coding: utf-8 -*-
"""
Created on Fri May  3 16:56:05 2019

@author: lpmatos

import pyrebase

config = {
  "apiKey": "apiKey",
  "authDomain": "gumplus-9d995.firebaseapp.com",
  "databaseURL": "https://gumplus-9d995.firebaseio.com",
  "storageBucket": "gumplus-9d995.appspot.com"
}

firebase = pyrebase.initialize_app(config)

"""

from firebase import firebase
import json
from ordenacao import merge, bubble, insert, selection, quick, gnome, heap, comb, shell, otherquick
import timeit

firebase = firebase.FirebaseApplication('https://gumplus-9d995.firebaseio.com', None)
result = firebase.get('.', None)
parsed_json = json.dumps(result, sort_keys=False, indent=4)

lista = [elemento for elemento in result.items() if elemento[0] == 'Profissional']

lista = lista[0][1]

filtro = []

print()

for profissional in range(len(lista)):
    pegando = lista[profissional]
    if('Nota' not in pegando):
        continue
    endereco = pegando['Endereco']
    nome = pegando['Nome']
    nota = pegando['Nota']
    filtro.append([nome, nota, endereco])
    
for elemento in range(len(filtro)):
    pegando = filtro[elemento]
    print(pegando)
    
filtro_1 = filtro.copy()
filtro_2 = filtro.copy()
filtro_3 = filtro.copy()
filtro_4 = filtro.copy()
filtro_5 = filtro.copy()
filtro_6 = filtro.copy()
filtro_7 = filtro.copy()
filtro_8 = filtro.copy()
filtro_9 = filtro.copy()
filtro_10 = filtro.copy()

print()

def arredondar(num):
    return float('%.20g'%( num))

lista_tempo = []

inicio = timeit.default_timer()
ordenado_1 = bubble.bubbleSort(filtro_1)
fim = timeit.default_timer()
duracao = fim - inicio
lista_tempo.append(arredondar(duracao))
print ('Duracao Bubble: %f' % (duracao))
inicio = timeit.default_timer()
ordenado_2 = merge.mergeSort(filtro_2)
fim = timeit.default_timer()
duracao = fim - inicio
lista_tempo.append(arredondar(duracao))
print ('Duracao Merge: %f' % (duracao))
inicio = timeit.default_timer()
ordenado_3 = selection.selectionSort(filtro_3)
fim = timeit.default_timer()
duracao = fim - inicio
lista_tempo.append(arredondar(duracao))
print ('Duracao Selection: %f' % (duracao))
inicio = timeit.default_timer()
ordenado_4 = quick.quickSort(filtro_4, 0, len(filtro_4) - 1)
fim = timeit.default_timer()
duracao = fim - inicio
lista_tempo.append(arredondar(duracao))
print ('Duracao Quick: %f' % (duracao))
inicio = timeit.default_timer()
ordenado_5 = insert.insertionSort(filtro_5)
fim = timeit.default_timer()
duracao = fim - inicio
lista_tempo.append(arredondar(duracao))
print ('Duracao Insertion: %f' % (duracao))
inicio = timeit.default_timer()
ordenado_6 = gnome.gnomeSort(filtro_6)
fim = timeit.default_timer()
duracao = fim - inicio
lista_tempo.append(arredondar(duracao))
print ('Duracao Gnome: %f' % (duracao))
inicio = timeit.default_timer()
ordenado_7 = heap.heapSort(filtro_7)
fim = timeit.default_timer()
duracao = fim - inicio
lista_tempo.append(arredondar(duracao))
print ('Duracao Heap: %f' % (duracao))
inicio = timeit.default_timer()
ordenado_8 = comb.combSort(filtro_8)
fim = timeit.default_timer()
duracao = fim - inicio
lista_tempo.append(arredondar(duracao))
print ('Duracao Comb: %f' % (duracao))
inicio = timeit.default_timer()
ordenado_9 = shell.shellSort(filtro_9)
fim = timeit.default_timer()
duracao = fim - inicio
lista_tempo.append(arredondar(duracao))
print ('Duracao Shell: %f' % (duracao))
inicio = timeit.default_timer()
ordenado_10 = otherquick.quick(filtro_10, 0, len(filtro_10))
fim = timeit.default_timer()
duracao = fim - inicio
lista_tempo.append(arredondar(duracao))
print ('Duracao Other: %f' % (duracao))

print()
print('Menor:', min(lista_tempo))
print('Maior:', max(lista_tempo))

lista = [{"Nome":filtro_4[elemento][0], "Nota":filtro_4[elemento][1], "Endereco":filtro_4[elemento][2]} 
            for elemento in range(len(filtro_4))]

print(lista)

dicionario = {"Profissional":lista}


print()
print(len(result))
print()
print(result["Profissional"])
result["Profissional"] = lista
print()
print(len(result["Profissional"]))
print()
print(result["Profissional"])
print()
print(len(result["Profissional"]))

print(result)

parsed_json = json.dumps(result["Profissional"], sort_keys=False, indent=4)

print(parsed_json)

post = firebase.put('.', '/Profissional', result["Profissional"])
print(post)
print()
print(lista_tempo)

algoritmos = [elemento for elemento in range(1, 10)]
print(algoritmos)


from matplotlib import pyplot as plt

nomes = ("Bubble", "Merge", "Selection", "Quick", "Insertion", "Gnome", "Heap", "Comb", "Shell", "Other")

plt.figure(0, figsize=(15, 6))
plt.bar(nomes, lista_tempo, width = 0.7, bottom = 0, linewidth = 2.0, align = 'center')
plt.title('Elementos ordenados')
plt.xlabel('Algoritmos', fontsize = 15)
plt.ylabel('Tempo de Execução', fontsize = 15)
plt.savefig('Figura_2.pdf')    


