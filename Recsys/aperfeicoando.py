# -*- coding: utf-8 -*-
"""
Created on Mon May  6 00:38:28 2019

@author: CASA
"""

import pandas as pd

filmes = pd.read_csv("movies.csv")
filmes.columns = ["filmeId", "titulo", "generos"]
filmes = filmes.set_index("filmeId")
print(filmes.head())
notas = pd.read_csv("ratings.csv")
notas.columns = ["usuarioId", "filmeId", "nota", "momento"]
print(notas.head())