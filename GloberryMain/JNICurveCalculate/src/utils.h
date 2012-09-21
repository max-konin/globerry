/* 
 * File:   utils.h
 * Author: signal
 *
 * Created on September 7, 2012, 7:09 PM
 */

#ifndef UTILS_H
#define	UTILS_H

#ifdef	__cplusplus
extern "C" {
#endif

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <stdbool.h>

typedef struct SElem Elem;
typedef struct SList List;
typedef struct SStack Stack;
typedef struct SCity City;
typedef struct SPoint Point;
typedef struct SCityArray CityArray;
typedef struct SListCurves ListCurves;
typedef struct SInit Init;

typedef struct SInit {
	void (*initCityArray)(CityArray* this, City* cities, int size);
	void (*initPoint)(Point* this, float lng, float lat);
	void (*initCity)(City* this, float lng, float lat, float w);
	void (*initStack)(Stack* this, List* list);
	void (*initList)(List* this);
} Init;

typedef struct SElem {
	void* data;
	Elem* next;
	Elem* prev;
	Elem* (*delete)(Elem* this, List* from);
} Elem;
	
typedef struct SList {
	Elem* start;
	Elem* end;
	const int size;
	void (*append)(List* this, void* elemData);
	void (*prepend)(List* this, void* elemData);
	void (*insert)(List* this, void* elemData, int after);
	Elem* (*get)(List* this, int which);
	Elem* (*delete)(List* this, int which);
	void (*erase)(List* this, Elem* start);
} List;
	
typedef struct SStack {
	List* stack;
	int size;
	void* (*pop)(Stack* this);
	void (*push)(Stack* this, void* data);
} Stack;
	
typedef struct SCity {
    float lng;
    float lat;
    float w;
} City;

typedef struct SPoint {
	float lng;
	float lat;
	Point* next;
} Point;

typedef struct SListCurves {
	Point* curve;
	ListCurves* next;
} ListCurves;

typedef struct SCityArray {
	const int size;
	City* cities;
} CityArray;

Elem* deleteElem(Elem* this, List* from);

void append(List* this, void* elemData);
void prepend(List* this, void* elemData);
void insert(List* this, void* elemData, int after);
Elem* get(List* this, int which);
Elem* delete(List* this, int which);
void erase(List* this, Elem* start);

double distance(Point* L, Point* R);

void* pop(Stack* this);
void push(Stack* this, void* data);
void initStack(Stack* this, List* list);

void initCity(City* this, float lng, float lat, float w);

void initPoint(Point* this, float lng, float lat);

void initList(List* this);

void initCityArray(CityArray* this, City* cities, int size);

bool pointInPolygon(Point* polygon, City* city);

#ifdef	__cplusplus
}
#endif

#endif	/* UTILS_H */

