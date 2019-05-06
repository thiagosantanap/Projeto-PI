# -*- coding: utf-8 -*-
"""
Created on Sun May  5 21:38:15 2019

@author: CASA
"""

import pandas as pd

"""
pd.set_option('display.max_columns', 1000)
pd.set_option('display.max_colwidth', 1000)
pd.set_option('display.width', None)
"""

# Funções

def ler(arquivo):
    return pd.read_csv(arquivo)

def setaColunasFilme(dataframe):
    dataframe.columns = ["Id_Filme", "Titulo", "Generos"]
    
def setaColunasNotas(dataframe):
    dataframe.columns = ["Id_Usuario", "Id_Filme", "Nota", "Data"]
    
def popular(dataframe, coluna):
    popular = dataframe[coluna].value_counts()
    return popular

def adicionar(dataframe, coluna, dados):
    dataframe[coluna] = dados
    
def ordena(dataframe, coluna):
    return dataframe.sort_values(coluna, ascending = False)

def mostPopular(dataframe, coluna_1, coluna_2, dados):
    # Adicionando Popular ao Conjunto de dados de Filme
    adicionar(dataframe, coluna_2, dados)
    # Ordenando
    recomendacao = ordena(dataframe, coluna_2)
    return recomendacao
    
# Chamando Funções

# Lendo
filmes = ler('movies.csv')
notas = ler('ratings.csv')

# Setando colunas
setaColunasFilme(filmes)
setaColunasNotas(notas)

# Setando Indice

filmes = filmes.set_index("Id_Filme")

# Mais popular

popular = popular(notas, 'Id_Filme')

recomendacao = mostPopular(filmes, 'Id_Filme', 'Popularidade', popular)

print(recomendacao.head())

# Calculando a média de nota de cada filme

nota_media = notas.groupby("Id_Filme").mean()
nota_media = nota_media["Nota"]
print(nota_media)

# Adicionando a filmes

adicionar(filmes, 'Nota Media', nota_media)

print(filmes)

recomendacao = mostPopular(filmes, 'Id_Filme', 'Popularidade', popular)

print(recomendacao.head(20))

ordenado_pela_nota_media = filmes.sort_values("Nota Media", ascending = False).head(10)

print(ordenado_pela_nota_media)

filtro = filmes.query("Popularidade >= 10").sort_values("Nota Media", ascending = False).head(10)

print(filtro)

filtro_50 = filmes.query("Popularidade > 50").sort_values("Nota Media", ascending = False).head(10)

print(filtro_50)

eu_assiti = [1, 21, 19, 10, 11, 7, 2]

print(filmes.loc[eu_assiti])

print(filmes.query("Generos == 'Adventure|Children|Fantasy'").sort_values("Nota Media", ascending = False).head(10))