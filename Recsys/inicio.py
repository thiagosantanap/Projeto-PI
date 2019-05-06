# -*- coding: utf-8 -*-
"""
Created on Sun May  5 19:45:20 2019

@author: CASA
"""

# Importando Pandas

import pandas as pd

# Lendo filmes CSV com Pandas

filmes = pd.read_csv("movies.csv")

# Exibindo DataFrame de Filmes

print(filmes)

# Exibindo os 5 primeiros elementos do DataFrame

print(filmes.head())

# Renomeando as colunas do DataFrame (Sobrescrever)

filmes.columns = ["Id_Filme", "Titulo", "Generos"]

print(filmes.head())

# Lendo notas CSV com Pandas

notas = pd.read_csv("ratings.csv")

# Exibindo DataFrame de Notas

print(notas)

# Exibindo os 5 primeiros elementos do DataFrame

print(notas.head())

# Renomeando as colunas do DataFrame (Sobrescrever)

notas.columns = ["Id_Usuario", "Id_Filme", "Nota", "Data"]

print(notas.head())

# Describe DataFrame - Breve resumo das colunas ou coluna (se especificado) de um DataFrame

print(notas["Nota"].describe())
print(notas.describe())