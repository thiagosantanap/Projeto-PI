# -*- coding: utf-8 -*-
"""
Created on Sun May  5 23:14:21 2019

@author: CASA
"""

import pandas as pd

filmes = pd.read_csv("movies.csv")
notas = pd.read_csv("ratings.csv")

filmes.columns = ["Id_Filme", "Titulo", "Generos"]
notas.columns = ["Id_Usuario", "Id_Filme", "Nota", "Data"]

filmes = filmes.set_index("Id_Filme")

eu_assiti = [1, 21, 19, 10, 11, 7, 2]

#print(filmes.loc[eu_assiti])

#print(filmes.query("Generos == 'Adventure|Children|Fantasy'"))

frequencia_filme = notas['Id_Filme'].value_counts()
filmes['Popularidade'] = frequencia_filme

#print(filmes)

nota_media = notas.groupby("Id_Filme").mean()
nota_media = nota_media["Nota"]
#print(nota_media)
filmes["Nota Média"] = nota_media

filtro_50 = filmes.query("Popularidade > 50").sort_values("Nota Média", ascending = False).head(10)

print(filtro_50)

print(filmes.query("Generos == 'Adventure|Children|Fantasy'").sort_values("Nota Média", ascending = False).head(10))

aventura = filtro_50.query("Generos == 'Adventure|Children|Fantasy'")
print(aventura.drop(eu_assiti, errors='ignore').sort_values("Nota Média", ascending = False).head(10))