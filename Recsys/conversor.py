# -*- coding: utf-8 -*-
"""
Created on Sun May  5 22:17:20 2019

@author: CASA
"""

import csv
import json

csvfile = open('ratings.csv', 'r')
jsonfile = open('ratings.json', 'w')

fieldnames = ("userId","movieId","rating","timestamp")
reader = csv.DictReader(csvfile, fieldnames)
for row in reader:
    json.dump(row, jsonfile)
    jsonfile.write(',\n')