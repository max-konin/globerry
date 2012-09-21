
#include "utils.h"
#define PI 3.1415926f

void append(List* this, void* elemData) {
	if(this != 0) {
		Elem* nextEl = (Elem*)malloc(sizeof(Elem));
		nextEl->data = elemData;
		nextEl->next = 0;
		nextEl->prev = this->end;
		if(this->size == 0) {
			this->start = nextEl;
			this->end = nextEl;
		} else {
			this->end->next = nextEl;
			this->end = nextEl;
		}
		(this->size)++;
	} else {
		printf("Error appending!\n");
	}
}

void prepend(List* this, void* elemData) {
	if(this != 0) {
		Elem* prevEl = (Elem*)malloc(sizeof(Elem));
		prevEl->data = elemData;
		prevEl->next = this->start;
		prevEl->prev = 0;
		if(this->size == 0) {
			this->start = prevEl;
			this->end = prevEl;
		} else {
			this->start = prevEl;
		}
		(this->size)++;
	} else {
		printf("Error prepending!\n");
	}
}

void insert(List* this, void* elemData, int after) {
	int i;
	if(this != 0 && after > 0) {
		Elem* newEl = (Elem*)malloc(sizeof(Elem)), *tElem = this->start;
		newEl->data = elemData;	
		if(this->size < after) {
			this->append(this, elemData);
		} else {
			for(i = 0; i < after - 1; i++) {
				tElem = tElem->next;
			}
			newEl->next = tElem->next;
			newEl->prev = tElem;
			tElem->next = newEl;
			(this->size)++;
		}
	} else {
		printf("Error prepending!\n");
	}
}

Elem* get(List* this, int which) {
	if(this == 0 || which <= 0) {
		printf("Error!\n");
		return 0;
	}		
	if(this->size == 0) {
		printf("Don't have element!\n");
		return 0;
	}
	if(which >= this->size)
		return this->end;
	Elem* tElem = this->start;
	int i = 0;
	for(i = 0; i < which - 1; i++) {
		tElem = tElem->next;
	}
	return tElem;
}

Elem* delete(List* this, int which) {
	if(this == 0 || which <= 0) {
		printf("Error!\n");
		return 0;
	}		
	if(this->size == 0) {
		printf("Don't have element!\n");
		return 0;
	}
	Elem* tElem = this->start, *retElem;
	int i;
	if(this->size == 1) {
		retElem = this->start;
		this->start = 0;
		this->end = 0;
	} else if(which > this->size) {
		tElem = this->end->prev;
		retElem = this->end;
		tElem->next = 0;
		this->end = tElem;
	} else if(which > 1) {
		for(i = 0; i < which - 2; i++) {
			tElem = tElem->next;
		}
		retElem = tElem->next;		
		tElem->next = tElem->next->next;
		tElem->next->next->prev = tElem;
		//Маразм, использоватся эта функция не будет. Вроде.
	} else {
		retElem = this->start;
		this->start = this->start->next;
		this->start->prev = 0;
	}
	(this->size)--;
	retElem->next = 0;
	retElem->prev = 0;
	return retElem;
}

void erase(List* this, Elem* start) {
	if(start->next != 0) {
		this->erase(this, start->next);
	}
	free(start->data);
	free(start);
}

// Плохо если this не лежит в from
Elem* deleteElem(Elem* this, List* from) {
	if(this->next != 0 && this->prev != 0) {
		this->prev->next = this->next;
		this->next->prev = this->prev;
		this->next = 0;
		this->prev = 0;
		(from->size)++;
		return this;
	}	
	if(this->next == 0)
		return from->delete(from, (int)from->size);
	if(this->prev == 0)
		return from->delete(from, 1);
}


inline double distance(Point* L, Point* R) {
	int Rad = 6371210;
	double d2r = 3.1415926f / 180;
	double dLat = (L->lat - R->lat) * d2r;
	double dLng = (L->lng - R->lng) * d2r;
	double latL = L->lat * d2r;
	double latR = R->lat * d2r;
	double sinL = sin(dLat / 2);
	double sinR = sin(dLng / 2);
	double a = sinL * sinL + sinR * sinR * cos(latL) * cos(latR);
	
	return Rad * 2 * atan2(sqrt(a), sqrt(1-a));
}



void* pop(Stack* this) {
	void* ret = this->stack->delete((List*)this->stack, 1)->data;
	this->size = this->stack->size;
	return ret;
}

void push(Stack* this, void* data) {
	this->stack->append((List*)this->stack, data);
	this->size = this->stack->size;	
}

void initStack(Stack* this, List* list) {
	this->pop = pop;
	this->push = push;	
	if(list == NULL) {
		this->stack = (List*)malloc(sizeof(List));
		this->size = 0;
	} else {
		this->stack = list;
		this->size = list->size;
	}
}

void initList(List* this) {
	this->start = 0;
	this->end = 0;
	this->append = append;
	this->delete = delete;
	this->erase = erase;
	this->get = get;
	this->insert = insert;
	this->prepend = prepend;
	this->size = 0;	
}

void initPoint(Point* this, float lng, float lat) {
	this->lat = lat;
	this->lng = lng;
	this->next = 0;
}

void initCity(City* this, float lng, float lat, float w) {
	this->lat = lat;
	this->lng = lng;
	this->w = w;	
	
}

void initCityArray(CityArray* this, City* cities, int size) {
	this->cities = cities;
	this->size = size;	
}

double rate(float x, float y) {
	return sqrt(x*x + y*y);
}
int sign(float exp) {
	if(exp > 0)
		return 1;
	return -1;
}

bool pointInPolygon(Point* polygon, City* city) {
	Point* tPoint = polygon;
	float influence = 0.5f;
	float x0 = city->lng, y0 = city->lat, x1, x2, y1, y2, angle = 0, cos;
	do {
		x1 = tPoint->lng;
		y1 = tPoint->lat;
		x2 = tPoint->next->lng;
		y2 = tPoint->next->lat;
		cos = ((x1 - x0)*(x2 - x0) + (y1 - y0)*(y2 - y0))/(rate(x1 - x0, y1 - y0)*rate(x2 - x0, y2 - y0));
		if(cos > 1) {
			cos = 1;
		}
		angle += sign((x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0)) * acos(cos) * 180 / PI;
		tPoint = tPoint->next;
	} while(tPoint != polygon);
	angle = abs(angle);
	if(abs(angle - 360) < influence * 180 / PI)
		return true;
	return false;
}