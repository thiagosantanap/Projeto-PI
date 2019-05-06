# -*- coding: utf-8 -*-
"""
Created on Sun May  5 20:09:28 2019

@author: CASA
"""

import pandas as pd

filmes = pd.read_csv("movies.csv")
notas = pd.read_csv("ratings.csv")

filmes.columns = ["Id_Filme", "Titulo", "Generos"]
notas.columns = ["Id_Usuario", "Id_Filme", "Nota", "Data"]

# Primeira tentativa de recomendação - Como eu posso recomendar algo?

# A primeiro momento vamos levar em consideração que não sabemos nada sobre os usuários, mas sabemos sobre os filmes.

# Agrupando todas as notas por filme e contando a frequência do filme

frequencia_filme = notas['Id_Filme'].value_counts()

print(frequencia_filme)

# Setando o índice de filmes para Id_Filme

filmes = filmes.set_index("Id_Filme")

# Localizando filme pelo índice

localizando = filmes.loc[318]

print()
print(localizando)

# Exibindo total de votos dos filmes

print()
print(frequencia_filme.head())

# Filmes e Total de Votos estão indexados pelo mesmo ID, então vamos adicionar uma coluna a Filmes

# Faremos uma inclusão de coluna

# Quando fazemos isso o pandas tenta juntar da Série e do DataFrame o índice

filmes['total_de_votos'] = frequencia_filme


recomendacao = filmes.sort_values('total_de_votos', ascending = False)

print()
print(recomendacao)



































































